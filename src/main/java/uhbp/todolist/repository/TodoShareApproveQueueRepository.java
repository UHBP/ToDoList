package uhbp.todolist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uhbp.todolist.domain.Member;
import uhbp.todolist.domain.TodoShareApproveQueue;

import java.util.List;

@Repository
public interface TodoShareApproveQueueRepository extends JpaRepository<TodoShareApproveQueue, Long>, CustomRepository {
    List<TodoShareApproveQueue> findBySharedMemberIndex(Member loginMember);
}
