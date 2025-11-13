package com.tuosresjours.calendar.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.task.DelegatingSecurityContextAsyncTaskExecutor;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    public final static int USER_ID_ALREADY_EXIST = 0;
    public final static int USER_SIGNUP_SUCCESS = 1;
    public final static int USER_SIGNUP_FAIL = -1;

    @Autowired
    MemberDao memberDao;

    @Autowired
    PasswordEncoder passwordEncoder;

    public int signupConfirm(MemberDto memberDto) {
        System.out.println("[MemberService] signupConfirm()");

        boolean isMember = memberDao.isMember(memberDto.getId());

        if (!isMember) {

            String encodedPW = passwordEncoder.encode(memberDto.getPw());
            memberDto.setPw(encodedPW);
            int insertResult = memberDao.insertMember(memberDto);
            // 암호화

            if (insertResult > 0) {
                return USER_SIGNUP_SUCCESS;
            } else {
                return USER_SIGNUP_FAIL;
            }

        } else {
            return USER_ID_ALREADY_EXIST;

        }

    }

    public String signinConfirm(MemberDto memberDto) {
        MemberDto dto = memberDao.selectMemberByID(memberDto.getId());
        if (dto != null && passwordEncoder.matches(memberDto.getPw(), dto.getPw())) {
            System.out.println("[MemberService] MEMBER LOGIN SUCCESS");
            return dto.getId();
        } else {
            System.out.printf("[MemberService] MEMBER LOGIN FAIL");
            return null;
        }
    }
}
