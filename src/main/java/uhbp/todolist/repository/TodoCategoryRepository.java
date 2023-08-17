package uhbp.todolist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uhbp.todolist.domain.TodoCategory;

public interface TodoCategoryRepository extends JpaRepository<TodoCategory, Long> {
}
