package com.pojo;

import lombok.Data;

/**
 * date:2019-01-29
 * 12:45
 * description:WebUser
 * author:潘全科
 */
@Data
public class WebUser {
    private Long id;
    private String username;
    private String pwd;
    private String phone;
    private String email;
    private String smsCode;
}
