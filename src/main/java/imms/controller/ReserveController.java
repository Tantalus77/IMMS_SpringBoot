package imms.controller;

import imms.model.Reserve;
import imms.model.Result;
import imms.service.ReserveServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

import static imms.utils.Code.*;

@RestController
@RequestMapping("/reserves")
public class ReserveController {
    @Autowired
    private ReserveServiceInterface rs;
    @PostMapping ("/add")
    public Result addReserve(@RequestBody Reserve reserve){
        if(reserve == null) return new Result(INPUT_ERROR,null,"没有传入数据！");
        try{
            rs.addReserve(reserve);
        }catch (Exception e){
            return new Result(DAO_ERROR,null,"数据库操作失败！请重试");
        }
        return new Result(DAO_SUCCESS,null,"添加成功！");
    }

    @GetMapping("/delete/{reserveId}")
    public Result deleteReserve(@PathVariable Integer reserveId){
        if (reserveId == null) return new Result(INPUT_ERROR,null,"没有传入数据！");
        try{
            rs.deleteReserve(reserveId);
        }catch (Exception e) {
            return new Result(DAO_ERROR,null,"数据库操作失败！请重试");
        }
        return new Result(DAO_SUCCESS,null,"删除成功！");
    }

    @PostMapping("/delete")
    public Result deleteReserves(@RequestBody List<Integer> reserveIds){
        if(reserveIds.isEmpty()) return new Result(INPUT_ERROR,null,"没有传入数据！");
        try{
            rs.deleteReserves(reserveIds);
        }catch (Exception e) {
            return new Result(DAO_ERROR,null,"数据库操作失败！请重试");
        }
        return new Result(DAO_SUCCESS,null,"删除成功！");
    }

    @PostMapping("/update")
    public Result updateReserve(@RequestBody Reserve reserve){
        if(reserve == null) return new Result(INPUT_ERROR,null,"没有传入数据！");

        if(reserve.getReserveId()==null) return new Result(INPUT_ERROR,null,"没有输入会议id！");

        try{
            rs.updateReserve(reserve);
        }catch (Exception e) {
            return new Result(DAO_ERROR,null,"数据库操作失败！请重试");
        }
        return new Result(DAO_SUCCESS,null,"更新成功！");
    }

    @GetMapping("/selectAll")
    public Result selectAll(){
        List<Reserve> result = null;
        try{
            result = rs.selectAll();
        }catch (Exception e) {
            return new Result(DAO_ERROR,null,"数据库操作失败！请重试");
        }
        if(result.isEmpty()){
            return new Result(SELECT_ERROR,null,"系统中目前没有预约！");
        }
        return new Result(DAO_SUCCESS,result,"搜索成功!");

    }

    @PostMapping("/select")
    public Result select(@RequestBody Reserve reserve){
        List<Reserve> result = null;
        try{
            result = rs.select(reserve);
        }catch (Exception e) {
            return new Result(DAO_ERROR,null,"数据库操作失败！请重试");
        }
        if (result.isEmpty()) return new Result(SELECT_ERROR,null,"没有查询到符合条件的预约！");
        return new Result(DAO_SUCCESS,result,"搜索成功！");
    }
    @GetMapping("/selectByUser/{userId}")
    public Result selectByUser(@PathVariable int userId){
        List<Reserve> result = null;
        try{
            result = rs.selectByUserId(userId);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(DAO_ERROR,null,"数据库操作失败！请重试");
        }
        if(result.isEmpty()) return new Result(SELECT_ERROR,null,"没有查询到符合条件的预约！");
        return new Result(DAO_SUCCESS,result,"搜索成功！");
    }


    @GetMapping("/selectByStatus/{status}")
    public Result selectByStatus(@PathVariable int status){
        List<Reserve> result = null;
        try{
            result = rs.selectByReserveStatus(status);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(DAO_ERROR,null,"数据库操作失败！请重试");
        }
        if(result.isEmpty()) return new Result(SELECT_ERROR,null,"没有查询到符合条件的预约！");
        return new Result(DAO_SUCCESS,result,"搜索成功！");
    }

    @GetMapping("/selectByRoom/{roomId}")
    public Result selectByRoom(@PathVariable int roomId){
        List<Reserve> result = null;
        try{
            result = rs.selectByRoomId(roomId);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(DAO_ERROR,null,"数据库操作失败！请重试");
        }
        if(result.isEmpty()) return new Result(SELECT_ERROR,null,"没有查询到符合条件的预约！");
        return new Result(DAO_SUCCESS,result,"搜索成功！");
    }

    @GetMapping("/pass/{reserveId}")
    public Result pass(@PathVariable Integer reserveId){
        if (reserveId == null) {return new Result(INPUT_ERROR,null,"没有输入预约id！");}
        try{
            rs.pass(reserveId);
        }catch (Exception e) {
            e.printStackTrace();
            return new Result(DAO_ERROR,null,"数据库操作失败！请重试");
        }
        return new Result(DAO_SUCCESS,null,"审核成功！");
    }

    @PostMapping("/pass")
    public Result pass(@RequestBody Map<String,Object> data){
        if(data.isEmpty()) return new Result(INPUT_ERROR,null,"没有输入数据！");
        try{
            rs.pass((Integer) data.get("reserveId"),(String)data.get("info"));
        }catch (Exception e) {
            e.printStackTrace();
            return new Result(DAO_ERROR,null,"数据库操作失败！");
        }
        return new Result(DAO_SUCCESS,null,"审核成功！");

    }

    @PostMapping("/reject")
    public Result reject(@RequestBody Map<String,Object> data){
        if (data.isEmpty()) {return new Result(INPUT_ERROR,null,"没有输入数据！");}
        try{
            rs.reject((Integer) data.get("reserveId"),(String) data.get("info"));
        }catch (Exception e){
            e.printStackTrace();
            return new Result(DAO_ERROR,null,"数据库操作失败！");
        }
        return new Result(DAO_SUCCESS,null,"审核成功！");

    }



}
