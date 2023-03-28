package imms.service.serviceimpl;

import imms.dao.ReserveRoomMapper;
import imms.model.Room;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;
import java.util.List;

public class ReserveService {
    private static SqlSessionFactory sqlSessionFactory = null;

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

        /*
    对会议室的预约的功能中，包含以下功能
       1.查询已被预约的房间：
            1.1. 通过会议室id查询；
            1.2. 通过会议室尺寸查询；
            1.3. 通过会议室门牌号查询；
            1.4. 通过会议室地址查询；
            1.5. 查询所有会议室
        2.查询没有被预约的房间：
            2.1. 通过会议室id查询；
            2.2. 通过会议室尺寸查询；
            2.3. 通过会议室门牌号查询；
            2.4. 通过会议室地址查询；
            2.5. 查询所有会议室
        3.是否已存在会议室
        4.修改会议室为已经被预约
        5.修改会议室为没有被预约
     */

    /**
     * 以下为查询已经被预约的会议室的各种方法
     */

    /**
     * 1.1.通过ID查找已经被预约的会议室
     * @param roomId 要查找的会议室的id
     * @return 查找到的room（唯一）
     */
    public Room selectReservedMeetingRoomById(int roomId){
        //获取执行对象
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //获取接口代理对象
        ReserveRoomMapper reserveRoomMapper = sqlSession.getMapper(ReserveRoomMapper.class);
        //执行
        Room room = reserveRoomMapper.selectReservedMeetingRoomById(roomId);
        //提交事务
        sqlSession.commit();
        //释放资源
        sqlSession.close();

        return room;
    }

    /**
     * 1.2.通过会议室尺寸找已被预约的会议室
     * @param roomSize 要查找的会议室的尺寸
     * @return rooms 查找到的会议室们
     */
    public List<Room> selectReservedMeetingRoomBySize(int roomSize){
        //获取执行对象
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //获取接口代理对象
        ReserveRoomMapper reserveRoomMapper = sqlSession.getMapper(ReserveRoomMapper.class);
        //执行
        List<Room> rooms = reserveRoomMapper.selectReservedMeetingRoomBySize(roomSize);
        //提交事务
        sqlSession.commit();
        //释放资源
        sqlSession.close();

        return rooms;
    }

    /**
     * 1.3.通过会议室门牌号查找已经被预约的会议室
     * @param roomNumber 要查找的会议室的门牌号
     * @return 查找到的会议室们
     */
    public List<Room> selectReservedMeetingRoomByNumber(int roomNumber){
        //获取执行对象
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //获取接口代理对象
        ReserveRoomMapper reserveRoomMapper = sqlSession.getMapper(ReserveRoomMapper.class);
        //执行
        List<Room> rooms = reserveRoomMapper.selectReservedMeetingRoomByNumber(roomNumber);
        //提交事务
        sqlSession.commit();
        //释放资源
        sqlSession.close();

        return rooms;
    }

    /**
     * 1.4.通过房间地址查找已经被预约的会议室（模糊查询）
     * @param roomAddress 要查找的会议室的地址
     * @return 查找到的会议室们
     */
    public List<Room> selectReservedMeetingRoomByAddress(String roomAddress){
        //获取执行对象
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //获取接口代理对象
        ReserveRoomMapper reserveRoomMapper = sqlSession.getMapper(ReserveRoomMapper.class);
        //执行
        List<Room> rooms = reserveRoomMapper.selectReservedMeetingRoomByAddress(roomAddress);
        //提交事务
        sqlSession.commit();
        //释放资源
        sqlSession.close();

        return rooms;
    }

    /**
     * 1.5.动态查找已经被预约的会议室。对房间门牌号、房间地址为模糊查询；
     * 其中roomOpenTime和roomCloseTime携带的是会议开启时间和会议结束时间，
     * 即只要用户查找的时间在开启时间和关闭时间之间，就可以找到对应的会议室。
     * @param room 一个携带要查找的会议室信息的会议室对象
     * @return 查找到的会议室们
     */
    public List<Room> selectRoom(Room room){
        //获取执行对象
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //获取接口代理对象
        ReserveRoomMapper reserveRoomMapper = sqlSession.getMapper(ReserveRoomMapper.class);
        //执行
        List<Room> rooms = reserveRoomMapper.selectReservedMeetingRoom(room);
        //提交事务
        sqlSession.commit();
        //释放资源
        sqlSession.close();

        return rooms;
    }

    /**
     * 以下为查询没有被预约的会议室的各种方法
     */

    /**
     * 2.1.通过ID查找没有被预约的会议室
     * @param roomId 要查找的会议室的id
     * @return 查找到的room（唯一）
     */
    public Room selectAvailableMeetingRoomById(int roomId){
        //获取执行对象
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //获取接口代理对象
        ReserveRoomMapper reserveRoomMapper = sqlSession.getMapper(ReserveRoomMapper.class);
        //执行
        Room room = reserveRoomMapper.selectAvailableMeetingRoomById(roomId);
        //提交事务
        sqlSession.commit();
        //释放资源
        sqlSession.close();

        return room;
    }

