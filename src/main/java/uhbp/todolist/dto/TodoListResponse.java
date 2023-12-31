package uhbp.todolist.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import uhbp.todolist.domain.TodoCategory;
import uhbp.todolist.domain.TodoList;

import java.time.LocalDate;

// Read 에 사용
// 할일 목록의 정보를 담음
@Data
public class TodoListResponse {
    private Long todoIndex;
    private String todoTitle;
    private String todoContent;
    private LocalDate todoGendate;
    private LocalDate todoUpdatedate;
    private boolean todoIspinned;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate todoDuedate;
    private TodoCategory todoCategory;
    private Long todoMemberIndex;

    public static TodoListResponse fromEntity(TodoList todoList) {
        TodoListResponse response = new TodoListResponse();
        response.setTodoIndex(todoList.getTodoIndex());
        response.setTodoTitle(todoList.getTodoTitle());
        response.setTodoContent(todoList.getTodoContent());
        response.setTodoGendate(todoList.getTodoGendate());
        response.setTodoUpdatedate(todoList.getTodoUpdatedate());
        response.setTodoIspinned(todoList.isTodoIspinned());
        response.setTodoDuedate(todoList.getTodoDuedate());
        response.setTodoCategory(todoList.getTodoCategory());
        return response;
    }
}
