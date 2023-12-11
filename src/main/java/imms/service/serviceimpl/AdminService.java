package imms.service.serviceimpl;

import imms.dao.MeetingMapper;
import imms.dao.RoomMapper;
import imms.dao.UserMapper;
import imms.model.Meeting;
import imms.model.Room;
import imms.model.User;
import imms.service.AdminServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService implements AdminServiceInterface {
    @Autowired
    private UserMapper um;

    @Autowired
    private RoomMapper rm;

    @Autowired
    private MeetingMapper mm;

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
        return user.getUserPassword().equals(password.trim()) && (user.getIsAdmin()==1) ;
    }

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

    @Override
    public Integer addUser(User newUser) {
        um.addUser(newUser);
        return newUser.getUserId();
    }

    @Override
    public boolean deleteUser(List<Integer> userIds) {
        for (Integer userId: userIds) {
            um.deleteUser(userId);
        }
        return true;
    }

    @Override
    public List<User> selectUser(User user) {
        if (user == null) {
            return um.selectAll();
        }
        user.setUserName("%"+user.getUserName()+"%");
        user.setUserEmail("%"+user.getUserEmail()+"%");
        user.setUserNumber("%"+user.getUserNumber()+"%");
        user.setUserPhoneNumber("%"+user.getUserPhoneNumber()+"%");

        return um.select(user);
    }

    public User selectByEmail(String email){
        if(email == null) return null;
        return um.selectByEmail(email);
    }

    @Override
    public boolean updateUser(User user) {
        if (user == null) return false;
        try{
            um.updateUser(user);
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean addRoom(Room newRoom) {
        if(newRoom == null)return false;
        try{
         rm.addRoom(newRoom);
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteRoom(List<Integer> roomIds) {
        if(roomIds==null||roomIds.isEmpty()) return false;
        try {
            for (Integer roomId:
                 roomIds) {
                rm.deleteRoom(roomId);
            }
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public List<Room> selectRoom(Room room) {
        if(room == null) return rm.selectAll();
        room.setRoomAddress("%"+room.getRoomAddress()+"%");
        return rm.selectRoom(room);
    }

    @Override
    public boolean updateRoom(Room room) {
        rm.updateRoom(room);
        return true;
    }

    @Override
    public List<Meeting> allMeeting() {
        return mm.selectAll();
    }

    @Override
    public boolean deleteMeeting(List<Integer> meetingId) {
        try{
            for (Integer id:
                 meetingId) {
                mm.deleteMeeting(id);
            }
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public List<Meeting> selectMeeting(Meeting meeting) {
        if(meeting == null)return mm.selectAll();
        return mm.select(meeting);
    }

    @Override
    public boolean updateMeeting(Meeting meeting) {
        mm.updateMeeting(meeting);
        return true;
    }

}
