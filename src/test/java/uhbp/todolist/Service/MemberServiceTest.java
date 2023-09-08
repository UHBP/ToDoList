package uhbp.todolist.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import uhbp.todolist.dto.MemberJoinForm;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@Transactional
@SpringBootTest
class MemberServiceTest {

    @Autowired
    private MemberServiceImple memberServiceImple;

    @Test
    void isMemberExist() {
    }

    @Test
    void joinMember() {
        // Given
        MemberJoinForm form = new MemberJoinForm();
        form.setMemberId("testUser");
        form.setMemberPw("testPassword");
        form.setMemberNickName("TestUser123");

        // When
        int memberId = memberServiceImple.JoinMember(form);

        // Then
        assertNotNull(memberId);
    }

    @Test
    void login() {
    }

    @Test
    void isDuplicateMemberId() {
    }
}