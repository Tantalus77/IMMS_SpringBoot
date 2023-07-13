package imms.dao;

import imms.model.Reserve;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ReserveMapper {
    /**
     * 总功能：
     * 1.增加一条预约
     * 2.删除一条预约
     * 3.修改某条预约
     * 4.查询数据库中所有的预约
     * 5.通过不确定的条件查询预约
     * 6.查询某个用户的全部预约
     * 7.查询某个房间的全部预约
     * 8.查询某个会议的所有预约
     * 9.通过预约状态查询预约
     * 10.设置预约通过
     * 11.设置预约驳回
     */

    //1.增加一条预约
    void addReserve(Reserve reserve);

    //2.删除一条预约
    void deleteReserve(Integer reserveId);

    //3.修改某条预约
    void updateReserve(Reserve reserve);

    //4.查询数据库中所有的预约
    @Select("select * from reserve")
    List<Reserve> selectAll();

    //5.通过不确定的条件查询预约
    List<Reserve> select(Reserve reserve);

    //6.查询某个用户的全部预约
    @Select("select * from reserve where userId = #{userId}}")
    List<Reserve> selectByUserId(Integer userId);

    //7.查询某个房间的全部预约
    @Select("select * from reserve where roomId = #{roomId}}")
    List<Reserve> selectByRoomId(Integer roomId);

    //8.查询某个会议的所有预约
    @Select("select * from reserve where meetingId = #{meeting}}")
    List<Reserve> selectByMeetingId(Integer meetingId);

    //9.通过预约状态查询预约
    @Select("select * from reserve where status = #{status}}")
    List<Reserve> selectByStatus(Integer status);

    //10.设置预约通过
    @Update("update reserve set status = 1 where reserveId = #{reserveId}}")
    void reservePass(Integer reserveId);

    @Update("update reserve set status = 1, info = #{info} where reserveId = #{reserveId}")
    void reservePassWithInfo(Integer reserveId, String info);

    //11.设置预约驳回
    @Update("update reserve set status = 2, info = #{info} where reserveId = #{reserveId}")
    void reserveReject(Integer reserveId,String info);
}
