package uhbp.todolist.session;

import uhbp.todolist.dto.MemberInfo;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

public interface CookieMemberStore {
    String store(Long value);

    MemberInfo getViewUsingMemberFormByKey(String key);

    Long findValueByKey(HttpServletRequest request);

    default Cookie findSessionCookie(HttpServletRequest request) {
        if (request.getCookies() == null) {
            return null;
        }
        return Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals(CookieMemberStoreImpl.SESSION_COOKIE_NAME))
                .findAny()
                .orElse(null);
    }

    void remove(HttpServletRequest request);
}
