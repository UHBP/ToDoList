package uhbp.todolist.dto;

import lombok.Data;

import java.util.List;

@Data
public class ShareRequestData {
    private Long todoIndex;
    private List<String> selectedMembers;
}
