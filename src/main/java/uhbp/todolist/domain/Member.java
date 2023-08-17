package uhbp.todolist.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * MemberEntity
 */
@Table(name = "MEMBER")
@Entity
@Getter
@EqualsAndHashCode
@ToString
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
    private String memberNickname;

    @Column(name = "MEMBER_JOINDATE", nullable = false)
    private LocalDate memberJoinDate;

    /**
     * memberFactory에 사용되는 생성자
     */
    private Member(String memberId, String memberPw, String memberNickname, LocalDate memberJoindate) {
        this.memberId = memberId;
        this.memberPw = memberPw;
        this.memberNickname = memberNickname;
        this.memberJoinDate = memberJoindate;
    }

    /**
     * Member Entity를 생성하기 위한 정적 팩토리
     */
    public static Member memberFactory(String memberId, String memberPw, String memberNickname, LocalDate memberJoindate) {
        return new Member(memberId, memberPw, memberNickname, memberJoindate);
    }

    /**
     * 암호화된 PW 를 받아서, 비밀번호를 변경
     */
    public void changePw(String hashedNewPw) {
        this.memberPw = hashedNewPw;
    }
}
