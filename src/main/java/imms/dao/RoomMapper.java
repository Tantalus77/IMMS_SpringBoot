package imms.dao;

import imms.model.Meeting;
import imms.model.Room;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface RoomMapper {

    /**
    增加会议室的方法。
     */
    void addRoom(Room room);

    /**
    删除会议室的方法
     */
    void deleteRoom(int roomId);

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

    @Select("select * from roominfo where roomId = #{roomId}")
    Room selectRoomById(Integer roomId);

    @Select("select * from roominfo where roomSize = #{roomSize}")
    List<Room> selectRoomBySize(int roomSize);

    @Select("select * from roominfo where roomAddress = #{roomAddress}")
    List<Room> selectRoomByAddress(String roomAddress);

    @Select("select * from roominfo where roomNumber = #{roomNumber}")
    List<Room> selectRoomByNumber(int roomNumber);

    @Select("select * from roominfo")
    List<Room> selectAll();

    List<Room> selectRoom(Room room);

    List<Room> hasRoom(Room room);

    /**
    修改数据库的方法
     */
    void updateRoom(Room room);

    @Select("select * from roominfo where roomOpenTime < #{startTime} and roomCloseTime > #{endTime}")
    List<Room> selectByTime(String startTime, String endTime);


}
