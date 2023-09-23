//title 클릭 시 모달창 보여주는 스크립트
    // DOM 캐싱 (문서의 요소를 변수에 저장)
const modal = document.getElementById("titleModal");
const modalTitle = document.querySelector(".modalTitle");
const span = document.getElementsByClassName("close")[0];
const todoIndex = document.querySelector("#todoIndex").value;
const editButton = document.querySelector(".TodoEdit");
const completeButton = document.querySelector(".TodoEditSubmit");
const contentDiv = document.querySelector(".TodoContent");
const contentTextarea = document.querySelector(".TodoContentEdit");
const deleteButton = document.querySelector(".TodoDelete");
const cancelButton = document.querySelector(".TodoCancel");

document.body.addEventListener("click", function(event) {
    // .TodoTitle 클릭 시 모달창을 연다.
    if (event.target.matches('.TodoTitle')) {
        modalTitle.textContent = event.target.textContent;
        modal.style.display = "block";
    }

    // x 혹은 모달 외부 클릭 시 모달창을 닫는다.
    if (event.target === modal || event.target.matches('.close-shape')) {
        modal.style.display = "none";
        resetModal();
    }
});

// 수정일 있을 경우 생성일을 숨기고, 없을 경우 생성일을 보여준다.
function checkAndUpdateDateDisplay() {
    const updateDate = document.querySelector(".TodoUpdateDate");
    const genDate = document.querySelector(".TodoGenDate");

    if (updateDate && updateDate.textContent.trim() !== "") {
        if (genDate) {
            updateDate.style.display = "block";
            genDate.style.display = "none";
        }
    } else {
        if (genDate) {
            updateDate.style.display = "none";
            genDate.style.display = "block";
        }
    }
}

function resetModal() {
    contentDiv.style.display = "block";
    contentTextarea.style.display = "none";
    editButton.style.display = "block";
    deleteButton.style.display = "block";
    completeButton.style.display = "none";
    cancelButton.style.display = "none";
}

// 수정 버튼 클릭시의 동작을 정의
editButton.addEventListener("click", function() {
    contentTextarea.value = contentDiv.textContent;
    contentDiv.style.display = "none";
    contentTextarea.style.display = "block";
    editButton.style.display = "none";
    deleteButton.style.display = "none";
    completeButton.style.display = "block";
    cancelButton.style.display = "block";
});

// 수정 완료 버튼 클릭시의 동작을 정의
completeButton.addEventListener("click", function() {
    const updatedContent = contentTextarea.value;
    $.ajax({
        url: `/todo/update/${todoIndex}`,
        method: "POST",
        data: {
            todoIndex: todoIndex,
            content: updatedContent,
        },
        success: function(response) {
            contentDiv.textContent = updatedContent;
            resetModal();
        },
        error: function(error) {
            console.error("내용 업데이트 중 오류:", error);
        },
    });
});

// 취소 버튼 클릭시의 동작을 정의
cancelButton.addEventListener("click", resetModal);