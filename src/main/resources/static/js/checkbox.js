//TodoList의 체크박스 체크됐을때 상태 조절
    function checkboxFunction() {
      function applyStylesByCheckboxState(checkbox) {
        const todoList = checkbox.closest(".ToDoList");
        const title = todoList.querySelector(".TodoTitle");
        const listdate = todoList.querySelector(".DueDate");
        const listdatespan = listdate.querySelector("span");
        const completeButton = todoList.querySelector(".complete");

        if (checkbox.checked) {
          title.style.textDecoration = "line-through";
          title.style.color = "gray";
          listdate.style.textDecoration = "line-through";
          listdate.style.color = "gray";
          completeButton.textContent = "완료취소";
          if (listdatespan) {
            listdatespan.style.textDecoration = "none";
          }
        } else {
          title.style.textDecoration = "none";
          title.style.color = "";
          listdate.style.textDecoration = "none";
          listdate.style.color = "";
          completeButton.textContent = "완료하기";
          if (listdatespan) {
            listdatespan.style.textDecoration = "none";
          }
        }
      }

      // 체크박스 상태 변경 이벤트 리스너
      document.querySelectorAll(".ListCheckbox").forEach(function (checkbox) {
        checkbox.addEventListener("change", function () {
          applyStylesByCheckboxState(this);
        });
      });

      // 완료하기 버튼 클릭 이벤트 리스너
      document.querySelectorAll(".complete").forEach(function (element) {
        element.addEventListener("click", function () {
          const parentToDoList = element.closest(".ToDoList");
          const checkbox = parentToDoList.querySelector(".ListCheckbox");
          if (element.textContent === "완료하기") {
            checkbox.checked = true;
          } else {
            checkbox.checked = false;
          }
          applyStylesByCheckboxState(checkbox);
        });
      });
    }

  document.addEventListener("DOMContentLoaded", checkboxFunction);