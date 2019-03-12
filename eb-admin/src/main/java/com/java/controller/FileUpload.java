package com.java.controller;

import com.yuqing.common.FastDFSClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * date:2019-02-17
 * 18:12
 * description:FileUpload
 * author:潘全科
 */
@Controller
public class FileUpload {
    /**
     * fastDFS上传图片
     * @param uploadFile
     * @return
     * @throws Exception
     */
    @RequestMapping("/fastDFSUpload.do")

    public @ResponseBody Map<String,Object> fastDFSUpload(MultipartFile uploadFile) throws Exception {
        Map<String,Object> resultMap = new HashMap<>();
        try {
            FastDFSClient client = new FastDFSClient("classpath:resources/fdfs_client.conf");
            //获取上传文件的源文件名
            String oldName = uploadFile.getOriginalFilename();
            //获取后缀名
            String extName = oldName.substring(oldName.lastIndexOf(".")+1);
            //开始将文件保存到fastdfs服务器中去，并且返回相对地址  12\\23\\56\\17\\1.jpg
            String basepath = client.uploadFile(uploadFile.getBytes(),extName);
            basepath="http://192.168.25.133/"+basepath;
            resultMap.put("error",0);//status代表的是状态码，0代表成功
            resultMap.put("url",basepath);
            System.out.println("basePath="+basepath);
            return resultMap;
        } catch (Exception e) {
            resultMap.put("error",1);//1代表文件上传失败
            return resultMap;
        }
    }
}
