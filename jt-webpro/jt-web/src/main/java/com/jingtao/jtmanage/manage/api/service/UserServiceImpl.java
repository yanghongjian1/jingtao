package com.jingtao.jtmanage.manage.api.service;

import java.util.HashMap;
import java.util.Map;

import com.jingtao.jtcommon.configurer.HttpClientHelper;
import com.jingtao.jtcommon.vo.SysResult;
import com.jingtao.jtmanage.manage.api.module.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private HttpClientHelper httpClient;

    private static ObjectMapper objectMapper = new ObjectMapper();

    @Value("${sso.url}")
    private String ssoUrl;

    @Override
    public void saveUser(User user) {
        //1.定义uri
        String uri = ssoUrl + "/user/register";

        Map<String, String> params = new HashMap<String, String>();

        params.put("username", user.getUsername());
        params.put("password", user.getPassword());
        params.put("phone", user.getPhone());
        params.put("email", user.getEmail());

        //返回sysResultJSON数据
        String jsonData =
                httpClient.doPost(uri, params);

        SysResult sysResult = null;
        try {
            sysResult = objectMapper.readValue(jsonData, SysResult.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (sysResult.getStatus() != 200) {
            throw new RuntimeException();
        }
    }

    @Override
    public String findUserByUP(String username, String password) {
        //定义uri
        String uri = ssoUrl + "/user/login";
//		String uri = "http://localhost:8094"+"/user/login";
        Map<String, String> map = new HashMap<String, String>();
        map.put("u", username);
        map.put("p", password);
        //返回的是sysResultJSON数据
        String jsonData = httpClient.doPost(uri, map);

        SysResult sysResult = null;

        try {
            sysResult = objectMapper.readValue(jsonData, SysResult.class);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }

        //开始解析参数
        if (sysResult.getStatus() != 200) {

            throw new RuntimeException();
        }
        //返回秘钥
        return (String) sysResult.getData();
    }
}





