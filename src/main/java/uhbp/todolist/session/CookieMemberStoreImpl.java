package uhbp.todolist.session;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import uhbp.todolist.domain.Member;
import uhbp.todolist.dto.MemberInfo;
import uhbp.todolist.repository.MemberRepository;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
@RequiredArgsConstructor
public class CookieMemberStoreImpl implements CookieMemberStore {
    public static final String SESSION_COOKIE_NAME = "memberCookie";
    private Map<String, Long> cookieMemberStore = new ConcurrentHashMap<>();

    private final MemberRepository memberRepository;

    @Override
    public String store(Long value) {
        log.info("Create Session value ={}", value);
        String key = UUID.randomUUID().toString();
        cookieMemberStore.put(key, value);
        return key;
    }

    @Override
    public MemberInfo getViewUsingMemberFormByKey(String key) {
        Member getMember = memberRepository.findById(cookieMemberStore.get(key)).orElseGet(null);
        return new MemberInfo(getMember.getMemberNickname());
    }

    /**
     * HttpServletRequest 를 받아서, requst 에 있는 쿠키를 확인하여<br>
     * 해당 쿠키에 맞는 Member Entity 의 Index 를 반환합니다.
     * @param request
     * @return
     */
    @Override
    public Long findValueByKey(HttpServletRequest request) {
        Cookie memberCookie = findSessionCookie(request);
        if (memberCookie == null) {
            log.info("No Member Cookie");
            return null;
        }
        return cookieMemberStore.get(memberCookie.getValue());
    }

    @Override
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
