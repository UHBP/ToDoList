package uhbp.todolist.domain;

import lombok.*;
import org.hibernate.annotations.Fetch;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * TodoListEntity
 */
@Entity
@Table(name = "TODO_LIST")
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TodoList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TODO_INDEX")
    private Long todoIndex;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CATEGORY_INDEX", nullable = false, referencedColumnName = "CATEGORY_INDEX")
    private TodoCategory categoryIndex;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TODO_GENMEMBER_INDEX", nullable = false, referencedColumnName = "MEMBER_INDEX")
    private Member todoGenmemberIndex;

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

    /**
     * todoListFactory에 사용되는 생성자
     */
    private TodoList(Long categoryIndex, Long todoGenmemberIndex, String todoTitle, String todoContent, LocalDate todoGendate, LocalDate todoUpdatedate, boolean todoIspinned, LocalDate todoDuedate) {
        this.categoryIndex = categoryIndex;
        this.todoGenmemberIndex = todoGenmemberIndex;
        this.todoTitle = todoTitle;
        this.todoContent = todoContent;
        this.todoGendate = todoGendate;
        this.todoUpdatedate = todoUpdatedate;
        this.todoIspinned = todoIspinned;
        this.todoDuedate = todoDuedate;
    }

    /**
     * TodoList Entity를 생성하기 위한 정적 팩토리
     */
    public static TodoList todoListFactory(Long categoryIndex, Long todoGenmemberIndex, String todoTitle, String todoContent, LocalDate todoGendate, LocalDate todoUpdatedate, boolean todoIspinned, LocalDate todoDuedate) {
        return new TodoList(categoryIndex, todoGenmemberIndex, todoTitle, todoContent, todoGendate, todoUpdatedate, todoIspinned, todoDuedate);
    }
}
