//dots 클릭시 메뉴 나오는 것

    //menuItem 전부 닫은 상태로 처음 index 불러오려고
    let menuItemOpen = null;

    document.body.addEventListener('click', function(event) {
        // .dots 클릭시의 로직
        if (event.target.matches('.dots')) {
            let menuItem = event.target.parentElement.nextElementSibling;

            //dots가 많으니까 클릭한 dots가 기존에 클릭해둔 dots이거나 dots 외부 클릭 시에 메뉴창 닫으려고
            if (menuItemOpen && menuItemOpen !== menuItem) {
                menuItemOpen.style.display = "none";
            }

            if (menuItem && menuItem.classList.contains("ToDoListSetting")) {
                if (menuItem.style.display === "none" || menuItem.style.display === "") {
                    menuItem.style.display = "block";
                   menuItemOpen = menuItem;
                } else {
                    menuItem.style.display = "none";
                    menuItemOpen = null;
                }
           }

          event.stopPropagation(); // 이 이벤트가 상위 Dom에 적용 안되게 막음
      } else if (menuItemOpen) {
           // menuItem 외부 클릭 시 menuItem 숨김
          menuItemOpen.style.display = "none";
         menuItemOpen = null;
       }
    });