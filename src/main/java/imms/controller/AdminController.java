package imms.controller;

import com.alibaba.fastjson.JSON;
import imms.model.Result;
import imms.model.User;
import imms.service.AdminServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import imms.utils.Code;

import java.util.List;

import static imms.utils.Code.*;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminServiceInterface adminService;
    @PostMapping(value = "/loginByEmail")
    public Result loginByEmail(@RequestBody User user, HttpServletRequest request){
        //业务流程
        boolean flag = adminService.loginByEmail(user.getUserEmail(), user.getUserPassword());

        if (flag) {
            //如果登录成功，发送session
            User currentUser = adminService.selectByEmail(user.getUserEmail());//获取当前登录的user的全部信息
            //发Session
            HttpSession session = request.getSession();
            session.removeAttribute("User");//先把之前的session对应的值去掉
            session.setAttribute("User", currentUser);//写入session

            return new Result(DAO_SUCCESS,null,"管理员登陆成功！");

        }else{
            return new Result(SELECT_ERROR,null,"不存在该账户或你不是管理员！");
        }

    }

    @PostMapping("/addUSer")
    public Result addUser(@RequestBody User user){
        //业务流程
        Integer userId = adminService.addUser(user);
        if(userId == -1){
            //注册失败
            return new Result(DAO_ERROR,null,"添加失败！");
        }else{
            //注册成功，返回userId
            return new Result(DAO_SUCCESS,userId,"添加成功！");
        }

    }

    @PostMapping("/deleteUsers")
    public Result deleteUser(@RequestBody List<Integer> userIds,HttpServletRequest request){
        User curUser = JSON.parseObject(JSON.toJSONString(request.getSession().getAttribute("User")),User.class);
        //业务流程
        if(userIds.contains(curUser.getUserId())){
            //如果要删除的用户的id与当前登录的用户的id相同，不允许删除，返回错误信息“mistake”
            return new Result(SERVICE_ERROR,null,"你不能删除自己！");
        }else{
            adminService.deleteUser(userIds);
            return new Result(DAO_SUCCESS, null, "成功删除选中的用户！");
        }

    }



}
