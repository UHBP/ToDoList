//모달창 1 - .alarmTitle 클릭시 열리는 것
document.addEventListener("DOMContentLoaded", function () {
    var modal = document.getElementById("alarmModal");
    var span = document.getElementsByClassName("close3")[0];
    var alarm = document.getElementById("sse");

    //.alarmTitle 클릭 시 모달 열림
    alarm.onclick = function () {
        $.ajax({
            type: 'POST',
            url: '/share/getSharedTodo',
            success: function (response) {
                if (typeof response === 'string' && response === "새로운 알림이 없습니다.") {
                    alert(response);
                } else {
                    console.log(response);
                    modal.style.display = "block";

                    // 검색 결과가 표시될 영역 선택
                    var shareResult = $('.share-result');

                    // 컨트롤러부터 받아온 공유 내역들을 순회하며 표시
                    response.forEach(function (share) {
                        var giveNickname = share.shareMemberIndex.memberNickname;
                        var todoTitle = share.todoIndex.todoTitle;
                        var todoContent = share.todoIndex.todoContent;
                        var shareDate = share.approveDate;
                        var approveIndex = share.approveIndex;

                        // 각 공유 내역을 추가할 HTML 요소 생성
                        var shareContent = $('<div class="share-result-content"></div>');
                        var approveCheckbox = $('<div class="approve-checkbox"><input type="checkbox" name="selectedShares" value="' + approveIndex + '"/></div>');
                        var sharedNickname = $('<div class="shared-nickname">' + giveNickname + '</div>');
                        var sharedTitle = $('<div class="shared-title">' + todoTitle + '</div>');
                        var sharedContent = $('<div class="shared-content">' + todoContent + '</div>');
                        var sharedDate = $('<div class="shared-date">' + shareDate + '</div>');

                        // 생성한 HTML 요소들을 결과 영역에 추가
                        shareContent.append(approveCheckbox);
                        shareContent.append(sharedNickname);
                        shareContent.append(sharedTitle);
                        shareContent.append(sharedContent);
                        shareContent.append(sharedDate);
                        shareResult.append(shareContent);
                    });
                }
            },
            error: function (error) {
                alert("다시 시도해주세요.");
            }
        });
    };

    // x 혹은 외부 클릭 시 모달 꺼짐
    span.onclick = function () {
        $('.share-result').empty();
        modal.style.display = "none";
    };

    //모달 외부 클릭 시 모달 꺼짐
    window.onclick = function (event) {
        if (event.target == modal) {
            $('.share-result').empty();
            modal.style.display = "none";
        }
    };


    $(document).ready(function () {
        // 사용자가 승인한 공유 todo를 컨트롤러로 넘기는 코드
        // 승인 버튼 클릭 이벤트 감지
        $('.approveButton').click(function (e) {
            e.preventDefault();

            // 선택된 공유 index를 가져와 배열로 저장
            var selectedShares = [];
            $('input[name="selectedShares"]:checked').each(function () {
                selectedShares.push($(this).val());
            });

            // ajax를 통해 컨트롤러로 전송
            $.ajax({
                url: '/share/approve',
                type: 'POST',
                data: { selectedShares: selectedShares },
                traditional: true, // 배열 데이터를 전송할 때 필요한 옵션
                success: function (response) {
                    alert("ToDoList 공유가 승인되었습니다.");
                    $('.share-result').empty();
                    modal.style.display = "none";
                },
                error: function (error) {
                    alert("실패하였습니다. 다시 시도해주세요.");
                }
            });
        });

        // 사용자가 거절한 공유 todo를 컨트롤러로 넘기는 코드
        // 거절 버튼 클릭 이벤트 감지
        $('.refuseButton').click(function (e) {
            e.preventDefault();

            // 선택된 공유(거절) index를 가져와 배열로 저장
            var selectedRefuses = [];
            $('input[name="selectedShares"]:checked').each(function () {
                selectedRefuses.push($(this).val());
            });

            // ajax를 통해 컨트롤러로 전송
            $.ajax({
                url: '/share/refuse',
                type: 'POST',
                data: { selectedRefuses: selectedRefuses },
                traditional: true,
                success: function (response) {
                    alert("ToDoList 공유가 거절되었습니다.");
                    $('.share-result').empty();
                    modal.style.display = "none";
                },
                error: function (error) {
                    alert("실패하였습니다. 다시 시도해주세요.");
                }
            });
        });
    });
});