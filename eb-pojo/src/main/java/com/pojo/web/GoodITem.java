package com.pojo.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * date:2019-02-26
 * 15:18
 * description:GoodITem
 * author:潘全科
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodITem implements Serializable {
    private Integer productId;
    private Integer count;
}
