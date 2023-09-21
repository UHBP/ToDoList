package uhbp.todolist.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * TodoShareApproveQueueEntity
 */
@Table(name = "TODO_SHARE_APPROVE_QUEUE")
@Entity
@Getter
@EqualsAndHashCode
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TodoShareApproveQueue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "APPROVE_INDEX")
    private Long approveIndex;

    @ManyToOne
    @JoinColumn(name = "TODO_INDEX", nullable = false)
    private TodoList todoIndex;

    @ManyToOne
    @JoinColumn(name = "SHAERED_MEMBER_INDEX", nullable = false)
    private Member sharedMemberIndex;

    @ManyToOne
    @JoinColumn(name = "SHARE_MEMBER_INDEX", nullable = false)
    private Member shareMemberIndex;

    @Column(name = "APPORVE_DATE", nullable = false)
    private LocalDate approveDate;

    /**
     * 생성자
     */
    private TodoShareApproveQueue(TodoList todoIndex, Member sharedMemberIndex, Member shareMemberIndex, LocalDate approveDate) {
        this.todoIndex = todoIndex;
        this.sharedMemberIndex = sharedMemberIndex;
        this.shareMemberIndex = shareMemberIndex;
        this.approveDate = approveDate;
    }

    /**
     * TodoShareApproveQueue Entity를 생성하기 위한 정적 팩토리
     */
    public static TodoShareApproveQueue todoShareApproveQueueFactory(TodoList todoIndex, Member sharedMemberIndex, Member shareMemberIndex, LocalDate approveDate) {
        return new TodoShareApproveQueue(todoIndex, sharedMemberIndex, shareMemberIndex, approveDate);
    }
}
