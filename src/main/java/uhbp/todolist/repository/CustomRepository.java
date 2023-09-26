package uhbp.todolist.repository;

import uhbp.todolist.domain.Member;
import uhbp.todolist.domain.TodoList;
import uhbp.todolist.domain.TodoMemberManage;
import uhbp.todolist.domain.TodoShareApproveQueue;

import java.util.List;

public interface CustomRepository {

    List<Member> findMember(String searchId);

    List<TodoList> findAllByOrderByTodoGendateAsc(Member currentMember);
    List<TodoList> findAllByOrderByTodoDuedateAsc(Member currentMember);

    List<TodoList> findAllByMemberOrSharedMember(Member member, Member sharedMember);

    List<TodoList> findSharedTodoListsByMember(String memberId);

    List<TodoShareApproveQueue> findAlreadyShare(TodoList todo, Member loginMember, Member selectedMember);

    List<TodoMemberManage> findAlreadyShareTodo(TodoList todo, Member selectedMember);
}
