package imms.controller;

import imms.model.Result;
import imms.service.Analyser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static imms.utils.Code.*;

/**
 * @author
 * @lastUpdateTime 2023/12/16 14:04
 * @description
 */

@RestController
@RequestMapping("/analyser")
public class AnalyserController {
    
    @Autowired
    Analyser ana;
    @GetMapping("/timesPerRoomOfUser")
    public Result timesPerRoomOfUser(@RequestParam Integer userId){
        HashMap<Integer,Integer> res = new HashMap<>();
        try {
            res = ana.timesPerRoomOfUser(userId);
        }catch (Exception e) {
            e.printStackTrace();
            return new Result(DAO_ERROR,null,"数据库操作出错！");
        }
        return new Result(DAO_SUCCESS,res,"查询成功！");
    }
    
    @GetMapping("/totalMeetingNumOfUser")
    public Result totalMeetingNumOfUser(@RequestParam Integer userId){
        Integer num = 0;
        try{
            num = ana.totalMeetingNum(userId);
        }catch (Exception e) {
            e.printStackTrace();
            return new Result(DAO_ERROR,null,"数据库出错！");
        }
        
        return new Result(DAO_SUCCESS,num,"查询成功！");
    }
    
    @GetMapping("/meetingTimesPerUser")
    public Result meetingTimesPerUser(){
        Map<Integer,Integer> res = new HashMap<>();
        try{
            res = ana.meetingtimesPerUser();
        }catch (Exception e) {
            e.printStackTrace();
            return new Result(DAO_ERROR,null,"数据库操作错误！");
        }
        return new Result(DAO_SUCCESS,res,"查询成功！");
        
    }

    @GetMapping("/meetingTimesPerRoom")
    public Result meetingTimesPerRoom(){
        Map<Integer,Integer> res = new HashMap<>();
        try{
            res = ana.meetingNumPerRoom();
        }catch (Exception e) {
            e.printStackTrace();
            return new Result(DAO_ERROR,null,"数据库操作错误！");
        }
        return new Result(DAO_SUCCESS,res,"查询成功！");

    }
    @GetMapping("/attendRate")
    public Result attendRate(@RequestParam Integer meetingId){
        Double ratio = 0.0;
        try{
            ratio = ana.attendRate(meetingId);
        }catch (Exception e) {
            e.printStackTrace();
            return new Result(DAO_ERROR,null,"数据库操作出错！");

        }
        return new Result(DAO_SUCCESS,ratio,"查询成功！");
    }

    @GetMapping("/meetingNumOdDate")
    public Result meetingNumPerDate(){
        Map<String,Integer> res = new HashMap<>();
        try{
         res = ana.meetingNumByDate();
        }catch (Exception e) {
            e.printStackTrace();
            return new Result(DAO_ERROR,null,"数据库操作出错！");
        }
        return new Result(DAO_SUCCESS,res,"查询成功");
    }

    @PostMapping("/meetingAverageDuration")
    public Result meetingAverageDuration(@RequestBody List<Integer> meetingIds){
        Double averageDuration = 0.0;
        try {
            averageDuration = ana.meetingAverageDuration(meetingIds);
        }catch (Exception e) {
            e.printStackTrace();
            return new Result(DAO_ERROR,null,"数据库操作出错！");
        }
        return new Result(DAO_SUCCESS,averageDuration,"查询成功！");



    }

    @GetMapping("/currentMeetingNum")
    public Result currentMeetingNum(){
        Integer num = 0;
        try {
            num = ana.currentMeetingNum();
        }catch (Exception e) {

            e.printStackTrace();
            return new Result(DAO_ERROR,null,"数据库操作出错！");
        }
        return new Result(DAO_SUCCESS,num,"查询成功！");
    }
    
}
