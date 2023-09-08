package uhbp.todolist.Service;

import uhbp.todolist.domain.TodoList;
import uhbp.todolist.dto.TodoListRequest;
import uhbp.todolist.exception.NoSuchMemberException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface TodoListService {

    void createTodo(TodoListRequest todoListRequest, HttpServletRequest request) throws NoSuchMemberException;

    // 할일 목록 조회
    List<TodoList> getAllTodoLists();

    void deleteTodoListById(Long todoIndex);

//  void updateTodo(Long todoIndex, TodoListRequest todoListRequest, Long categoryIndex, HttpServletRequest request);

//  Member getMemberById(Long memberId) throws NoSuchMemberException;

}
