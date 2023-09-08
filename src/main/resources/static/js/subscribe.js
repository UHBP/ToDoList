const eventSource = new EventSource("/sub");

eventSource.addEventListener("alarm", function(event){
    let message = event.data;
    alert(message);
});

eventSource.addEventListener("error", function(event){
    eventSource.close();
});