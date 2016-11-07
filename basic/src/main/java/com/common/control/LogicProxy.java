package com.common.control;

import android.util.Log;

import com.common.control.annotation.Implement;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by snower on 16-1-13.
 */
public class LogicProxy {
    private static final LogicProxy m_instance = new LogicProxy();

    public static LogicProxy getInstance() {
        return m_instance;
    }

    private LogicProxy() {
        m_objects = new HashMap<>();
    }

    private Map<Class, Object> m_objects;
    //private Object m_proxy;

    public void init(Class... clss) {
        Log.d("PROXY", "准备初始化Proxy");
        List<Class> list = new LinkedList<Class>();
        for (Class cls : clss) {
            if (cls.isAnnotationPresent(Implement.class)) {
                list.add(cls);
                for (Annotation ann : cls.getDeclaredAnnotations()) {
                    if (ann instanceof Implement) {
                        try {
                            m_objects.put(cls, ((Implement) ann).value().newInstance());
                        } catch (InstantiationException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    public <T> T getProxy(Class cls) {
        Log.d("PROXY", "获取Proxy");
        //return (T) m_proxy;
        return (T) m_objects.get(cls);
    }

    public <T> T getBindViewProxy(Class cls, Object o) {
        Object ret = m_objects.get(cls);
        ((LogicControl) ret).attachView(o);
        return (T) ret;
    }
}
