## 我眼中的 Android 应用架构

完成一些项目之后,发现每个项目中有一部分内容是重复的,这样就可以把重复的一部分提取出
来,根据个人开发经验,如果正确设计一个Android 应用架构.
好的应用架构应该有如下特点:

>   一个好的网络请求框架
>   View 不应该参杂太多业务逻辑
>   重复代码不要太多
>   易于拓展、易于修改

<!-- more -->

#### 为什么要把精力花在架构上
在开始这篇文章之前,讲一下一个好的架构的重要性以及为什么要花费时间和资源去搭建一个好的架构.公司项目中运用的第三方库图片库[Picasso](https://github.com/square/picasso)没有进行二次封装后来在网上看到[Glide和Picasso的对比](http://jcodecraeer.com/a/anzhuokaifa/androidkaifa/2015/0327/2650.html)Glide的性能优于Picasso,显示速度也比Picasso要快.由于没有进行二次封装所以如果替换框架的话会非常麻烦,因此可见一个好的框架和一个好的封装的重要性..
> Picasso.with(context).load(url).into(imageView);

#### API的设计遵循RESTful
- GET：从服务器取出资源（一项或多项）
- POST: 在服务器新建一个资源
- PUT：在服务器更新资源（客户端提供改变后的完整资源）
- PATCH：在服务器更新资源（客户端提供改变的属性）
- DELETE:从服务器删除资源

与服务器沟通好,所有数据都放在Data中返回给客户端,errcode=0代表请求成功,非0代表请求出现问题,可以提示msg
data返回的数据可以是一个对象 也可是一个数组包含多个对象
```
{"errcode": 0, "msg": "success"}
{"errcode": 0, "msg": "success", "data":{...}}
{"errcode": 0, "msg": "success", "data":[{ }]

public class Response<T> {    
    public int errcode;    
    public String errmsg;    
    public T data;    
    public boolean isSuccess() {        
    return errcode == 0;    
}}
```

**HttpControl 使用单例线程锁封装Retrofit**
```
/**
 * author miekoz on 2016/3/17.
 * email  meikoz@126.com
 */
public class HttpControl {

    private static HttpControl mInstance;
    private Retrofit retrofit;

    public static HttpControl getIns(){
        if (mInstance == null){
            synchronized (HttpControl.class){
                if (mInstance == null) mInstance = new HttpControl();
            }
        }
        return mInstance;
    }

    public HttpControl(){

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(7676, TimeUnit.MILLISECONDS)
                .connectTimeout(7676, TimeUnit.MILLISECONDS)
                .addInterceptor(interceptor)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(EasyApplication.getInstance().gson))
                .client(okHttpClient)
                .build();
    }

    public  <T> T createService(Class<T> clz){
        return retrofit.create(clz);
    }
}
```

#### 架构的分层
API的设计完成之后，接下来我就会考虑App项目的整体架构了。整体如何架构，我也曾经做过不少尝试。早期的时候，Android就是将所有操作都放在Activity里完成，包括界面数据处理、业务逻辑处理、调用API。后来发现Activity越来越臃肿，代码越来越复杂，很难维护。于是就开始思考如何拆分，如何才能做到松耦合高内聚。

一个App的核心就是数据，那么，从App对数据处理的角色划分出发，最简单的划分就是：数据管理、数据工、数据展示。
![enter description here][1]

使用Model-View-Presenter(Mvp) 将UI、逻辑和数据隔离开来
> View:View层,就是当前的Activity或Fragment
> Presenter:逻辑层,UI的逻辑和数据的逻辑处理
> Model: 数据层,由于Mvp需要写的接口和实现类太多 所以把Model层 归为JavaBean对象


**LoginLogic 登录接口的定义**
```
/**
 * author meikoz on 2016/4/13.
 * email  meikoz@126.com
 */

@Implement(LoginLogicImpl.class)
public interface LoginLogic extends LogicControl<LoginLogic.LoginView> {

    interface LoginView {
        void onLoginSuccess();

        void onLoginFail();
    }

    void login(String name, String passwrod);
}

```

**LoginLogicImpl 登录逻辑的实现** 
```
/**
 * author meikoz on 2016/4/13.
 * email  meikoz@126.com
 */
public class LoginLogicImpl implements LoginLogic {

    LoginView mView;

    @Override
    public void login(String name, String passwrod) {
        if (name.equals("zhangsan") && passwrod.equals(123)) {
            Log.d("cs", "登录成功,通知Activity");
            mView.onLoginSuccess();
        } else {
            Log.d("cs", "登录失败,通知UI");
            mView.onLoginSuccess();
        }
    }

    @Override
    public void attachView(LoginView mvpView) {
        this.mView = mvpView;
    }

}
```

**LoginActivity 网络请求之后登录页面相应的处理** 
```
/**
 * author meikoz on 2016/4/13.
 * email  meikoz@126.com
 */
public class LoginActivity extends BaseActivity implements LoginLogic.LoginView {

    public static void start(Activity activity) {
        Intent intent = new Intent(activity, LoginActivity.class);
        activity.startActivity(intent);
        activity.finish();
    }
    @Bind(R.id.edit_username) EditText mEditName;
    @Bind(R.id.edit_passwrod) EditText mEditPasswrod;
    LoginLogic mLoginLogic;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_login;
    }

    @Override
    protected void onInitView() {
        mLoginLogic = LogicProxy.getInstance().getBindViewProxy(LoginLogic.class, this);
    }

    @OnClick(R.id.btn_login)
    void login() {
        mLoginLogic.login("zhangsan", "12344");
    }

    @Override
    public void onLoginSuccess() {
        Log.d("cs", "登录成功");
    }

    @Override
    public void onLoginFail() {
        Log.d("cs", "登录失败");
    }
}
```

#### View层的封装
代码结构清晰,保持高质量代码,减少重复代码,每一个页面都要封装一个基础类,进行一些公共方法的处理

- BaseActivity/BaseFragment/  ButterKnife的初始化,getLayoutResource()不需要每Activity在初始化
- RecyclerAdapter/CommonAdapter RecyclerView 和ListView 的封装 减少代码数据 代码整洁
- GlideManager  Glide框架的二次封装,可以把调用都统一起来如过后期切换方便更改.
- LocationManager 支出百度地图定位的初始化和获取经纬度和地址信息
- HttpControl  网络请求的封装 实例化Retrofit 根据不同的业务创建不同的Service
- LogicControl 每个Presenter 需要实现的方法定义
- LogicProxy 利用注解的方式在App一启动时候初始化所有Presenter,Activity调用的时候直接去大池子取.
- ToastTip Toast的封装 只需要专递一个参数 方便调用
- SwipBackActivity 只要继承这个类就可以实现Activity 右滑Finish当前页面

#### 支持好多功能,没有进行一一介绍,大家感兴趣可以看源码
> 启动页面图片加动画
> 自定义页面的统一TitleBar 
> 沉浸式的实现
> Mvp 具体优美的封装



#### 写在最后
至此，关于App架构方面的经验总结就先讲这么多了,如果喜欢请Follow! Start! 评论！你的关注我的动力!!!
架构没有真正的好坏之分,只要适用于自己的业务,就是好的架构！
请关注项目,后期会更新更多的工具方便开发...

**架构项目主页**
[BasicApp](https://github.com/meikoz/BasicApp)
https://github.com/meikoz/BasicApp

**转载请注明出处**
http://meikoz.github.io/2016/04/14/basic_app/#more