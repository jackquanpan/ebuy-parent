package com.java.pojo;

import lombok.Data;

/**
 * date:2019-02-22
 * 12:48
 * description:productdetail
 * author:潘全科
 */
@Data
public class productdetail {
    private Integer id;
    private String title;
    private String subTitle;
    private Float price;
    private String color;
    private String type;
    private Integer num;
    private Integer TypeId;
    private String imgId;
}
