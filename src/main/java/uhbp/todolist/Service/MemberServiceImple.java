package uhbp.todolist.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uhbp.todolist.common.tool.StringEncrypter;
import uhbp.todolist.domain.Member;
import uhbp.todolist.dto.MemberJoinForm;
import uhbp.todolist.repository.MemberRepository;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class MemberServiceImple implements MemberService {
    
    StringEncrypter encrypter;
    MemberRepository memberRepository;
    
    @Override
    public void JoinMember(MemberJoinForm form) {
        String encryptPw = form.getMemberPw();
        Member member = Member.memberFactory(form.getMemberId(), encryptPw, form.getMemberNickName(), LocalDate.now());
        Member savedMember = memberRepository.save(member);
    }
}
