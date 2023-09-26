document.addEventListener("DOMContentLoaded", function () {
    var modal = document.getElementById("shareModal");
    var shares = document.querySelectorAll(".share");
    var close = document.getElementsByClassName("close2-shape")[0];

    shares.forEach(function(share) {
        share.addEventListener("click", function() {
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

    close.onclick = function () {
        $('.search-input').val('');
        $('.search-result').empty();
        modal.style.display = "none";
    };

    window.onclick = function (event) {
        if (event.target == modal) {
            $('.search-input').val('');
            $('.search-result').empty();
            modal.style.display = "none";
        }
    };

    $('.shareButton').click(function (e) {
        e.preventDefault();

        var selectedMembers = [];
        $('input[name="selectedMembers"]:checked').each(function () {
            selectedMembers.push($(this).val());
        });

        var todoIndex = $('.TodoIndex').text();
        var dataToSend = {
            'todoIndex': todoIndex,
            'selectedMembers': selectedMembers
        };

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
