
package imms.controller;


import imms.model.*;
import imms.service.UserServiceInterface;
import static imms.utils.Code.*;

import imms.utils.EmailService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;


@RestController
@RequestMapping("/user")
@CrossOrigin(originPatterns = "*", allowCredentials = "true")
public class UserController {
    // 引入对象
    @Autowired
    private UserServiceInterface userServicer;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private EmailService emailService;
    // 用户邮箱登录
    @PostMapping("/loginByEmail")
    public Result userlogin(@RequestBody User user) {
        User curUser = userServicer.loginByEmail(user.getUserEmail(), user.getUserPassword());
        if (curUser != null) {
            return new Result(100, curUser, "登录成功！");
        } else {
            return new Result(213, null, "邮箱或密码错误");
        }
    }

    // 管理个人信息
    @PostMapping("/editInfo")
    public Result userEdit(@RequestBody User user) {
        boolean flag =userServicer.editInfo(user);
        if (flag) {
            return new Result(100,null,"修改成功！");
        }
        else {
            return new Result(211,null,"修改失败！");
        }
    }

    // 修改头像
    @Value("${file.upload-path}")
    private String imgUrl;
    @ApiOperation(value = "返回前端发送来的图片")
    @PostMapping("/setPicture")
    public Result setPicture(@RequestBody User user,@RequestBody MultipartFile file){
        int userId = user.getUserId();// 得到userId
        String originalFilename = file.getOriginalFilename();
        int index = originalFilename.indexOf(".");
        String formatFileName = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm")).toString();
        String newFileName = formatFileName + originalFilename.substring(index);
        try {
            //将文件保存指定目录
            File picture = new File(imgUrl + newFileName);
            file.transferTo(picture);
            String filePath = picture.getAbsolutePath();
            boolean flag =userServicer.setPicture(userId,filePath);
            if (flag){
                return new Result(100,null,"图片修改成功！");
            }else {
                return new Result(210,null,"图片修改失败！");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(210,null,"图片修改失败！");
    }

    // 预定会议
    @PostMapping("/reserveMeeting")
    public Result reserveMeeting(@RequestBody Meeting meeting) throws ParseException {
        boolean flag = userServicer.reserveMeeting(meeting);
        if(flag){
            return new Result(100,null,"预约成功！");
        }else {
            return new Result(220,null,"预约失败！");
        }
    }

    // 我的会议
    @GetMapping("/myMeetings")
    public Result myMeetings(Integer userId){
        List<Meeting> meetings = userServicer.myMeetings(userId);
        if (meetings != null){
            return new Result(100,meetings,"查询所有会议！");
        }else {
            return new Result(220,null,"查询失败！");
        }
    }

    // 我组织的会议
    @GetMapping("/myOrganizedMeeting")
    public Result myOrganizedMeeting(Integer userId){
        List<Meeting> meetings = userServicer.myOrganizedMeeting(userId);
        if (meetings != null){
            return new Result(100,meetings,"查询组织的会议！");
        }else {
            return new Result(220,null,"查询失败！");
        }
    }

    // 我的历史会议
    @GetMapping("/historyMeeting")
    public Result historyMeeting(Integer userId){
        List<Meeting> meetings = userServicer.historyMeetings(userId);
        if (meetings != null){
            return new Result(100,meetings,"查询历史会议！");
        }else {
            return new Result(220,null,"查询失败！");
        }
    }

    // 通过会议码参加会议
    @GetMapping("/attend")
    public Result attend(@RequestParam Integer userId, @RequestParam String meetingId){
       boolean flag = userServicer.attendMeetingByCode(userId, meetingId);
        if(flag){
            return new Result(100,null,"加入成功！");
        }else {
            return new Result(220,null,"加入失败！");
        }
    }



    @GetMapping("/sendCode")
    public Result sendCode(@RequestParam("toEmail") String toEmail){
       // System.out.println("666toEmail:"+toEmail);
        Random random = new Random();
        int code = 1000+random.nextInt(8999);//随机产生从1000到9999的数
        //将生成的验证码缓存到redis中，设置有效期5分钟
        redisTemplate.opsForValue().set("NAME",123213);
        System.out.println("redis的NAME:"+redisTemplate.opsForValue().get("NAME"));
        redisTemplate.opsForValue().set(toEmail,code,5, TimeUnit.MINUTES);
        if(emailService.sendEmail(toEmail,"IMMS验证码","您的验证码为:"+code+",请在5分钟内输入!")){
            return new Result(100,null,"验证码发送成功");
        }else{
            return new Result(220,null,"验证码发送失败");
        }
    }

    // 通过邮箱注册(在邮箱注册前先发送验证码:sendCode)
    @PostMapping("/register")
    public Result register(@RequestBody Map map, HttpSession httpSession){
        System.out.println("map.code:"+map.get("code"));
        System.out.println("map.UserEmail:"+map.get("UserEmail"));
        System.out.println("map.UserPassword:"+map.get("UserPassword"));
        //String code =httpSession.getAttribute("code").toString();
        String code = redisTemplate.opsForValue().get(map.get("UserEmail")).toString();
//        System.out.println("httpSession.code:"+code);
        System.out.println("redis中的code:"+code);
        if (!code.equals(map.get("code").toString())){
            return new Result(220,null,"验证码校验失败!");
        }
        boolean flag = userServicer.register(map.get("UserEmail").toString(),map.get("UserPassword").toString());
        if(flag){
            //如果注册成功，则删除redis中缓存的验证码
            redisTemplate.delete(code);
            return new Result(100,null,"注册成功！");
        }else {
            return new Result(220,null,"注册失败！");
        }
    }

    // 所有会议室
    @GetMapping("/allRooms")
    public Result allRooms(){
        //使用redis缓存数据
        List<Room> rooms =(List<Room>)redisTemplate.opsForValue().get("allRooms");
        if(rooms!=null){
            return new Result(100,rooms,"所有会议室!");
        }
        rooms = userServicer.allRooms();
        if(rooms != null){
            redisTemplate.opsForValue().set("allRooms",rooms,60,TimeUnit.MINUTES);
            return new Result(100,rooms,"所有会议室!");
        }else {
            return new Result(220,null,"没有会议室！");
        }
    }

    // 查询会议室
    @PostMapping("/selectRoom")
    public Result selectRoom(@RequestBody Room room){
        System.out.println(room);
        List<Room> selectrooms = userServicer.selectRooms(room);
        if(selectrooms != null){
            return new Result(100,selectrooms,"所有可用的会议室!");
        }else {
            return new Result(220,null,"没有可用的会议室！");
        }

    }


    // 邀请参会
    @GetMapping("/invite")
    public Result invite(@RequestParam Integer userId, @RequestParam Integer inviterId,@RequestParam Integer meetingId){
        boolean flag = userServicer.invite(userId, inviterId, meetingId);
        if(flag){
            return new Result(100,null,"邀请成功！");
        }else {
            return new Result(220,null,"邀请失败！");
        }
    }

    // 同意参会
    @GetMapping("/agree")
    public Result agree(@RequestParam Integer userId, @RequestParam Integer meetingId){
        boolean flag = userServicer.agree(userId, meetingId);
        if(flag){
            return new Result(100,null,"同意入会！");
        }else {
            return new Result(220,null,"操作失败！");
        }
    }


    // 拒绝参会
    @GetMapping("/reject")
    public Result reject(@RequestParam Integer userId, @RequestParam Integer meetingId){
        boolean flag = userServicer.reject(userId, meetingId);
        if(flag){
            return new Result(100,null,"拒绝入会！");
        }else {
            return new Result(220,null,"操作失败！");
        }
    }

    // 我的邀请
    @GetMapping("/myInvitations")
    public Result myInvitations(@RequestParam Integer inviterId){
        List<Invite> inviters = userServicer.myInvitations(inviterId);
        if(inviters != null){
            return new Result(100,inviters,"所有的邀请!");
        }else {
            return new Result(220,null,"没有邀请！");
        }

    }


    // 会议时间可行
    @PostMapping("/isAvailableTime")
    public Result isAvailableTime(@RequestBody Meeting meeting){
        Boolean flag = null;
        Result result = null;
        try {
            flag = userServicer.isAvailableTime(meeting);
        } catch (ParseException e) {
            result.setCode(SERVICE_ERROR);
            result.setData(null);
            result.setMsg("时间转换出错！请输入正确的时间格式！");
            throw new RuntimeException(e);
        }catch (Exception e) {
            result.setCode(UNKNOWN_ERROR);
            result.setData(null);
            result.setMsg("遇到了未知错误");
            e.printStackTrace();
        }

        if(flag == null){
            return result;
        }

        if(flag){
            return new Result(100,true,"会议时间可行！");
        }else {
            return new Result(220,false,"会议时间不可行！");
        }
    }


}