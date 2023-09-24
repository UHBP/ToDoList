// title클릭 시 나오는 모달의 설정 - 수정 클릭 시
  document.addEventListener("DOMContentLoaded", function () {
    const editButton = document.querySelector(".TodoEdit");
    const completeButton = document.querySelector(".TodoEditSubmit");
    const contentDiv = document.querySelector(".TodoContent");
    const contentTextarea = document.querySelector(".TodoContentEdit");
    const deleteButton = document.querySelector(".TodoDelete");
    const cancelButton = document.querySelector(".TodoCancel");

    editButton.addEventListener("click", function () {
      // 기존 내용을 textarea로 복사
      contentTextarea.value = contentDiv.textContent;

      // 요소를 보이거나 숨김
      contentDiv.style.display = "none";
      contentTextarea.style.display = "block";
      editButton.style.display = "none";
      deleteButton.style.display = "none";
      completeButton.style.display = "block";
      cancelButton.style.display = "block";
    });

    completeButton.addEventListener("click", function () {
      const updatedContent = contentTextarea.value;

      //
      $.ajax({
        url: "/updateTodoContent", // 여기에 서버 엔드포인트 URL을 지정
        method: "POST",
        data: {
          content: updatedContent,
        },
        success: function (response) {
          // 성공 시 .TodoContent 갱신 및 원래 상태로 되돌리기
          contentDiv.textContent = updatedContent;
          contentDiv.style.display = "block";
          contentTextarea.style.display = "none";
          editButton.style.display = "block";
          completeButton.style.display = "none";
          deleteButton.style.display = "block";
          cancelButton.style.display = "none";
          DateFunction();
        },
        error: function (error) {
          console.error("Error updating content:", error);
        },
      });
    });
    cancelButton.addEventListener("click", function () {
      // 수정 취소 클릭 시에 display상태
      contentDiv.style.display = "block";
      contentTextarea.style.display = "none";
      editButton.style.display = "block";
      deleteButton.style.display = "block";
      completeButton.style.display = "none";
      cancelButton.style.display = "none";
    });
  });