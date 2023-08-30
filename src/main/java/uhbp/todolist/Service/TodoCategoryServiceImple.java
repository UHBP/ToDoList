package uhbp.todolist.Service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uhbp.todolist.domain.TodoCategory;
import uhbp.todolist.exception.TodoListNotFoundException;
import uhbp.todolist.repository.TodoCategoryRepository;

@Slf4j
@Service
@Transactional
public class TodoCategoryServiceImple implements TodoCategoryService {
    @Autowired
    private final TodoCategoryRepository todoCategoryRepository;

    @Autowired
    public TodoCategoryServiceImple(TodoCategoryRepository todoCategoryRepository) {
        this.todoCategoryRepository = todoCategoryRepository;
    }

    public TodoCategory getTodoCategoryById(Long categoryIndex) {
        return todoCategoryRepository.findById(categoryIndex)
                .orElseThrow(() -> new TodoListNotFoundException("해당 카테고리를 찾을 수 없습니다."));
    }
}
