// Todo를 공유할 회원의 아이디 검색하는 코드
$(document).ready(function () {
    // 검색 이미지 클릭 이벤트 감지
    $('#search-image').click(function () {
        $('.search-result').empty();
        $('.search-result').css('visibility', 'visible');
        // 입력된 아이디 값 가져오기
        var inputId = $('.search-input').val();

        // 컨트롤러로 ajax 요청 보내기
        $.ajax({
            url: '/share/search',
            type: 'POST',
            data: { searchId: inputId },
            success: function (response) {
                console.log('서버 응답: ', response);

                // 검색 결과가 없는 경우
                if (response === null || response.length === 0) {
                    alert("검색 결과가 없습니다. 다시 검색해주세요.");
                }

                // 검색 결과가 표시될 영역 선택
                var searchResult = $('.search-result');

                // 컨트롤러부터 받아온 Member 객체들을 순회하며 표시
                response.forEach(function (member) {
                    var memberId = member.memberId;
                    var memberNickname = member.memberNickname;

                    // 각 Member 객체의 정보를 추가할 HTML 요소 생성
                    var resultContent = $('<div class="search-result-content"></div>');
                    var checkbox = $('<div class="search-checkbox"><input type="checkbox" name="selectedMembers" value="' + memberId + '"/></div>');
                    var idElement = $('<div class="search-id">' + memberId + '</div>');
                    var nicknameElement = $('<div class="search-nickname">' + memberNickname + '</div>');

                    // 생성한 HTML 요소들을 결과 영역에 추가
                    resultContent.append(checkbox);
                    resultContent.append(idElement);
                    resultContent.append(nicknameElement);
                    searchResult.append(resultContent);
                });
            },
            error: function (error) {
                console.error('에러: ', error);
            }
        });
    });
});