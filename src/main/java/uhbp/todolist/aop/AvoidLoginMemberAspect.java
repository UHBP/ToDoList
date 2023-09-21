package uhbp.todolist.aop;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import uhbp.todolist.session.CookieMemberStore;

import javax.servlet.http.HttpServletRequest;

@Component
@Aspect
@Slf4j
@RequiredArgsConstructor
public class AvoidLoginMemberAspect {

    private final CookieMemberStore cookieMemberStore;

    @Before("execution(* uhbp.todolist.Controller.*.login(..)) && args(model,request,..) || " +
            "execution(* uhbp.todolist.Controller.*.join(..)) && args(model,request,..)")
    public void checkCookie(JoinPoint joinPoint, Model model, HttpServletRequest request){
        if(cookieMemberStore.findValueByKey(request) != null){
            throw new IllegalStateException("로그인 사용자에게 허가되지 않은 기능입니다. ");
        }
    }
}
