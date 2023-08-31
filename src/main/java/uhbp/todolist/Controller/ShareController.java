package uhbp.todolist.Controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uhbp.todolist.Service.ShareServiceImple;
import uhbp.todolist.domain.Member;
import uhbp.todolist.dto.ShareRequestData;
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

    @RequestMapping("/init")
    public String gotoMain(){
        // HomeController로 redirect하면 해당 메소드에서 빈 객체 생성하여 form 데이터를 담을 객체 설정
        return "redirect:/";
    }

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
        String todoIndex = requestData.getTodoIndex();
        log.info("index = {}", todoIndex);

        // 체크박스 선택된 사용자들의 아이디 리스트
        List<String> selectedMembers = requestData.getSelectedMembers();

        // 현재 로그인한 사용자의 loginIndex, 공유할 todo의 index, 공유받을 사용자들의 아이디 리스트 대기큐에 저장
        shareService.shareTodo(loginIndex, todoIndex, selectedMembers);

        String result = "공유 완료";
        return ResponseEntity.ok(result);
    }
}
