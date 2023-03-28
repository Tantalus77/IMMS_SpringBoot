package imms.service;

import imms.model.Room;

import java.util.List;

public interface RoomServiceInterface {
    /**
     * 添加会议室
     * @param room
     * @return
     */
    public int addRoom(Room room);

    /**
     * 通过id删除某个会议室
     * @param roomId
     * @return
     */
    public boolean deleteRoom(int roomId);

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
     * 通过id查找会议室
     * @param roomId
     * @return
     */
    public Room selectRoomById(int roomId);

    /**
     * 通过会议室尺寸查找会议室
     * @param roomSize
     * @return
     */
    public List<Room> selectRoomBySize(int roomSize);

    /**
     * 通过会议室地址模糊查找会议室
     * @param roomAddress
     * @return
     */
    public List<Room> selectRoomByAddress(String roomAddress);


    /**
     * 通过会议室门牌号查找会议室
     * @param roomNumber
     * @return
     */
    public List<Room> selectRoomByNumber(int roomNumber);

    /**
     * 查找所有会议室
     * @return
     */
    public List<Room> selectAll();

    /**
     * 通过不确定的条件查找会议室，注意事项：
     * 1. 地址为模糊查询；
     * 2. 门牌号是模糊查询
     * 3. 用户输入的会议室开启时间和关闭时间只要在实际时间区间之内，就可以查询到响应会议室
     * @param room
     * @return
     */
    public List<Room> selectRoom(Room room);

    /**
     * 是否已经存在会议室，注意事项：
     * 1. 通过地址号和门牌号组合确定
     * @param room
     * @return
     */
    public boolean hasRoom(Room room);

    /**
     * 通过id修改会议室
     * @param room
     */
    public boolean updateRoom(Room room);

}
