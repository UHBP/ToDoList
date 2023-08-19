package uhbp.todolist.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
public class SessionStoreMemberForm {
    private String memberIndex;
    private String memberNickname;
}
