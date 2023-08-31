package uhbp.todolist.Service;

import uhbp.todolist.domain.Member;

import java.util.List;

public interface ShareService {
    List<Member> search(String searchId);

    Member findById(String memberId);

    void shareTodo(Long loginIndex, String todoIndex, List<String> selectedMembers);
}
