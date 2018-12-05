package com.yht.eurekaclient.web;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @GetMapping(value = "/login")
    public String loginUser() throws InterruptedException {
        Thread.sleep(5000L);
        return "cshkdjchsicjisdij";
    }

}
