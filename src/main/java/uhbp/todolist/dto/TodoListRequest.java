package uhbp.todolist.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import uhbp.todolist.domain.Member;
import uhbp.todolist.domain.TodoList;
import uhbp.todolist.domain.TodoList.TodoCategory;

import java.time.LocalDate;

// Create, Update 에 사용
// 할일 목록을 생성 시 필요한 정보를 담음
@Data
public class TodoListRequest {
    private String todoTitle;
    private String todoContent;
    private int todoCategory; // TodoCategory Enum 사용

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate todoDuedate;

    // 생성 로직
    public TodoList toEntity(Member member, TodoCategory categoryIndex, boolean isupdate) {
        LocalDate now = LocalDate.now();
        LocalDate todoUpdatedate = isupdate ? now : null;
        TodoCategory category = TodoCategory.fromCategoryIndex(todoCategory);
        return TodoList.todoListFactory(
                category,
                member,
                todoTitle,
                todoContent,
                now, // 생성 날짜
                todoUpdatedate, // 업데이트 날짜
                false, // TODO_ISPINNED는 false로
                todoDuedate
        );
    }
}
