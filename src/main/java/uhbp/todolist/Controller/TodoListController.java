package uhbp.todolist.Controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    // Controller : View or Server로 연결하는 관심사
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

//    // (Delete) 공유 할일 삭제
//    @PostMapping("/delete")
//    public String deleteSharedTodo(@RequestParam("todoIndex") Long todoIndex, HttpServletRequest request) throws NoSuchMemberException {
//        todoListService.deleteSharedTodo(todoIndex, request);
//        return "redirect:/";
//    }





    // (Sort) 할일 목록 정렬
    @GetMapping("/sort")
    public String sortTodo(Model model, @RequestParam String sortingOption, HttpServletRequest request) throws NoSuchMemberException {
        List<TodoList> todoLists;
        if (sortingOption.equals("gendate")) {
            todoLists = todoListService.genDateAscTodo(request);
        } else {
            todoLists = todoListService.dueDateAscTodo(request);
        }
        model.addAttribute("todoLists", todoLists);
        // log.info("받아온 정렬 리스트 = {}", todoLists);
        return "index::todoListFragment"; // th:fragment 해당 템플릿 조각을 렌더링
    }


    // (Pin) 할일 상단 고정
    @PostMapping("/pin")
    public String pinTodo(@RequestParam("todoIndex") Long todoIndex) {
        todoListService.setTodoIspinned(todoIndex, true);
        return "redirect:/";
    }

    // (Pin) 할일 상단 고정 해제
    @PostMapping("/unpin")
    public String unpinTodo(@RequestParam("todoIndex") Long todoIndex) {
        todoListService.setTodoIspinned(todoIndex, false);
        return "redirect:/";
    }

    // (Category) 카테고리 선택
    @GetMapping("/selectCategory")
    public String selectCategory(Model model, @RequestParam String category, HttpServletRequest request) throws NoSuchMemberException {
        List<TodoList> todoLists;
        if ("ALL".equals(category)) {
            // 전체 카테고리인 경우 정렬된 전체 할일 목록을 가져옴
            todoLists = todoListService.readTodo(request);
        } else {
            // 선택한 카테고리로 할일을 필터링
            todoLists = todoListService.filterTodoByCategory(category, request);
        }
        // log.info("카테고리에 해당하는 글 = {}", todoLists);
        model.addAttribute("todoLists", todoLists);
        return "index::todoListFragment"; // th:fragment 해당 템플릿 조각을 렌더링
    }


    // (Category) 공유 카테고리 선택
    @GetMapping("/shareCategory")
    public String shareCategory(Model model, HttpServletRequest request) throws NoSuchMemberException {
        List<TodoList> todoLists = todoListService.readSharedTodo(request);
        model.addAttribute("todoLists", todoLists);
        log.info("공유되고 있는 글 & 글 주인 정보 = {}", todoLists);
        return "index::todoListFragment";
    }
}