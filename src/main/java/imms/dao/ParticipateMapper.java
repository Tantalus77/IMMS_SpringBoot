package imms.dao;

import imms.model.Meeting;
import imms.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ParticipateMapper {
    /**
    *  1.给某个会议添加参会人
     * 2.从某个会议的参会者中删除某个参会人
     * 3.查询某个会议的所有参会人
     * 4.查询某个人参加的所有会议
    * */

    //1.给某个会议添加参会人
    @Insert("insert into participate values (#{meetingId},#{userId},0)")
    void addParticipant(Integer meetingId, Integer userId);

    //2.从某个会议的参会者中删除某个参会人
    @Delete("delete from participate where meetingId = #{meetingId} and userId = #{userId}")
    void deleteParticipant(Integer meetingId, Integer userId);

    //3.查询某个会议的所有参会人
    @Select("select u.userId,u.userName,u.userNumber,u.userPhoneNumber,u.userPassword,u.userEmail,u.isAdmin " +
            "from participate p left join userinfo u on p.userId = u.userId where p.meetingId = #{meetingId};")
    List<User> selectParticipants(Integer meetingId);

    //4.查询某个人参加的所有会议
    @Select("select m.meetingId,m.userId,m.roomId,m.date,m.startTime,m.endTime,m.theme,m.introduction,m.code" +
            " from participate p,userinfo u,meeting m" +
            "where u.userId = #{userId} and u.userId = p.userId and p.meetingId = m.meetingId;")
    List<Meeting> selectMeetings(Integer userId);

    @Select("select * from participate where meetingId = #{meetingId} and userId = #{userId}")
    List<User> isParticipate(Integer meetingId, Integer userId);

    @Update("update participate set isAttend = 1 where meetingId = #{meetingId} and userId = #{userId}")
    void checkIn(Integer meetingId, Integer userId);

    @Select("select u.userId,u.userName,u.userNumber,u.userPhoneNumber,u.userPassword,u.userEmail,u.isAdmin " +
            "from participate p,userinfo u where p.meetingId = #{meetingId} and isAttend = #{isAttend};")
    List<User> checkInList(Integer meetingId, int isAttend);

    @Select("select TIMESTAMPDIFF(hour, startTime, endTime) from meeting where meeting.meetingId = #{meetindId};")
    List<Integer> selectIsAttend(Integer meetingId);
}
