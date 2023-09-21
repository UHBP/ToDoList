// SSE를 위한 에미터 등록하는 코드 & 실시간으로 공유알림(alarm) 받는 코드
$(document).ready(function () {
    const eventSource = new EventSource("/sub");

    eventSource.addEventListener("alarm", function (event) {
        let message = event.data;
        alert(message);
    });

    eventSource.addEventListener("error", function (event) {
        eventSource.close();
    });
});