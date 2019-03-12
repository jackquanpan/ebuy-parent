package com.pojo;

import lombok.Data;

import java.util.Date;

/**
 * date:2019-02-03
 * 11:38
 * description:Banner
 * author:潘全科
 */
@Data
public class Banner {
    private Long id;
    private String img_url;//图片地址
    private String href;//图片链接
    private String remake;//备注
    private int sort;
    private Date updateTime;


}
