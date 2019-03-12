package com.pojo.web;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * date:2019-02-26
 * 15:21
 * description:BuyCar
 * author:潘全科
 */
@Data
public class BuyCar implements Serializable {
    private List<GoodITem>goodITemList;
}
