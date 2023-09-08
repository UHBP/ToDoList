package uhbp.todolist.Controller;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@RequestMapping("/error")
public class ErrorTestController {

    @GetMapping("/400")
    public String goto400(){
        return "error/4xx";
    }

    @GetMapping("/500")
    public String goto500(){
        return "error/5xx";
    }

}

