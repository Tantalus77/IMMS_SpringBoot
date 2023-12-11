package imms.service;

import imms.model.*;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.List;
@Transactional
 public interface UserServiceInterface {
    /**
     * 用户通过邮箱登录
     * @param email
     * @param password
     * @return
     */
     User loginByEmail(String email,String password);

    /**
     * 用户管理自己的个人信息
     * @param user
     * @return
     */
     boolean editInfo(User user);

    /**
     * 用户修改头像，这里是把头像图片在文件系统中的地址保存到数据库中去
     * 也就是说在controller里应当要接受图片，并且把图片保存到一个指定路径上去
     * @param picAddress
     * @return
     */
     boolean setPicture(Integer userId, String picAddress);

    /**
     * 预定会议
     * 用户预期输入主题、日期、时间范围、简介、是否签到
     * reserve中的字段有：预约者、房间id、会议id
     * meeting中的字段有：主持人、日期、开始时间、结束时间、主题、简介、是否签到、会议码
     * 这个服务就把会议添加到数据库中，添加时也生成一个会议code，并且自动生成一个reserve，同样放到数据库里，
     * 然后同时也在participate表里添加一条记录，即主持会议就等于参加了该会议
     * invite表待设计
     * @param
     * @param meeting
     * @return
     */
     boolean reserveMeeting(Meeting meeting) throws ParseException;

    /**
     * 获取用户所有会议，包括主持的和参加的
     * @param
     * @return
     */
     List<Meeting> myMeetings(Integer userId);

    /**
     * 获取我组织的会议（我是预定者的会议）
     * @param userId
     * @return
     */
     List<Meeting> myOrganizedMeeting(Integer userId);

    /**
     * 获取用户所有的历史会议，即获取当前时间之前的所有会议
     * @param userId
     * @return
     */
     List<Meeting> historyMeetings(Integer userId);


    /**
     * 通过会议的code参与某个会议。具体流程为：输入会议code，点击参加按钮，调用这个服务，然后添加一条participate记录
     * 要求用code找到会议id
     * @return
     */

    boolean attendMeetingByCode(Integer userId, String code);

    /**
     * 使用邮箱注册
     * @param email
     * @param password
     * @return
     */
     boolean register(String email, String password);

    /**
     * 向用户展示当前所有会议室
     *
     * @return
     */
     List<Room> allRooms();

    /**
     * 搜索会议，预期通过尺寸、时间、地址搜索会议室
     *
     * @param room
     * @return
     */
     List<Room> selectRooms(Room room);

    /**
     * 邀请某位用户参加会议
     * 预期：用户进入“我的会议”，点击查看某一会议的详情，配有一个可以搜索用户的搜索框，搜索框中输入邮箱或者姓名后会弹出下拉框，下拉框中有搜索到的用户，点击旁边的邀请按钮即可邀请
     * @param userId
     * @return
     */
     boolean invite(Integer userId,Integer inviterId,Integer meetingId);

    /**
     * 用户同意某个会议的邀请
     * @param userId
     * @param meetingId
     * @return
     */
     boolean agree(Integer userId,Integer meetingId);

    /**
     * 用户同意某个会议的邀请
     * @param invite
     * @return
     */
     boolean agree(Invite invite);

    /**
     * 用户拒绝某个会议的邀请
     * @param userId
     * @param meetingId
     * @return
     */
     boolean reject(Integer userId,Integer meetingId);

    /**
     * 用户拒绝某个会议的邀请
     * @param invite
     * @return
     */
     boolean reject(Invite invite);

    /**
     * 获取某个用户的所有邀请（这个用户是被邀请的）
     * @param userId
     * @return
     */
     List<Invite> myInvitations(Integer userId);

    /**
     *  检查输入的会议时间是否空闲
     * @param meeting
     * @return
     */
    boolean isAvailableTime(Meeting meeting) throws ParseException;

    /**
     *  查询某个会议室的所有已有会议
     * @param roomId
     * @return
     */
    List<Meeting> meetingsOfRoom(Integer roomId);

}
