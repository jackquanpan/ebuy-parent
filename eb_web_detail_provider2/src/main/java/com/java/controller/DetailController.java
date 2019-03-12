package com.java.controller;

import com.java.service.DatailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * date:2019-02-23
 * 14:39
 * description:DetailController
 * author:潘全科
 */
@Controller
public class DetailController {
    @Autowired
    private DatailService datailService;
    @RequestMapping("getAlldetail.do")
    public @ResponseBody
    List<Map<String, Object>> getAllDetail(){
        return datailService.getALLProductDetail();
    }
}
