package application.infrastructure.threads.configurators;

import application.infrastructure.configurators.impl.configurators.ProxyConfigurator;
import application.infrastructure.core.Context;
import application.infrastructure.threads.annotations.Schedule;
import lombok.SneakyThrows;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.concurrent.*;

public class ScheduleConfigurator implements ProxyConfigurator {
    @Override
    @SneakyThrows
    public <T> T makeProxy(T object, Class<T> implemenation, Context context) {
        Method[] methods = object.getClass().getDeclaredMethods();
        for (Method m : methods) {
            m.setAccessible(true);
            if (m.isAnnotationPresent(Schedule.class) && !m.getReturnType().getSimpleName().equalsIgnoreCase("void") &&
                    m.getModifiers() != Modifier.PUBLIC) throw new Exception("Invalid method signature");
            if (m.isAnnotationPresent(Schedule.class) && m.getReturnType().getSimpleName().equalsIgnoreCase("void") &&
                    m.getModifiers() == Modifier.PUBLIC) {
                return (T) Enhancer.create(implemenation, (MethodInterceptor) this::invoke);
            }
        }
        return object;
    }

    @SneakyThrows
    private Object invoke(Object object, Method method, Object[] args, MethodProxy methodProxy) {
        Schedule scheduleSync = method.getAnnotation(Schedule.class);
        if (scheduleSync != null) {
            System.out.println(method);
            Thread thread = new Thread(() -> this.invoker(object, methodProxy, args, scheduleSync.timeout(), scheduleSync.delta()));
            thread.setDaemon(true);
            thread.start();
            return null;
        }
        return methodProxy.invokeSuper(object, args);
    }

    private void invoker(Object object, MethodProxy method, Object[] args, int milliseconds, int delta) {
        Thread thread = new Thread(() -> {
            while (true) {
                Thread invokeThread = new Thread(() -> {
                    ExecutorService executorService = Executors.newSingleThreadExecutor(r -> {
                        Thread fThread = Executors.defaultThreadFactory().newThread(r);
                        fThread.setDaemon(true);
                        return fThread;
                    });
                    try {
                        executorService.submit(() -> {
                            try {
                                return method.invokeSuper(object, args);
                            } catch (Throwable throwable) {
                                throwable.printStackTrace();
                            }
                            return null;
                        }).get(milliseconds, TimeUnit.MILLISECONDS);
                    } catch (Exception exception) {
                        executorService.shutdownNow();
                    }
                    executorService.shutdown();
                });
                invokeThread.setDaemon(true);
                invokeThread.start();
                try {
                    Thread.sleep(delta);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.setDaemon(true);
        thread.start();
    }
}
