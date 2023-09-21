package uhbp.todolist.Controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import uhbp.todolist.exception.NoSuchMemberException;
import uhbp.todolist.Service.MemberServiceImple;
import uhbp.todolist.domain.Member;
import uhbp.todolist.dto.MemberJoinForm;
import uhbp.todolist.dto.MemberLoginForm;
import uhbp.todolist.session.CookieMemberStore;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import static uhbp.todolist.session.CookieMemberStore.SESSION_COOKIE_NAME;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberServiceImple memberService;
    private final CookieMemberStore cookieMemberStore;

    @GetMapping("/login")
    public String login(Model model, HttpServletRequest request) {
        if(cookieMemberStore.findValueByKey(request) != null){
            throw new IllegalStateException();
        }
        MemberLoginForm memberLoginForm = new MemberLoginForm();
        model.addAttribute("loginForm", memberLoginForm);
        return "login";
    }

    @PostMapping("/login")
    public String login(@Valid MemberLoginForm loginForm, BindingResult bindingResult, Model model, HttpServletResponse response) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("loginForm", new MemberLoginForm());
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "login";
        }

        // Login 처리
        Member loginMember = null;
        try {
            loginMember = memberService.login(loginForm.getInputId(), loginForm.getInputPw());
        } catch (NoSuchMemberException e) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다. ");
            model.addAttribute("loginForm", new MemberLoginForm());
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "login";
        }
        // Login 성공 시
        String currentMemberUUID = cookieMemberStore.store(loginMember.getMemberIndex());
        log.info("currentMemberUUID = {}", currentMemberUUID);

        Cookie cookie = new Cookie(SESSION_COOKIE_NAME, currentMemberUUID);
        cookie.setMaxAge(86400);// 하루
        cookie.setPath("/");
        response.addCookie(cookie);

        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        cookieMemberStore.remove(request);
        Cookie expiredCookie = new Cookie(SESSION_COOKIE_NAME, null);
        expiredCookie.setMaxAge(0); // 쿠키의 expiration 타임을 0으로 하여 없앤다.
        expiredCookie.setPath("/"); // 모든 경로에서 삭제 됬음을 알린다.
        response.addCookie(expiredCookie);

        return "redirect:/member/login";
    }

    @GetMapping("/join")
    public String join(Model model, HttpServletRequest request) {
        if(cookieMemberStore.findValueByKey(request) != null){
            throw new IllegalStateException();
        }
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
        // TODO 뭔가 더 간지나는 방법이 있을거같음
        // 가입시 입력한 아이디가 중복되었는지 확인
        if (memberService.isDuplicateMemberId(joinForm.getMemberId())) {
            bindingResult.reject("duplicateId", "아이디가 중복되었습니다. ");
            model.addAttribute("joinForm", new MemberJoinForm());
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "join";
        }
        memberService.JoinMember(joinForm);
        return "redirect:/member/login";
    }
}
