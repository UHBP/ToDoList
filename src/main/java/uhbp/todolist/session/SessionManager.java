package uhbp.todolist.session;

import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Deprecated
@Component
public class SessionManager {
    public static final String SESSION_COOKIE_NAME = "memberCookie";
    private Map<String, Objects> sessionMemberStore = new ConcurrentHashMap<>();

    /**
     * 세션을 생성합니다.
     *
     * @param value
     * @param response
     */
    public void createSession(Objects value, HttpServletResponse response) {
        String sessionMemberId = UUID.randomUUID().toString();
        sessionMemberStore.put(sessionMemberId, value);

        Cookie sessionMemberCookie = new Cookie(SESSION_COOKIE_NAME, sessionMemberId);
        response.addCookie(sessionMemberCookie);
    }

    /**
     * Request 에 있는 쿠키를 조회하여, SessionStore 에 있는<br>
     * Key 와 비교하여, Member object 를 반환합니다.
     *
     * @param request
     * @return
     */
    public Objects getSession(HttpServletRequest request) {
        Cookie sessionCookie = findCookie(request);
        if (sessionCookie == null) {
            return null;
        }
        return sessionMemberStore.get(sessionCookie.getValue());
    }

    /**
     * sessionStore 에서 UUID 에 일치하는<br>
     * Object 를 찾아서 반환합니다.
     *
     * @param request
     * @return
     */
    private Cookie findCookie(HttpServletRequest request) {
        if (request.getCookies() == null) {
            return null;
        }
        return Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals(SESSION_COOKIE_NAME))
                .findAny()
                .orElse(null);
    }
}
