package imms;

import imms.service.UserServiceInterface;
import org.jasypt.encryption.StringEncryptor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ImmsSpringBootApplicationTests {

    @Autowired
    UserServiceInterface userService;

    @Autowired
    private StringEncryptor stringEncryptor;
    @Test
    public void encrypt() {
        String encryptStr = stringEncryptor.encrypt("immsredis");
        System.out.println("加密后的内容1：" + encryptStr);
//        CWVKWBKBMGRJXFPG
        encryptStr = stringEncryptor.encrypt("CWVKWBKBMGRJXFPG");
        System.out.println("加密后的内容2：" + encryptStr);
    }
}
