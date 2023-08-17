package uhbp.todolist.domain;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.transaction.annotation.Transactional;
import uhbp.todolist.repository.MemberRepository;


import java.time.LocalDate;

@Slf4j
@DataJpaTest
@Transactional
class MemberTest {

    @Autowired
    TestEntityManager em;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void memberFactory() {
        // 테스트 데이터 정의
        String memberId = "exampleId";
        String memberPw = "password123";
        String memberNickname = "exampleNick";
        LocalDate memberJoindate = LocalDate.now();

        // 테스트 데이터를 데이터베이스에 저장
        Member newMember = Member.memberFactory(memberId, memberPw, memberNickname, memberJoindate);
        log.info("newMember = {}", newMember);

        Member savedMember = em.persistAndFlush(newMember); // TestEntityManager를 사용하여 저장
        log.info("savedMember = {}", savedMember);

        // 저장된 데이터를 불러옴
        Member foundMember = memberRepository.findById(savedMember.getMemberIndex()).get();
        log.info("foundMember = {}", foundMember);
        log.info("new Member after persist = {}", newMember);

        // 저장된 데이터와 불러온 데이터가 동일한지 확인
        Assertions.assertThat(foundMember).isEqualTo(newMember);
    }
}