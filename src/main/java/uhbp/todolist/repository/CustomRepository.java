package uhbp.todolist.repository;

import uhbp.todolist.domain.Member;

import java.util.List;

public interface CustomRepository {

    List<Member> findMember(String searchId);
}
