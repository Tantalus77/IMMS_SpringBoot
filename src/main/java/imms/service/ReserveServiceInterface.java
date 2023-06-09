package imms.service;

import imms.model.Reserve;

import java.util.List;

public interface ReserveServiceInterface {
    /**
     * 新增一个预约
     * @param reserve
     * @return
     */
    public boolean addReserve(Reserve reserve);

    /**
     * 更改预约信息
     * @param reserve
     * @return
     */
    public boolean updateReserve(Reserve reserve);

    /**
     * 根据Id删除某个预约
     * @param reserveId
     * @return
     */
    public boolean deleteReserve(Integer reserveId);

    /**
     * 根据ID批量删除预约
     * @param reserveIds
     * @return
     */
    public boolean deleteReserves(List<Integer> reserveIds);

    /**
     * 查询系统中所有预约
     * @return
     */
    public List<Reserve> selectAll();

    /**
     * 动态查询预约
     * @return
     */
    public List<Reserve> select(Reserve reserve);

    /**
     * 预约审核通过
     * @param reserveId
     * @return
     */
    public boolean pass(Integer reserveId);

    /**
     * 预约审核通过，附加说明信息
     * @param reserveId
     * @param info
     * @return
     */
    public boolean pass(Integer reserveId, String info);

    /**
     * 预约审核不通过，需要说明原因
     * @param reserveId
     * @return
     */
    public boolean reject(Integer reserveId, String info);

    /**
     * 通过用户Id查找预约
     * @param userId
     * @return
     */
    public List<Reserve> selectByUserId(int userId);

    /**
     * 通过预约状态查询会议
     * @param status
     * @return
     */
    public List<Reserve> selectByReserveStatus(int status);

    /**
     * 通过会议室id查询会议
     * @param MeetingId
     * @return
     */
    public List<Reserve> selectByMeetingId(Integer MeetingId);


    /**
     * 通过会议室Id查找预约
     * @param roomId
     * @return
     */
    public List<Reserve> selectByRoomId(int roomId);

}
