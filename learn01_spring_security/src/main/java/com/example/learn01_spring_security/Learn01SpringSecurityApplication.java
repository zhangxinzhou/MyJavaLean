package com.example.learn01_spring_security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 文档
 * https://mp.weixin.qq.com/s?__biz=MzAxODcyNjEzNQ==&mid=2247569821&idx=2&sn=8dd5d1da536fffd189d56b0119af462e&chksm=9bd27c05aca5f513646c9ef378aad1d0f8d318d8b28b531c5debb9d4ca15cbf9df39a71cd6e0&scene=90&subscene=93&sessionid=1663048620&clicktime=1663048627&enterid=1663048627&ascene=56&fasttmpl_type=0&fasttmpl_fullversion=6323481-zh_CN-zip&fasttmpl_flag=0&realreporttime=1663048627487&devicetype=android-31&version=28001b57&nettype=3gnet&abtest_cookie=AAACAA%3D%3D&lang=zh_CN&session_us=gh_ee62f85a5486&exportkey=AaoLlTXTKPFqeatIV2EDVSs%3D&pass_ticket=wubtYMdyNrRt1hMJBYTghXkasv3XTBwpzVgMuJwsWqOnGfmZ4oJNDFiCf8OAua%2FB&wx_header=3
 */
@SpringBootApplication
public class Learn01SpringSecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(Learn01SpringSecurityApplication.class, args);
    }


}
