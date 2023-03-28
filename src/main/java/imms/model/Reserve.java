package imms.model;

public class Reserve {
    private int reserveId;
    private int roomId;
    private int userId;
    private int reserveStart;
    private int reserveEnd;

    public int getReserveId() {
        return reserveId;
    }

    public void setReserveId(int reserveId) {
        this.reserveId = reserveId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getReserveStart() {
        return reserveStart;
    }

    public void setReserveStart(int reserveStart) {
        this.reserveStart = reserveStart;
    }

    public int getReserveEnd() {
        return reserveEnd;
    }

    public void setReserveEnd(int reserveEnd) {
        this.reserveEnd = reserveEnd;
    }

    @Override
    public String toString() {
        return "Reserve{" +
                "reserveId=" + reserveId +
                ", roomId=" + roomId +
                ", userId=" + userId +
                ", reserveStart=" + reserveStart +
                ", reserveEnd=" + reserveEnd +
                '}';
    }


}
