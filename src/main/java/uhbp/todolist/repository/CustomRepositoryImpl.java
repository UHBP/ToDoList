package uhbp.todolist.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import uhbp.todolist.domain.Member;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
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
}
