$(document).ready(function () {
           // 공유 카테고리 버튼 클릭 이벤트 핸들러
           $('#shareCategoryButton').click(function () {
               $.ajax({
                   type: 'GET',
                   url: '/todo/shareCategory',
                   success: function (data) {
                    // 서버로부터 받은 데이터를 #todoListContainer(todoLists)에 적용
                    $('#todoListContainer').html(data);
                   },
                   error: function (error) {
                       console.log('공유 카테고리 실패:', error);
                   }
               });
           });
       });