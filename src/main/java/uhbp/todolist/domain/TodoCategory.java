package uhbp.todolist.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TodoCategory {
    STUDY, EXERCISE, APPOINTMENT, OTHER;
}
