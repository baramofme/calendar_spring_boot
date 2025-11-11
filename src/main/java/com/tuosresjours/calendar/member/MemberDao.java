package com.tuosresjours.calendar.member;

import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class MemberDao {

    Map<String, String > map = new HashMap<>();

    public boolean isMember(String id) {
        System.out.println("[MemberDao] isMember()");
        return true;
    }

    public int insertMember(MemberDto memberDto) {
     System.out.println("[MemberDao] insertMember()");
        return 1;
    }

//    public MemberDto selectMemberByID(String id) {
//         System.out.println("[MemberDao] selectMemberByID()");
//        return "";
//    }

}
