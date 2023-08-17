package uhbp.todolist.domain;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.transaction.annotation.Transactional;
import uhbp.todolist.repository.TodoListRepository;
import uhbp.todolist.repository.TodoShareApproveQueueRepository;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@DataJpaTest
@Transactional
class TodoShareApproveQueueTest {

    @Autowired
    TestEntityManager em;

    @Test
    void todoShareApproveQueueFactory() {
        // Given
        TodoCategory category = TodoCategory.todoCategoryFactory("Study");
        Member sharedMember = Member.memberFactory("testSharedId", "testSharedPw", "testSharedNickname", LocalDate.now());
        Member shareMember = Member.memberFactory("testShareId", "testSharePw", "testShareNickname", LocalDate.now());

        // Save Entities
        TodoCategory savedCategory = em.persistAndFlush(category);
        Member savedSharedMember = em.persistAndFlush(sharedMember);
        Member savedShareMember = em.persistAndFlush(shareMember);

        TodoList todoIndex = TodoList.todoListFactory(
                savedCategory,
                savedSharedMember,
                "testTitle",
                "testContent",
                LocalDate.now(),
                LocalDate.now(),
                true,
                LocalDate.now().plusDays(7)
        );
        LocalDate approveDate = LocalDate.now();
        TodoList savedTodoIndex = em.persistAndFlush(todoIndex);

        // When
        TodoShareApproveQueue approveQueue = TodoShareApproveQueue.todoShareApproveQueueFactory(todoIndex, savedSharedMember, savedShareMember, approveDate);
        log.info("approveQueue = {}", approveQueue);
        TodoShareApproveQueue savedApproveQueue = em.persistAndFlush(approveQueue);
        log.info("approveQueue = {}", approveQueue);

        // Then
        assertThat(savedApproveQueue.getTodoIndex()).isEqualTo(todoIndex);
        assertThat(savedApproveQueue.getSharedMemberIndex()).isEqualTo(savedSharedMember);
        assertThat(savedApproveQueue.getShareMemberIndex()).isEqualTo(savedShareMember);
        assertThat(savedApproveQueue.getApproveDate()).isEqualTo(approveDate);
    }
}