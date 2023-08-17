package uhbp.todolist.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import javax.persistence.*;

@Entity
@Table(name = "TODO_MEMBER_MANAGE")
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TodoMemberManage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MANAGE_INDEX")
    private Long manageIndex;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_INDEX", nullable = false, referencedColumnName="MEMBER_INDEX")
    private Member memberIndex;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TODO_INDEX", nullable = false, referencedColumnName="TODO_INDEX")
    private TodoList todoIndex;
}