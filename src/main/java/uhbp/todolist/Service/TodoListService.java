package uhbp.todolist.Service;

import uhbp.todolist.domain.TodoList;
import uhbp.todolist.dto.TodoListRequest;
import uhbp.todolist.exception.NoSuchMemberException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface TodoListService {

    void createTodo(TodoListRequest todoListRequest, HttpServletRequest request) throws NoSuchMemberException;

//    List<TodoList> readTodo(HttpServletRequest request) throws NoSuchMemberException;

//    // 할일 수정
//    void updateTodo(Long todoIndex, TodoListRequest updateRequest, HttpServletRequest request) throws NoSuchMemberException;

    List<TodoList> readSharedTodo(HttpServletRequest request) throws NoSuchMemberException;

    List<TodoList> readTodo(HttpServletRequest request) throws NoSuchMemberException;

    void updateTodo(Long todoIndex, TodoListRequest todoListRequest);

    void deleteTodo(Long todoIndex);

//    // 공유 할일 삭제
//    void deleteSharedTodo(Long todoIndex, HttpServletRequest request) throws NoSuchMemberException;

    List<TodoList> genDateAscTodo(HttpServletRequest request) throws NoSuchMemberException;

    List<TodoList> dueDateAscTodo(HttpServletRequest request) throws NoSuchMemberException;

    void setTodoIspinned(Long todoIndex, boolean isPinned);

    List<TodoList> filterTodoByCategory(String category, HttpServletRequest request) throws NoSuchMemberException;

    void setTodoIsfinished(Long todoIndex, boolean isFinished);
}
