package imms.dao;

import imms.model.Meeting;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author Plum-Snow
 * @author 普朗千克
 * @lastUpdateTime 2023/11/17 11:45
 * @despription:
 *  包含方法：
 *  1.添加会议
 *  2.删除某个会议
 *  3.修改某个会议信息
 *  4.获取系统中所有会议
 *  5.使用Id查询会议
 *  6.使用会议码查询会议
 *  7.不确定条件查询会议
 *  8.查询某个会议室的所有会议
 *  9.使用当前时间查询历史会议
 */
@Mapper
public interface MeetingMapper {

    /**
     * 添加一个会议
     * @param meeting
     * @return
     */
    void addMeeting(Meeting meeting);

    /**
     * 通过meetingId删除某个会议
     * @param meetingId
     */
    void deleteMeeting(Integer meetingId);

    /**
     * 修改某个会议的信息
     * id和会议码不允许修改
     * @param meeting
     */
    void updateMeeting(Meeting meeting);

    /**
     * 获取系统中所有的会议
     * @return
     */
    @Select("select * from meeting")
    List<Meeting> selectAll();

    /**
     * 通过id查询会议
     * @param meetingId
     * @return
     */
    @Select("select * from meeting where meetingId = #{meetingId}")
    List<Meeting> selectById(Integer meetingId);

    /**
     * 通过会议码查找会议
     * @param code
     * @return
     */
    @Select("select * from meeting where code = #{code}")
    Meeting selectByCode(String code);

    /**
     * 通过不确定的条件查询会议
     * 条件包括：会议id、会议室id、主持人id、日期、开始时间、结束时间、主题、介绍、是否需要签到、会议码
     * @param meeting
     * @return
     */
    List<Meeting> select(Meeting meeting);

    @Select("select * from meeting where userId = #{userId}")
    List<Meeting> selectByUserId(Integer userId);

    @Select("select * from meeting where roomId = #{roomId}")
    List<Meeting> selectByRoomId(Integer roomId);

    /**
     * 查询某一天某个会议室的所有会议
     * @param roomId
     * @return
     */
    @Select("select * from meeting where roomId = #{roomId} and date = #{date}")
    List<Meeting> selectByRoom(int roomId,String date);

    /**
     * 查询某一天某个会议室的所有会议
     * @param date
     * @return
     */
    @Select("select * from meeting where meeting.date <= #{date}")
    List<Meeting> selectByTime(String date);

    /**
     * 通过某日的一段时间来查询会议
     * @param startTime
     * @param endTime
     * @return
     */
    @Select("select * from meeting where meeting.startTime <= #{startTime} and meeting.endTime >= #{endTime} and meeting.date = #{date}")
    List<Meeting> selectByPeriod(String startTime, String endTime, String date);


}