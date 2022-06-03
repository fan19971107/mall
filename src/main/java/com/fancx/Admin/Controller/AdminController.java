package com.fancx.Admin.Controller;

import com.fancx.Admin.Result.AdminResultCode;
import com.fancx.Admin.Service.AdminService;
import com.fancx.Common.Result.CommonResultData;
import com.fancx.Common.Result.CommonResultUtil;
import com.fancx.Admin.Entity.AdminEntity;
import com.fancx.Admin.Exception.AdminFailMessageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    AdminService adminService;

    @GetMapping(value = "/checkUsername")
    public CommonResultData<String> checkUsername(String username) {
        System.out.println(username);
        boolean useable = adminService.checkUsername(username);
        System.out.println(useable);
        if (!useable) {
            throw new AdminFailMessageException(AdminResultCode.USERNAME_OCCUPIED);
        }
        return CommonResultUtil.success();
    }

    @PostMapping(value = "/register")
    public CommonResultData Register(@RequestBody AdminEntity adminEntity) {
        boolean success = adminService.register(adminEntity);
        if (success)
            return CommonResultUtil.success();
        throw new AdminFailMessageException(AdminResultCode.REGISITER_ERROR);
    }

    @PostMapping(value = "/login")
    public CommonResultData login(@RequestBody AdminEntity loginUser, HttpServletRequest request, HttpServletResponse response) {
        System.out.println("#" + loginUser.getUsername());
        System.out.println("#" + loginUser.getPassword());
        adminService.checkLogin(loginUser.getUsername(), loginUser.getPassword());

        //执行到此则说明adminService未报异常(可以登录)
        HttpSession session = request.getSession();//第一次调用则创建Session会话
        System.out.println("#" + session.getId());
        loginUser = adminService.getAdminInfo(loginUser.getUsername());
        session.setAttribute("AdminInfo", loginUser);
        return CommonResultUtil.success();
    }
}
