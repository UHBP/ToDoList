package uhbp.todolist.exception;

/**
 * 사용자에게 공유된 목록이 없을 때 발생하는 예외
 */
public class SharedTodoNotFoundException extends RuntimeException {
    public SharedTodoNotFoundException() {
        super();
    }

    public SharedTodoNotFoundException(String message) {
        super(message);
    }

    public SharedTodoNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public SharedTodoNotFoundException(Throwable cause) {
        super(cause);
    }
}
