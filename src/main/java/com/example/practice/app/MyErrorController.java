package com.example.practice.app;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/error") // エラーページへのマッピング
public class MyErrorController implements ErrorController {

    @Override
    //エラーページのパスを返す
    public String getErrorPath() {
        return "/error";
    }


    @RequestMapping
    public ModelAndView error(HttpServletRequest request, ModelAndView mav) {

        // HTTP ステータスを決める
        // 404 以外は全部 500 にする
        Object statusCode = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        if (statusCode != null && statusCode.toString().equals("404")) {
            status = HttpStatus.NOT_FOUND;
        }

        //出力したい情報をセット
        mav.setStatus(status); //Httpステータス
        mav.setViewName("error"); //ビュー名
        mav.addObject("status",status.value()); //ステータス
        mav.addObject("error",status.getReasonPhrase()); //エラーメッセージ

        return mav;
    }
}

