package uhbp.todolist.Service;

import uhbp.todolist.domain.TodoList;

public interface TodoListService {
    void saveTodoList(TodoList todoList);

    TodoList getTodoListById(Long todoIndex);

    void deleteTodoListById(Long todoIndex);
}
