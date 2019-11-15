package com.seven.request.domain.vo;

import lombok.Data;

/**
 * @program: huo
 * @description: 用户注册,登录请求信息
 * @author: Mr.Y
 * @create: 2019-11-14 13:53
 **/
@Data
public class UserVo {

    /** 账号 */
    private String phone;

    /** 密码 */
    private String pwd;
}
