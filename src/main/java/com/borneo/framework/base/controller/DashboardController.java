package com.borneo.framework.base.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/dashBoard")
public class DashboardController extends BaseController {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    @RequestMapping
    public String list(HttpServletRequest request) {
        return "dashBoard";
    }
}
