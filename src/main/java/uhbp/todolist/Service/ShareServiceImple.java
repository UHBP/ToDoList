package uhbp.todolist.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uhbp.todolist.domain.Member;
import uhbp.todolist.domain.TodoList;
import uhbp.todolist.domain.TodoShareApproveQueue;
import uhbp.todolist.repository.MemberRepository;
import uhbp.todolist.repository.TodoListRepository;
import uhbp.todolist.repository.TodoShareApproveQueueRepository;
import uhbp.todolist.session.CookieMemberStore;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class ShareServiceImple implements ShareService {

    private final AlarmServiceImpl alarmService;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    TodoShareApproveQueueRepository shareRepository;

    @Autowired
    TodoListRepository todoRepository;

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
    public void shareTodo(Long loginIndex, String todoIndex, List<String> selectedMembers) {
        // String 타입의 todoIndex를 Long 타입으로 변환
        Long todoLong = Long.parseLong(todoIndex);
        // todoIndex를 통해 해당 TodoList 객체 불러오기
        TodoList todo = todoRepository.findById(todoLong).get();

        // 현재 로그인한 사용자의 Member 객체 불러오기
        Member loginMember = memberRepository.findById(loginIndex).get();

        // 체크박스 선택된 사용자 아이디 하나씩 꺼내기
        for(String memberId : selectedMembers){
            //log.info("선택된 아이디 = {}", memberId);
            // 사용자의 아이디를 통해 해당 사용자의 Member 객체 불러오기
            Member selectedMember = findById(memberId);
            log.info("선택된 회원의 정보 = {}", selectedMember);

            // 팩토리를 통해 Entity 생성
            TodoShareApproveQueue shareTodo = TodoShareApproveQueue.todoShareApproveQueueFactory(todo, selectedMember, loginMember, LocalDate.now());
            // 위에서 생성한 Entity를 DB에 저장
            shareRepository.save(shareTodo);

            // sse 알림
            alarmService.alarmShareEvent(loginIndex);
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
