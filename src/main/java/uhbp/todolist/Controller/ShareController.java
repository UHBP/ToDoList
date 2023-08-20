package uhbp.todolist.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import uhbp.todolist.dto.ShareTargetSearch;

@Controller
@RequestMapping("/share")
public class ShareController {

    @ResponseBody
    @RequestMapping("/init")
    public String gotoMain(Model model){
        // 빈 객체 생성하여 form 데이터를 담을 객체 설정
        ShareTargetSearch shareTargetSearch = new ShareTargetSearch();
        model.addAttribute("shareTargetSearch", shareTargetSearch);
        return "index";
    }
}
