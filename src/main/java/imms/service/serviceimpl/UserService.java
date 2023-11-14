package imms.service.serviceimpl;

import imms.dao.UserMapper;
import imms.model.Meeting;
import imms.model.User;
import imms.service.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserServiceInterface {

    @Autowired
    private UserMapper um;
    
    /**
     * 查询相关的方法，包含7个方法：
     * 1.查询所有用户；
     * 2.通过id（精确）查询用户 @param userId
     * 3.通过名字（模糊）查询用户@param userName
     * 4.通过邮箱（精确）查询用户@param userEmail
     * 5.通过手机号（精确）查询用户@param userPhoneNumber
     * 6.通过内部号（精确）查询用户@param userNumber
     * 7.通过不确定的条件查询用户@param user
     * 8.通过不确定的条件查询用户，忽略userName和isAdmin@param user
     *   （因为我们允许用户姓名重复。在注册或者修改用户信息的时候需要验证信息是否以及存在，但不包括姓名，所以写了这个方法）
     */
    public List<User> selectAll() {

        List<User> users = um.selectAll();

        return users;
    }


    public User selectById(Integer userId) {

        //封装结果集
        User user = um.selectById(userId);

        //返回结果对象集合
        return user;
    }


    public List<User> selectByName(String userName) {

        //封装结果集
        List<User> users = um.selectByName(userName);

        //返回结果对象集合
        return users;
    }


    public User selectByEmail(String userEmail) {

        //封装结果集
        User user = um.selectByEmail(userEmail);

        //返回结果对象集合
        return user;
    }


    public User selectByPhoneNumber(String userPhoneNumber) {

        //封装结果集
        User user = um.selectByPhoneNumber(userPhoneNumber);

        //返回结果对象集合
        return user;
    }


    public User selectByNumber(String userNumber) {

        //封装结果集
        User user = um.selectByNumber(userNumber);

        //返回结果对象集合
        return user;
    }


    public List<User> select(User user) {

        //因为是姓名、邮箱、手机号、内部号都是模糊查询，所以要把user的相关信息都加上百分号
        user.setUserName("%"+user.getUserName()+"%");
        user.setUserNumber("%"+user.getUserNumber()+"%");
        user.setUserPhoneNumber("%"+user.getUserPhoneNumber()+"%");
        user.setUserEmail("%"+user.getUserEmail()+"%");

        System.out.println("模糊处理之后==>"+user);
        //封装结果集
        List<User> users = um.select(user);

        //返回结果对象集合
        return users;
    }

    public List<User> check(User user){

        //封装结果集
        List<User> users = um.check(user);

        //返回结果对象集合
        return users;
    }

    /**
     * 添加用户的方法。注意：
     * 1.id由数据库自动递增生成，不需要输入；
     * 2.isAdmin字段不需要输入，默认为0，要修改权限需要管理员进行修改；
     * 3.返回新添加的用户的id：userId
     * @param user
     * @return userId
     */
    public int addUser(User user) {

        //执行
        um.addUser(user);

        //返回用户id
        return user.getUserId();
    }

    /**
     * 更改用户信息的方法。注意：
     * 1.id不允许修改；
     * 2.user的值一开始默认为修改前的值；
     * @param user
     * @return void
     */
    public boolean updateUser(User user) {

        //执行
        um.updateUser(user);


        return true;
    }

    /**
     * 删除用户的方法。注意：
     * 1.需要提供用户的id
     * @param userId
     * @return void
     */
    public boolean deleteUser(Integer userId) {

        //执行
        um.deleteUser(userId);


        return true;
    }

    /**
     * 判断是否存在用用户的方法。
     * 在数据库中，内部号、邮箱、手机号、id、不允许重复，id由数据库自动生成不允许更改，所以判断其余三个是否重复就行。
     * 任中一个重复都不行。
     * @param user
     * @return
     */
    public boolean hasUser(User user){
        //判断邮箱是否重复
        if(!user.getUserEmail().isEmpty()){
            if(selectByEmail(user.getUserEmail()) != null) return true;
        }
        //判断手机号是否重复
        if(!user.getUserNumber().isEmpty()){
            if(selectByNumber(user.getUserEmail()) != null) return true;
        }
        //判断内部号是否重复
        if(!user.getUserPhoneNumber().isEmpty()){
            if(selectByPhoneNumber(user.getUserEmail()) != null) return true;
        }
        return false;
    }
    /**
     * 登录相关的方法
     * 1.使用邮箱登录，@param userEmail，@param userPassword
     * 2.使用内部号登录，@param userEmail，@param userPassword
     *
     */
    public boolean loginByEmail(String userEmail, String userPassword) {
        if(userEmail == "" || userPassword == "") {
            return false;
        }
        User user = selectByEmail(userEmail);

        if(user == null) {
            return false;
        }
        //判断用户输入邮箱对应的用户的密码是否等于用户输入的密码
        //同时忽略用户输入密码前后的空格
        boolean flag = user.getUserPassword().equals(userPassword.trim());
        //返回flag
        return flag;
    }

    public boolean loginByNumber(String userNumber, String userPassword) {
        User user = selectByNumber(userNumber);
        //判断用户输入邮箱对应的用户的密码是否等于用户输入的密码
        //同时忽略用户输入密码前后的空格
        boolean flag = user.getUserPassword().equals(userPassword.trim());
        //返回flag
        return flag;
    }
    /**
     * 注册相关的方法
     * 1.使用邮箱注册；@param user,返回userId
     *  注意：如果用户已存在，将会返回-1
     * 2.使用内部号批量注册@param user
     *
     */


    public int registerByEmail(User user){
        //设置一个id，默认值为-1
        int id = -1;
        //判断是否输入正确
        boolean flag = hasUser(user);
        //如果不存在，就添加用户
        if(!flag){
            id = addUser(user);
        }

        return id;
    }

    public void registerByNumbers(List<User> users){
        //循环遍历
        for(int i=0;i<users.size();i++){
            //判断用户是否存在
            if(!hasUser(users.get(i))){
                //如果不存在，就添加用户；
                addUser(users.get(i));
            }else{
                int count = i+1;//计数
                System.out.println("第" + count + "个用户已存在");
                //跳过
                i++;
            }
        }
    }

    /**
     * 更新用户信息相关的方法
     * 1.更新用户信息，@param:user
     * 2.检查输入信息是否正确（邮箱、手机号、内部号不允许重复）
     */


    public boolean editUser(User user){
        if(!checkUpdate(user)){
            return false;
        }
        updateUser(user);
        return true;
    }

    /**
     * 判断更新信息是否输入正确，判断是否重复的同时要排除掉自己的信息.
     * 检查通过返回true，不通过返回false
     * @param user
     * @return
     */
    public boolean checkUpdate(User user){
        //先用id搜索当前用户
        User check = selectById(user.getUserId());
        //判断邮箱
        if(check.getUserEmail().equals(user.getUserEmail())){
            ;
        }else{
            if(selectByEmail(user.getUserEmail())==null){
                ;
            }else{
                return false;
            }
        }
        //判断手机号
        if(check.getUserEmail().equals(user.getUserEmail())){
            ;
        }else{
            if(selectByEmail(user.getUserEmail())==null){
                ;
            }else{
                return false;
            }
        }
        //判断内部号
        if(check.getUserEmail().equals(user.getUserEmail())){
            return true;
        }else{
            if(selectByEmail(user.getUserEmail())==null){
                return true;
            }else{
                return false;
            }
        }

    }

    /**
     * 新增用户相关的方法。
     * 1.添加用户的方法，@param user,@return userId;如果失败，返回-1
     * todo：2.批量注册（使用内部号）；
     */
    public int insertUser(User user){
        boolean flag = hasUser(user);
        Integer userId = null;
        if(flag){
            return userId;
        }else{
            userId = addUser(user);
        }

        return userId;
    }

    /*****************************************************************************
     *
     *                                重写的方法
     *
     ****************************************************************************/
    //TODO
    @Override
    public boolean editInfo(User user) {
        return false;
    }

    @Override
    public boolean setPictureInfo(String picAddress) {
        return false;
    }

    @Override
    public boolean reserveMeeting(Meeting meeting, List<Integer> userIds) {
        return false;
    }

    @Override
    public List<Meeting> myMeetings(String dateTime) {
        return null;
    }

    @Override
    public List<Meeting> historyMeetings(Integer userId) {
        return null;
    }

    @Override
    public boolean attendMeetingByCode(Integer userId, Integer code) {
        return false;
    }
}
