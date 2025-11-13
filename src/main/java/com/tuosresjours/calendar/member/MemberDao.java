package com.tuosresjours.calendar.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class MemberDao {
    @Autowired
    JdbcTemplate jdbcTemplate;

    Map<String, String> map = new HashMap<>();

    public boolean isMember(String id) {
        System.out.println("[MemberDao] isMember()");

        String sql = "SELECT COUNT(*) FROM USER_MEMBER " +
                "WHERE ID = ?";

        // 객체(값) 하나만 받는 쿼리. 결과 행의 개수
        int result = jdbcTemplate.queryForObject(sql, Integer.class, id);
        if (result > 0) {
            return true;
        } else {
            return false;
        }
    }

    public int insertMember(MemberDto memberDto) {
        System.out.println("[MemberDao] insertMember()");

        String sql = "INSERT INTO USER_MEMBER(ID, PW, MAIL, PHONE) " +
                "VALUES(?,?,?,?)";
        int result = -1;

        try {
            // 결과 : 추가된 행의 개수
            result = jdbcTemplate.update(sql,
                                            memberDto.getId(),
                                            memberDto.getPw(),
                                            memberDto.getMail(),
                                            memberDto.getPhone());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

//    public MemberDto selectMemberByID(String id) {
//         System.out.println("[MemberDao] selectMemberByID()");
//        return "";
//    }

}
