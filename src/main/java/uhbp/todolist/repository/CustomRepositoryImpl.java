package uhbp.todolist.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import uhbp.todolist.domain.Member;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class CustomRepositoryImpl implements CustomRepository {
    @Autowired
    EntityManager em;
    @Override
    public List<Member> findMember(String searchId) {
        return em.createQuery("SELECT m FROM Member m WHERE m.memberId LIKE :searchId", Member.class)
                .setParameter("searchId", "%"+searchId+"%")
                .getResultList();
    }
}
