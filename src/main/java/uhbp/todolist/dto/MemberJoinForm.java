package uhbp.todolist.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
public class MemberJoinForm {
    @Email(message = "올바른 이메일 형식이여야 합니다. ")
    @NotEmpty(message = "아이디는 필수입니다. ")
    private String memberId;
    @NotEmpty(message = "비밀번호는 필수입니다. ")
    private String memberPw;
    @NotEmpty(message = "닉네임은 필수입니다. ")
    private String memberNickName;
}
