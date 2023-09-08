package uhbp.todolist.exception;

/**
 * 할일 목록을 찾을 수 없을 때 발생하는 예외
 */
public class TodoListNotFoundException extends RuntimeException {
    public TodoListNotFoundException(String message) {
        super(message);
    }
}

