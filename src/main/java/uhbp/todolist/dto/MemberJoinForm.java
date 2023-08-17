package uhbp.todolist.dto;

import lombok.Data;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class MemberJoinForm {
    @Email
    @NotEmpty
    private String memberId;
    @NotEmpty
    private String memberPw;
    @NotEmpty
    private String memberNickName;
}
