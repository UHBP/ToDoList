package uhbp.todolist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uhbp.todolist.domain.Member;
import uhbp.todolist.domain.TodoCategory;
import uhbp.todolist.domain.TodoList;

import java.util.List;

@Repository
public interface TodoListRepository extends JpaRepository<TodoList, Long>, CustomRepository {
    // 할일 목록 Read
    // List<TodoList> findAllByMember(Member currentMember);

    // 할일 목록 Read + Pin 포함
    List<TodoList> findAllByMemberOrderByTodoIspinnedDesc(Member currnetMember);

    void deleteByTodoIndex(Long todoIndex);

   // List<TodoList> findByMemberAndTodoCategoryOrderByTodoDuedateAsc(Member currentMember, TodoCategory todoCategory);

    List<TodoList> findByTodoCategoryAndMemberOrderByTodoIspinnedDesc(TodoCategory todoCategory, Member currentMember);
}
