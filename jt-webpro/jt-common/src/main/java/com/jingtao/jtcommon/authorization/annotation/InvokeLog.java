package com.jingtao.jtcommon.authorization.annotation;

import java.lang.annotation.*;

/**
 * @author zww
 * @email zwwtnt@yeah.net
 * @create 2018-08-30 上午10:24
 **/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface InvokeLog {
    // 描述
    String description() default "";
    // 调用
    String invoke() default "";
}
