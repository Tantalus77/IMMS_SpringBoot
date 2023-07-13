package imms.service;

import imms.model.Meeting;
import imms.model.User;

import java.util.List;

public interface MeetingServiceInterface {
    /**
     * 新增一个会议
     * @param meeting
     * @return
     */
    public Integer addMeeting(Meeting meeting);

    /**
     * 修改某个会议
     * @param meeting
     * @return
     */
    public boolean updateMeeting(Meeting meeting);

    /**
     * 根据ID删除某个会议
     * @param meetingId
     * @return
     */
    public boolean deleteMeeting(Integer meetingId);

    /**
     * 根据ID批量删除会议
     * @param meetingIds
     * @return
     */
    public boolean deleteMeetings(List<Integer> meetingIds);

    /**
     * 查找系统中当前所有的会议
     * @return
     */
    public List<Meeting> selectAll();

    /**
     * 动态查找会议
     * @return
     */
    public List<Meeting> select(Meeting meeting);

    /**
     * 根据会议ID查看某个会议的所有参会人
     * @param meetingId
     * @return
     */
    public List<User> participants(Integer meetingId);

    /**
     * 根据会议ID和用户ID删除某个会议中的某个参会人
     * @param meetingId
     * @param userId
     * @return
     */
    public boolean deleteParticipant(Integer meetingId, Integer userId);

    /**
     * 根据会议ID和用户ID批量删除某个会议中的参会人
     * @param meetingId
     * @param userIds
     * @return
     */
    public boolean deleteParticipants(Integer meetingId, int[] userIds);

    /**
     * 根据会议ID和用户ID新增参会人
     * @param meetingId
     * @param userId
     * @return
     */
    public boolean addParticipant(Integer meetingId, Integer userId);

    /**
     * 根据会议ID和用户ID批量新增参会人
     * @param meetingId
     * @param userIds
     * @return
     */
    public boolean addParticipants(Integer meetingId, List<Integer> userIds);

    /**
     * 签到
     * @param meetingId
     * @param userId
     * @return
     */
    public boolean checkIn(Integer meetingId, Integer userId);

    /**
     * 查询签到或没签到的参会人
     * @param meetingId
     * @param isAttend
     * @return
     */
    public List<User> checkInList(Integer meetingId, int isAttend);

    /**
     * 检查会议是否存在
     * @param meetingId
     * @return
     */
    public boolean isExist(Integer meetingId);
}
