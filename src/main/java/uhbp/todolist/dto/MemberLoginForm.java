package uhbp.todolist.dto;

import com.sun.istack.NotNull;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
public class MemberLoginForm {

    @NotEmpty(message = "id 를 입력해주세요")
    @Email(message = "id 는 이메일 형식입니다. ")
    private String inputId;

    @NotEmpty(message = "비밀번호를 입력해주세요")
    private String inputPw;
}
