package com.java.service.impl;

import com.java.mapper.LoginMapper;
import com.java.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private LoginMapper loginMapper;
    @Override
    public boolean login(String username, String pwd){
        return loginMapper.login(username,pwd)>=1;

    }

    @Override
    public List<Map<String, Object>> getAuthorityByUsername(Map<String, Object> paramMap) {
        return loginMapper.getAuthorityByUsername(paramMap);
    }

    @Override
    public int isroot(String username) {
        return loginMapper.flag(username);
    }

}
