package imms;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import java.util.Set;

/**
 * 使用jedis操作redis
 */

@SpringBootTest
@RunWith(SpringRunner.class)
public class RedisTest {

    @Autowired
    private RedisTemplate redisTemplate;
    @Test
    public void testJedis(){
        Jedis jedis = new Jedis("localhost", 6379);
        jedis.auth("123456");
        jedis.set("username","qiyijin");
        String username = jedis.get("username");
        System.out.println(username);
        jedis.close();
    }

    /**
     * 注意在测试spring-boot-starter-data-redis的时候需要在类上加下面的注解
     * @SpringBootTest
     * @RunWith(SpringRunner.class)
     */
    @Test
    public void testString(){
        redisTemplate.opsForValue().set("myMeetingName123","imms");
        String val = (String) redisTemplate.opsForValue().get("myMeetingName123");
        System.out.println(val);
    }

    @Test
    public void testCommon(){
        Set<String> keys = redisTemplate.keys("*");
        for (String key : keys) {
            System.out.println(key);
        }
    }
}
