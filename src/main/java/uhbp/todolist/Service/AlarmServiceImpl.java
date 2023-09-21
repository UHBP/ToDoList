package uhbp.todolist.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import uhbp.todolist.domain.Member;
import uhbp.todolist.domain.TodoShareApproveQueue;
import uhbp.todolist.repository.MemberRepository;
import uhbp.todolist.repository.TodoShareApproveQueueRepository;
import uhbp.todolist.session.CookieMemberStore;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import java.io.IOException;
import java.util.List;

import static uhbp.todolist.Controller.SseController.sseEmitters;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class AlarmServiceImpl implements AlarmService{

    private final CookieMemberStore cookieMemberStore;
    @Autowired
    private TodoShareApproveQueueRepository shareRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public void alarmShareEvent(Long loginIndex) {
        // 공유받는 사용자의 Member 객체 불러오기
        // .get() : Optional에서 Member 객체를 추출
        Member sharedMember = memberRepository.findById(loginIndex).get();
        // 현재 로그인한 사용자에게 공유된 todo가 있는지 조회
        List<TodoShareApproveQueue> approvals = shareRepository.findBySharedMemberIndex(sharedMember);

        // 공유된 todo가 있는 경우
        if(approvals != null){
            // 공유 요청이 있다는 알림 보내기
            for(TodoShareApproveQueue approval : approvals){
                // 현재 공유받는 사용자가 서버와 연결된 상태(sseEmitters 저장소에 존재)라면
                if(sseEmitters.containsKey(loginIndex)) {
                    // 해당 emitter를 가져옴
                    SseEmitter emitter = sseEmitters.get(loginIndex);
                    try {
                        // 해당 emitter에 이벤트 발행
                        emitter.send(SseEmitter.event().name("alarm").data("공유 요청이 들어왔습니다."));
                    } catch (Exception e) {
                        sseEmitters.remove(loginIndex);
                    }
                }
            }
        }
    }

    @Override
    public int countShare(HttpServletRequest request) {
        // 현재 로그인한 사용자의 index
        long memberIndex = cookieMemberStore.findValueByKey(request);
        Member loginMember = memberRepository.findById(memberIndex).get();
        int shareCount = shareRepository.findBySharedMemberIndex(loginMember).size();

        return shareCount;
    }
}
