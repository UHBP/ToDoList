package uhbp.todolist.domain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import uhbp.todolist.repository.MemberRepository;
import uhbp.todolist.repository.TodoCategoryRepository;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Slf4j
@RequiredArgsConstructor
class TodoCategoryTest {

    public TodoCategoryRepository todoCategoryRepository;

    @Test
    void todoCategoryFactory() {
        // given
        String expectedCategoryName = "공부";

        // when
        TodoCategory todoCategory = TodoCategory.todoCategoryFactory(expectedCategoryName);

        // then
        assertThat(todoCategory.getCategoryName()).isEqualTo(expectedCategoryName);

        TodoCategory save = todoCategoryRepository.save(todoCategory);

        log.info("save = {}", save);
        TodoCategory foundCategory = todoCategoryRepository.findById(save.getCategoryIndex()).get();

        assertThat(foundCategory).isEqualTo(save);
    }
}
