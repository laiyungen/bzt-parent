package com.atwzw.security.filter;

import com.atwzw.commonutils.utils.ResponseUtil;
import com.atwzw.commonutils.RestResult;
import com.atwzw.security.security.TokenManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 访问认证、授权过滤器 BasicAuthenticationFilter 类对用户进行认证、授权操作
 */
public class TokenAuthenticationFilter extends BasicAuthenticationFilter {
    private TokenManager tokenManager;
    private RedisTemplate redisTemplate;

    public TokenAuthenticationFilter(AuthenticationManager authManager, TokenManager tokenManager,RedisTemplate redisTemplate) {
        super(authManager);
        this.tokenManager = tokenManager;
        this.redisTemplate = redisTemplate;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        logger.info("当前请求的URI=========="+req.getRequestURI());
        // 若当前请求 URI 中第一个位置不是 admin 的逻辑
        if(req.getRequestURI().indexOf("admin") == -1) {
            chain.doFilter(req, res);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = null;
        try {
            authentication = getAuthentication(req);
        } catch (Exception e) {
            ResponseUtil.out(res, RestResult.error());
        }

        if (authentication != null) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } 
        chain.doFilter(req, res);
    }

    // 根据当前 http 请求对用户进行认证与授权
    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {

        // 从请求 header 中获取 token 值
        String token = request.getHeader("token");

        // 判断 token 是否存在或是否为空
        if (token != null && !"".equals(token.trim())) {

            // 根据 token 值获取用户名
            String userName = tokenManager.getUserFromToken(token);

            // 根据用户名先从缓存中获取其所拥有的权限
            List<String> permissionValueList = (List<String>) redisTemplate.opsForValue().get(userName);

            // 遍历该用户所拥有的权限并对其进行一一授权
            Collection<GrantedAuthority> authorities = new ArrayList<>();
            for(String permissionValue : permissionValueList) {
                if(StringUtils.isEmpty(permissionValue)) continue;
                SimpleGrantedAuthority authority = new SimpleGrantedAuthority(permissionValue);
                authorities.add(authority);
            }

            // 若用户名不为空，则创建一个用户密码认证 token 对象返回
            if (!StringUtils.isEmpty(userName)) {
                return new UsernamePasswordAuthenticationToken(userName, token, authorities);
            }
            return null;
        }
        return null;
    }
}