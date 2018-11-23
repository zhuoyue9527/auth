package com.afis.web.security.inf;

import java.util.List;

import com.afis.web.exception.AuthenticationException;
import com.afis.web.modal.UserDetails;

/**
 * 认证安全的Token管理器
 * Created by hsw on 2017/1/12.
 */
public interface TokenService {

    /**
     * 存储用户信息
     * @param userDetails
     */
    void storeToken(UserDetails userDetails);

    /**
     * 根据用户名获取用户信息
     * @param userName
     * @return
     */
    UserDetails getToken(String userName);

    /**
     * 根据用户名刷新用户的Token过期时间
     * @param userName
     */
    void freshTokenTime(String userName);

    /**
     * 删除指定用户Token
     * @param userName
     */
    void removeToken(String userName);
    
    /**
     * 批量删除tokens
     * @param tokens
     */
    void removeTokens(List<Object> tokens);

    /**
     * 验证Token
     * @param userDetails
     * @return
     * @throws AuthenticationException
     */
    UserDetails validateToken(UserDetails userDetails) throws AuthenticationException;
}
