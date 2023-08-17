package uhbp.todolist.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "TODO_MEMBER_MANAGE")
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class TodoMemberManage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MANAGE_INDEX")
    private Long manageIndex;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_INDEX", nullable = false, referencedColumnName="MEMBER_INDEX")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TODO_INDEX", nullable = false, referencedColumnName="TODO_INDEX")
    private TodoList todoList;

    private TodoMemberManage(Member member, TodoList todoList) {
        this.member = member;
        this.todoList = todoList;
    }

    public static TodoMemberManage todoMemberManageFactory(Member member, TodoList todoList){
        return new TodoMemberManage(member, todoList);
    }
}
