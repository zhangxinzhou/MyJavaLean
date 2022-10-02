package com.example.learn01_spring_security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class MyWebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 配置登录请求相关内容
        http.formLogin()
                .defaultSuccessUrl("/loginSuccess")
                .failureUrl("/loginFail");

        // 认证
        http.authorizeRequests()
                //.antMatchers("/toLogin").anonymous() //只能匿名用户访问
                .antMatchers("/*login*", "/register", "/login", "/favicon.ico").permitAll() // /toLogin请求地址，可以随便访问。
                .antMatchers("/**/*.js").permitAll() // 授予所有目录下的所有.js文件可访问权限
                .regexMatchers(".*[.]css").permitAll() // 授予所有目录下的所有.css文件可访问权限
                .anyRequest().authenticated(); // 任意的请求，都必须认证后才能访问。

//        //配置记住密码
//        http.rememberMe()
//                .rememberMeParameter("remember-me") // 修改请求参数名。 默认是remember-me
//                .tokenValiditySeconds(14*24*60*60) // 设置记住我有效时间。单位是秒。默认是14天
//                .rememberMeCookieName("remember-me") // 修改remember me的cookie名称。默认是remember-me
//                .tokenRepository(persistentTokenRepository) // 配置用户登录标记的持久化工具对象。
//                .userDetailsService(userSecurity); // 配置自定义的UserDetailsService接口实现类对象

        // 配置退出登录
        http.logout()
                .invalidateHttpSession(true) // 回收HttpSession对象。退出之前调用HttpSession.invalidate() 默认 true
                .clearAuthentication(true) // 退出之前，清空Security记录的用户登录标记。 默认 true
                // .addLogoutHandler() // 增加退出处理器。
                .logoutSuccessUrl("/") // 配置退出后，进入的请求地址。 默认是loginPage?logout
                .logoutUrl("/logout"); // 配置退出登录的路径地址。和页面请求地址一致即可。

        // 关闭CSRF安全协议。
        // 关闭是为了保证完整流程的可用。
        http.csrf().disable();
    }

//    @Bean
//    public PersistentTokenRepository persistentTokenRepository(DataSource dataSource){
//        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
//        jdbcTokenRepository.setDataSource(dataSource);
//        //jdbcTokenRepository.setCreateTableOnStartup(true);
//        return jdbcTokenRepository;
//    }

}
