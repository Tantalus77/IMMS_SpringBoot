package imms.controller;

import com.alibaba.fastjson.JSON;
import imms.model.Result;
import imms.model.Room;
import imms.model.User;
import imms.service.AdminServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

import static imms.utils.Code.*;

@RestController
@RequestMapping("/admin")
@CrossOrigin(originPatterns = "*", allowCredentials = "true")
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

            return new Result(DAO_SUCCESS,currentUser,"管理员登陆成功！");

        }else{
            return new Result(SELECT_ERROR,null,"不存在该账户或你不是管理员！");
        }

    }

    @GetMapping("/curUser")
    public Result curUser(HttpServletRequest request){
        User curUser = null;
        try {
            curUser = JSON.parseObject(JSON.toJSONString(request.getSession().getAttribute("User")),User.class);
        }catch (Exception e) {
            e.printStackTrace();
            return new Result(SERVICE_ERROR,null,"获取当前用户出错！");
        }
        if (curUser == null) {
            return new Result(SERVICE_ERROR,null,"获取当前用户出错！");
        }
        return new Result(DAO_SUCCESS,curUser,"获取成功");
    }

    @PostMapping("/addUser")
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
            return new Result(SERVICE_ERROR,null,"你不能删除自己！");
        }else{
            adminService.deleteUser(userIds);
            return new Result(DAO_SUCCESS, null, "成功删除选中的用户！");
        }

    }

    @PostMapping("/selectUser")
    public Result selectUser(@RequestBody User condition){
        List<User> users = adminService.selectUser(condition);

        return (!users.isEmpty())? new Result(DAO_SUCCESS, users, "成功查找到用户"):new Result(SERVICE_ERROR, null, "系统中没有符合条件的用户！");
    }

    @GetMapping("/allUsers")
    public Result allUsers(){
        return new Result(DAO_SUCCESS,adminService.selectUser(null),"获取成功！");
    }

    @PostMapping("/updateUser")
    public Result updateUser(@RequestBody User user){

        return adminService.updateUser(user)?new Result(DAO_SUCCESS,null,"成功更新！"):new Result(DAO_ERROR,null,"更新出错！");

    }
    @PostMapping("/addRoom")
    public Result addRoom(@RequestBody Room newRoom){
        return adminService.addRoom(newRoom)?new Result(DAO_SUCCESS,null,"添加成功！"):new Result(DAO_ERROR,null,"添加失败！");
    }

    @PostMapping("/deleteRooms")
    public Result deleteRoom(@RequestBody List<Integer> roomIds){
        return adminService.deleteRoom(roomIds)?new Result(DAO_SUCCESS,null,"删除成功！"):new Result(DAO_ERROR,null,"删除失败！");
    }

    @GetMapping("/allRooms")
    public Result allRoom(){
        List<Room> rooms = adminService.selectRoom(null);
        return (!rooms.isEmpty())?new Result(DAO_SUCCESS,rooms,"获取成功！"): new Result(DAO_ERROR,null,"获取失败！");
    }

    @PostMapping("/selectRoom")
    public Result selectRoom(@RequestBody Room room){
        List<Room> rooms = adminService.selectRoom(room);
        return (!rooms.isEmpty()) ? new Result(DAO_SUCCESS,rooms,"搜索成功！"):new Result(SERVICE_ERROR,null,"系统中没有符合条件的会议室！");
    }

    @PostMapping("/updateRoom")
    public Result updateRoom(@RequestBody Room room){
        return adminService.updateRoom(room)?new Result(DAO_SUCCESS, null, "成功更新！"):new Result(DAO_ERROR,null,"更新失败！");
    }

}
