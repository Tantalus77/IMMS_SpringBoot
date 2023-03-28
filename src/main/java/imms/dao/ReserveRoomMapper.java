package imms.dao;

import imms.model.Room;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ReserveRoomMapper {

    /**
    查询会议室相关的方法，有以下一些：
    1.通过id查找会议室
    2.通过roomSize查找会议室
    3.通过roomOpenTime查找会议室
    4.通过roomCloseTime查找会议室
    5.通过roomAddress查找会议室（模糊）
    6.通过roomNumber查找会议室
    7.通过以上条件的随机组合查找会议室
    8.检查数据库中是否已存在相应的会议室（根据roomAddress和roomNumber确定）
     9.查询所有会议室
     */

    /**
     通过roomId、roomSize、roomAddress、roomNumber、roomIsReserved查找已经被预约的会议室
    */
    @Select("select * from roominfo where roomId = #{roomId} and roomIsReserved = 1")
    Room selectReservedMeetingRoomById(int roomId);

    @Select("select * from roominfo where roomSize = #{roomSize} and roomIsReserved = 1")
    List<Room> selectReservedMeetingRoomBySize(int roomSize);

    @Select("select * from roominfo where roomAddress = #{roomAddress} and roomIsReserved = 1")
    List<Room> selectReservedMeetingRoomByAddress(String roomAddress);

    @Select("select * from roominfo where roomNumber = #{roomNumber} and roomIsReserved = 1")
    List<Room> selectReservedMeetingRoomByNumber(int roomNumber);

    @Select("select * from roominfo where roomIsReserved = 1")
    List<Room> selectAllReservedMeetingRoom();

    /**
     通过roomId、roomSize、roomAddress、roomNumber、roomIsReserved查找没有被预约的会议室
     */
    @Select("select * from roominfo where roomId = #{roomId} and roomIsReserved = 0")
    Room selectAvailableMeetingRoomById(int roomId);

    @Select("select * from roominfo where roomSize = #{roomSize} and roomIsReserved = 0")
    List<Room> selectAvailableMeetingRoomBySize(int roomSize);

    @Select("select * from roominfo where roomAddress = #{roomAddress} and roomIsReserved = 0")
    List<Room> selectAvailableMeetingRoomByAddress(String roomAddress);

    @Select("select * from roominfo where roomNumber = #{roomNumber} and roomIsReserved = 0")
    List<Room> selectAvailableMeetingRoomByNumber(int roomNumber);

    @Select("select * from roominfo where roomIsReserved = 1")
    List<Room> selectAllAvailableMeetingRoom();

    //查找全部的会议室
    List<Room> selectAvailableMeetingRoom(Room room);
    List<Room> selectReservedMeetingRoom(Room room);


    List<Room> hasMeetingRoom(Room room);

    /**
    修改数据库的方法
     */
    void updateMeetingRoomIsReserved(Room room);

    void updateMeetingRoomIsAvailable(Room room);
}
