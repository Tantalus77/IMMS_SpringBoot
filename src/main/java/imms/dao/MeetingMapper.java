package imms.dao;

import imms.model.Meeting;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MeetingMapper {
    /**
     * 1.添加一个会议
     * 2.通过id删除一个会议
     * 3.修改会议信息
     * 4.查询所有会议
     * 5.通过Id查询会议
     * 6.通过特定条件查询会议
     */

    //1.添加一个会议
    Integer addMeeting(Meeting meeting);

    //2.通过id删除一个会议
    void deleteMeeting(Integer meetingId);

    //3.修改会议信息
    void updateMeeting(Meeting meeting);

    //4.查询所有会议
    @Select("select * from meeting")
    List<Meeting> selectAll();

    //5.通过Id查询会议
    @Select("select * from meeting where meetingId = #{meetingId}")
    List<Meeting> selectById(Integer meetingId);

    //6.通过特定条件查询会议
    List<Meeting> select(Meeting meeting);

    /**
     * 查询某一天某个会议室的所有会议
     * @param roomId
     * @return
     */
    @Select("SELECT m.meetingId,m.date,m.startTime,m.endTime,m.theme,m.introduction " +
            "FROM reserve left join meeting m on reserve.meetingId = m.meetingId " +
            "WHERE reserve.roomId = #{roomId} and m.date = #{date}")
    List<Meeting> selectByRoom(int roomId,String date);
}