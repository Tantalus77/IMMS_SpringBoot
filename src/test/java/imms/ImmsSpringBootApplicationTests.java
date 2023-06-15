package imms;

import imms.dao.MeetingMapper;
import imms.dao.RoomMapper;
import imms.model.Room;
import imms.model.User;
import imms.service.RoomServiceInterface;
import imms.service.UserServiceInterface;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.List;

@SpringBootTest
class ImmsSpringBootApplicationTests {

    @Autowired
    private UserServiceInterface userServiceInterface;

    @Autowired
    private RoomServiceInterface roomServiceInterface;

    @Autowired
    private RoomMapper roomMapper;

    @Autowired
    private MeetingMapper meetingMapper;
//    @Test
//    void testUserSelect() {
//        User user = new User();
//        user.setUserName("张");
//
//        List<User> select = userServiceInterface.select(user);
//        System.out.println(select);
//    }
//
//    @Test
//    void testRoomSelect(){
//        Room room = new Room();
//        room.setRoomAddress("沙河");
//
//        List<Room> select = roomServiceInterface.selectRoom(room);
//        System.out.println(select);
//    }


    @Test
    void test2(){
        System.out.println(roomMapper.selectByTime("10:30","12:55"));
    }

    @Test
    void test3(){
        System.out.println(meetingMapper.selectByRoom(4,"2023-6-14"));
    }

    @Test
    void test4(){
        System.out.println(roomServiceInterface.availableRoom("2023-6-15","13:00","15:00"));
    }

    @Test
    void test5(){
        System.out.println(roomServiceInterface.availableRoom("2023-6-15","7:30:08","11:90"));
    }


}
