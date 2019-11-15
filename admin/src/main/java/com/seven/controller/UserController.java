package com.seven.controller;


import com.alibaba.druid.util.StringUtils;
import com.seven.annotation.NoLogin;
import com.seven.model.Code;
import com.seven.model.User;
import com.seven.request.domain.vo.UserVo;
import com.seven.service.ICodeService;
import com.seven.service.IUserService;
import com.seven.util.JwtTokenUtil;
import com.seven.util.MD5Util;
import com.seven.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.omg.CORBA.PRIVATE_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author huo
 * @since 2019-11-14
 */
@RestController
@RequestMapping("/user")
@Api(value = "登录",description = "用户描述")
public class UserController {

    @Autowired
    private ICodeService codeService;
    @Autowired
    private IUserService userService;

    /**
     *  用户登录
     */
    @NoLogin
    @ApiOperation("用户登录")
    @PostMapping("/login")
    public Object login(@RequestBody UserVo userVo){
        if(StringUtils.isEmpty(userVo.getPhone())){
            return Result.failure("账号不能为空");
        }
        if(StringUtils.isEmpty(userVo.getPwd())){
            return Result.failure("密码不能为空");
        }
        User user = userService.findByPhone(userVo.getPhone());
        if(user == null){
            return Result.failure("该用户不存在");
        }
        if(!user.getPwd().equals(MD5Util.toMD5(userVo.getPwd()))){
            return Result.failure("密码错误");
        }
        if(user.getStatus() != 1){
            return Result.failure("您的账号被禁用，请联系管理员");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("phone", user.getPhone());
        map.put("userid",user.getId());
        return Result.success(map);
    }


}
