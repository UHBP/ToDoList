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
import uhbp.todolist.repository.MemberRepository;
import uhbp.todolist.repository.TodoListRepository;
import uhbp.todolist.session.CookieMemberStore;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class TodoListServiceImple implements TodoListService {
    private final TodoListRepository todoListRepository;
    private final MemberRepository memberRepository;
    private final CookieMemberStore cookieMemberStore;


    // controller 이동
    @Override
    public void createTodo(TodoListRequest todoListRequest, HttpServletRequest request) throws NoSuchMemberException {
        // log.info("받아온 객체 = {}", todoListRequest.toString());
        Member currentMember = getCurrentMember(request);
        TodoList todoList = createTodoListEntity(todoListRequest, currentMember);
        // log.info("final todo = {}", todoList);
        todoListRepository.save(todoList);
    }


    // 현재 로그인한 회원 INDEX 가져오기
    private Member getCurrentMember(HttpServletRequest request) throws NoSuchMemberException {
        Long currentMemberIndex = cookieMemberStore.findValueByKey(request);
        return memberRepository.findById(currentMemberIndex)
                .orElseThrow(() -> new NoSuchMemberException("로그인한 사용자 정보를 찾을 수 없습니다."));
    }


    // 할일 요청 객체로부터 할일 엔티티 생성
    private TodoList createTodoListEntity(TodoListRequest todoListRequest, Member currentMember) {
        // log.info("Entry Point = {}", todoListRequest);
        TodoCategory category = todoListRequest.getCategory();
        return todoListRequest.toEntity(currentMember, category, false);
    }


    // 할일 목록 조회
    @Override
    public List<TodoList> readTodo(HttpServletRequest request) throws NoSuchMemberException {
        Member currentMember = getCurrentMember(request);
        return todoListRepository.findAllByMemberOrderByTodoIspinnedDesc(currentMember);
    }


//    // 할일 수정
//    @Override
//    public void updateTodo(Long todoIndex, TodoListRequest updateRequest, HttpServletRequest request) throws NoSuchMemberException {
//        TodoList todoList = todoListRepository.findById(todoIndex)
//                .orElseThrow(() -> new IllegalArgumentException("해당 할일을 찾을 수 없습니다."));
//
//        // 변경 감지를 이용하여 업데이트
//        if (updateRequest.getTodoTitle() != null) {
//            todoList.setTodoTitle(updateRequest.getTodoTitle());
//        }
//        if (updateRequest.getTodoContent() != null) {
//            todoList.setTodoContent(updateRequest.getTodoContent());
//        }
//        if (updateRequest.getCategory() != null) {
//            todoList.setTodoCategory(updateRequest.getCategory());
//        }
//        if (updateRequest.getTodoDuedate() != null) {
//            todoList.setTodoDuedate(updateRequest.getTodoDuedate());
//        }
//
//        // 변경 감지를 활용하여 엔티티를 업데이트
//        todoListRepository.save(todoList);
//    }

    // 할일 수정
    public void updateTodo(Long todoIndex, TodoListRequest todoListRequest) {
        TodoList todoList = todoListRepository.findById(todoIndex)
                .orElseThrow(() -> new EntityNotFoundException("해당하는 할일 목록이 존재하지 않습니다."));

        todoList.updateTodo(
                todoListRequest.getTodoTitle(),
                todoListRequest.getTodoContent(),
                todoListRequest.getTodoDuedate(),
                todoListRequest.getCategory()
        );
    }

    // 할일 삭제
    @Override
    public void deleteTodo(Long todoIndex) {
        todoListRepository.deleteByTodoIndex(todoIndex);
    }


    // 기본 순 정렬
    @Override
    public List<TodoList> genDateAscTodo(HttpServletRequest request) throws NoSuchMemberException {
        Member currentMember = getCurrentMember(request);
        return todoListRepository.findAllByOrderByTodoGendateAsc(currentMember);
    }

    // 마감일 순 정렬
    @Override
    public List<TodoList> dueDateAscTodo(HttpServletRequest request) throws NoSuchMemberException {
        Member currentMember = getCurrentMember(request);
        return todoListRepository.findAllByOrderByTodoDuedateAsc(currentMember);
    }

    // 핀 설정
    @Override
    public void setTodoIspinned(Long todoIndex, boolean isPinned) {
        TodoList todoList = todoListRepository.findById(todoIndex)
                .orElseThrow(() -> new EntityNotFoundException("해당하는 할일 목록이 존재하지 않습니다."));
        todoList.setTodoIspinned(isPinned);
        todoListRepository.save(todoList);

    }

    @Override
    public List<TodoList> filterTodoByCategory(String category, HttpServletRequest request) throws NoSuchMemberException {
        Member currentMember = getCurrentMember(request);
        if ("ALL".equals(category)) {
            return todoListRepository.findAllByMemberOrderByTodoIspinnedDesc(currentMember);
        }
        return todoListRepository.findByTodoCategoryAndMemberOrderByTodoIspinnedDesc(TodoCategory.valueOf(category), currentMember);
    }


}
