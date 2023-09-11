package uhbp.todolist.Service;

import uhbp.todolist.domain.TodoList;
import uhbp.todolist.dto.TodoListRequest;
import uhbp.todolist.exception.NoSuchMemberException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface TodoListService {

    void createTodo(TodoListRequest todoListRequest, HttpServletRequest request) throws NoSuchMemberException;

    List<TodoList> readTodo(HttpServletRequest request) throws NoSuchMemberException;

//    // 할일 수정
//    void updateTodo(Long todoIndex, TodoListRequest updateRequest, HttpServletRequest request) throws NoSuchMemberException;

    // 할일 수정
    void updateTodo(Long todoIndex, TodoListRequest todoListRequest);

    void deleteTodo(Long todoIndex);

//    // 마감일순 정렬
//    List<TodoList> dueDateAscTodo();
//
//    // 기본순 정렬
//    List<TodoList> genDateAscTodo();

}
