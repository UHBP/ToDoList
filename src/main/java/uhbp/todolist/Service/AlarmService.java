package uhbp.todolist.Service;

import javax.servlet.http.HttpServletRequest;

public interface AlarmService {
    void alarmShareEvent(Long loginIndex);

    int countShare(HttpServletRequest request);
}
