package com.java.controller;

import com.java.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * date:2019-02-18
 * 16:56
 * description:BannerController
 * author:潘全科
 */
@Controller
public class BannerController {
    @Autowired
    private BannerService bannerService;
    @RequestMapping("/getWebBanners.do")
    public @ResponseBody
    List<Map<String,Object>>getAllBanners(){
        return bannerService.getAllbanners();
    }

}
