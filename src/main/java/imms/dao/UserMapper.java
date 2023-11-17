package imms.dao;

import imms.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author 普朗千克
 * @2023/11/17 11:43 update
 * 用户相关的数据库操作，使用时最好看一下注释
 */
@Mapper
public interface UserMapper {

    /**
     * 获取系统中所有用户
     * @return
     */
    @Select("select * from userinfo")
    List<User> selectAll();

    /**
     * 通过Id查询用户
     * @param userId
     * @return
     */
    @Select("select * from userinfo where userId = #{userId}")
    User selectById(Integer userId);

    /**
     * 通过姓名查询用户
     * @param userName
     * @return
     */
    @Select("select * from userinfo where username = #{username}")
    List<User> selectByName(String userName);

    /**
     * 通过邮箱查询用户
     * @param userEmail
     * @return
     */
    @Select("select * from userinfo where userEmail = #{userEmail}")
    User selectByEmail(String userEmail);

    /**
     * 通过手机号查询用户
     * @param userPhoneNumber
     * @return
     */
    @Select("select * from userinfo where userPhoneNumber = #{userPhoneNumber}")
    User selectByPhoneNumber(String userPhoneNumber);

    /**
     * 通过用户内部号查询用户
     * @param userNumber
     * @return
     */
    @Select("select * from userinfo where userNumber = #{userNumber}")
    User selectByNumber(String userNumber);

    /**
     * 通过用户输入的条件查询用户
     * 条件包括：用户id、用户姓名、用户手机号、用户邮箱、用户内部号、是否是管理员
     * 其中用户姓名、手机号、邮箱、内部号均为模糊查询
     * @param user
     * @return
     */
    List<User> select(User user);

    /**
     * 查找条件包括：邮箱、手机号、内部号，均为精确查询
     * 注册时检查用户的注册信息或者更改信息是否重复，
     * 因为姓名是允许重复的，所以这个方法并不使用姓名查询
     * @param user
     * @return
     */
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


    /**
     * 修改头像，是一个单独的方法
     * @param userId
     * @param picAddress
     */
    @Update("update user set picture = #{picAddress} where userId = #{userId} ")
    void setPicture(Integer userId, String picAddress);


}
