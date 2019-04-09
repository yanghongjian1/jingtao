package com.jingtao.jtmanage.constants;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jingtao.jtmanage.manage.api.module.User;
import com.jingtao.jtmanage.manage.api.utiles.RedisUtil;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Log4j
public class LoginIntercept implements HandlerInterceptor {
    @Autowired
    private RedisUtil redisUtil;

    private ObjectMapper objectMapper = new ObjectMapper();


    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        String jt_ticket = null;

        if (redisUtil == null) {
            BeanFactory factory = WebApplicationContextUtils.getRequiredWebApplicationContext(httpServletRequest.getServletContext());
            redisUtil = (RedisUtil) factory.getBean("redisUtil");
        }

        log.info("开始拦截");
        Cookie[] cookies = httpServletRequest.getCookies();

        for (Cookie cookie : cookies) {

            if ("JT_TICKET".equals(cookie.getName())) {

                jt_ticket = cookie.getValue();
                break;
            }
        }

        //判断ticket是否 不为null;
        if (!StringUtils.isEmpty(jt_ticket)) {

            //从缓存中获取userJSON数据
            String userJSON = redisUtil.get(jt_ticket);

            if (!StringUtils.isEmpty(userJSON)) {
                //表示userJSON数据不为空
                User user =
                        objectMapper.readValue(userJSON, User.class);

				/*如果将user信息存入域对象中,则每个Controller方法中
				必须添加域对象的参数.这样的写法不是特别好,代码复杂
				耦合性高
					解决方案: 使用ThreadLocal实现user对象共享
					本地线程变量作用:在当前线程内实现数据的共享.
				*/
                UserThreadLocal.set(user);
                return true; //表示让程序放行
            }
        }

        //如果程序执行到这里,表示用户数据为空,则跳转到 登陆页面
        httpServletResponse.sendRedirect("/user/login.html");
        //表示用户的请求是否放行.
        //false 必须添加路径的转向,否则程序卡死
        return false;


    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        System.out.println("用户信息删除");
        UserThreadLocal.remove();

    }
}