    /**
     * 2.2.通过会议室尺寸找没有被预约的会议室
     * @param roomSize 要查找的会议室的尺寸
     * @return rooms 查找到的会议室们
     */
    public List<Room> selectAvailableMeetingRoomBySize(int roomSize){
        //获取执行对象
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //获取接口代理对象
        ReserveRoomMapper reserveRoomMapper = sqlSession.getMapper(ReserveRoomMapper.class);
        //执行
        List<Room> rooms = reserveRoomMapper.selectAvailableMeetingRoomBySize(roomSize);
        //提交事务
        sqlSession.commit();
        //释放资源
        sqlSession.close();

        return rooms;
    }

    /**
     * 2.3.通过会议室门牌号查找没有经被预约的会议室
     * @param roomNumber 要查找的会议室的门牌号
     * @return 查找到的会议室们
     */
    public List<Room> selectAvailableMeetingRoomByNumber(int roomNumber){
        //获取执行对象
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //获取接口代理对象
        ReserveRoomMapper reserveRoomMapper = sqlSession.getMapper(ReserveRoomMapper.class);
        //执行
        List<Room> rooms = reserveRoomMapper.selectAvailableMeetingRoomByNumber(roomNumber);
        //提交事务
        sqlSession.commit();
        //释放资源
        sqlSession.close();

        return rooms;
    }

    /**
     * 2.4.通过房间地址查找没有被预约的会议室（模糊查询）
     * @param roomAddress 要查找的会议室的地址
     * @return 查找到的会议室们
     */
    public List<Room> selectAvailableMeetingRoomByAddress(String roomAddress){
        //获取执行对象
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //获取接口代理对象
        ReserveRoomMapper reserveRoomMapper = sqlSession.getMapper(ReserveRoomMapper.class);
        //执行
        List<Room> rooms = reserveRoomMapper.selectAvailableMeetingRoomByAddress(roomAddress);
        //提交事务
        sqlSession.commit();
        //释放资源
        sqlSession.close();

        return rooms;
    }

    /**
     * 2.5.动态查找没有被预约的会议室。对房间门牌号、房间地址为模糊查询；
     * 其中roomOpenTime和roomCloseTime携带的是会议开启时间和会议结束时间，
     * 即只要用户查找的时间在开启时间和关闭时间之间，就可以找到对应的会议室。
     * @param room 一个携带要查找的会议室信息的会议室对象
     * @return 查找到的会议室们
     */
    public List<Room> selectAvailableMeetingRoom(Room room){
        //获取执行对象
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //获取接口代理对象
        ReserveRoomMapper reserveRoomMapper = sqlSession.getMapper(ReserveRoomMapper.class);
        //执行
        List<Room> rooms = reserveRoomMapper.selectAvailableMeetingRoom(room);
        //提交事务
        sqlSession.commit();
        //释放资源
        sqlSession.close();

        return rooms;
    }

    /**
     * 3.是否已存在会议室，通过门牌号和地址确定。
     * @param room 要检查的会议室对象
     * @return 是否存在
     */
    public boolean hasMeetingRoom(Room room){
        //获取执行对象
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //获取接口代理对象
        ReserveRoomMapper reserveRoomMapper = sqlSession.getMapper(ReserveRoomMapper.class);
        //执行
        boolean flag = !reserveRoomMapper.hasMeetingRoom(room).isEmpty();
        //提交事务
        sqlSession.commit();
        //释放资源
        sqlSession.close();

        return flag;
    }

    /**
     * 4.修改会议室为已被预约
     * @param room 修改后的会议室
     */
    public boolean updateMeetingRoomIsReserved(Room room){
        //获取执行对象
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //获取接口代理对象
        ReserveRoomMapper reserveRoomMapper = sqlSession.getMapper(ReserveRoomMapper.class);
        //执行
        List<Room> rooms = reserveRoomMapper.hasMeetingRoom(room);
        System.out.println("hasMeetingRoom方法查到的会议室"+rooms);
        if(rooms.isEmpty()){
            System.out.println("没有查找到会议室");
        }else{
            reserveRoomMapper.updateMeetingRoomIsReserved(room);
        }

        //提交事务
        sqlSession.commit();
        //释放资源
        sqlSession.close();

        return true;

    }

    /**
     * 4.修改会议室为没有被预约
     * @param room 修改后的会议室
     */
    public boolean updateMeetingRoomIsAvailable(Room room){
        //获取执行对象
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //获取接口代理对象
        ReserveRoomMapper reserveRoomMapper = sqlSession.getMapper(ReserveRoomMapper.class);
        //执行
        List<Room> rooms = reserveRoomMapper.hasMeetingRoom(room);
        System.out.println("hasMeetingRoom方法查到的会议室"+rooms);
        if(rooms.isEmpty()){
            System.out.println("没有查找到会议室");
        }else{
            reserveRoomMapper.updateMeetingRoomIsAvailable(room);
        }

        //提交事务
        sqlSession.commit();
        //释放资源
        sqlSession.close();

        return true;

    }

}
