package com.borneo.framework.base.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminLoginController extends BaseController {

    @RequestMapping("/login")
    public String backend() {
        return "login";
    }

    @RequestMapping("/success")
    public String success() {
        return "login_success_by_role_redirect";
    }
}
