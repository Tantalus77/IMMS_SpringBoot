package imms.service;

import java.util.HashMap;
import java.util.List;

public interface Analyser {
    /**
     * 1.用户行为
     *  1.1.某个用户预定会议室的次数排序
     *  1.2.某个用户预预定会议的总次数
     *  1.3.某个用户预定会议室最频繁的时间段(暂时搁置)
     *  1.4.用户预定会议次数排行
     * 2.会议室相关
     *  2.1.某个会议室按日期分类计算会议数量
     *  2.2.按会议室统计会议数量
     * 3.会议相关
     *  3.1.某个会议的到会率（设置了需要签到的会议）
     *  3.2.指定某些会议的平均时长
     *  3.3.按日期分类计算会议数量
     *  3.4.当前正在使用的会议室数量
     */

    //1.1.某个用户预定会议室的次数排序
    HashMap<Integer,Integer> timesPerRoomOfUser(Integer userId);

    //1.2.某个用户预预定会议的总次数
    Integer totalMeetingNum(Integer userId);

    //1.4.用户预定会议次数排行
    HashMap<Integer, Integer> meetingtimesPerUser();

    //2.1.某个会议室按日期分类计算会议数量
    HashMap<String, Integer> meetingNumPerDay(Integer roomId);

    //2.2.按会议室统计会议数量
    HashMap<Integer,Integer> meetingNumPerRoom();

    //3.1.某个会议的到会率（设置了需要签到的会议）
    Double attendRate(Integer meetingId);

    //3.2.指定某些会议的平均时长(返回以小时为单位)
    Double meetingAverageDuration(List<Integer> meetingIds);

    //3.3.按日期分类计算会议数量
    HashMap<String, Integer> meetingNumByDate();

    //3.4.当前正在使用的会议室数量
    Integer currentMeetingNum();

}
