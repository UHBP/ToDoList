//제목 클릭 시 생기는 모달에서 수정일 있을 시 생성일 안 보이고, 없을 시 생성일 보이게 하기
  window.addEventListener("DOMContentLoaded", (event) => {
    // 수정일 있을 경우
    let updateDate = document.querySelector(".TodoUpdateDate");
    let genDate = document.querySelector(".TodoGenDate");
    if (updateDate && updateDate.textContent.trim() !== "") {
      // 생성일 없애기
      if (genDate) {
        updateDate.style.display = "block";
        genDate.style.display = "none";
      }
    } else {
      // 수정일의 내용이 없을 경우
      if (genDate) {
        updateDate.style.display = "none";
        genDate.style.display = "block";
      }
    }
  });