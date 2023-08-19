package uhbp.todolist.Service;

import uhbp.todolist.domain.Member;
import uhbp.todolist.dto.MemberJoinForm;

public interface MemberService {

    Boolean isMemberExist(String memberId, String memberPw);
    int JoinMember(MemberJoinForm form);

    Member login(String inputId, String inputPw);
}
