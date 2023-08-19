package uhbp.todolist.Controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import uhbp.todolist.domain.Member;
import uhbp.todolist.session.SessionManager;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final SessionManager sessionManager;

    @GetMapping("/")
    public String home(HttpServletRequest request){
        log.info("홈 컨트롤러 호출");
        Object session = sessionManager.getSession(request);
        if(session != null){
            log.info("currentSession = {}", (Member) session);
        }else {
            log.info("뭔가 뭔가 이상함");
        }
        return "index";
    }
}
