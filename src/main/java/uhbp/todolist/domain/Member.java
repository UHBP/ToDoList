package uhbp.todolist.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Table(name = "MEMBER")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_INDEX")
    private Long memberIndex;

    @Column(name = "MEMBER_ID", unique = true, nullable = false)
    private String memberId;

    @Column(name = "MEMBER_PW", nullable = false)
    private String memberPw;

    @Column(name = "MEMBER_NICKNAME", unique = true, nullable = false)
    private String memberNicname;

    @Column(name = "MEMBER_JOINDATE", nullable = false)
    private LocalDate memberJoinDate;

    /**
     * Member Entity를 생성하기 위한 정적 팩토리
     *
     * @param memberIndex
     * @param memberId
     * @param memberPw
     * @param memberNickname
     * @param memberJoindate
     * @return Member
     */
    public static Member memberFactory(Long memberIndex, String memberId, String memberPw, String memberNickname, LocalDate memberJoindate) {
        return new Member(memberIndex, memberId, memberPw, memberNickname, memberJoindate);
    }

    /**
     * 암호화된 PW 를 받아서, 비밀번호를 변경
     * @param hashedNewPw
     */
    public void changePw(String hashedNewPw){
        this.memberPw = hashedNewPw;
    }
}
