package imms.service.serviceimpl;

import imms.dao.MeetingMapper;
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
    @Override
    public HashMap<Integer, Integer> timesPerRoomOfUser(Integer userId) {
        HashMap<Integer,Integer> result = new HashMap<>();

        List<HashMap<String,Object>> records = mm.timesPerRoomOfUser(userId);
        for (HashMap<String,Object> record:records) {
            Number key = (Number) record.get("roomId");
            Number value = (Number) record.get("times");
            result.put(key.intValue(), value.intValue());
        }

        return result;
    }
}
