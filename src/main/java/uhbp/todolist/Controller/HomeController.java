package uhbp.todolist.Controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import uhbp.todolist.Service.AlarmService;
import uhbp.todolist.dto.MemberInfo;
import uhbp.todolist.dto.TodoListRequest;
import uhbp.todolist.dto.ShareTargetSearch;
import uhbp.todolist.session.CookieMemberStore;

import javax.servlet.http.HttpServletRequest;

import static uhbp.todolist.session.CookieMemberStore.SESSION_COOKIE_NAME;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final CookieMemberStore cookieMemberStore;
    private final AlarmService alarmService;

    @GetMapping("/")
    public String home(@CookieValue(name = SESSION_COOKIE_NAME, required = false)String cookie, Model model, HttpServletRequest request){
        if(cookie != null){
            MemberInfo memberInfoByKey = cookieMemberStore.getViewUsingMemberFormByKey(cookie);
            model.addAttribute("memberInfo", memberInfoByKey);
            model.addAttribute("todoListRequest", new TodoListRequest());
            model.addAttribute("shareTargetSearch", new ShareTargetSearch());

            log.info("current member = {}", memberInfoByKey);

            // 공유 알림 갯수
            int shareCount = alarmService.countShare(request);
            log.info("공유받은 todo 갯수 = {}", shareCount);
            model.addAttribute("shareCount", shareCount);

        }
        return "index";
    }
}
