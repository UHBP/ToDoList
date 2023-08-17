package uhbp.todolist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uhbp.todolist.domain.TodoList;

@Repository
public interface TodoListRepository extends JpaRepository<TodoList, Long> {
}
