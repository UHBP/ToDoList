package uhbp.todolist.exception;

/**
 * 로그인한 입력값에 맞는 회원이 없을 때 발생하는 예외
 */
public class NoSuchMemberException extends Exception {
    public NoSuchMemberException() {
        super();
    }

    public NoSuchMemberException(String message) {
        super(message);
    }

    public NoSuchMemberException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSuchMemberException(Throwable cause) {
        super(cause);
    }

    protected NoSuchMemberException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
