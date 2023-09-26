package uhbp.todolist.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uhbp.todolist.domain.Member;
import uhbp.todolist.domain.TodoList;
import uhbp.todolist.domain.TodoMemberManage;
import uhbp.todolist.domain.TodoShareApproveQueue;
import uhbp.todolist.exception.SharedTodoNotFoundException;
import uhbp.todolist.repository.MemberRepository;
import uhbp.todolist.repository.TodoListRepository;
import uhbp.todolist.repository.TodoMemberManageRepository;
import uhbp.todolist.repository.TodoShareApproveQueueRepository;
import uhbp.todolist.session.CookieMemberStore;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class ShareServiceImple implements ShareService {

    private final AlarmServiceImpl alarmService;

    private final CookieMemberStore cookieMemberStore;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    TodoShareApproveQueueRepository shareRepository;

    @Autowired
    TodoListRepository todoRepository;

    @Autowired
    TodoMemberManageRepository memberManageRepository;

    @Override
    public List<Member> search(String searchId) {
        List<Member> searchMember = memberRepository.findMember(searchId);
        if(searchMember != null){
            return searchMember;
        } else{
            return null;
        }
    }

    @Override
    public void shareTodo(Long loginIndex, Long todoIndex, List<String> selectedMembers) {
        // String 타입의 todoIndex를 Long 타입으로 변환
        // Long todoLong = Long.parseLong(todoIndex);
        // todoIndex를 통해 해당 TodoList 객체 불러오기
        TodoList todo = todoRepository.findById(todoIndex).get();

        // 현재 로그인한 사용자의 Member 객체 불러오기
        Member loginMember = memberRepository.findById(loginIndex).get();

        // 체크박스 선택된 사용자 아이디 하나씩 꺼내기
        for(String memberId : selectedMembers){
            //log.info("선택된 아이디 = {}", memberId);
            // 사용자의 아이디를 통해 해당 사용자의 Member 객체 불러오기
            Member selectedMember = findById(memberId);
            log.info("선택된 회원의 정보 = {}", selectedMember);

            // 이미 공유된 todo인지 확인 (alert창 따로 빼면 좋을텐데 더 좋은 방법 없을까)
            List<TodoShareApproveQueue> approveAlready = shareRepository.findAlreadyShare(todo, loginMember, selectedMember);
            List<TodoMemberManage> todoAlready = memberManageRepository.findAlreadyShareTodo(todo, selectedMember);

            if(approveAlready.isEmpty() && todoAlready.isEmpty()){
                // 팩토리를 통해 Entity 생성
                TodoShareApproveQueue shareTodo = TodoShareApproveQueue.todoShareApproveQueueFactory(todo, selectedMember, loginMember, LocalDate.now());
                // 위에서 생성한 Entity를 DB에 저장
                shareRepository.save(shareTodo);

                // sse 알림 (공유받는 사용자의 index 넘겨서 해당 사용자의 emitter에 알림 이벤트 발행)
                alarmService.alarmShareEvent(selectedMember.getMemberIndex());
            }
        }
    }

    @Override
    public List<TodoShareApproveQueue> getSharedTodo(Long loginIndex) {
        Member loginMember = memberRepository.findById(loginIndex).get();
        List<TodoShareApproveQueue> sharedTodo = shareRepository.findBySharedMemberIndex(loginMember);
        if(sharedTodo == null){
            // 예외 발생 : 공유된 ToDo가 없음
            throw new SharedTodoNotFoundException("공유된 ToDo가 없습니다.");
        }
        return sharedTodo;
    }

    @Override
    public void approveSelectedShares(List<TodoShareApproveQueue> selectedShares, HttpServletRequest request) {
        // 현재 로그인한 사용자의 index
        long memberIndex = cookieMemberStore.findValueByKey(request);
        Member loginMember = memberRepository.findById(memberIndex).get();

        // 승인할 todo 하나씩 꺼내기
        for(TodoShareApproveQueue selectedShare : selectedShares){
            TodoList todo = selectedShare.getTodoIndex();

            // 팩토리를 통해 Entity 생성
            TodoMemberManage memberManage = TodoMemberManage.todoMemberManageFactory(loginMember, todo);
            // 위에서 생성한 Entity를 DB(member_manage)에 저장
            memberManageRepository.save(memberManage);

            // 승인 대기큐에서는 삭제해주기
            long approveIndex = selectedShare.getApproveIndex();
            shareRepository.deleteById(approveIndex);
        }
    }

    @Override
    public void refuseSelectedShares(List<TodoShareApproveQueue> selectedRefuses) {
        // 거절할 todo 하나씩 꺼내기
        for(TodoShareApproveQueue selectedRefuse : selectedRefuses){
            // 대기큐에서 삭제
            long refuseIndex = selectedRefuse.getApproveIndex();
            shareRepository.deleteById(refuseIndex);
        }
    }

    @Override
    public Member findById(String memberId) {
        Member selectedMember = memberRepository.findByMemberId(memberId);
        if(selectedMember != null){
            return selectedMember;
        } else{
            return null;
        }
    }


}
