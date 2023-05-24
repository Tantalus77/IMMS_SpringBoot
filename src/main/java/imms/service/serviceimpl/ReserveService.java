package imms.service.serviceimpl;

import imms.dao.ReserveMapper;
import imms.dao.RoomMapper;
import imms.model.Reserve;
import imms.model.Room;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;

@Service
public class ReserveService {
    private static SqlSessionFactory sqlSessionFactory = null;
    @Autowired
    private ReserveMapper reserveMapper;
    //初始化sqlSessionFactory
    static{
        try {
            String resource = "mybatis-config.xml.bak";
            InputStream inputStream = Resources.getResourceAsStream(resource);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        } catch (Exception e) {
            System.out.println("数据库出错！");
            e.printStackTrace();
        }
    }

    /**
     * 功能:
     * 1.增加一条预约
     * 2.删除一条预约
     * 3.修改某条预约的信息
     * 4.查询数据库中所有的预约
     * 5.通过不确定的条件查询预约
     * 6.查询某个用户的全部预约
     * 7.查询某个房间的全部预约
     * 8.查询某个会议的所有预约
     * 9.通过预约状态查询预约
     * 10.管理员审核通过某个预约，成功返回true，失败返回false
     * 11.管理员驳回某个预约，成功返回true，失败返回false
    * */

    //1.增加一条预约
    public boolean addReserve(Reserve reserve){
        try {
            reserveMapper.addReserve(reserve);
            return true;
        }catch (Exception e) {
            System.out.println("预约失败");
            return false;
        }
    }

    //2.删除一条预约
    public boolean deleteReserve(Integer reserveId){
        try {
            reserveMapper.deleteReserve(reserveId);
            return true;
        } catch (Exception e) {
            System.out.println("删除失败");
            return false;
        }
    }

    //3.修改某条预约的信息
    public boolean updateReserveInfo(Reserve reserve) {
        try {
            reserveMapper.updateReserve(reserve);
            return true;
        } catch (Exception e) {
            System.out.println("修改失败");
            return false;
        }
    }

    //4.查询数据库中所有的预约
    public List<Reserve> selectAll(){
        List<Reserve> reservers = reserveMapper.selectAll();
        return reservers;
    }

    //5.通过不确定的条件查询预约
    public List<Reserve> select(Reserve reserve){
        List<Reserve> reserves = reserveMapper.select(reserve);
        return reserves;
    }

    //6.查询某个用户的全部预约
    public List<Reserve> selectByUserId(int UserId){
        List<Reserve> reserves = reserveMapper.selectByUserId(UserId);
        return reserves;
    }

    //7.查询某个房间的全部预约
    public List<Reserve> selectByRoomId(int roomId){
        List<Reserve> reserves = reserveMapper.selectByRoomId(roomId);
        return reserves;
    }

    //8.查询某个会议的所有预约
    public List<Reserve> selectByMeetingId(Integer meetingId) {
        List<Reserve> reserves = reserveMapper.selectByMeetingId(meetingId);
        return reserves;
    }

    //9.通过预约状态查询预约
    public List<Reserve> selectByReserveStatus(Integer status) {
        List<Reserve> reserves = reserveMapper.selectByStatus(status);
        return reserves;
    }

    //10.管理员审核通过某个预约，成功返回true，失败返回false
    public boolean pass(Integer reserveId) {
        try {
            reserveMapper.reservePass(reserveId);
            return true;
        }catch (Exception e) {
            System.out.println("审核失败");
            return false;
        }
    }

    //11.管理员驳回某个预约，成功返回true，失败返回false
    public boolean reject(Integer reserveId){
        try{
            reserveMapper.reserveReject(reserveId);
            return true;
        }catch (Exception e) {
            System.out.println("审核失败");
            return false;
        }
    }
}
