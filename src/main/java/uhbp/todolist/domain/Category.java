package uhbp.todolist.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Category {
    STUDY("공부"), EXERCISE("운동"), APPOINTMENT("약속"), OTHER("기타");
    private String value;
}
