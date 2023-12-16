package imms.service.serviceimpl;

import imms.dao.MeetingMapper;
import imms.dao.ParticipateMapper;
import imms.model.Meeting;
import imms.service.Analyser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * @author
 * @lastUpdateTime 2023/12/04 22:54
 * @description
 */
@Service
public class AnalyserImp implements Analyser {

    @Autowired
    MeetingMapper mm;
    @Autowired
    ParticipateMapper pm;

    @Override
    public HashMap<Integer, Integer> timesPerRoomOfUser(Integer userId) {
        HashMap<Integer,Integer> result = new HashMap<>();

        List<HashMap<String,Object>> records = mm.timesPerRoomOfUser(userId);
        if(result.isEmpty()){return null;}
        for (HashMap<String,Object> record:records) {
            Number key = (Number) record.get("roomId");
            Number value = (Number) record.get("times");
            result.put(key.intValue(), value.intValue());
        }

        return result;
    }

    @Override
    public Integer totalMeetingNum(Integer userId){
        List<Meeting> records = mm.selectByUserId(userId);
        return records.size();
    }

    @Override
    public HashMap<Integer, Integer> meetingtimesPerUser(){
        HashMap<Integer, Integer> result = new HashMap<>();
        List<HashMap<String, Object>> records = mm.selectMeetingtimesPerUser();
        for (HashMap<String, Object> record: records) {
            Number key = (Number) record.get("userId");
            Number value = (Number) record.get("meetingNum");
            result.put(key.intValue(), value.intValue());
        }
        return result;
    }

    @Override
    public HashMap<Integer, Integer> meetingNumPerRoom() {
        HashMap<Integer,Integer> result = new HashMap<>();

        List<HashMap<String, Object>> records = mm.meetingNumberPerRoomBydata();
        for (HashMap<String, Object> record: records) {
            Number key = (Number) record.get("roomId");
            Number value = (Number) record.get("meetingNumber");
            result.put(key.intValue(), value.intValue());
        }
            return result;
    }

    public HashMap<String, Integer> meetingNumPerDay(Integer roomId) {
        HashMap<String, Integer> result = new HashMap<>();

        List<HashMap<String, Object>> records = mm.selectMeetingNumByData(roomId);
        for (HashMap<String, Object> record: records) {
            String key = (String) record.get("date");
            Number value = (Number) record.get("meetingNumber");
            result.put(key, value.intValue());
        }
        return result;
    }

    public Double attendRate(Integer meetingId){
        Double numerator = 0.0;
        List<Integer> rows = pm.selectIsAttend(meetingId);
        if(rows.size() > 0){
        for(int i = 0; i < rows.size(); i++){
            if(rows.get(i) == 1)
                    numerator++;
        }
        return numerator / rows.size();
    }
        return 0.0;
    }

    public HashMap<String, Integer> meetingNumByDate(){
        HashMap<String, Integer> result = new HashMap<>();
        List<HashMap<String, Object>> records = mm.selectAllMeetingNumByData();
        for (HashMap<String, Object> record: records) {
            String key = (String) record.get("date");
            Number value = (Number) record.get("meetingNumber");
            result.put(key, value.intValue());
        }
        return result;
    }

    public Double meetingAverageDuration(List<Integer> meetingIds){
        Double meetingTotalDuration = 0.0;
        for (Integer meetingId: meetingIds) {
            meetingTotalDuration += mm.getMeetingDuration(meetingId);
        }
        return meetingTotalDuration / meetingIds.size();
    }


    public Integer currentMeetingNum(){
        List<Meeting> records = mm.selectAllCurrentMeeting();
        return records.size();
    }
}
