package com.java.service;

import java.util.List;
import java.util.Map;

public interface LoginService {
    boolean login(String username, String pwd);
    List<Map<String,Object>> getAuthorityByUsername(Map<String,Object> paramMap);
    int isroot(String username);
}
