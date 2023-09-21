 // 정렬 관련 Ajax
  document.getElementById('selectOption').addEventListener('change', function () {
      const selectedOption = this.value;

      $.ajax({
          type: 'GET',
          url: '/todo/sort',
          data: { sortingOption: selectedOption },
          success: function (data) {
              $('#todoListContainer').html(data);
              // 정렬 후에도 해당 함수를 다시 호출하여 적용
              setDueDateWarnings();
              setCategoryBackgroundColors();
              console.log('정렬 성공');
          },
          error: function () {
              console.error('정렬 실패');
          }
      });
  });
