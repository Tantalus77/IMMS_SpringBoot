package imms.service.serviceimpl;

import imms.dao.MeetingMapper;
import imms.dao.ParticipateMapper;
import imms.model.Meeting;
import imms.model.User;
import imms.service.MeetingServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MeetingService implements MeetingServiceInterface {
    @Autowired
    private MeetingMapper meetingMapper;

    @Autowired
    private ParticipateMapper participateMapper;

    @Override
    public boolean addMeeting(Meeting meeting) {
        if(meeting!=null){
            meetingMapper.addMeeting(meeting);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateMeeting(Meeting meeting) {
        if(meeting != null){
            meetingMapper.updateMeeting(meeting);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteMeeting(Integer meetingId) {
        if(meetingId != null){
            meetingMapper.deleteMeeting(meetingId);
            return true;
        }
        return false;
    }


    @Override
    public boolean deleteMeetings(List<Integer> meetingIds) {
        if(!meetingIds.isEmpty()){
            for (Integer meetingId:
                 meetingIds) {
                meetingMapper.deleteMeeting(meetingId);
            }
            return true;
        }
        return false;
    }

    @Override
    public List<Meeting> selectAll() {
        return meetingMapper.selectAll();
    }

    @Override
    public List<Meeting> select(Meeting meeting) {
        return meetingMapper.select(meeting);
    }

    @Override
    public List<User> participants(Integer meetingId) {
        return participateMapper.selectParticipants(meetingId);
    }

    @Override
    public boolean deleteParticipant(Integer meetingId, int userId) {
        participateMapper.deleteParticipant(meetingId,userId);
        return true;
    }

    @Override
    public boolean deleteParticipants(Integer meetingId, int[] userIds) {
        int length = userIds.length;
        if (length > 0) {
            for (int i = 0; i < userIds.length; i++) {
                participateMapper.deleteParticipant(meetingId, userIds[i]);
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean addParticipant(Integer meetingId, int userId) {
        participateMapper.addParticipant(meetingId, userId);
        return true;
    }

    @Override
    public boolean addParticipants(Integer meetingId, int[] userIds) {
        int length = userIds.length;
        if (length > 0) {
            for (int i = 0; i < length; i++) {
                participateMapper.addParticipant(meetingId, userIds[i]);
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean checkIn(Integer meetingId, int userId) {
        participateMapper.checkIn(meetingId,userId);
        return true;
    }

    @Override
    public List<User> checkInList(Integer meetingId, int isAttend) {
        return participateMapper.checkInList(meetingId,isAttend);
    }

    @Override
    public boolean isExist(Integer meetingId) {
        List<Meeting>result = meetingMapper.selectById(meetingId);
        return result.size() > 0 ? true: false;
    }
}
