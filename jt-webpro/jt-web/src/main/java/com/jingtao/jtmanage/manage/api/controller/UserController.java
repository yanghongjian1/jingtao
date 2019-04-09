package com.jingtao.jtmanage.manage.api.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jingtao.jtcommon.vo.SysResult;
import com.jingtao.jtmanage.manage.api.module.User;
import com.jingtao.jtmanage.manage.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import redis.clients.jedis.JedisCluster;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JedisCluster jedisCluster;

    //实现页面的通用跳转
    @RequestMapping("/{module}")
    public String module(@PathVariable String module) {

        return module;
    }

    @RequestMapping("/doRegister")
    @ResponseBody
    public SysResult saveUser(User user) {
        try {
            userService.saveUser(user);
            return SysResult.oK();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return SysResult.build(201, "新增用户失败");
    }


    //192.168.159.144:8093/service/user/doLogin?r=0.309153798750589
    @RequestMapping("/doLogin")
    @ResponseBody
    public SysResult login(String username, String password, HttpServletResponse response) {

        try {
            //获取秘钥
            String ticket = userService.findUserByUP(username, password);

            //将数据保存到Cookie中
            Cookie cookie = new Cookie("JT_TICKET", ticket);
            //0:立即删除  -1 关闭会话后删除    数字:生命周期 秒
            cookie.setMaxAge(60 * 60 * 48);//Cookie保存2天
            cookie.setPath("/");//表示所有的用户共享Cookie
            response.addCookie(cookie);

            return SysResult.oK();//数据正确返回
        } catch (Exception e) {
            e.printStackTrace();
        }

        return SysResult.build(201, "用户登陆失败");
    }

    //192.168.159.144:8093/user/logout.html

    /**
     * 1.获取Cookie信息
     * 2.获取ticket信息
     * 3.删除缓存数据
     * 4.删除Cookie
     * 5.重定向到系统首页
     *
     * @return
     */
    @RequestMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {

        //1.获取Cookie数据
        Cookie[] cookies = request.getCookies();
        String ticket = null;
        for (Cookie cookie : cookies) {

            if ("JT_TICKET".equals(cookie.getName())) {

                ticket = cookie.getValue();
                //System.out.println("获取Cookie时间:"+cookie.getMaxAge());
            }
        }

        //2.删除redis数据
        jedisCluster.del(ticket);

        //3.删除Cookie
        Cookie jtCookie = new Cookie("JT_TICKET", "");
        jtCookie.setMaxAge(0);
        jtCookie.setPath("/");
        response.addCookie(jtCookie);

        //4.重定向到系统首页
        //forward:/转发地址
        return "redirect:/index.html";
    }


}
