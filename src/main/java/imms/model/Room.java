package imms.model;

public class Room {
    private Integer roomId;
    private int roomSize;
    private String roomOpenTime;
    private String roomCloseTime;
    private String roomNumber;
    private String roomAddress;

    private Integer numberOfUses;

    public Integer getNumberOfUses() {
        return numberOfUses;
    }

    public void setNumberOfUses(Integer numberOfUses) {
        this.numberOfUses = numberOfUses;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getRoomSize() {
        return roomSize;
    }

    public void setRoomSize(int roomSize) {
        this.roomSize = roomSize;
    }

    public String getRoomOpenTime() {
        return roomOpenTime;
    }

    public void setRoomOpenTime(String roomOpenTime) {
        this.roomOpenTime = roomOpenTime;
    }

    public String getRoomCloseTime() {
        return roomCloseTime;
    }

    public void setRoomCloseTime(String roomCloseTime) {
        this.roomCloseTime = roomCloseTime;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getRoomAddress() {
        return roomAddress;
    }

    public void setRoomAddress(String roomAddress) {
        this.roomAddress = roomAddress;
    }

    @Override
    public String toString() {
        return "Room{" +
                "roomId=" + roomId +
                ", roomSize=" + roomSize +
                ", roomOpenTime='" + roomOpenTime + '\'' +
                ", roomCloseTime='" + roomCloseTime + '\'' +
                ", roomNumber='" + roomNumber + '\'' +
                ", roomAddress='" + roomAddress + '\'' +
                '}';
    }


}
