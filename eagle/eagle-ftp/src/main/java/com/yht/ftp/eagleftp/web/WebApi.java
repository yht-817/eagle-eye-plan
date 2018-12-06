package com.yht.ftp.eagleftp.web;


import com.yht.ftp.eagleftp.config.FtpOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 测试上传文件
 */
@RestController
public class WebApi {

    @RequestMapping(value = "/ceshi", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String ceshi(@RequestParam("img") MultipartFile img) {
        String dir = "yhr";
        return FtpOperation.uploadSingle(img, dir);
    }


}
