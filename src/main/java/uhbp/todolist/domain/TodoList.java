package uhbp.todolist.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * TodoListEntity
 */
@Entity
@Table(name = "TODO_LIST")
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class TodoList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TODO_INDEX")
    private Long todoIndex;

    @Enumerated(EnumType.STRING)
    @Column(name = "TODO_CATEGORY", nullable = false)
    private TodoCategory todoCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TODO_GENMEMBER_INDEX", nullable = false, referencedColumnName = "MEMBER_INDEX")
    private Member member;

    @Column(name = "TODO_TITLE", nullable = false)
    private String todoTitle;

    @Column(name = "TODO_CONTENT", nullable = true)
    private String todoContent;

    @Column(name = "TODO_GENDATE", nullable = false)
    private LocalDate todoGendate;

    @Column(name = "TODO_UPDATEDATE", nullable = true)
    private LocalDate todoUpdatedate;

    @Column(name = "TODO_ISPINNED", nullable = false)
    private boolean todoIspinned;

    @Column(name = "TODO_DUEDATE", nullable = false)
    private LocalDate todoDuedate;

    @Column(name = "TODO_ISFINISHED", nullable = false)
    private boolean todoIsfinished;

    /**
     * todoListFactory에 사용되는 생성자
     */
    private TodoList(TodoCategory todoCategory, Member member, String todoTitle, String todoContent, LocalDate todoGendate, LocalDate todoUpdatedate, boolean todoIspinned, LocalDate todoDuedate, boolean todoIsfinished) {
        this.todoCategory = todoCategory;
        this.member = member;
        this.todoTitle = todoTitle;
        this.todoContent = todoContent;
        this.todoGendate = todoGendate;
        this.todoUpdatedate = todoUpdatedate;
        this.todoIspinned = todoIspinned;
        this.todoDuedate = todoDuedate;
        this.todoIsfinished = todoIsfinished;
    }

    /**
     * TodoList Entity를 생성하기 위한 정적 팩토리
     */
    public static TodoList todoListFactory(TodoCategory todoCategory, Member member, String todoTitle, String todoContent, LocalDate todoGendate, LocalDate todoUpdatedate, boolean todoIspinned, LocalDate todoDuedate, boolean todoIsfinished) {
        return new TodoList(todoCategory, member, todoTitle, todoContent, todoGendate, todoUpdatedate, todoIspinned, todoDuedate, todoIsfinished);
    }

    // 할일 수정
    public void updateTodo(String todoTitle, String todoContent, LocalDate todoDuedate, TodoCategory todoCategory) {
        this.todoTitle = todoTitle;
        this.todoContent = todoContent;
        this.todoDuedate = todoDuedate;
        this.todoCategory = todoCategory;
        this.todoUpdatedate = LocalDate.now();
    }

}
