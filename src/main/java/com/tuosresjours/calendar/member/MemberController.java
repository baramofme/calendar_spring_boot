package com.tuosresjours.calendar.member;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    // 회원 가입 양식
    @GetMapping("/signup")
    public String signup() {
         System.out.println("[MemberController] signup()");

        String nextPage = "member/signup_form";

        return nextPage;

    }

    // 회원 가입 확인
    @PostMapping("/signup_confirm")
    public String signupConfirm(MemberDto memberDto, Model model) {
         System.out.println("[MemberController] signupConfirm()");

        String nextPage = "member/signup_result";

        int result = memberService.signupConfirm(memberDto);
        model.addAttribute("result", result);

        return nextPage;

    }

    @GetMapping("/signin")
    public String signin(Principal principal) {
         System.out.println("[MemberController] signin()");
         String nextPage = "member/signin_form";
         return nextPage;
    }

    @PostMapping("/signin_confirm")
    public String signinConfirm(MemberDto memberDto, Model model, HttpSession session) {
        System.out.println("[MemberController] signinConfirm()");

        String nextPage = "member/signin_result";

        String loginedID = memberService.signinConfirm(memberDto);
        System.out.println(loginedID);
        model.addAttribute("LoginedID", loginedID);

        if(loginedID != null) {
            session.setAttribute("loginedID", loginedID);
        }

        return nextPage;

    }

    // 로그아웃확인
    @GetMapping("/signout_confirm")
    public String signoutConfirm(HttpSession session) {
        System.out.println("[MemberController] logout()");
        session.invalidate();
        // 사용자에게 응답하는 대신, 서버의 다른 경로로 요청 방향을 바꾼다
        return "redirect:/";
    }
}
