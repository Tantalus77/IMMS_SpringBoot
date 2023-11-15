package imms.service;

import imms.model.Meeting;
import imms.model.Reserve;
import imms.model.Room;
import imms.model.User;

import java.util.List;

public interface AdminServiceInterface {
    /**
     * 管理员的登录，需要判断身份
     * @param email
     * @param password
     * @return
     */
    public boolean loginByEmail(String email, String password);

    /**
     * 修改自己的信息，实现方法同用户
     * @param user
     * @return
     */
    public boolean editInfo(User user);

    /**
     * 修改自己的头像，实现方法同用户
     * @param picAddress
     * @return
     */
    public boolean setPicture(Integer userId, String picAddress);

    /**
     * 在系统中直接添加用户，返回新建用户的id
     * @param newUser
     * @return
     */
    public Integer addUser(User newUser);

    /**
     * 在系统中删除用户
     * @param userIds
     * @return
     */
    public boolean deleteUser(List<Integer> userIds);

    /**
     * 根据条件搜索用户
     * @param user
     * @return
     */
    public List<User> selectUser(User user);

    /**
     * 通过邮箱搜索用户
     * @param email
     * @return
     */
    public User selectByEmail(String email);

    /**
     * 根据传入的User的id修改该用户的信息
     * @param user
     * @return
     */
    public boolean updateUser(User user);

    /**
     * 在系统中添加会议室
     * @param newRoom
     * @return
     */
    public boolean addRoom(Room newRoom);

    /**
     * 在系统中删除会议室
     * @param roomIds
     * @return
     */
    public boolean deleteRoom(List<Integer> roomIds);

    /**
     * 根据条件搜索会议室
     * @param room
     * @return
     */
    public List<Room> selectRoom(Room room);

    /**
     * 根据接受的Room的id更新该会议室的信息
     * @param room
     * @return
     */
    public boolean updateRoom(Room room);

    /**
     * 删除某个会议
     * @param meetingId
     * @return
     */
    public boolean deleteMeeting(List<Integer> meetingId);

    /**
     * 根据条件搜索会议
     * @param meeting
     * @return
     */
    public List<Meeting> selectMeeting(Meeting meeting);

    /**
     * 根据接受的Meeting的id修改该会议
     * @param meeting
     * @return
     */
    public boolean updateMeeting(Meeting meeting);

    /**
     * 删除预约
     * @param reserveIds
     * @return
     */
    public boolean deleteReserve(List<Integer> reserveIds);

    /**
     * 根据条件搜索预约
     * @param reserve
     * @return
     */
    public List<Reserve> selectReserve(Reserve reserve);

    /**
     * 根据传入的Reserve更改预约
     * @param reserve
     * @return
     */
    public boolean updateReserve(Reserve reserve);
}
