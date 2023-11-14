package imms.service;

import imms.model.Meeting;
import imms.model.Reserve;
import imms.model.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Transactional
public interface UserServiceInterface {
    /**
     * 用户通过邮箱登录
     * @param email
     * @param password
     * @return
     */
    public boolean loginByEmail(String email,String password);

    /**
     * 用户管理自己的个人信息
     * @param user
     * @return
     */
    public boolean editInfo(User user);

    /**
     * 用户修改头像，这里是把头像图片在文件系统中的地址保存到数据库中去
     * 也就是说在controller里应当要接受图片，并且把图片保存到一个指定路径上去
     * @param picAddress
     * @return
     */
    public boolean setPictureInfo(String picAddress);

    /**
     * 用户预期输入主题、日期、时间范围、简介、是否签到、参与者
     * reserve中的字段有：预约者、房间id、会议id
     * meeting中的字段有：日期、开始时间、结束时间、主题、简介、是否签到
     * 这个服务就把会议添加到数据库中，添加时也生成一个会议code，并且自动生成一个reserve，同样放到数据库里，
     * 然后同时也在participate表里添加一条记录，即主持会议就等于参加了该会议
     * 要求前端给出邀请用户的Id，然后这个方法里就把它加入到数据库invite表里去
     * invite表待设计
     * @param
     * @param meeting
     * @return
     */
    public boolean reserveMeeting(Meeting meeting, List<Integer> userIds);

    /**
     * 获取用户所给月份的所有会议，包括主持的和参加的
     * @param dateTime
     * @return
     */
    public List<Meeting> myMeetings(String dateTime);

    /**
     * 获取用户所有的历史会议，即获取当前时间之前的所有会议
     * @param userId
     * @return
     */
    public List<Meeting> historyMeetings(Integer userId);


    /**
     * 通过会议的code参与某个会议。具体流程为：输入会议code，点击参加按钮，调用这个服务，然后添加一条participate记录
     * 要求用code找到会议id
     * @return
     */
    public boolean attendMeetingByCode(Integer userId, Integer code);



}
