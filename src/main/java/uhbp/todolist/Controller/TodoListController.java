package uhbp.todolist.Controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import uhbp.todolist.Service.TodoListService;
import uhbp.todolist.dto.TodoListRequest;
import uhbp.todolist.exception.NoSuchMemberException;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Controller
@RequestMapping("/todo")
@RequiredArgsConstructor
public class TodoListController {
    private final TodoListService todoListService;


    // (Create) 할일 생성
    @PostMapping("/create")
    public String createTodo(@ModelAttribute("todoListRequest") TodoListRequest todoListRequest, HttpServletRequest request) throws NoSuchMemberException {
        todoListService.createTodo(todoListRequest, request);
        return "redirect:/";
    }


//    // (Update) 할일 수정
//    @PostMapping("/update/{todoIndex}")
//    public String updateTodo(Long todoIndex, @ModelAttribute("todoListRequest") TodoListRequest todoListRequest, @RequestParam("categoryIndex") Long categoryIndex, HttpServletRequest request) throws NoSuchMemberException {
//        todoListService.updateTodo(todoIndex, todoListRequest, request);
//        return "redirect:/";
//    }

}
