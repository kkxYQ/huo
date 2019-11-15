package com.seven.controller.test;

import com.seven.util.MD5Util;
import com.sun.org.apache.xpath.internal.objects.XString;

/**
 * @program: huo
 * @description: 测试Md5密码
 * @author: Mr.Y
 * @create: 2019-11-15 09:16
 **/
public class Md5Test {
    public static void main(String[] args) {
        String m1="123";
        String m2="345";
        String s = MD5Util.toMD5(m1+m2);
        System.out.println(s);
    }
}
