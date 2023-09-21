package uhbp.todolist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uhbp.todolist.domain.TodoShareApproveQueue;

@Repository
public interface TodoShareApproveQueueRepository extends JpaRepository<TodoShareApproveQueue, Long> {
}
