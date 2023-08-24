package uhbp.todolist.Controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uhbp.todolist.Service.ShareServiceImple;
import uhbp.todolist.domain.Member;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/share")
public class ShareController {

    private final ShareServiceImple shareService;

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
    public ResponseEntity<String> shareSelectedMembers(@RequestParam("selectedMembers") String[] selectedMembers){
        // 체크박스 선택된 사용자의 아이디를 통해 해당 사용자의 Member 객체 불러오기
        for(String memberId : selectedMembers){
            log.info("선택된 아이디 = {}", memberId);
            Member selectedMember = shareService.findById(memberId);
            log.info("선택된 회원의 정보 = {}", selectedMember);

            // 불러온 Member 객체에서 memberIndex와 현재 로그인 중인 회원의 memberIndex, 선택한 todo의 index 대기큐에 저장하기
        }
        String result = "공유 완료";
        return ResponseEntity.ok(result);
    }
}
