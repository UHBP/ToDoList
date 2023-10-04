package uhbp.todolist.Service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import uhbp.todolist.domain.Member;
import uhbp.todolist.domain.TodoList;
import uhbp.todolist.domain.TodoShareApproveQueue;
import uhbp.todolist.repository.MemberRepository;
import uhbp.todolist.repository.TodoListRepository;
import uhbp.todolist.repository.TodoShareApproveQueueRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
// @ExtendWith(MockitoExtension.class) 어노테이션을 사용하여 목 객체를 초기화하고 주입
class ShareServiceTest {
    // @Mock : 목 객체를 생성하고 설정
    // @InjectMocks : 생성된 목 객체를 자동으로 주입
    @Mock
    private MemberRepository memberRepository;

    @Mock
    private TodoShareApproveQueueRepository shareRepository;

    @Mock
    private TodoListRepository todoRepository;

    @InjectMocks
    private ShareServiceImple shareService;

    @Test
    void testSearchExistingMember() {
        // Given (테스트를 위해 준비하는 과정 / 변수 및 입력값, mock 객체를 정의하는 구문도 given에 포함)
        String searchId = "testId";
        Member mockMember = new Member();
        when(memberRepository.findMember(searchId)).thenReturn(Arrays.asList(mockMember));

        // When (실제로 동작하는 테스트를 실행하는 과정)
        List<Member> searchResult = shareService.search(searchId);

        // Then (테스트를 검증하는 과정)
        assertEquals(1, searchResult.size());
        assertEquals(mockMember, searchResult.get(0));
    }

    @Test
    void testSearchNonExistingMember() {
        // Given
        String searchId = "nonExistingId";
        when(memberRepository.findMember(searchId)).thenReturn(null);

        // When
        List<Member> searchResult = shareService.search(searchId);

        // Then
        assertNull(searchResult); // assertEquals(null, searchResult); 로 대체 가능
    }

    @Test
    void testShareTodo() {
        // Given
        Long loginIndex = 1L;
        Long todoIndex = 1L;
        // "member1"과 "member2"를 원소로 갖는 리스트 생성
        List<String> selectedMembers = Arrays.asList("member1", "member2");

        TodoList mockTodo = new TodoList();
        when(todoRepository.findById(2L)).thenReturn(Optional.of(mockTodo));

        Member loginMember = new Member();
        when(memberRepository.findById(loginIndex)).thenReturn(Optional.of(loginMember));

        Member selectedMember1 = new Member();
        Member selectedMember2 = new Member();
        when(memberRepository.findByMemberId("member1")).thenReturn(selectedMember1);
        when(memberRepository.findByMemberId("member2")).thenReturn(selectedMember2);

        // When
        shareService.shareTodo(loginIndex, todoIndex, selectedMembers);

        // Then
        // save()가 2번(times) 호출되었는지 검증
        verify(shareRepository, times(2)).save(any(TodoShareApproveQueue.class));
    }

    @Test
    void testFindByIdExistingMember() {
        // Given
        String memberId = "existingMember";
        Member mockMember = new Member();
        when(memberRepository.findByMemberId(memberId)).thenReturn(mockMember);

        // When
        Member result = shareService.findById(memberId);

        // Then
        assertEquals(mockMember, result);
    }

    @Test
    void testFindByIdNonExistingMember() {
        // Given
        String memberId = "nonExistingMember";
        when(memberRepository.findByMemberId(memberId)).thenReturn(null);

        // When
        Member result = shareService.findById(memberId);

        // Then
        assertNull(result); // assertEquals(null, result); 로 대체 가능
    }
}