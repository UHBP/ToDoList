package uhbp.todolist.domain;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@DataJpaTest
@Transactional
class TodoShareApproveQueueTest {
    @Test
    void todoShareApproveQueueFactory() {
        // Given
        TodoList todoIndex = TodoList.todoListFactory(
                TodoCategory.todoCategoryFactory("Study"),
                Member.memberFactory("testSharedId", "testSharedPw", "testSharedNickname", LocalDate.now()),
                "testTitle",
                "testContent",
                LocalDate.now(),
                null,
                true,
                LocalDate.now().plusDays(7)
        );
        Member sharedMemberIndex = Member.memberFactory("testSharedId", "testSharedPw", "testSharedNickname", LocalDate.now());
        Member shareMemberIndex = Member.memberFactory("testShareId", "testSharePw", "testShareNickname", LocalDate.now());
        LocalDate approveDate = LocalDate.now();

        // When
        TodoShareApproveQueue approveQueue = new TodoShareApproveQueue(todoIndex, sharedMemberIndex, shareMemberIndex, approveDate);
        log.info("approveQueue = {}", approveQueue);

        // Then
        assertThat(approveQueue.getTodoIndex()).isEqualTo(todoIndex);
        assertThat(approveQueue.getSharedMemberIndex()).isEqualTo(sharedMemberIndex);
        assertThat(approveQueue.getShareMemberIndex()).isEqualTo(shareMemberIndex);
        assertThat(approveQueue.getApproveDate()).isEqualTo(approveDate);
    }
}