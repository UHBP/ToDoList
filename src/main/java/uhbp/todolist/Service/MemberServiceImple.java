package uhbp.todolist.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uhbp.todolist.common.tool.StringEncrypter;
import uhbp.todolist.domain.Member;
import uhbp.todolist.dto.MemberJoinForm;
import uhbp.todolist.repository.MemberRepository;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberServiceImple implements MemberService {

    @Autowired
    StringEncrypter encrypter;

    @Autowired
    MemberRepository memberRepository;

    @Override
    public Boolean isMemberExtist(String memberId, String memberPw) {
        return null;
    }

    @Override
    public void JoinMember(MemberJoinForm form) {
        String encryptPw = encrypter.doHash(form.getMemberPw());
        Member member = Member.memberFactory(form.getMemberId(), encryptPw, form.getMemberNickName(), LocalDate.now());
        memberRepository.save(member);
    }
}
