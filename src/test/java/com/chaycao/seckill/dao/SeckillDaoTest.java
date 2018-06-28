package com.chaycao.seckill.dao;

import com.chaycao.seckill.dto.SeckillExecution;
import com.chaycao.seckill.entity.Seckill;
import com.chaycao.seckill.entity.SuccessKilled;
import com.chaycao.seckill.enums.SeckillStateEnum;
import org.apache.commons.collections.MapUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * @author chaycao
 * @description 配置spring和junit整合,junit启动时加载springIOC容器
 * @date 2018-06-25 15:19.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SeckillDaoTest {

    // 注入Dao实现类依赖
    @Resource
    private SeckillDao seckillDao;

    @Test
    public void queryById() throws Exception {
        long id = 1000;
        Seckill seckill = seckillDao.queryById(id);
        System.out.println(seckill.getName());
        System.out.println(seckill);
    }

    @Test
    public void queryAll() throws Exception {
        List<Seckill> seckills = seckillDao.queryAll(0, 100);
        for (Seckill seckill : seckills) {
            System.out.println(seckill);
        }
    }

    @Test
    public void reduceNumber() throws Exception {
        Date killTime = new Date();
        int updateCount = seckillDao.reduceNumber(1000L, killTime);
        System.out.println("updateCount=" + updateCount);
    }

    @Test
    public void killByProcedure() {
        long seckillId = 1001L;
        long phone = 13607017059L;
        Date killTime = new Date();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("seckillId", seckillId);
        map.put("phone", phone);
        map.put("killTime", killTime);
        map.put("result", null);

        seckillDao.killByProcedure(map);
        int result = MapUtils.getInteger(map, "result", -2);
        System.out.println(result);
    }

}