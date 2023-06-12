package imms.controller;

import imms.model.Meeting;
import imms.model.Result;
import imms.model.User;
import imms.service.MeetingServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static imms.utils.Code.*;

@RestController
@RequestMapping("/meetings")
public class MeetingController {
    @Autowired
    private MeetingServiceInterface ms;

    @PostMapping("/add")
    public Result addMeeting(@RequestBody Meeting meeting){
        if(meeting == null) return new Result(INPUT_ERROR,null,"没有输入数据！");
        try{
            ms.addMeeting(meeting);

        }catch (Exception e) {
            e.printStackTrace();
            return new Result(DAO_ERROR,null,"数据库操作失败");

        }
        return new Result(DAO_SUCCESS,null,"添加成功！");
    }

    @PostMapping("/update")
    public Result updateMeeting(@RequestBody Meeting meeting){
        if(meeting == null) return new Result(INPUT_ERROR,null,"没有输入数据");
        if(!ms.isExist(meeting.getMeetingId())) return new Result(INPUT_ERROR,null,"要修改的会议不存在！");

        try{
            ms.updateMeeting(meeting);
        }catch (Exception e) {
            e.printStackTrace();
            return new Result(DAO_ERROR,null,"数据库操作失败");
        }
        return new Result(DAO_SUCCESS,null,"更新成功！");

    }

    @GetMapping("/delete/{meetingId}")
    public Result deleteMeeting(@PathVariable Integer meetingId){
        if(meetingId == null) return new Result(INPUT_ERROR, null, "没有输入数据！");
        try{
            ms.deleteMeeting(meetingId);
        }catch (Exception e) {
            e.printStackTrace();
            return new Result(DAO_ERROR,null,"数据库操作失败！");
        }
        return new Result(DAO_SUCCESS,null,"删除成功！");

    }

    @PostMapping("/delete")
    public Result deleteMeetings(@RequestBody List<Integer> meetingIds){
        if(meetingIds.isEmpty()) return new Result(INPUT_ERROR, null, "没有输入数据！");
        try{
            ms.deleteMeetings(meetingIds);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(DAO_ERROR,null,"数据库操作失败！");

        }
        return new Result(DAO_SUCCESS,null,"删除成功！");
    }

    @GetMapping("/selectAll")
    public Result selectAll(){
        List<Meeting> result = null;
        try{
            result = ms.selectAll();
        }catch (Exception e) {
            e.printStackTrace();
            return new Result(DAO_ERROR,null,"数据库操作失败！");
        }
        if(result.isEmpty()){return new Result(SELECT_ERROR,null,"系统中暂时没有数据！");}
        return new Result(DAO_SUCCESS,result,"查找成功！");
    }

    @PostMapping("/select")
    public Result select(@RequestBody Meeting meeting){
        if(meeting == null ) return new Result(INPUT_ERROR,null,"没有输入数据！");
        List<Meeting> result = new ArrayList<Meeting>();
        try{
            result = ms.select(meeting);
        }catch (Exception e) {
            e.printStackTrace();
            return new Result(DAO_ERROR,null,"数据库操作失败！");
        }
        if(result.isEmpty()) return new Result(SELECT_ERROR,null,"没有找到符合条件的数据！");
        return new Result(DAO_SUCCESS,result,"操作成功！");
    }

    @PostMapping("/addParticipant")
    public Result addParticipant(@RequestBody Map<String,Object> data){
        if(data.isEmpty()) return new Result(INPUT_ERROR,null,"没有输入数据！");
        try{
            ms.addParticipant((Integer)data.get("meetingId"),(Integer)data.get("userId"));
        }catch (Exception e) {
            e.printStackTrace();
            return new Result(DAO_ERROR,null,"数据库操作失败！");

        }
        return new Result(DAO_SUCCESS,null,"添加成功！");
    }
    @PostMapping("/addParticipants")
    public Result addParticipants(@RequestBody Map<String,Object> data){
        if(data.isEmpty()) return new Result(INPUT_ERROR, null,"没有输入数据！");
        int[] userIds = null;
        try{
            userIds = (int[])data.get("userIds");
        }catch (Exception e) {
            e.printStackTrace();
            return new Result(SERVICE_ERROR,null,"类型转换出错！！");
        }
        try{
            ms.addParticipants((Integer)data.get("meetingId"),userIds);
        }catch (Exception e) {
            e.printStackTrace();
            return new Result(DAO_ERROR, null, "数据库操作出错！");
        }
        return new Result(DAO_SUCCESS,null,"添加成功！");
    }

    @PostMapping("/deleteParticipant")
    public Result deleteParticipant(@RequestBody Map<String,Object> data){
        if(data.isEmpty()) return new Result(INPUT_ERROR,null,"没有输入数据！");
        try{
            ms.deleteParticipant((Integer)data.get("meetingId"),(Integer)data.get("userId"));
        }catch (Exception e) {
            e.printStackTrace();
            return new Result(DAO_ERROR,null,"数据库操作失败！");

        }
        return new Result(DAO_SUCCESS,null,"删除成功！");
    }

    @PostMapping("/deleteParticipants")
    public Result deleteParticipants(@RequestBody Map<String,Object> data){
        if(data.isEmpty()) return new Result(INPUT_ERROR, null,"没有输入数据！");
        int[] userIds = null;
        try{
            userIds = (int[])data.get("userIds");
        }catch (Exception e) {
            e.printStackTrace();
            return new Result(SERVICE_ERROR,null,"类型转换出错！！");
        }
        try{
            ms.deleteParticipants((Integer)data.get("meetingId"),userIds);
        }catch (Exception e) {
            e.printStackTrace();
            return new Result(DAO_ERROR, null, "数据库操作出错！");
        }
        return new Result(DAO_SUCCESS,null,"删除成功！");
    }

    @GetMapping("/participants/{meetingId}")
    public Result participants(@PathVariable Integer meetingId){
        if(meetingId == null) return new Result(INPUT_ERROR,null,"没有输入数据！");
        List<User> users = null;
        try{
            users = ms.participants(meetingId);
        }catch (Exception e) {
            e.printStackTrace();
            return new Result(DAO_ERROR,null,"数据库操作出错！");
        }
        if(users.isEmpty()) return new Result(SELECT_ERROR, null, "没有参会人！");
        return new Result(DAO_SUCCESS,users,"查询成功！");
    }

    @PostMapping("/checkIn")
    public Result checkIn(@RequestBody Map<String,Object> data){
        if(data.isEmpty()) return new Result(INPUT_ERROR,null,"未输入数据！");
        try{
            ms.checkIn((Integer)data.get("meetingId"),(Integer)data.get("userId"));
        }catch (Exception e) {
            e.printStackTrace();
            return new Result(DAO_ERROR,null,"数据库操作出错！");
        }
        return new Result(DAO_SUCCESS,null,"签到成功！");
    }

    @PostMapping("/checkInList")
    public Result checkInList(@RequestBody Map<String,Object> data){
        if(data.isEmpty()) return new Result(INPUT_ERROR,null,"未输入数据！");
        List<User> users = null;
        try{
            users = ms.checkInList((Integer)data.get("meetingId"),(Integer)data.get("isAttend"));
        }catch (Exception e) {
            e.printStackTrace();
            return new Result(DAO_ERROR, null, "数据库操作失败！");
        }
        if(users.isEmpty()) return new Result(SELECT_ERROR, null, "没有查找到对应状态的用户！");
        return new Result(DAO_SUCCESS,users,"查询成功！");

    }
}
