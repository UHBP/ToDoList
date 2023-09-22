//모달창 1 - Todo 추가 클릭 시 열리는 것
  document.addEventListener("DOMContentLoaded", function () {
    var modal = document.getElementById("AddModal");
    var title = document.getElementById("addToDoList");
    var modalTitle = document.querySelector(".modalTitle");
    var span = document.getElementsByClassName("close1")[0];

    //#addToDoList(ToDo 추가) 클릭 시
    title.onclick = function () {
      modalTitle.textContent = this.textContent;
      modal.style.display = "block";
    };

    // x 혹은 외부 클릭 시 모달 꺼짐
    span.onclick = function () {
      modal.style.display = "none";
      contentDiv.style.display = "block";
      contentTextarea.style.display = "none";
    };

    //모달 외부 클릭 시 모달 꺼짐
    window.onclick = function (event) {
      if (event.target == modal) {
        modal.style.display = "none";
        contentDiv.style.display = "block";
        contentTextarea.style.display = "none";
      }};

      //모달 외부 클릭 시 모달 꺼짐
      window.onclick = function (event) {
        if (event.target == modal) {
          modal.style.display = "none";
          contentDiv.style.display = "block";
          contentTextarea.style.display = "none";
        }
      };
    });