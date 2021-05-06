package com.example.practice;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HelloController {
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index (Model model){
        model.addAttribute("message","HelloWorld.");

        //ログ出力
        LoggingService.info("情報ログです");
        LoggingService.warn("警告ログです");
        return "index";
    }
}
