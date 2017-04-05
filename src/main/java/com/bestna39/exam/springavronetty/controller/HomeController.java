package com.bestna39.exam.springavronetty.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
/**
 * @author kayson Yang
 * @description
 * @create 2017-03-18 13:23
 */
@Controller
public class HomeController {

    @RequestMapping("/")
    @ResponseBody
    public String home() {
        return "Hello World!";
    }
}