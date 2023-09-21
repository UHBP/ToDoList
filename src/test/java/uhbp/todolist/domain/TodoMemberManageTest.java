package uhbp.todolist.domain;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@Transactional
@SpringBootTest
class TodoMemberManageTest {

    @Autowired
    EntityManager em;

    @Test
    void todoMemberManageFactory() {
        Member joinMember = Member.memberFactory("temp", "temp", "temp", LocalDate.now());
        Member genMember = Member.memberFactory("genTemp", "genTemp", "genTemp", LocalDate.now());
        TodoCategory category = TodoCategory.APPOINTMENT;
        em.persist(joinMember);
        em.persist(genMember);
        //em.persist(category);

        log.info("joinMember = {}", joinMember);
        log.info("genMember = {}", genMember);
        log.info("category = {}", category);

        TodoList todoList = TodoList.todoListFactory(category, genMember, "tempTitle", "TempContent", LocalDate.now(), LocalDate.now(), true, LocalDate.now());

        em.persist(todoList);
        log.info("todoList = {}", todoList);
        TodoMemberManage todoMemberManage = TodoMemberManage.todoMemberManageFactory(joinMember, todoList);
        em.persist(todoMemberManage);

        TodoMemberManage foundTodoMember = em.find(TodoMemberManage.class, todoMemberManage.getManageIndex());
        log.info("foundTodoMember = {}", foundTodoMember);

        Assertions.assertThat(foundTodoMember).isEqualTo(todoMemberManage);
    }
}