package uhbp.todolist.Controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import uhbp.todolist.Service.MemberServiceImple;
import uhbp.todolist.dto.MemberJoinForm;
import uhbp.todolist.dto.MemberLoginForm;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.UUID;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    @Autowired
    MemberServiceImple memberService;

    @GetMapping("/login")
    public String login(Model model) {
        MemberLoginForm memberLoginForm = new MemberLoginForm();
        model.addAttribute("loginForm", memberLoginForm);
        return "login";
    }

    // TODO Session 기반 로그인 구현
    @PostMapping("/login")
    public String login(@ModelAttribute @Valid MemberLoginForm input, BindingResult bindingResult, HttpServletResponse response) {
        log.info("current input = {}", input);
        Boolean memberExist = memberService.isMemberExist(input.getInputId(), input.getInputPw());
        log.info("ismemberExist = {}", memberExist);
        if (memberExist) {
            return "index";
        }else {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 일치하지 않습니다. ");
            return "login";
        }
    }

    @GetMapping("logout")
    public String logout(HttpServletResponse response){

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
