package com.pojo;

import lombok.Data;

import java.util.List;

/**
 * date:2019-02-15
 * 19:17
 * description:OneMenu
 * author:潘全科
 */
@Data
public class OneMenu {
    private long oneId;
    private String oneText;
    private List<TwoMenu> twoMenuList;
}
