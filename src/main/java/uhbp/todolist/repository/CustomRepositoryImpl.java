package uhbp.todolist.repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import uhbp.todolist.domain.*;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

import static uhbp.todolist.domain.QMember.member;

@Repository
@Transactional
public class CustomRepositoryImpl implements CustomRepository {
    @Autowired
    private EntityManager em;

    /**
     * - EntityManager 대신 JPAQueryFactory 적용
     * - QueryDSL 을 통해 생성된 QMember 클래스로 사용자 조회
     * - QMember 클래스 : Member Entity 를 상속
     * @param searchId 검색할 사용자의 아이디 일부
     * @return
     */
    @Override
    public List<Member> findMember(String searchId) {
        // JPAQueryFactory : JPAQuery를 생성해주는 factory 클래스
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        return queryFactory
                .selectFrom(member)
                .where(member.memberId.like("%" + searchId + "%"))
                .fetch();  // fetch() : 조회 대상 전체를 반환
    }


    @Override
    public List<TodoList> findAllByOrderByTodoGendateAsc(Member currentMember) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QTodoList todoList = QTodoList.todoList;

        // OrderSpecifier : Querydsl 라이브러리에서 제공하는 클래스, JPA 쿼리에서 정렬 순서를 지정할 때 사용
        OrderSpecifier<LocalDate> orderByGenDateAsc = todoList.todoGendate.asc();

        // 핀 상단 고정 포함
        return queryFactory.selectFrom(todoList)
                .where(todoList.member.eq(currentMember))
                .orderBy(todoList.todoIspinned.desc(), orderByGenDateAsc)
                .fetch();
    }

    @Override
    public List<TodoList> findAllByOrderByTodoDuedateAsc(Member currentMember) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QTodoList todoList = QTodoList.todoList;
        OrderSpecifier<LocalDate> orderByDueDateAsc = todoList.todoDuedate.asc();

        return queryFactory.selectFrom(todoList)
                .where(todoList.member.eq(currentMember))
                .orderBy(todoList.todoIspinned.desc(), orderByDueDateAsc)
                .fetch();
    }


    @Override
    public List<TodoList> findSharedTodoListsByMember(String memberId) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QTodoList qTodoList = QTodoList.todoList;
        QTodoMemberManage qTodoMemberManage = QTodoMemberManage.todoMemberManage;

        return queryFactory.selectFrom(qTodoList)
                .innerJoin(qTodoMemberManage).on(qTodoList.todoIndex.eq(qTodoMemberManage.todoList.todoIndex))
                .where(qTodoMemberManage.member.memberId.eq(memberId))
                .fetch();
    }

}
