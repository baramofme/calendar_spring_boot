package com.tuosresjours.calendar.member;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

// 세션 체크 후 없으면 사인인 화면으로 리다이렉트
@Component
public class MemberSigniInterceptor implements org.springframework.web.servlet.HandlerInterceptor {
    // 핸들러(컨트롤러) 진입 전 요청을 가로채는 것을 구현
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("[MemberSigniInterceptor] preHandle()");
        // 요청 개게에서 세션을 사져옴
        HttpSession session = request.getSession();
        if(session != null){
            Object obj = session.getAttribute("loginedID");
            if (obj != null) {
                return true;
            }
        }
        response.sendRedirect("/member/signin");
        return false;
    }
}
