package imms.model;


public class Invite {
    private Integer userId;
    private Integer inviterId;
    private Integer meetingId;
    private Integer status;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getInviterId() {
        return inviterId;
    }
    public void setInviterId(Integer inviterId) {
        this.inviterId = inviterId;
    }

    public Integer getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(Integer meetingId) {
        this.meetingId = meetingId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Invite(Integer userId, Integer inviterId, Integer meetingId){
        this.userId = userId;
        this.inviterId = inviterId;
        this.meetingId = meetingId;
        this.status = 0;
    }

}
