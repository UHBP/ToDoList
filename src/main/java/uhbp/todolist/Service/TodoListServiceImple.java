package uhbp.todolist.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uhbp.todolist.domain.TodoList;
import uhbp.todolist.repository.TodoListRepository;
import uhbp.todolist.exception.TodoListNotFoundException;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class TodoListServiceImple implements TodoListService {
    private final TodoListRepository todoListRepository;

    // 할일 목록 저장
    @Override
    public void saveTodoList(TodoList todoList) {
        todoListRepository.save(todoList);
    }

    // 할일 목록 조회
    @Override
    public TodoList getTodoListById(Long todoIndex)  {
        return todoListRepository.findById(todoIndex)
                .orElseThrow(() -> new TodoListNotFoundException("해당 할 일을 찾을 수 없습니다."));
    }

    // 할일 목록 삭제
    @Override
    public void deleteTodoListById(Long todoIndex) {
        todoListRepository.deleteById(todoIndex);
    }
}
