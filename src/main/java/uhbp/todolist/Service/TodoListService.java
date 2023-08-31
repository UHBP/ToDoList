package uhbp.todolist.Service;

import uhbp.todolist.domain.TodoList;
import uhbp.todolist.dto.TodoListRequest;
import uhbp.todolist.exception.NoSuchMemberException;

import javax.servlet.http.HttpServletRequest;

public interface TodoListService {

    void createTodo(TodoListRequest todoListRequest, Long categoryIndex, HttpServletRequest request) throws NoSuchMemberException;

    TodoList getTodoListById(Long todoIndex);

    void deleteTodoListById(Long todoIndex);

//  Member getMemberById(Long memberId) throws NoSuchMemberException;

}
