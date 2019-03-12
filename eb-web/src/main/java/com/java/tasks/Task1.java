package com.java.tasks;

import com.java.service.IndexService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.util.List;
import java.util.Map;

/**
 * date:2019-02-23
 * 13:28
 * description:每隔5分钟扫描数据库找到修改的详情表，并且生成静态html
 * author:潘全科
 */
@Component
public class Task1 {
    @Autowired
    private IndexService  indexService;
    @Autowired
    private Configuration fkConfig;
    @Scheduled(cron = "1 0/5 * * * *")
    public void TeskUpdate() throws Exception{
        List<Map<String, Object>> productDetail = indexService.findUpdateDetail();
        //将数据保存到request域中并且跳转到Product--->Product.html
        //frmake取出数据后生成静态html页面（product.html）
        //1.获取指定模板
        File file= null;

        for (Map<String,Object>tempmap:productDetail) {
            Template template = fkConfig.getTemplate("Product.ftl");
            file = new File("E:\\fremaker\\details\\"+ tempmap.get("id")+".html");
            FileWriter out = new FileWriter(file);
            template.process(tempmap,out);
            out.close();
        }

        System.out.println("生成完毕");
    }
}
