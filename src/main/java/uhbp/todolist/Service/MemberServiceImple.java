package uhbp.todolist.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uhbp.todolist.common.tool.StringEncrypter;
import uhbp.todolist.domain.Member;
import uhbp.todolist.dto.MemberJoinForm;
import uhbp.todolist.repository.MemberRepository;

import java.time.LocalDate;

@Slf4j
@Service
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

    /**
     * 회원 가입 후, 해당 회원의 기본키를 반환
     */
    @Override
    public int JoinMember(MemberJoinForm form) {
        String encryptPw = encrypter.doHash(form.getMemberPw());
        Member member = Member.memberFactory(form.getMemberId(), encryptPw, form.getMemberNickName(), LocalDate.now());
        memberRepository.save(member);
        log.info("savedMember = {}", member);
        return member.getMemberIndex().intValue();
    }
}
