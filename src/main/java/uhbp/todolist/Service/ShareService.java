package uhbp.todolist.Service;

import uhbp.todolist.domain.Member;
import uhbp.todolist.domain.TodoShareApproveQueue;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface ShareService {
    List<Member> search(String searchId);

    void approveSelectedShares(List<TodoShareApproveQueue> selectedShares, HttpServletRequest request);

    Member findById(String memberId);

    void shareTodo(Long loginIndex, Long todoIndex, List<String> selectedMembers);

    List<TodoShareApproveQueue> getSharedTodo(Long loginIndex);

    void refuseSelectedShares(List<TodoShareApproveQueue> selectedRefuses);
}
