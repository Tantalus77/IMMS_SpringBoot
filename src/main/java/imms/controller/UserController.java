package imms.controller;


import imms.model.User;
import imms.service.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserServiceInterface userServiceInterface;

    @PostMapping(value = "/loginByEmail")
    public String loginByEmail(@RequestBody User user,HttpServletRequest request){

        System.out.println("/users/loginByEmail接受到的==>"+user);//打印接收到的数据方便检查

        //业务流程
        boolean flag = userServiceInterface.loginByEmail(user.getUserEmail(), user.getUserPassword());

        if (flag) {
            //如果登录成功，发送session
            User currentUser = userServiceInterface.selectByEmail(user.getUserEmail());//获取当前登录的user的全部信息
            System.out.println("/users/loginByEmail当前登录的用户的全部信息==>"+currentUser);//打印输出信息方便检查
            //发Session
            HttpSession session = request.getSession();
            session.removeAttribute("User");//先把之前的session对应的值去掉
            session.setAttribute("User", currentUser);//写入session

            System.out.println("登陆成功");

            //判断用户权限
            if(currentUser.getIsAdmin() ==1 ){
                return "admin";
            }else{
                return "normal";
            }

        }else{
            System.out.println("登录失败");
            return "fail";
        }

    }

    @PostMapping("/registerByEmail")
    public String registerByEmail(@RequestBody User user){
        System.out.println("/users/registerByEmail 接受到的==>"+user);//检查是否正确接受参数

        //业务流程
        Integer userId = userServiceInterface .registerByEmail(user);
        if(userId == -1){
            //注册失败
            System.out.println("注册失败");
            return "fail";
        }else{
            //注册成功，返回userId
            System.out.println("注册成功，id==>"+userId);
            return userId+"";
        }

    }
    
    @PostMapping("/addUSer")
    public String addUser(@RequestBody User user){
        System.out.println("/users/addUser接受到的==>"+user);//打印接受到的参数信息

        //业务流程
        Integer userId = userServiceInterface.insertUser(user);
        if(userId == -1){
            //注册失败
            System.out.println("添加失败");
            return "fail";
        }else{
            //注册成功，返回userId
            System.out.println("添加成功,id是==>"+userId);
            return userId+"";
        }

    }

    @GetMapping("/deleteUser")
    public String deleteUser(@RequestParam Integer userId,@RequestParam int curUserId){
        System.out.println("/users/deleteUser接受到的==>"+"要删除的用户的id"+userId+"当前登录用户的id"+curUserId);//打印接受的参数

        //业务流程
        if(userId==curUserId){
            //如果要删除的用户的id与当前登录的用户的id相同，不允许删除，返回错误信息“mistake”
            System.out.println("不能删除自己");
            return "mistake";
        }else{
            userServiceInterface.deleteUser(userId);//删除用户
            System.out.println("成功删除id为"+userId+"的用户");
            return "success";
        }

    }

    @PostMapping("/deleteUsers")
    public String deleteUsers(@RequestBody List<Integer> ids){
        System.out.println("/users/deleteUsers接收到的==>"+"要删除的用户的id数组"+ids);

        //业务流程
        int length = ids.size();//把id数组的长度拿出来，提升性能

        //循环删除id
        for (int j = 0; j < length; j++) {
            userServiceInterface.deleteUser(ids.get(j));
        }

        return "success";


    }

    @PostMapping("/editUser")
    public String editUser(@RequestBody User user){
        System.out.println("/users/editUser接受到的==>"+user);//打印接受的参数

        //业务流程
        if(userServiceInterface.editUser(user)){
            System.out.println("成功修改");

            return "success";
        }else{
            System.out.println("修改失败");

            return "fail";

        }

    }


    @GetMapping("/selectAll")
    public List<User> selectAll(){
        System.out.println("/users/selectAll is running...");
        //业务流程
        List<User> users = userServiceInterface.selectAll();
        System.out.println("/users/selectAll查询结果==>"+users);//打印查询结果方便检查

        return users;

    }

    @RequestMapping("/select")
    public List<User> select(@RequestBody User user){
        System.out.println("/users/select接受到的参数==>"+user);//打印接受到的查询条件

        //业务流程
        List<User> users = userServiceInterface.select(user);
        System.out.println("/users/select查询结果==>"+users);//打印查询结果

        return users;

    }

    @RequestMapping("/getSessionUser")
    public User getSessionUser(HttpServletRequest request){
        System.out.println("users/getSessionUserInfo is running...");

        //业务流程
        HttpSession session = request.getSession();//获取session
        User user = (User) session.getAttribute("User");//读取session

        return user;

    }




}
