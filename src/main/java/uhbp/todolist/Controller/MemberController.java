package uhbp.todolist.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import uhbp.todolist.Service.MemberService;
import uhbp.todolist.dto.LoginForm;
import uhbp.todolist.dto.MemberJoinForm;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    MemberService memberService;

    @GetMapping("/login")
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView();
        LoginForm loginForm = new LoginForm();
        modelAndView.addObject("loginForm", new LoginForm());
        modelAndView.setViewName("loginForm");
        return modelAndView;
    }

    @PostMapping("/login")
    public ModelAndView login(@ModelAttribute LoginForm input, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        Boolean memberExtist = memberService.isMemberExtist(input.getInputId(), input.getIntpuPw());
        if (memberExtist) {
            modelAndView.setViewName("index");
            return modelAndView;
        }
        modelAndView.setViewName("loginForm");
        return modelAndView;
    }
}
