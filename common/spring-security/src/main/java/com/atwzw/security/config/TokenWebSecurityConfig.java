package com.atwzw.security.config;

import com.atwzw.security.filter.TokenAuthenticationFilter;
import com.atwzw.security.filter.TokenLoginFilter;
import com.atwzw.security.security.DefaultPasswordEncoder;
import com.atwzw.security.security.TokenLogoutHandler;
import com.atwzw.security.security.TokenManager;
import com.atwzw.security.security.UnauthorizedEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

/**
 * Security配置类
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class TokenWebSecurityConfig extends WebSecurityConfigurerAdapter {

    // 用户认证业务逻辑类
    private UserDetailsService userDetailsService;

    // token 管理类
    private TokenManager tokenManager;

    // 密码加密处理类
    private DefaultPasswordEncoder defaultPasswordEncoder;

    // Redis 缓存类
    private RedisTemplate redisTemplate;

    // 数据源
    @Autowired
    private DataSource dataSource;

    @Autowired
    private PersistentTokenRepository persistentTokenRepository;

    @Autowired
    public TokenWebSecurityConfig(UserDetailsService userDetailsService, DefaultPasswordEncoder defaultPasswordEncoder,
                                  TokenManager tokenManager, RedisTemplate redisTemplate) {
        this.userDetailsService = userDetailsService;
        this.defaultPasswordEncoder = defaultPasswordEncoder;
        this.tokenManager = tokenManager;
        this.redisTemplate = redisTemplate;
    }

    /**
     * 配置设置
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // 第一步：关闭 csrf 防护
        http.csrf().disable();

        // 第二步：所有请求都必须要认证,必须登录之后被访问
        http.authorizeRequests().anyRequest().authenticated();

        // 第三步：自定义登录、认证过滤器
        http.addFilter(new TokenLoginFilter(authenticationManager(), tokenManager, redisTemplate))
                .addFilter(new TokenAuthenticationFilter(authenticationManager(), tokenManager, redisTemplate)).httpBasic();

        // 第四步：退出登录
        http.logout()
                // 自定义退出路径
                .logoutUrl("/admin/acl/index/logout")
                // 自定义退出处理器实现逻辑
                .addLogoutHandler(new TokenLogoutHandler(tokenManager, redisTemplate));

        // 第五步：自定义异常处理
        http.exceptionHandling().authenticationEntryPoint(new UnauthorizedEntryPoint());

        // 第六步：登录时记住我
        http.rememberMe()
                // 设置数据源
                .tokenRepository(persistentTokenRepository)
                .rememberMeParameter("rememberMe")
                .rememberMeCookieName("rememberMeCookie")
                .tokenValiditySeconds(60)
                .userDetailsService(userDetailsService);
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository(){
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        // 设置数据源
        jdbcTokenRepository.setDataSource(dataSource);
        // 设置自动建表：第一次启动时开启，第二次启动时注释
//        jdbcTokenRepository.setCreateTableOnStartup(true);
        return jdbcTokenRepository;
    }

    /**
     * 认证处理:从容器中取出 AuthenticationManagerBuilder，执行方法里面的逻辑之后，放回容器
     *
     * @param auth
     * @throws Exception
     */
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(defaultPasswordEncoder);
    }

    /**
     * 配置哪些请求不拦截
     *
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/api/**", "/swagger-resources",
                "/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**"
        );
    }
}