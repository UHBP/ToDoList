package uhbp.todolist.dto;

import com.sun.istack.NotNull;
import lombok.*;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MemberLoginForm {
    @NotEmpty
    private String inputId;
    @NotEmpty
    private String intpuPw;
}
