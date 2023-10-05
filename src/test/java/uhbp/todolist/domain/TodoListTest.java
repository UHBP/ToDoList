package uhbp.todolist.domain;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.transaction.annotation.Transactional;
import uhbp.todolist.repository.TodoListRepository;

import java.time.LocalDate;

@Slf4j
@DataJpaTest
@Transactional
class TodoListTest {

    @Autowired
    TestEntityManager tem;

    @Autowired
    private TodoListRepository todoListRepository;

    @Test
    void todoListFactory(){

        // 테스트 데이터(Member)를 생성하고 TestEntityManager를 사용하여 DB에 저장
        Member newMember = Member.memberFactory("testId", "testPw", "testNickname", LocalDate.now());
        Member savedMember = tem.persistAndFlush(newMember);

        // 나머지 테스트 데이터 정의
        String todoTitle = "testTitle";
        String todoContent = "testContent";
        LocalDate todoGendate = LocalDate.now();
        LocalDate todoUpdatedate = LocalDate.now();
        boolean todoIspinned = true;
        LocalDate todoDuedate = LocalDate.now();

        // 테스트 데이터(TodoList)를 생성하고 TestEntityManager를 사용하여 DB에 저장
        TodoList newTodoList = TodoList.todoListFactory(TodoCategory.APPOINTMENT, savedMember, todoTitle, todoContent, todoGendate, todoUpdatedate, todoIspinned, LocalDate.now(), false);
        TodoList savedTodoList = tem.persistAndFlush(newTodoList);

        // 저장된 데이터 불러오기
        TodoList foundTodoList = todoListRepository.findById(savedTodoList.getTodoIndex()).get();
        log.info("foundTodoList = {}", foundTodoList);
        log.info("new TodoList after persist = {}", newTodoList);

        // 저장된 데이터와 불러온 데이터가 동일한지 확인
        Assertions.assertThat(foundTodoList).isEqualTo(newTodoList);
        Assertions.assertThat(foundTodoList).isEqualTo(savedTodoList);

    }
}
