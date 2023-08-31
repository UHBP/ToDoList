package uhbp.todolist.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uhbp.todolist.domain.Member;
import uhbp.todolist.domain.TodoCategory;
import uhbp.todolist.domain.TodoList;
import uhbp.todolist.dto.TodoListRequest;
import uhbp.todolist.exception.NoSuchMemberException;
import uhbp.todolist.exception.TodoListNotFoundException;
import uhbp.todolist.repository.MemberRepository;
import uhbp.todolist.repository.TodoListRepository;
import uhbp.todolist.session.CookieMemberStore;

import javax.servlet.http.HttpServletRequest;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class TodoListServiceImple implements TodoListService {
    private final TodoListRepository todoListRepository;
    private final MemberRepository memberRepository;
    private final TodoCategoryService todoCategoryService;
    private final CookieMemberStore cookieMemberStore;


    // controller 이동
    @Override
    public void createTodo(TodoListRequest todoListRequest, Long categoryIndex, HttpServletRequest request) throws NoSuchMemberException {
        // log.info("받아온 객체 = {}", todoListRequest.toString());
        Member currentMember = getCurrentMember(request);
        TodoCategory todoCategory = getTodoCategory(categoryIndex);
        TodoList todoList = createTodoListEntity(todoListRequest, currentMember, todoCategory);
        saveTodoList(todoList);
    }

    // 현재 로그인한 회원 INDEX 가져오기
    private Member getCurrentMember(HttpServletRequest request) throws NoSuchMemberException {
        Long currentMemberIndex = cookieMemberStore.findValueByKey(request);
        return memberRepository.findById(currentMemberIndex)
                .orElseThrow(() -> new NoSuchMemberException("로그인한 사용자 정보를 찾을 수 없습니다."));
    }

    // 할일 요청 객체로부터 할일 엔티티 생성
    private TodoList createTodoListEntity(TodoListRequest todoListRequest, Member currentMember, TodoCategory todoCategory) {
        return todoListRequest.toEntity(currentMember, todoCategory);
    }

    // 선택한 카테고리 정보 가져오기
    private TodoCategory getTodoCategory(Long categoryIndex) {
        return todoCategoryService.getTodoCategoryById(categoryIndex);
    }

    // 할일 저장
    private void saveTodoList(TodoList todoList) {
        todoListRepository.save(todoList);
    }


    // 할일 목록 조회
    @Override
    public TodoList getTodoListById(Long todoIndex) {
        return todoListRepository.findById(todoIndex)
                .orElseThrow(() -> new TodoListNotFoundException("해당 할 일을 찾을 수 없습니다."));
    }

    // 할일 목록 삭제
    @Override
    public void deleteTodoListById(Long todoIndex) {
        todoListRepository.deleteById(todoIndex);
    }


//    현재 로그인한 회원 정보 가져오기
//    @Override
//    public Member getMemberById(Long memberId) throws NoSuchMemberException {
//        return memberRepository.findById(memberId)
//                .orElseThrow(() -> new NoSuchMemberException("로그인한 사용자 정보를 찾을 수 없습니다."));
//    }

}
