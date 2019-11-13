package com.seven.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;

public class JwtTokenUtil {


    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";

    private static final String SECRET = "qiduanlianzi";
    private static final String ISS = "ruantianruanjian";

    // 角色的key
    private static final String ROLE_CLAIMS = "rol";

    // 过期时间是3600秒，既是1个小时
    private static final long EXPIRATION = 3600L * 24 * 365 * 10;

    // 选择了记住我之后的过期时间为7天
    private static final long EXPIRATION_REMEMBER = 604800L;

    // 创建token
    public static String createToken(String ids,String role, boolean isRememberMe) {
        long expiration = isRememberMe ? EXPIRATION_REMEMBER : EXPIRATION;
        HashMap<String, Object> map = new HashMap<>();
        map.put(ROLE_CLAIMS, role);
        return Jwts.builder()
                //设置签名使用的签名算法和签名使用的秘钥
                .signWith(SignatureAlgorithm.HS512, SECRET)
                //如果有私有声明，一定要先设置这个自己创建的私有的声明，这个是给builder的claim赋值，
                // 一旦写在标准的声明赋值之后，就是覆盖了那些标准的声明的
                .setClaims(map)
                //jwt签发者
                .setIssuer(ISS)
                //代表这个JWT的主体，即它的所有人，这个是一个json格式的字符串，
                // 可以存放什么userid，roldid之类的，作为什么用户的唯一标志。
                .setSubject(ids)
                //jwt的签发时间
                .setIssuedAt(new Date())
                //jwt的过期时间，这个过期时间必须要大于签发时间
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
                //就开始压缩为xxxxxxxxxxxxxx.xxxxxxxxxxxxxxx.xxxxxxxxxxxxx这样的jwt
                .compact();
    }

    // 从token中获取用户的唯一标志
    public static String getIds(String token){
        return getTokenBody(token).getSubject();
    }

    // 获取用户角色
    public static String getAdminRole(String token){
        return (String) getTokenBody(token).get(ROLE_CLAIMS);
    }

    // 是否已过期
    public static boolean isExpiration(String token){
        return getTokenBody(token).getExpiration().before(new Date());
    }

    private static Claims getTokenBody(String token){
        try {
            return Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token)
                    .getBody();
        }catch (Exception e){
            throw new MyException(4004,"token错误");
        }
    }

    //在request中获取token
    public static String getToken(HttpServletRequest request){
        String token  = request.getHeader("token");
        return token;
    }

    //获取用户编号
    public static Long getIds(HttpServletRequest request){
        String token  = getToken(request);
        if(StringUtils.isEmpty(token)){
            throw new MyException(HttpStatus.MULTIPLE_CHOICES.value(),"请登录");
        }
        String ids = getIds(token);
        if(StringUtils.isEmpty(ids))
            throw new MyException(HttpStatus.MULTIPLE_CHOICES.value(),"请登录");
        return Long.parseLong(ids);
    }

}
