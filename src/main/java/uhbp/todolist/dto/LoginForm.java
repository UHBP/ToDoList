package uhbp.todolist.dto;

import com.sun.istack.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginForm {
    @NotNull
    private String inputId;
    @NotNull
    private String intpuPw;
}
