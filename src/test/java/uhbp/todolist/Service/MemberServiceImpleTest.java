package uhbp.todolist.Service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import uhbp.todolist.domain.Member;
import uhbp.todolist.dto.MemberJoinForm;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;


@Slf4j
@SpringBootTest
@Transactional(readOnly = true)
class MemberServiceImpleTest {

    @Autowired
    EntityManager em;

    @Autowired
    MemberServiceImple memberService;

    @Transactional
    @Test
    void joinMember() {
        MemberJoinForm form = new MemberJoinForm();
        form.setMemberId("tmpId");
        form.setMemberPw("tmpPw");
        form.setMemberNickName("tmpNickName");

        int memberIndex = memberService.JoinMember(form);

        Member foundMember = em.find(Member.class, Long.parseLong(Integer.toString(memberIndex)));
        log.info("found Member = {}", foundMember);

        assertEquals(form.getMemberId(), foundMember.getMemberId());
        assertEquals(form.getMemberNickName(), foundMember.getMemberNickname());
    }
}