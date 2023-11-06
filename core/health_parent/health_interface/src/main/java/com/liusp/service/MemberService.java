package com.liusp.service;
import com.liusp.pojo.Member;

import java.util.List;

/*
会员接口
 */
public interface MemberService {
    public void add(Member member);
    public Member findByTelephone(String telephone);
    public List<Integer> findMemberCountByMonth(List<String> month);
}