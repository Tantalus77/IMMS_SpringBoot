package imms.service.serviceimpl;

import imms.dao.UserMapper;
import imms.model.Meeting;
import imms.model.Room;
import imms.model.User;
import imms.service.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

//TODO:UserService需要实现
//TODO:invite表需要设计，创建Model和Dao
@Service
public class UserService implements UserServiceInterface {

    @Autowired
    private UserMapper um;


    @Override
    public boolean loginByEmail(String email, String password) {
        return false;
    }

    @Override
    public boolean editInfo(User user) {
        return false;
    }

    @Override
    public boolean setPictureInfo(String picAddress) {
        return false;
    }

    @Override
    public boolean reserveMeeting(Meeting meeting, List<Integer> userIds) {
        return false;
    }

    @Override
    public List<Meeting> myMeetings(Integer userId) {
        return null;
    }

    @Override
    public List<Meeting> myOrganizedMeeting(Integer userId) {
        return null;
    }

    @Override
    public List<Meeting> historyMeetings(Integer userId) {
        return null;
    }

    @Override
    public boolean attendMeetingByCode(Integer userId, Integer code) {
        return false;
    }

    @Override
    public boolean register(String email, String password) {
        return false;
    }

    @Override
    public boolean allRooms() {
        return false;
    }

    @Override
    public boolean selectRooms(Room room) {
        return false;
    }

    @Override
    public boolean invite(Integer userId) {
        return false;
    }
}

