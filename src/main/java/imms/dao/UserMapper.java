package imms.dao;

import imms.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface UserMapper {

    /**
     * 查询相关的方法，包含8个方法：
     * 1.查询所有用户；
     * 2.通过id（精确）查询用户 @param userId
     * 3.通过名字（模糊）查询用户@param userName
     * 4.通过邮箱（精确）查询用户@param userEmail
     * 5.通过手机号（精确）查询用户@param userPhoneNumber
     * 6.通过内部号（精确）查询用户@param userNumber
     * 7.通过不确定的条件查询用户@param User
     * 8.通过不确定的条件查询用户，忽略userName@param User
     */
    //查询所有用户
    @Select("select * from userinfo")
    List<User> selectAll();

    //通过id查询用户
    @Select("select * from userinfo where userId = #{userId}")
    User selectById(Integer userId);

    //通过名字查询用户
    @Select("select * from userinfo where username like #{username}")
    List<User> selectByName(String userName);

    //通过Email查询用户
    @Select("select * from userinfo where userEmail = #{userEmail}")
    User selectByEmail(String userEmail);

    //通过手机号查询用户
    @Select("select * from userinfo where userPhoneNumber = #{userPhoneNumber}")
    User selectByPhoneNumber(String userPhoneNumber);

    //通过内部号查询用户
    @Select("select * from userinfo where userNumber = #{userNumber}")
    User selectByNumber(String userNumber);

    //在不确定用户输入哪些值的情况下查询用户
    List<User> select(User user);

    //忽略userName
    List<User> check(User user);

    /**
     * 添加用户的方法。注意：
     * 1.id由数据库自动递增生成，不需要输入；
     *
     * @param user
     * @return void
     */
    void addUser(User user);

    /**
     * 更改用户信息的方法。注意：
     * 1.id不允许修改。
     * @param user
     * @return void
     */
    void updateUser(User user);

    /**
     * 删除用户的方法。注意：
     * 1.需要提供用户的id
     * @param userId
     * @return void
     */
    void deleteUser(int userId);

    @Update("update user set picture = #{picAddress} where userId = #{userId} ")
    void setPicture(Integer userId, String picAddress);


}
