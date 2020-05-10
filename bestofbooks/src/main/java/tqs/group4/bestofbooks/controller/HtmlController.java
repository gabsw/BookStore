package tqs.group4.bestofbooks.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HtmlController {
    @RequestMapping(value = "/")
    public static String home() {
        return "login";
    }
    @RequestMapping(value = "/register")
    public static String register() {
        return "register";
    }
    @RequestMapping(value = "/client")
    public static String clientIndex() {
        return "index";
    }
    @RequestMapping(value = "/admin")
    public static String adminIndex() {
        return "adminIndex";
    }
    @RequestMapping(value = "/publisher")
    public static String publisherIndex() {
        return "publisherIndex";
    }
}
