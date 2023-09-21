function selectCategory(category) {
    // 선택한 카테고리 값을 서버로 전송
    $.ajax({
        type: 'GET',
        url: '/todo/selectCategory',
        data: { category: category },
        success: function (data) {
            // 성공적으로 데이터를 받았을 때
            $('#todoListContainer').html(data);
            console.log('카테고리 필터링 성공');
        },
        error: function () {
            console.error('카테고리 필터링 실패');
        }
    });
}