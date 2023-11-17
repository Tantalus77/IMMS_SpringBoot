package imms.dao;

import imms.model.Invite;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface InviteMapper {

    void addInvite(Invite invite);

    @Delete("delete from invite where userId = #{userId} and meetingId = #{meetingId}")
    void deleteInvite(Integer userId,Integer meetingId);

    @Select("select * from invite")
    List<Invite> selectAll();

    @Select("select * from invite where meetingId = #{meetingId}")
    List<Invite> selectByMeetingId(Integer meetingId);

    @Select("select * from invite where userId = #{userId}")
    List<Invite> selectByUserId(Integer userId);

    @Select("select * from invite where inviterId = #{inviterId}")
    List<Invite> selectByInviterId(Integer InviterId);

    @Select("select userId from invite where meetingId = #{meetingId}")
    List<Integer> selectUserIdsByMeetingId(Integer meetingId);

    @Select("select meetingId from invite where userId = #{userId}")
    List<Integer> selectMeetingIdByUserId(Integer userId);

    @Select("select * from invite where userId = #{userId} && meetingId = #{meetingId}")
    List<Invite> selectByUserIdAndByMeetingId(Integer userId, Integer meetingId);
    /**
     * 只允许修改状态
     * @param invite
     */
    void updateInvite(Invite invite);


}
