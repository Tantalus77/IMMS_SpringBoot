package imms.model;

public class Meeting {
    private Integer meetingId;
    private String date;
    private String starTime;
    private String endTime;
    private String theme;
    private String introduction;

    public Integer getMeetingId() {
        return meetingId;
    }

    public void setMeetingID(Integer meetingId) {
        this.meetingId = meetingId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStarTime() {
        return starTime;
    }

    public void setStarTime(String starTime) {
        this.starTime = starTime;
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

    @Override
    public String toString() {
        return "Meeting{" +
                "meetingID=" + meetingId +
                ", date='" + date + '\'' +
                ", starTime='" + starTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", theme='" + theme + '\'' +
                ", introduction='" + introduction + '\'' +
                '}';
    }
}
