package uhbp.todolist.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import uhbp.todolist.Service.TodoCategoryService;
import uhbp.todolist.Service.TodoListService;
import uhbp.todolist.domain.Member;
import uhbp.todolist.domain.TodoCategory;
import uhbp.todolist.domain.TodoList;
import uhbp.todolist.dto.TodoListRequest;
import uhbp.todolist.exception.NoSuchMemberException;
import uhbp.todolist.repository.MemberRepository;
import uhbp.todolist.session.CookieMemberStore;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/todo")
@RequiredArgsConstructor
public class TodoListController {
    private final TodoListService todoListService;
    private final TodoCategoryService todoCategoryService;
    private final CookieMemberStore cookieMemberStore;
    private final MemberRepository memberRepository;

    // 생성 전 준비
    @GetMapping("/init")
    public String showAddTodoForm(Model model, HttpServletRequest request) throws NoSuchMemberException {
        // 현재 로그인한 회원 정보 가져오기
        Member currentMember = getCurrentMember(request);
        // 빈 할일 요청 객체 생성
        TodoListRequest todoListRequest = new TodoListRequest();

        System.out.println("showAddTodoForm - Created todoListRequest: " + todoListRequest);

        // 모델에 할일 요청 객체와 현재 회원 정보 추가해 전달
        model.addAttribute("todoListRequest", todoListRequest);
        model.addAttribute("currentMember", currentMember);
        return "index";
    }


    // Create 할일 생성
    @PostMapping("/create")
    public String createTodo(@ModelAttribute("todoListRequest") TodoListRequest todoListRequest, BindingResult bindingResult, @RequestParam("categoryIndex") Long categoryIndex, HttpServletRequest request) throws NoSuchMemberException {
        // 현재 로그인한 회원 정보 가져오기
        Member currentMember = getCurrentMember(request);
        // 선택한 카테고리 정보 가져오기
        TodoCategory todoCategory = todoCategoryService.getTodoCategoryById(categoryIndex);
        // 할일 요청 객체로부터 할일 엔티티 생성
        TodoList todoList = todoListRequest.toEntity(currentMember, todoCategory);
        // 할일 저장
        todoListService.saveTodoList(todoList);
        // 생성 후 이동
        return "redirect:/";
    }


    // 현재 로그인한 회원 정보 가져오기
    private Member getCurrentMember(HttpServletRequest request) throws NoSuchMemberException {
        Long currentMemberIndex = cookieMemberStore.findValueByKey(request);
        return memberRepository.findById(currentMemberIndex)
                .orElseThrow(() -> new NoSuchMemberException("로그인한 사용자 정보를 찾을 수 없습니다."));
    }
}
