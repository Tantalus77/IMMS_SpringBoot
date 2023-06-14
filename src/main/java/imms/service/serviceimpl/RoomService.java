package imms.service.serviceimpl;

import imms.dao.MeetingMapper;
import imms.dao.RoomMapper;
import imms.model.Meeting;
import imms.model.Room;
import imms.service.RoomServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class RoomService implements RoomServiceInterface {

    @Autowired
    private RoomMapper roomMapper;

    @Autowired
    private MeetingMapper meetingMapper;

    /*
    对会议室的增删查改功能，包含以下方法:
    1. 增加一个会议室；
    2. 删除某个会议室；
    3. 查询会议室：
        1. 通过会议室id查询；
        2. 通过会议室尺寸查询；
        3. 通过会议室门牌号查询；
        4. 通过会议室地址查询；
        5. 动态条件查询会议室；
        6. 检查是否存在会议室（根据roomAddress和roomNumber确定）
        7. 查询所有会议室
    4. 修改会议室信息
     */

    /**
     * 添加会议室。添加成功返回roomId，添加失败后返回-1。
     * @param room 新建的会议室对象
     * @return 新建会议室的id
     */
    public int addRoom(Room room){
        //执行
        if(hasRoom(room)){
            System.out.println("已经存在相应的会议室了！");
            return -1;
        }else{
            roomMapper.addRoom(room);
        }

        //返回用户id
        return room.getRoomId();

    }

    /**
     * 删除某个会议室
     * @param roomId 要删除的会议室的id
     */
    public boolean deleteRoom(int roomId){

        //执行
        roomMapper.deleteRoom(roomId);


        return true;
    }

    /*
     * 查询相关方法
     */

    /**
     * 1.通过ID查找会议室
     * @param roomId 要查找的会议室的id
     * @return 查找到的room（唯一）
     */
    public Room selectRoomById(int roomId){

        //执行
        Room room = roomMapper.selectRoomById(roomId);

        return room;
    }

    /**
     * 2.通过会议室尺寸找会议室
     * @param roomSize 要查找的会议室的尺寸
     * @return rooms 查找到的会议室们
     */
    public List<Room> selectRoomBySize(int roomSize){

        //执行
        List<Room> rooms = roomMapper.selectRoomBySize(roomSize);


        return rooms;
    }

    /**
     * 3.通过会议室门牌号查找会议室
     * @param roomNumber 要查找的会议室的门牌号
     * @return 查找到的会议室们
     */
    public List<Room> selectRoomByNumber(int roomNumber){

        //执行
        List<Room> rooms = roomMapper.selectRoomByNumber(roomNumber);


        return rooms;
    }

    /**
     * 4.通过房间地址查找会议室（模糊查询）
     * @param roomAddress 要查找的会议室的地址
     * @return 查找到的会议室们
     */
    public List<Room> selectRoomByAddress(String roomAddress){

        //执行
        List<Room> rooms = roomMapper.selectRoomByAddress(roomAddress);


        return rooms;
    }

    /**
     * 5.动态查找会议室。对房间门牌号、房间地址为模糊查询；
     * 其中roomOpenTime和roomCloseTime携带的是会议开启时间和会议结束时间，
     * 即只要用户查找的时间在开启时间和关闭时间之间，就可以找到对应的会议室。
     * @param room 一个携带要查找的会议室信息的会议室对象
     * @return 查找到的会议室们
     */
    public List<Room> selectRoom(Room room){

        //模糊查询做准备
        if(!room.getRoomNumber().equals("")) room.setRoomNumber("%"+room.getRoomNumber()+"%");
        if(!room.getRoomAddress().equals("")) room.setRoomAddress("%"+room.getRoomAddress()+"%");
        System.out.println("模糊处理之后==>"+room);

        //执行
        List<Room> rooms = roomMapper.selectRoom(room);


        return rooms;
    }

    /**
     * 6.是否已存在会议室，通过门牌号和地址确定。
     * @param room 要检查的会议室对象
     * @return 是否存在
     */
    public boolean hasRoom(Room room){

        //执行
        boolean flag = !roomMapper.hasRoom(room).isEmpty();


        return flag;
    }

    /**
     * 7.查找所有会议室
     * @return 查找到的会议室
     */
    public List<Room> selectAll(){

        //执行
        List<Room> rooms = roomMapper.selectAll();

        return rooms;
    }


    /**
     * 4.修改会议室信息
     * @param room 修改后的会议室
     */
    public boolean updateRoom(Room room){

        //执行
        List<Room> rooms = roomMapper.hasRoom(room);
        System.out.println("hasRoom方法查到的会议室"+rooms);
        if(rooms.isEmpty()){
            System.out.println("没有查找到会议室");
            roomMapper.updateRoom(room);
        }else{
            if(rooms.size() == 1){
                if(rooms.get(0).getRoomAddress().trim().equals(room.getRoomAddress().trim()) && rooms.get(0).getRoomNumber().trim().equals(room.getRoomNumber().trim())){
                    System.out.println("是同一个会议室");
                    roomMapper.updateRoom(room);
                }else{
                    System.out.println("已经存在该会议室了");
                    return false;
                }
            }else{
                System.out.println("已经存在该会议室了");
                return false;
            }

        }


        return true;

    }

    /**
     * 搜索可用的会议室
     * @param date
     * @param startTime
     * @param endTime
     * @return
     */

    @Override
    public List<Room> availableRoom(String date, String startTime, String endTime) {
        System.out.println("date:"+date+"startTime"+startTime+"endTime"+endTime);
        System.out.println("开始搜索可用会议室.....");
        //时间的格式化器
        SimpleDateFormat formatter = new SimpleDateFormat("hh:mm:ss");
        //搜索在输入时间内已经开门（或是没有关门）的房间的列表
        System.out.println("第一步：查找输入事件内开门的房间");
        List<Room> roomList = roomMapper.selectByTime(startTime,endTime);
        System.out.println(roomList);
        List<Room> availableRoom = new ArrayList<Room>();
        //是否冲突的标志
        boolean flag = false;
        //循环上述的会议室
        for (Room room:
             roomList) {
            System.out.println("正在处理会议室："+room.getRoomId()+"...");
            //搜索当前会议室的当天的所有会议
            List<Meeting> meetingOfTheDay = meetingMapper.selectByRoom(room.getRoomId(),date);

            System.out.println("房间"+room.getRoomId()+date+"的会议列表为："+meetingOfTheDay);
            if(meetingOfTheDay.size() > 0){
                //如果会议列表不空，循环这个会议列表；
                for (Meeting meeting:
                        meetingOfTheDay) {
                    //先将标志设置为false
                    flag = false;
                    //如果输入的开始时间早于当前会议的结束时间 并且 输入的结束时间晚于当前会议的开始时间，则说明时间冲突，直接结束循环，并设置flag为true
                    if(formatter.format(startTime).compareTo(formatter.format(meeting.getEndTime())) < 0
                            && formatter.format(endTime).compareTo(formatter.format(meeting.getStartTime())) > 0){
                        flag = true;
                        break;
                    }
                }
            }
            //若循环结束,标志为false，则说明该会议室的所有会议都不与输入的时间冲突，则该会议室可用
            if(flag == false) availableRoom.add(room);

        }
        return availableRoom;
    }
}
