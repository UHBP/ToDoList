package uhbp.todolist.Controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uhbp.todolist.Service.ShareServiceImple;
import uhbp.todolist.domain.Member;
import uhbp.todolist.domain.TodoShareApproveQueue;
import uhbp.todolist.dto.MemberInfo;
import uhbp.todolist.dto.ShareRequestData;
import uhbp.todolist.exception.SharedTodoNotFoundException;
import uhbp.todolist.session.CookieMemberStore;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/share")
public class ShareController {

    private final ShareServiceImple shareService;
    private final CookieMemberStore cookieMemberStore;

    @PostMapping("/search")
    @ResponseBody
    public ResponseEntity<List<Member>> shareTargetSearch(@RequestParam("searchId") String searchId, Model model){
        log.info("searchTargetId = {}", searchId);
        // 사용자가 입력한 아이디 검색
        List<Member> searchMember = shareService.search(searchId);
        return ResponseEntity.ok(searchMember);
    }

    @PostMapping("/selected")
    public ResponseEntity<String> shareSelectedMembers(@RequestBody ShareRequestData requestData, HttpServletRequest request){
        // 현재 로그인한 사용자의 index
        Long loginIndex = cookieMemberStore.findValueByKey(request);

        // 공유할 todo의 index
        Long todoIndex = requestData.getTodoIndex();
        log.info("index = {}", todoIndex);

        // 체크박스 선택된 사용자들의 아이디 리스트
        List<String> selectedMembers = requestData.getSelectedMembers();

        // 현재 로그인한 사용자의 loginIndex, 공유할 todo의 index, 공유받을 사용자들의 아이디 리스트 대기큐에 저장
        //shareService.shareTodo(loginIndex, todoIndex, selectedMembers);

        String result = "공유 완료";
        return ResponseEntity.ok(result);
    }

    @PostMapping("/getSharedTodo")
    @ResponseBody
    // <?>는 와일드카드 타입으로, 다양한 데이터 유형을 반환할 수 있어 유연한 처리가 가능함
    public ResponseEntity<?> getSharedTodo(HttpServletRequest request){
        // 현재 로그인한 사용자의 index
        Long loginIndex = cookieMemberStore.findValueByKey(request);
        log.info("로그인 사용자 index = {}", loginIndex);

        // 현재 로그인한 사용자에게 공유된 todo가 있는지 확인
        List<TodoShareApproveQueue> sharedTodo = shareService.getSharedTodo(loginIndex);
        if(sharedTodo.isEmpty()){
            return ResponseEntity.ok("새로운 알림이 없습니다.");
        } else{
            log.info("공유목록 = {}", sharedTodo);
            return ResponseEntity.ok(sharedTodo);
        }
    }

    @PostMapping("/approve")
    @ResponseBody
    public String approveSelectedShares(@RequestParam("selectedShares") List<TodoShareApproveQueue> selectedShares, HttpServletRequest request){
        System.out.println("선택된 공유 목록: " + selectedShares);
        shareService.approveSelectedShares(selectedShares, request);

        return "승인 완료";
    }

    @PostMapping("/refuse")
    @ResponseBody
    public String refuseSelectedShares(@RequestParam("selectedRefuses") List<TodoShareApproveQueue> selectedRefuses){
        shareService.refuseSelectedShares(selectedRefuses);

        return "거절 완료";
    }

}

