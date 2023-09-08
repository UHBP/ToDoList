package uhbp.todolist.Controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import uhbp.todolist.session.CookieMemberStore;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@RestController
@RequiredArgsConstructor
public class SseController {

    private final CookieMemberStore cookieMemberStore;
    public static Map<Long, SseEmitter> sseEmitters = new ConcurrentHashMap<>();

    @GetMapping(value="/sub", consumes = MediaType.ALL_VALUE)
    public SseEmitter subscribe(HttpServletRequest request){
        System.out.println("컨트롤러 진입");
        // 현재 로그인한 사용자의 index
        Long loginIndex = cookieMemberStore.findValueByKey(request);

        // 현재 사용자를 위한 SseEmitter 생성
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
        System.out.println("에미터 : " + emitter);
        try {
            // 연결
            emitter.send(SseEmitter.event().name("connect"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 사용자의 index 값을 key 값으로 하여 emitter를 Map에 저장
        sseEmitters.put(loginIndex, emitter);
        System.out.println("emitter map : " + sseEmitters);

        // 만료, 타임아웃, 에러 발생 시 해당 emitter 삭제
        emitter.onCompletion(() -> sseEmitters.remove(loginIndex));
        emitter.onTimeout(() -> sseEmitters.remove(loginIndex));
        emitter.onError((e) -> sseEmitters.remove(loginIndex));

        return emitter;
    }

}
