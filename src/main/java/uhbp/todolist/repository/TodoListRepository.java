package uhbp.todolist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uhbp.todolist.domain.Member;
import uhbp.todolist.domain.TodoList;

import java.util.List;

@Repository
public interface TodoListRepository extends JpaRepository<TodoList, Long>, CustomRepository {
    List<TodoList> findAllByMember(Member currentMember);

    void deleteByTodoIndex(Long todoIndex);

}
