package jcs;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/")
public class TestController {
    public TestController() {
        System.out.println("Controller Constructed");
    }

    @GetMapping("/hello")
    public String sayHello() {
        System.out.println("AAAAAA");
        String test = "Test";
        return "hello";
    }

    @GetMapping("/test")
    @ResponseBody
    public String testJson() {
        return "AAA";
    }
}
