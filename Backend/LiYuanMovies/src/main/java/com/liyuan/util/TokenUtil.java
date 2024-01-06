package com.liyuan.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.liyuan.db.entity.User;
import org.springframework.stereotype.Component;

/**
*  token工具类
*
* @author Yotars
* @since 2024-01-06 16:12:42
*/
@Component
public class TokenUtil {

    private String secretKey = "LiYuanMovies";

    /**
     * 加密 token, 将 User 对像加密为 token
     *
     * @param user {User} 传入 user 对象
     * @return token {String} 返回加密后的 token
     */
    public String getToken(User user) {

        String token =  JWT
                .create()
                .withClaim("uid", user.getUid())
                .withClaim("username", user.getUsername())
                .withClaim("password", user.getPassword())
                .withClaim("power", user.getPower())
                .sign(Algorithm.HMAC256(secretKey));

        return token;
    }

    /**
     * 解析token, 将 token 解析为 User 对象
     *
     * @param token {String} 传入 token
     * @return user {User} 返回解析后的 User 对象
     */
    private User parseToken(String token) {

        User user = new User();

        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(secretKey))
                .build().verify(token);

        Claim uid = decodedJWT.getClaim("uid");
        Claim username = decodedJWT.getClaim("username");
        Claim password = decodedJWT.getClaim("password");
        Claim power = decodedJWT.getClaim("power");

        user.setUid(uid.asInt());
        user.setUsername(username.asString());
        user.setPassword(password.asString());
        user.setPower(power.asInt());

        return user;
    }

    /**
     * 解析token, 判断用户权限是否为管理员
     *
     * @param token {String} 传入token
     * @return is {Boolean} 判断结果
     */
    public Boolean isAdmin(String token) {
        Boolean is = parseToken(token).getPower() == 0;
        return is;
    }
}

