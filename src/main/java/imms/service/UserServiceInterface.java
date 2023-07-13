package imms.service;

import imms.model.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Transactional
public interface UserServiceInterface {


    /**
     * 通过Id查询用户
     * @param userId
     * @return
     */
    public User selectById(Integer userId);

    /**
     * 通过姓名模糊查询用户
     * @param userName
     * @return
     */
    List<User> selectByName(String userName);

    /**
     * 查询所有用户
     * @return
     */
    public List<User> selectAll();

    /**
     * 通过邮箱精确查询用户
     * @param userEmail
     * @return
     */
    public User selectByEmail(String userEmail);

    /**
     * 通过手机号精确查询用户
     * @param userPhoneNumber
     * @return
     */
    public User selectByPhoneNumber(String userPhoneNumber);

    /**
     * 通过内部号精确查询用户
     * @param userNumber
     * @return
     */
    public User selectByNumber(String userNumber);

    /**
     * 通过不确定的条件查询用户
     * @param user
     * @return
     */
    public List<User> select(User user);

    /**
     * 忽略用户姓名查找用户
     * @param user
     * @return
     */
    public List<User> check(User user);


    /**
     * 添加用户的方法。注意：
     * 1. id由数据库自动生成，无需输入；
     * @param user
     */
    public int addUser(User user);

    /**
     * 通过id更改该用户的信息
     * @param user
     */
    public boolean updateUser(User user);

    /**
     * 通过id删除该用户
     * @param userId
     */
    public boolean deleteUser(Integer userId);

    /**
     * 通过邮箱登录
     * @param userEmail
     * @param userPassword
     * @return
     */
    public boolean loginByEmail(String userEmail,String userPassword);

    /**
     * 新增用户，如果添加用户失败，返回-1
     * @param user
     * @return
     */
    public int insertUser(User user);

    /**
     * 判断更新信息是否输入正确，判断更新信息是否与数据库中已有数据重复的同时要排除自身的信息。
     * 检查通过返回true，不通过返回false
     * @param user
     * @return
     */
    public boolean checkUpdate(User user);

    /**
     * 更新用户信息。要求邮箱、手机号、内部号不重复
     * @param user
     * @return
     */
    public boolean editUser(User user);

    /**
     * 使用邮箱注册。返回注册用户的数据库id。
     * 如果注册失败（已经存在用户），将会返回-1
     * @param user
     * @return
     */
    public int registerByEmail(User user);



}
