package uhbp.todolist.Controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import uhbp.todolist.Service.MemberServiceImple;
import uhbp.todolist.domain.Member;
import uhbp.todolist.dto.MemberJoinForm;
import uhbp.todolist.dto.MemberLoginForm;
import uhbp.todolist.session.SessionManager;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberServiceImple memberService;
    private final SessionManager sessionManager;

    @GetMapping("/login")
    public String login(Model model) {
        MemberLoginForm memberLoginForm = new MemberLoginForm();
        model.addAttribute("loginForm", memberLoginForm);
        return "login";
    }

    // TODO 스프링 세션 기반 로그인 구현 후 삭제 예정
    @Deprecated
//    @PostMapping("/login")
    public String login(@Valid MemberLoginForm loginForm, BindingResult bindingResult, HttpServletResponse response, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("loginForm", new MemberLoginForm());
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "login";
        }

        Member loginMember = memberService.login(loginForm.getInputId(), loginForm.getInputPw());

        if (loginMember == null) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다. ");
            model.addAttribute("loginForm", new MemberLoginForm());
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "login";
        }

        sessionManager.createSession(loginMember, response);
        return "redirect:/";
    }

    @GetMapping("/join")
    public String join(Model model) {
        MemberJoinForm memberJoinForm = new MemberJoinForm();
        model.addAttribute("joinForm", memberJoinForm);
        return "join";
    }

    @PostMapping("/join")
    public String join(@Valid MemberJoinForm joinForm, BindingResult bindingResult, Model model) {
        log.info("Join Input Dto = {}", joinForm);
        if (bindingResult.hasErrors()) {
            log.info(bindingResult.toString());
            model.addAttribute("joinForm", new MemberJoinForm());
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "join";
        }
        memberService.JoinMember(joinForm);
        return "index";
    }
}
