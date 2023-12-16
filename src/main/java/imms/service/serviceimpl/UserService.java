package imms.service.serviceimpl;

import imms.dao.*;
import imms.model.*;
import imms.service.UserServiceInterface;
import imms.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Date;

/**
 * @author 普朗千克
 * @lastUpdateTime 2023/11/17 13：45
 * @description: 为用户提供的服务
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
     * @description:  用户通过邮箱登录
     * @param email
     * @param password
     * @return
     */
    @Override
    public User loginByEmail(String email, String password) {
        if(email == "" || password == "") {
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
     * @description:  用户修改自己的信息
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
     * @description:  用户修改头像
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
     * @description:  用户预定会议
     * 前端预计传递信息：会议室Id、预定人id、预定日期、预定开始时间、预定结束时间、主题、介绍、是否签到
     * 该service需要根据这些信息，
     * 1.先创建一个Meeting对象保存信息，并生成一个code（单独写一个工具类放到utils包里），存到数据库里
     * 3.根据数据库返回的meetingId，在participate表中插入一条参会记录（meetingId、userId）
     * @param meeting
     * @return
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
     * @description:  查询该用户的所有会议，包括主持的和参加的
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
     * @description:  查询该用户组织的会议
     * 在meeting表里查询即可（用MeetingMapper中的方法）
     * @param userId
     * @return
     */
    @Override
    public List<Meeting> myOrganizedMeeting(Integer userId) {
        List<Meeting> meetings = mm.selectByUserId(userId);
        if(meetings.isEmpty()){return null;}
        return meetings;
    }

    /**
     * @description:  查询该用户的历史会议，包括组织的和参加的
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
        if(meetings.isEmpty()){return null;}
        return meetings;
    }

    /**
     * @description：  用户通过输入会议码参加会议
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
        if (meeting == null) {
            return false;
        }
        Integer meetingId = meeting.getMeetingId();

        pm.addParticipant(meetingId, userId);
        return true;
    }

    /**
     * @description：  使用邮箱注册
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
     * @description：  向用户展示系统中所有的会议室
     */
    @Override
    public List<Room> allRooms() {
        List<Room> rooms = rm.selectAll();
        if(rooms.isEmpty()){return null;}
        return rooms;
    }

    /**
     * @param room
     * @return
     * @description：  用户查询需要的会议室
     */
    @Override
    public List<Room> selectRooms(Room room) {
        List<Room> roomReturn = rm.selectRoom(room);
        if(roomReturn.isEmpty()){return null;}
        return roomReturn;
    }

    /**
     * @description：  用户邀请其他用户参会
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
     * @description:  用户同意某个邀请
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
     * @description  用户同意某个邀请
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
     * @description  用户拒绝某个邀请
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
     * @description  用户拒绝某个邀请
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
     * @description  查询某个被邀请者的所有邀请
     * @param userId
     * @return
     */
    @Override
    public List<Invite> myInvitations(Integer userId) {
        List<Invite> invites = im.selectByUserId(userId);
        return invites;
    }

    /**
     * @description: 判断一个会议的时间是否不与当前会议室其它会议冲突
     * @param meeting
     * @return
     * @throws ParseException
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

    @Override
    public List<Meeting> meetingsOfRoom(Integer roomId) {
        return mm.selectByRoomId(roomId);
    }

    /**
     * 根据开始与结束时间找出没有被预约的最空闲会议室，并根据使用程度进行排序，再根据传入的roomSize排除不合要求的会议室
     * @param startTime
     * @param endTime
     * @param date
     * @return
     */
    public List<Room> IntelligentSelectRoomsByTime(String startTime, String endTime, String date, int roomSize){
        //找出所有冲突的会议
        List<Meeting> meetings = mm.selectByPeriod(startTime, endTime, date);
        List<Room> rooms = rm.selectAll();
        List<Room> newRooms = new ArrayList<>();
        //找出所有的没有冲突的会议室
        for(int i = 0; i < meetings.size(); i++){
            if(rooms.contains(rm.selectRoomById(meetings.get(i).getRoomId()))){
                rooms.remove(rm.selectRoomById(meetings.get(i).getRoomId()));
            }
        }

        //将所有会议室的numberOfUses设为0
        for (int i = 0; i < rooms.size(); i++) {
            rooms.get(i).setNumberOfUses(0);
        }

        //根据会议室使用情况进行排序
        meetings = mm.selectAll();
        for(int i = 0; i < meetings.size(); i++){
            int roomId = meetings.get(i).getMeetingId();
            if(rooms.contains(rm.selectRoomById(roomId))){
                //找到这个被预约的会议室，在其numberOfUses上加一
                //可能会出错，出错就改为双循环
                rooms.get(rooms.indexOf(rm.selectRoomById(roomId))).setNumberOfUses(rooms.get(rooms.indexOf(rm.selectRoomById(roomId))).getNumberOfUses() + 1);
            }
        }

        while(rooms.size() > 0){
            int min = 999999999;
            int minId = -1;
            //根据numberOfUses，对会议室列表进行排序
            for (int i = 0; i < rooms.size(); i++) {
                if (rooms.get(i).getNumberOfUses() <= min) {
                    min = rooms.get(i).getNumberOfUses();
                    minId = i;
                }
            }
            //将最少使用的会议室放入newRooms并在rooms删除该会议室
            newRooms.add(rooms.get(minId));
            rooms.remove(minId);
        }

        //newRooms里的会议室中如果roomSize小于传入的roomSize则去除
        for (int i = 0; i < newRooms.size(); i++) {
            if (newRooms.get(i).getRoomSize() < roomSize) {
                newRooms.remove(i);
            }
        }

        return newRooms;
    }
}

