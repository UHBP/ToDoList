package uhbp.todolist.Service;

import uhbp.todolist.dto.MemberJoinForm;

public interface MemberService {

    Boolean isMemberExtist(String memberId, String memberPw);
    int JoinMember(MemberJoinForm form);
}
