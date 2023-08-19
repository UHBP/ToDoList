package uhbp.todolist.Service;

import uhbp.todolist.dto.MemberJoinForm;

public interface MemberService {

    Boolean isMemberExist(String memberId, String memberPw);
    int JoinMember(MemberJoinForm form);
}
