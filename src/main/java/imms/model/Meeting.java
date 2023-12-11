package imms.model;

public class Meeting {
    private Integer meetingId;
    private Integer userId;
    private Integer roomId;
    private String date;
    private String startTime;
    private String endTime;
    private String theme;
    private String introduction;

    //0：不需要签到 1：需要签到
    private Integer needSign = 0;
    private String code;

    public Integer getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(Integer meetingId) {
        this.meetingId = meetingId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String starTime) {
        this.startTime = starTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public Integer getNeedSign() {
        return needSign;
    }

    public void setNeedSign(Integer needSign) {
        this.needSign = needSign;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "Meeting{" +
                "meetingID=" + meetingId +
                ", date='" + date + '\'' +
                ", starTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", theme='" + theme + '\'' +
                ", introduction='" + introduction + '\'' +
                '}';
    }

    public Meeting(Integer meetingId, String date, String startTime, String endTime, String theme, String introduction) {
        this.meetingId = meetingId;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.theme = theme;
        this.introduction = introduction;
    }

}
