package uhbp.todolist.repository;

import uhbp.todolist.domain.Member;
import uhbp.todolist.domain.TodoList;

import java.util.List;

public interface CustomRepository {

    List<Member> findMember(String searchId);

    List<TodoList> findAllByOrderByTodoGendateAsc(Member currentMember);
    List<TodoList> findAllByOrderByTodoDuedateAsc(Member currentMember);

}
