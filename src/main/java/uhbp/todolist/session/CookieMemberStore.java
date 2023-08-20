package uhbp.todolist.session;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import uhbp.todolist.domain.Member;
import uhbp.todolist.dto.MemberInfo;
import uhbp.todolist.repository.MemberRepository;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
@RequiredArgsConstructor
public class CookieMemberStore {
    public static final String SESSION_COOKIE_NAME = "memberCookie";
    private Map<String, Long> cookieMemberStore = new ConcurrentHashMap<>();

    private final MemberRepository memberRepository;

    public String store(Long value) {
        log.info("Create Session value ={}", value);
        String key = UUID.randomUUID().toString();
        cookieMemberStore.put(key, value);
        return key;
    }

    public MemberInfo getViewUsingMemberFormByKey(String key) {
        Member getMember = memberRepository.findById(cookieMemberStore.get(key)).orElseGet(null);
        return new MemberInfo(getMember.getMemberNickname());
    }

    public Long findValueByKey(HttpServletRequest request) {
        Cookie memberCookie = findSessionCookie(request);
        if (memberCookie == null) {
            log.info("No Member Cookie");
            return null;
        }
        return cookieMemberStore.get(memberCookie.getValue());
    }

    private Cookie findSessionCookie(HttpServletRequest request) {
        if (request.getCookies() == null) {
            return null;
        }
        return Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals(SESSION_COOKIE_NAME))
                .findAny()
                .orElse(null);
    }

    public void remove(HttpServletRequest request) {
        log.info("pre remove = {}", cookieMemberStore);
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            try {
                cookieMemberStore.remove(cookie.getValue());
            } catch (NullPointerException ignored) {
            }
        }
        log.info("after remove = {}", cookieMemberStore);
    }
}
