package imms.service.serviceimpl;

import imms.dao.*;
import imms.model.*;
import imms.service.UserServiceInterface;
import imms.utils.Code;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Date;

/**
 * @author 普朗千克
 * @lastUpdateTime 2023/11/17 13：45
 * @description: TODO
 */
@Service
public class UserService implements UserServiceInterface {

    @Autowired
    private UserMapper um;

    @Autowired
    private ParticipateMapper pm;

    @Autowired
    private MeetingMapper mm;

    @Autowired
    private RoomMapper rm;

    @Autowired
    private ReserveMapper rem;

    @Autowired
    private InviteMapper im;

    /**
     * @description: TODO 用户通过邮箱登录
     * @param email
     * @param password
     * @return
     */
    @Override
    public boolean loginByEmail(String email, String password) {
        if(email == "" || password == "") {
            return false;
        }
        User user = um.selectByEmail(email);
        if(user == null) {
            return false;
        }
        //判断用户输入邮箱对应的用户的密码是否等于用户输入的密码
        //同时忽略用户输入密码前后的空格
        return user.getUserPassword().equals(password.trim());
    }

    /**
     * @description: TODO 用户修改自己的信息
     * @param user
     * @return
     */
    @Override
    public boolean editInfo(User user) {
        if(user.getUserId() == 0) return false;
        try{
            um.updateUser(user);
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * @description: TODO 用户修改头像
     * 由Controller接受用户上传的图片，并保存到系统下的相应目录中，命名为用户id。该service负责把controller给出的图片保存路径上传到数据库中
     * @param picAddress
     * @return
     */
    @Override
    public boolean setPicture(Integer userId,String picAddress) {
        if(picAddress == null) return false;
        try{
            um.setPicture(userId,picAddress);
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * @description: TODO 用户预定会议
     * 前端预计传递信息：会议室Id、预定人id、预定日期、预定开始时间、预定结束时间、主题、介绍、是否签到
     * 该service需要根据这些信息，
     * 1.先创建一个Meeting对象保存信息，并生成一个code（单独写一个工具类放到utils包里），存到数据库里
     * 2.根据数据库返回的meetingId，创建一个reserve对象（userId、roomId、meetingId），并存到数据库里
     * 3.根据数据库返回的meetingId，在participate表中插入一条参会记录（meetingId、userId）
     * @param meeting
     * @param roomId
     * @return
     */
    @Override
    public boolean reserveMeeting(Meeting meeting, Integer roomId) {
        String code = Code.dateCode();
        meeting.setCode(code);
        Integer meetingId = mm.addMeeting(meeting);

        Reserve reserve = new Reserve(meeting.getUserId(), roomId, meetingId);
        rem.addReserve(reserve);

        pm.addParticipant(meetingId, meeting.getUserId());
        return true;
    }

    /**
     * @description: TODO 查询该用户的所有会议，包括主持的和参加的
     * 在participate表里查询即可（用ParticipateMapper中的方法）
     * @param userId
     * @return
     */
    @Override
    public List<Meeting> myMeetings(Integer userId) {
        List<Meeting> meetings = pm.selectMeetings(userId);
        return meetings;
    }

    /**
     * @description: TODO 查询该用户组织的会议
     * 在meeting表里查询即可（用MeetingMapper中的方法）
     * @param userId
     * @return
     */
    @Override
    public List<Meeting> myOrganizedMeeting(Integer userId) {
        List<Meeting> meetings = mm.selectByUserId(userId);
        return meetings;
    }

    /**
     * @description: TODO 查询该用户的历史会议，包括组织的和参加的
     * 需要获取当前时间，并根据这个时间查找在此之前该用户参加的会议
     * @param userId
     * @return
     */
    @Override
    public List<Meeting> historyMeetings(Integer userId) {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = formatter.format(date);

        List<Meeting> meetings = mm.selectByTime(dateStr);
        return meetings;
    }

    /**
     * @description： TODO 用户通过输入会议码参加会议
     * 预期流程：
     * 1.根据code获取该meeting的meetingId
     * 2.根据meetingId和userId在participate表中插入一条记录（meetingId、userId）
     * @param userId
     * @param code
     * @return
     */
    @Override
    public boolean attendMeetingByCode(Integer userId, String code) {
        Meeting meeting = mm.selectByCode(code);
        Integer meetingId = meeting.getMeetingId();

        pm.addParticipant(meetingId, userId);
        return true;
    }

    /**
     * @description： TODO 使用邮箱注册
     * @param email
     * @param password
     * @return
     */
    @Override
    public boolean register(String email, String password) {
        User user = new User();
        user.setUserEmail(email);
        user.setUserPassword(password);
        um.addUser(user);
        return true;
    }

    /**
     * @return
     * @description： TODO 向用户展示系统中所有的会议室
     */
    @Override
    public List<Room> allRooms() {
        List<Room> rooms = rm.selectAll();
        return rooms;
    }

    /**
     * @param room
     * @return
     * @description： TODO 用户查询需要的会议室
     */
    @Override
    public List<Room> selectRooms(Room room) {
        List<Room> roomReturn = rm.selectRoom(room);
        return roomReturn;
    }

    /**
     * @description： TODO 用户邀请其他用户参会
     * @param userId
     * @return
     */
    @Override
    public boolean invite(Integer userId,Integer inviteId,Integer meetingId) {
        Invite invite = new Invite(userId, inviteId, meetingId);
        try {
            im.addInvite(invite);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * @description: TODO 用户同意某个邀请
     * @param userId
     * @param meetingId
     * @return
     */
    @Override
    public boolean agree(Integer userId, Integer meetingId) {
        List<Invite> invites = im.selectByUserIdAndByMeetingId(userId, meetingId);

        try {
            for(int i = 0; i < invites.size(); i++){
                Invite invite = invites.get(i);
                invite.setStatus(1);
                im.updateInvite(invite);
            }
            pm.addParticipant(meetingId, userId);

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * @description TODO 用户同意某个邀请
     * @param invite
     * @return
     */
    @Override
    public boolean agree(Invite invite) {
        invite.setStatus(1);
        im.updateInvite(invite);
        pm.addParticipant(invite.getMeetingId(), invite.getUserId());
        return true;
    }

    /**
     * @description TODO 用户拒绝某个邀请
     * @param userId
     * @param meetingId
     * @return
     */
    @Override
    public boolean reject(Integer userId, Integer meetingId) {
        List<Invite> invites = im.selectByUserIdAndByMeetingId(userId, meetingId);

        try {
            for(int i = 0; i < invites.size(); i++){
                Invite invite = invites.get(i);
                invite.setStatus(2);
                im.updateInvite(invite);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    /**
     * @description TODO 用户拒绝某个邀请
     * @param invite
     * @return
     */
    @Override
    public boolean reject(Invite invite) {
        invite.setStatus(2);
        im.updateInvite(invite);
        return true;
    }

    /**
     * @description TODO 查询某个被邀请者的所有邀请
     * @param userId
     * @return
     */
    @Override
    public List<Invite> myInvitations(Integer userId) {
        List<Invite> invites = im.selectByUserId(userId);
        return invites;
    }
}

