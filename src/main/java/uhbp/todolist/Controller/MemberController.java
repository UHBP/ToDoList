package uhbp.todolist.Controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import uhbp.todolist.Service.MemberService;
import uhbp.todolist.dto.MemberJoinForm;
import uhbp.todolist.dto.MemberLoginForm;

import javax.validation.Valid;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    @Autowired
    MemberService memberService;

    @GetMapping("/login")
    public String login(Model model) {
        MemberLoginForm memberLoginForm = new MemberLoginForm();
        model.addAttribute("loginForm", memberLoginForm);
        return "login";
    }

    // TODO 쿠키 기반 로그인 구현
    @PostMapping("/login")
    public String login(@ModelAttribute @Valid MemberLoginForm input, BindingResult bindingResult) {
        Boolean memberExtist = memberService.isMemberExtist(input.getInputId(), input.getIntpuPw());
        if (memberExtist) {
            return "";
        }
        return "";
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
