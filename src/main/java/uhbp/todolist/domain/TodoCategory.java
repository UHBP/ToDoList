package uhbp.todolist.domain;


import lombok.*;

import javax.persistence.*;

@Table(name = "TODO_CATEGORY")
@Entity
@Getter
@EqualsAndHashCode
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TodoCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CATEGORY_INDEX")
    private Long categoryIndex;

    @Column(name = "CATEGORY_NAME", nullable = false)
    private String categoryName;

//    categoryFactory에 사용되는 생성자
    private TodoCategory(String categoryName){
        this.categoryName = categoryName;
    }

//    cateogry Entity를 생성하기 위한 정적 팩토리
    public static TodoCategory todoCategoryFactory(String categoryName) {
        return new TodoCategory(categoryName);
    }
}


