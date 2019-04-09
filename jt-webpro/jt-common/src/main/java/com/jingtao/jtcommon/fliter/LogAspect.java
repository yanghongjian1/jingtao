package com.jingtao.jtcommon.fliter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jingtao.jtcommon.authorization.annotation.InvokeLog;
import lombok.extern.log4j.Log4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * Created by zhangwangwang on 2017/3/24.
 */
@Log4j
@Aspect
@Component
public class LogAspect {
    /**
     * apiController 参数切点
     */
    @Pointcut("@annotation(com.jingtao.jtcommon.authorization.annotation.InvokeLog)")
    public void invokeRemoteAspect() { }

    @Around("invokeRemoteAspect()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {

        // 调用方法名称
        String methodName = pjp.getSignature().getName();
        // 获取进入的类名
        String className = pjp.getSignature().getDeclaringTypeName();
        className = className.substring(className.lastIndexOf(".") + 1).trim();
        // 调用参数
        Object[] args = pjp.getArgs();
        // 方法描述
        String description = getWebServiceMethodDescription(pjp);

//        Logger logger = LoggerFactory.getLogger(Class.forName(pjp.getSignature().getDeclaringTypeName()));

        // 请求开始
        long startTime = System.currentTimeMillis();
        log.info("===" + description + "开始===");
        log.info("#TimeID-Start: " + startTime);
        log.info("执行方法 : " + className + "."+ methodName);
        JSONObject msg = new JSONObject();
        for (Object temp : args) {
            Class<? extends Object> paramClazz;
            try {
                paramClazz = temp.getClass();
            }catch (NullPointerException e) {
                continue;
            }
            String classType = paramClazz.getName();
            if (classType.equals("java.lang.String")) {
                msg.put("key", temp);
            } else if (classType.equals("java.util.HashMap")) {
                msg.putAll((Map<? extends String, ? extends Object>) temp);
            } else if (classType.startsWith("com.") || classType.startsWith("client.")) {
                try {
                    Field[] f = paramClazz.getDeclaredFields();
                    for (Field field : f) {
                        String fieldName = field.getName();
                        field.setAccessible(true);
                        msg.put(fieldName, field.get(temp));
                    }
                } catch (SecurityException e) {
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                }
            }
        }
        log.info("请求参数 : " + msg.toString());

        Object ret = pjp.proceed();
        // 处理完请求，返回内容
        log.info("响应结果 : " + JSON.toJSONString(ret));
        log.info("请求用时 : " + (System.currentTimeMillis() - startTime));
        log.info("#TimeID-End: " + startTime);
        log.info("===" + description + "结束===");

        return ret;
    }

    /**
     * 获取注解中对方法的描述信息
     *
     * @param joinPoint 切点
     * @return 方法描述
     * @throws Exception
     */
    public  static String getWebServiceMethodDescription(ProceedingJoinPoint joinPoint)  throws Exception {
        String targetName = joinPoint.getTarget().getClass().getName();    //获得执行方法的类名
        String methodName = joinPoint.getSignature().getName();            //获得执行方法的方法名
        Object[] arguments = joinPoint.getArgs();                          //获取切点方法的所有参数类型
        Class targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getMethods();    //获取公共方法，不包括类私有的
        String description = "";
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                Class[] clazzs = method.getParameterTypes();     //对比方法中参数的个数
                if (clazzs.length == arguments.length) {
                    description = method.getAnnotation(InvokeLog.class).description();
                    String invoke = method.getAnnotation(InvokeLog.class).invoke();
                    if(org.apache.commons.lang3.StringUtils.isNotBlank(invoke)){
                        description = description + "(调用远程接口" + invoke + ")";
                    }
                    break;
                }
            }
        }
        return description;
    }
}
