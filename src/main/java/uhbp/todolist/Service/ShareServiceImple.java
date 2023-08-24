package uhbp.todolist.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uhbp.todolist.domain.Member;
import uhbp.todolist.repository.MemberRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ShareServiceImple implements ShareService {

    @Autowired
    MemberRepository memberRepository;

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
    public Member findById(String memberId) {
        Member selectedMember = memberRepository.findByMemberId(memberId);
        if(selectedMember != null){
            return selectedMember;
        } else{
            return null;
        }
    }
}
