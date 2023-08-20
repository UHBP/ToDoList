package uhbp.todolist.intercepter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import uhbp.todolist.Controller.MemberController;
import uhbp.todolist.session.CookieMemberStore;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    CookieMemberStore cookieMemberStore;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("LoginIntercepter 진입");
        Long valueByKey = cookieMemberStore.findValueByKey(request);
        if(valueByKey == null){
            response.sendRedirect(request.getContextPath() + "/member/login");
            return false;
        }
        return true;
    }
}
