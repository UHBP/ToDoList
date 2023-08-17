package uhbp.todolist.dto;

import lombok.Data;

@Data
public class MemberJoinForm {
    private String memberId;
    private String memberPw;
    private String memberNickName;
}
