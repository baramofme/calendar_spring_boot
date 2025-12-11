package com.tuosresjours.calendar.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.autoconfigure.health.ConditionalOnEnabledHealthIndicator;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.support.SimpleTriggerContext;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.task.DelegatingSecurityContextAsyncTaskExecutor;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;


@Service
public class MemberService {

    public final static int USER_ID_ALREADY_EXIST = 0;
    public final static int USER_SIGNUP_SUCCESS = 1;
    public final static int USER_SIGNUP_FAIL = -1;

    @Value("${MAIL_SENDER}")
    private String mailSender;

    private final MemberDao memberDao;

    private final PasswordEncoder passwordEncoder;

    private final JavaMailSender javaMailSender;

//    @Autowired 생성자는 생략 가능
    public MemberService(MemberDao memberDao, PasswordEncoder passwordEncoder, JavaMailSender javaMailSender){
        this.memberDao = memberDao;
        this.passwordEncoder = passwordEncoder;
        this.javaMailSender = javaMailSender;
    }

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

    public MemberDto modify(String loginedID) {
        System.out.println("[MemberService] modify()");
        MemberDto dto = memberDao.selectMemberByID(loginedID);
        return dto;
    }

    public int modifyConfirm(MemberDto memberDto) {
        System.out.println("[MemberService] modifyConfirm");
        String encodedPW = passwordEncoder.encode(memberDto.getPw());
        memberDto.setPw(encodedPW);
        return memberDao.updateMember(memberDto);
    }

    public int findpasswordConfirm(MemberDto memberDto) {
        System.out.println("[MemberService] findpasswordConfirm");
        MemberDto selectedMemberDto = memberDao.selectMemberByIDAndMail(memberDto);

        int result= 0;
        if( selectedMemberDto != null ) {

            String newPassword = createNewPassword();
            result = memberDao.updatePassword(memberDto.getId(), passwordEncoder.encode(newPassword));

            if(result > 0) {
                sendNewPasswordByMail(memberDto.getMail(),newPassword);
            }
        }

        return result;
    }

    private String createNewPassword() {
        System.out.println( "[MemberService] createNewPassword()");

        // 사용할 문자셋 (0-9, a-z)
        String chars = "0123456789abcdefghijklmnopqrstuvwxyz";
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder();

        for (int i = 0; i < 8; i++) {
            int index = random.nextInt(chars.length());
            char c = chars.charAt(index);

            if (i % 2 == 0) {
                c = Character.toUpperCase(c);
            }

            password.append(c);
        }
        return password.toString();
    }

    private void sendNewPasswordByMail(String toMail, String newPassword) {
        System.out.println( "[MemberService] sendNewPasswordByMail()");

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toMail);
        message.setSubject("[MyCalendar] 새 비밀번호 안내입니다.");
        message.setText("새 비밀번호 : " + newPassword);
        message.setFrom(mailSender);

        javaMailSender.send(message);
    }
}
