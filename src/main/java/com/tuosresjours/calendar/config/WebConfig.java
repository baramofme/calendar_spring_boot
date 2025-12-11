package com.tuosresjours.calendar.config;

import com.tuosresjours.calendar.member.MemberSigniInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    MemberSigniInterceptor memberSigniInterceptor;

    @Override
    public void addInterceptors(org.springframework.web.servlet.config.annotation.InterceptorRegistry registry) {
        registry.addInterceptor(memberSigniInterceptor)
                .addPathPatterns("/member/modify")
                .excludePathPatterns(
                        "/member/signup",
                        "/member/signin",
                        "/member/signout_confirm",
                        "/member/findpassword");
    }
}
