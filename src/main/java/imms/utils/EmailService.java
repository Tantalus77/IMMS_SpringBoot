package imms.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class EmailService {

    @Resource
    private JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String fromEmail;
    public Boolean sendEmail(String toEmail,String sub,String text)
    {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(fromEmail);
        //收件人邮箱
        simpleMailMessage.setTo(toEmail);
        //主题标题
        simpleMailMessage.setSubject(sub);
        //信息内容
        simpleMailMessage.setText(text);
        System.out.println("fromEmail:"+fromEmail);
        System.out.println("toEmail:"+toEmail);
        System.out.println("sub:"+sub);
        System.out.println("text:"+text);
        //执行发送
        try {//发送可能失败
            javaMailSender.send(simpleMailMessage);
            //没有异常返回true，表示发送成功
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            //发送失败，返回false
            return false;
        }
    }
}
