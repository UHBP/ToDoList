package uhbp.todolist.Service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uhbp.todolist.exception.NoSuchMemberException;
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

    /**
     * Id 와 Pw 로 해당하는 멤버가 가입되어 있는지 여부를 반환
     *
     * @param memberId
     * @param memberPw
     * @return
     */
    @Override
    public Boolean isMemberExist(String memberId, String memberPw) {
        Member findMember = memberRepository.findByMemberId(memberId);
        if (findMember == null) {
            return false;
        } else {
            if (encrypter.isMatch(memberPw, findMember.getMemberPw())) {
                return true;
            }
        }
        return false;
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

    /**
     * 로그인을 처리하는 비즈니스 로직
     *
     * @param inputId
     * @param inputPw
     * @return
     */
    @Override
    public Member login(String inputId, String inputPw) throws NoSuchMemberException {
        Member byMemberId = memberRepository.findByMemberId(inputId);
        if (byMemberId != null) {
            if (encrypter.isMatch(inputPw, byMemberId.getMemberPw())) {
                return byMemberId;
            }
        }
        throw new NoSuchMemberException();
    }

    /**
     * 입력된 멤버 아이디로 조회했을 떄, 해당하는 계정이 있으면 true, flase
     * @param memberId
     * @return
     */
    @Override
    public boolean isDuplicateMemberId(String memberId) {
        Member byMemberId = memberRepository.findByMemberId(memberId);
        return byMemberId != null ? true : false;
    }
}
