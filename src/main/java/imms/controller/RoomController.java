package imms.controller;


import imms.model.Room;
import imms.service.RoomServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/rooms")
public class RoomController {

    @Autowired
    private RoomServiceInterface roomServiceInterface;

    @PostMapping("/addRoom")
    public String addRoom(@RequestBody Room room){
        System.out.println("/rooms/addRoom接受到的数据==>"+room);//打印输出接受的参数

        //业务流程
        int roomId = roomServiceInterface.addRoom(room);

        if(roomId == -1){
            //注册失败
            System.out.println("添加失败！");
            return "fail";
        }else{
            //注册成功，返回userId
            System.out.println("添加成功！");

            return ""+roomId;
        }

    }

    @DeleteMapping("/{roomId}")
    public String deleteRoom(@PathVariable int roomId){
        System.out.println("/rooms/"+roomId+" is running...");//打印输出参数

        //业务流程
        roomServiceInterface.deleteRoom(roomId);
        System.out.println("delete success!");

        return "success";

    }

    @PostMapping("/deleteRooms")
    public String deleteRooms(@RequestBody ArrayList<Integer> roomIds){
        System.out.println("/rooms/deleteRooms接受到参数==>"+roomIds);//打印输出接受的参数

        for(int i:roomIds){
            roomServiceInterface.deleteRoom(i);
        }

        System.out.println("delete success!");
        return "success";

    }

    @PostMapping("/editRoom")
    public String editRoom(@RequestBody Room room){
        System.out.println("/rooms/editRoom接受到的参数==>"+room);//打出输出参数

        boolean flag = room.getRoomNumber().equals("") && room.getRoomAddress().equals("")
                && room.getRoomCloseTime().equals("") && room.getRoomOpenTime().equals("");
        if(flag) return "empty";

        if(roomServiceInterface.updateRoom(room)){
            System.out.println("修改成功");
            return "success";
        }else{
            System.out.println("修改失败");
            return "fail";
        }

    }

    @GetMapping("/selectAll")
    public List<Room> selectAll(){
        System.out.println("/rooms/selectAll is running...");

        List<Room> rooms = roomServiceInterface.selectAll();

        return rooms;
    }

    @PostMapping("/select")
    public List<Room> select(@RequestBody Room room){
        System.out.println("/rooms/select接受到的参数==>"+room);

        //业务流程
        List<Room> rooms = roomServiceInterface.selectRoom(room);

        return rooms;

    }

}
