package uhbp.todolist.dto;

import lombok.Data;

import java.util.List;

@Data
public class ShareRequestData {
    private String todoIndex;
    private List<String> selectedMembers;
}
