package uhbp.todolist.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import uhbp.todolist.domain.Member;
import uhbp.todolist.domain.TodoCategory;
import uhbp.todolist.domain.TodoList;

import java.time.LocalDate;

// Create, Update 에 사용
// 할일 목록을 생성 시 필요한 정보를 담음
// 클라이언트에서 넘어온 요청을 처리, 클라이언트가 보내는 정보를 담고 이를 기반으로 할일 목록을 생성
@Data
public class TodoListRequest {
    private String todoTitle;
    private String todoContent;
    private TodoCategory category; // TodoCategory Enum 사용

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate todoDuedate;


    // 생성 로직
    public TodoList toEntity(Member member, TodoCategory category, boolean isUpdate) {
        LocalDate now = LocalDate.now();
        LocalDate todoUpdatedate = isUpdate ? now : null;
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
