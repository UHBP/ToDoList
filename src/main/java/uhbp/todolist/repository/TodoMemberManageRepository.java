package uhbp.todolist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uhbp.todolist.domain.TodoMemberManage;

@Repository
public interface TodoMemberManageRepository extends JpaRepository<TodoMemberManage, Long> {
}
