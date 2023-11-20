
package imms.controller;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@CrossOrigin(originPatterns = "*", allowCredentials = "true")
public class UserController {


}
