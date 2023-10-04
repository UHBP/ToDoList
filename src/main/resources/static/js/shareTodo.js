// 모달창 2 - 공유하기 클릭 시
document.addEventListener("DOMContentLoaded", function () {
    var modal = document.getElementById("shareModal");
    var shares = document.querySelectorAll(".share");
    var close = document.getElementsByClassName("close2-shape")[0];
    var todoIndex;  // todoIndex를 전역변수로 선언하여 사용가능하도록

    // 각 share 버튼에 클릭 이벤트 연결
    shares.forEach(function(share) {
        share.addEventListener("click", function() {
            // 현재 클릭한 share 버튼의 관련 TodoIndex 값 가져오기
            todoIndex = $(this).closest('.ToDoList').find('.TodoIndex').text();

            $.ajax({
                url: '/',
                success: function (data) {
                    modal.style.display = "block";
                },
                error: function (error) {
                    alert("다시 시도해주세요.");
                }
            });
        });
    });

    // x 혹은 외부 클릭 시 모달 꺼짐
    close.onclick = function () {
        $('.search-input').val('');
        $('.search-result').empty();
        modal.style.display = "none";
    };

    //모달 외부 클릭 시 모달 꺼짐
    window.onclick = function (event) {
        if (event.target == modal) {
            $('.search-input').val('');
            $('.search-result').empty();
            modal.style.display = "none";
        }
    };

    // todoIndex와 선택한 회원의 아이디를 컨트롤러로 넘기는 코드
    $('.shareButton').click(function (e) {
        e.preventDefault(); // 폼 기본 제출 동작 방지

        // 선택된 회원들의 아이디 값을 가져와 배열로 저장
        var selectedMembers = [];
        $('input[name="selectedMembers"]:checked').each(function () {
            selectedMembers.push($(this).val());
        });

        // ajax로 보낼 data 객체 생성
        var dataToSend = {
            'todoIndex': todoIndex,  // 전역변수로 선언한 todoIndex
            'selectedMembers': selectedMembers
        };

        // 선택된 회원들과 todoIndex 정보를 컨트롤러로 전송
        $.ajax({
            url: '/share/selected',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(dataToSend),
            success: function (response) {
                alert("공유 요청이 전송되었습니다.");
                $('.search-input').val('');
                $('.search-result').empty();
                modal.style.display = "none";
            },
            error: function (error) {
                alert("실패하였습니다. 다시 시도해주세요.");
                $('.search-input').val('');
                $('.search-result').empty();
            }
        });
    });
});