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
    void addMeeting(Meeting meeting);

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
}