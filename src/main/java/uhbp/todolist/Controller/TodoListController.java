package uhbp.todolist.Controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import uhbp.todolist.Service.TodoListService;
import uhbp.todolist.domain.TodoList;
import uhbp.todolist.dto.TodoListRequest;
import uhbp.todolist.exception.NoSuchMemberException;
import uhbp.todolist.repository.TodoListRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/todo")
@RequiredArgsConstructor
public class TodoListController {
    private final TodoListService todoListService;
    private final TodoListRepository todoListRepository;


    // (Create) 할일 생성
    @PostMapping("/create")
    public String createTodo(@ModelAttribute("todoListRequest") TodoListRequest todoListRequest, HttpServletRequest request) throws NoSuchMemberException {
        todoListService.createTodo(todoListRequest, request);
        return "redirect:/";
    }


//    // (Update) 할일 수정 / 변경 감지?
//    @PostMapping("/update/{todoIndex}")
//    public String updateTodo(@PathVariable Long todoIndex, @ModelAttribute("todoListRequest") TodoListRequest updateRequest, HttpServletRequest request) throws NoSuchMemberException {
//        todoListService.updateTodo(todoIndex, updateRequest, request);
//        return "redirect:/";
//    }

    // (Update) 할일 수정
    @PostMapping("/update")
    public String updateTodo(@ModelAttribute("todoListRequest") TodoListRequest todoListRequest, @RequestParam("todoIndex") Long todoIndex) {
        todoListService.updateTodo(todoIndex, todoListRequest);
        return "redirect:/";
    }


    // (Delete) 할일 삭제
    @PostMapping("/delete")
    public String deleteTodo(@RequestParam("todoIndex") Long todoIndex) {
        todoListService.deleteTodo(todoIndex);
        return "redirect:/";
    }


    // (Sort & Pin) 마감일순
    @GetMapping("/duedate-asc")
    public List<TodoList> dueDateAscTodo() {
        return todoListRepository.findAllByOrderByTodoDuedateAsc();
    }

    // (Sort & Pin) 기본순
    @GetMapping("/gendate-asc")
    public List<TodoList> genDateAscTodo() {
        return todoListRepository.findAllByOrderByTodoGendateAsc();
    }

}
