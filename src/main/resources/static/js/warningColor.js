//특정 ㅇㅇ순 정렬할 때 스크립트
      //마감기한 지났을 때 색깔,상태 조정
  function setDueDateWarnings() {
      let toDoLists = document.querySelectorAll(".ToDoList");

      toDoLists.forEach((toDoList) => {
          let checkbox = toDoList.querySelector(".ListCheckbox");
          let dueDateDiv = toDoList.querySelector(".DueDate");

          if (!checkbox.checked) {
              let dueDateValue = new Date(dueDateDiv.textContent);
              let now = new Date();

              if (dueDateValue < now) {
                  let breakTag = document.createElement("br");
                  dueDateDiv.appendChild(breakTag);
                  let overdueNote = document.createElement("span");
                  overdueNote.textContent = "(마감 기한 지남)";
                  overdueNote.style.color = "red";
                  dueDateDiv.appendChild(overdueNote);
              }
          }
      });
  }

      //카테고리 종류에 따른 색깔 지정
  function setCategoryBackgroundColors() {
      var categoryElements = document.querySelectorAll('.ToDoList .Category');

      function setCategoryBackgroundColor(categoryElement) {
          let category = categoryElement.textContent.trim();

          switch (category) {
              case "STUDY":
                  categoryElement.style.backgroundColor = "orange";
                  categoryElement.textContent = "공부";
                  break;
              case "EXERCISE":
                  categoryElement.style.backgroundColor = "rgb(130, 186, 146)";
                  categoryElement.textContent = "운동";
                  break;
              case "APPOINTMENT":
                  categoryElement.style.backgroundColor = "skyblue";
                  categoryElement.textContent = "약속";
                  break;
              case "OTHER":
                  categoryElement.style.backgroundColor = "midnightblue";
                  categoryElement.textContent = "그 외";
                  break;
              default:
                  categoryElement.style.backgroundColor = "";
          }
      }

      categoryElements.forEach(function (categoryElement) {
          setCategoryBackgroundColor(categoryElement);
      });
  }

  // 페이지가 처음 로드될 때 적용
  document.addEventListener("DOMContentLoaded", function () {
      warningAndColor();
  });

  function warningAndColor() {
    setDueDateWarnings();
    setCategoryBackgroundColors();
  }