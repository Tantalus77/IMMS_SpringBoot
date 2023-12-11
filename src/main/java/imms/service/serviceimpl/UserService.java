package imms.service.serviceimpl;

import imms.dao.*;
import imms.model.Invite;
import imms.model.Meeting;
import imms.model.Room;
import imms.model.User;
import imms.service.UserServiceInterface;
import imms.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author 普朗千克
 * lastUpdateTime: 2023/11/17 13：45
 *  为用户提供的服务
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
    private InviteMapper im;

    /**
     * 用户通过邮箱登录
     * @param email 用户邮箱
     * @param password 用户密码
     * @return 返回登录的用户
     */
    @Override
    public User loginByEmail(String email, String password) {
        if("".equals(email) || "".equals(password)) {
            return null;
        }
        User user = um.selectByEmail(email);
        if(user == null) {
            return null;
        }
        //判断用户输入邮箱对应的用户的密码是否等于用户输入的密码
        //同时忽略用户输入密码前后的空格
        if(user.getUserPassword().equals(password.trim())){return user;}
        else return null;
    }

    /**
     * 用户修改自己的信息
     * @param user 要修改的用户修改后的信息
     * @return 是否修改成功
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
     * 用户修改头像
     * 由Controller接受用户上传的图片，并保存到系统下的相应目录中，命名为用户id。该service负责把controller给出的图片保存路径上传到数据库中
     *
     * @param picAddress 图片保存的路径
     * @return 是否保存成功
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
     * 用户预定会议
     * 前端预计传递信息：会议室Id、预定人id、预定日期、预定开始时间、预定结束时间、主题、介绍、是否签到
     * 该service需要根据这些信息，
     * 1.先创建一个Meeting对象保存信息，并生成一个code（单独写一个工具类放到utils包里），存到数据库里
     * 3.根据数据库返回的meetingId，在participate表中插入一条参会记录（meetingId、userId）
     *
     * @param meeting 预定的会议的信息
     * @return 是否预定成功
     */
    @Override
    public boolean reserveMeeting(Meeting meeting) throws ParseException {

        if(!isAvailableTime(meeting)){
            return false;
        }

        String code = Utils.dateCode();
        meeting.setCode(code);

        mm.addMeeting(meeting);

        Integer meetingId = meeting.getMeetingId();
        System.out.println(meetingId);

        pm.addParticipant(meetingId, meeting.getUserId());
        return true;
    }

    /**
     * 查询该用户的所有会议，包括主持的和参加的
     * 在participate表里查询即可（用ParticipateMapper中的方法）
     *
     * @param userId 要查询的用户的id
     * @return 该用户的所有用户
     */
    @Override
    public List<Meeting> myMeetings(Integer userId) {
        return pm.selectMeetings(userId);
    }

    /**
     * 查询该用户组织的会议
     * 在meeting表里查询即可（用MeetingMapper中的方法）
     *
     * @param userId 要查询的用户的id
     * @return 该用户组织的会议
     */
    @Override
    public List<Meeting> myOrganizedMeeting(Integer userId) {
        List<Meeting> meetings = mm.selectByUserId(userId);
        if(meetings.isEmpty()){return null;}
        return meetings;
    }

    /**
     * 查询该用户的历史会议，包括组织的和参加的
     * 需要获取当前时间，并根据这个时间查找在此之前该用户参加的会议
     *
     * @param userId 要查询的用户的id
     * @return 该用户的历史会议
     */
    @Override
    public List<Meeting> historyMeetings(Integer userId) {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = formatter.format(date);

        List<Meeting> meetings = mm.selectByTime(dateStr);
        if(meetings.isEmpty()){return null;}
        return meetings;
    }

    /**
     * 用户通过输入会议码参加会议
     * 预期流程：
     * 1.根据code获取该meeting的meetingId
     * 2.根据meetingId和userId在participate表中插入一条记录（meetingId、userId）
     * @param userId 要参加会议的用户
     * @param code 要参加的会议的会议码
     * @return 是否参加成功
     */
    @Override
    public boolean attendMeetingByCode(Integer userId, String code) {
        Meeting meeting = mm.selectByCode(code);
        if (meeting == null) {
            return false;
        }
        Integer meetingId = meeting.getMeetingId();

        pm.addParticipant(meetingId, userId);
        return true;
    }

    /**
     * 使用邮箱注册
     * @param email 用户注册的邮箱
     * @param password 用户的密码
     * @return 是否注册成功
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
     * 向用户展示系统中所有的会议室
     *
     * @return 系统中所有会议室
     */
    @Override
    public List<Room> allRooms() {
        List<Room> rooms = rm.selectAll();
        if(rooms.isEmpty()){return null;}
        return rooms;
    }

    /**
     * 用户查询需要的会议室
     *
     * @param room 要查询的会议室条件
     * @return 符合用户条件的会议室
     */
    @Override
    public List<Room> selectRooms(Room room) {
        List<Room> roomReturn = rm.selectRoom(room);
        if(roomReturn.isEmpty()){return null;}
        return roomReturn;
    }

    /**
     * 用户邀请某位用户参加某个会议
     *
     * @param userId 被邀请的用户id
     * @param inviteId 邀请者id
     * @param meetingId 会议id
     * @return 是否成功
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
     * 用户同意某个邀请
     * @param userId 该用户id
     * @param meetingId 会议id
     * @return 是否成功
     */
    @Override
    public boolean agree(Integer userId, Integer meetingId) {
        List<Invite> invites = im.selectByUserIdAndByMeetingId(userId, meetingId);

        try {
            for (Invite invite : invites) {
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
     * 用户同意某个邀请
     * @param invite 某个邀请
     * @return 是否成功
     */
    @Override
    public boolean agree(Invite invite) {
        invite.setStatus(1);
        im.updateInvite(invite);
        pm.addParticipant(invite.getMeetingId(), invite.getUserId());
        return true;
    }

    /**
     *   用户拒绝某个邀请
     * @param userId 用户id
     * @param meetingId 会议id
     * @return 是否成功
     */
    @Override
    public boolean reject(Integer userId, Integer meetingId) {
        List<Invite> invites = im.selectByUserIdAndByMeetingId(userId, meetingId);

        try {
            for (Invite invite : invites) {
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
     * 用户拒绝某个邀请
     * @param invite 某个邀请
     * @return 是否成功
     */
    @Override
    public boolean reject(Invite invite) {
        invite.setStatus(2);
        im.updateInvite(invite);
        return true;
    }

    /**
     * 查询某个被邀请者的所有邀请
     * @param userId 查询的用户的id
     * @return 该用户的所有邀请
     */
    @Override
    public List<Invite> myInvitations(Integer userId) {
        return im.selectByUserId(userId);
    }
    

    /**
     * 判断一个会议的时间是否不与当前会议室其它会议冲突
     * @param meeting 要判断的会议
     * @return 是否不冲突
     * @throws ParseException 字符串转换为Date对象异常
     */
    @Override
    public boolean isAvailableTime(Meeting meeting) throws ParseException {
        Date curDateTime = new Date();

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat sdft = new SimpleDateFormat("yyyy-MM-dd");
        Date curDate = sdft.parse(sdft.format(curDateTime));

        if(sdft.parse(meeting.getDate()).before(curDate)){
            return false;
        }else if(sdft.parse(meeting.getDate()).equals(curDate)){
            Date curTime = sdf.parse(sdf.format(curDateTime));
            if(sdf.parse(meeting.getStartTime()).before(curTime)) {
                return false;
            }
        }

        List<Meeting> curMeetings = mm.selectByRoom(meeting.getRoomId(),meeting.getDate());

        Date bt = sdf.parse(meeting.getStartTime());
        Date et = sdf.parse(meeting.getEndTime());
        if(bt.after(et)) {
            return false;
        }


        for (Meeting curmeeting:curMeetings) {
            Date curBt = sdf.parse(curmeeting.getStartTime());
            Date curEt = sdf.parse(curmeeting.getEndTime());
            if(bt.before(curEt) && et.after(curBt)){
                return false;}
        }
        return true;
    }

    /**
     * 查询某个会议室的所有会议
     * @param roomId 某个会议室id
     * @return 该会议室的所有会议
     */
    @Override
    public List<Meeting> meetingsOfRoom(Integer roomId) {
        return mm.selectByRoomId(roomId);
    }
}

