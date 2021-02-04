package com.example.syshology.jpa.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by s.h.kim.
 * User: 김상희
 * Date: 2021-01-27
 * Time: 오후 4:18
 * Project : IntelliJ IDEA
 */
@RestController
public class HomeController {
    @RequestMapping("/")
    public ModelAndView index(ModelAndView modelAndView) {
        modelAndView.setViewName("index");
        return modelAndView;
    }
}
