package com.chaycao.seckill.service;

import com.chaycao.seckill.dto.Exposer;
import com.chaycao.seckill.dto.SeckillExecution;
import com.chaycao.seckill.entity.Seckill;
import com.chaycao.seckill.exception.RepeatKillException;
import com.chaycao.seckill.exception.SeckillException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author chaycao
 * @description
 * @date 2018-06-25 21:08.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml",
                        "classpath:spring/spring-service.xml"})
public class SeckillServiceTest {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SeckillService seckillService;

    @Test
    public void getSeckillList() throws Exception {
        List<Seckill> list = seckillService.getSeckillList();
        logger.info("list={}", list);
    }

    @Test
    public void getById() throws Exception {
        long id = 1000;
        Seckill seckill = seckillService.getById(id);
        logger.info("seckill={}", seckill);
        /*
        21:20:58.241 [main] INFO  c.c.s.service.SeckillServiceTest -
        exposer=Exposer{exposed=true,
        md5='41987932c4dcd75eff322146300fd3d4',
        seckillId=1000, now=0, start=0, end=0}
         */
    }

    @Test
    public void exportSeckillLogic() throws Exception {
        long id = 1000;
        Exposer exposer = seckillService.exportSeckillUrl(id);
        if (exposer.isExposed()) {
            logger.info("exposer={}", exposer);
            long phone = 13607017057L;
            String md5 = exposer.getMd5();
            try {
                SeckillExecution execution = seckillService.executeSeckill(id, phone, md5);
                logger.info("result={}", execution);
            } catch (RepeatKillException e) {
                logger.error(e.getMessage());
            } catch (SeckillException e) {
                logger.error(e.getMessage());
            }
        } else {
            // 秒杀未开启
            logger.warn("expose={}", exposer);
        }

    }

    @Test
    public void executeSeckillProcedure() {
        long seckillId = 1001L;
        long phone = 13607017080L;
        Exposer exposer = seckillService.exportSeckillUrl(seckillId);
        if (exposer.isExposed()) {
            logger.info("exposer={}", exposer);
            String md5 = exposer.getMd5();
            try {
                SeckillExecution execution = seckillService.executeSeckillProcedure(seckillId, phone, md5);
                logger.info("result={}", execution.getStateInfo());
            } catch (RepeatKillException e) {
                logger.error(e.getMessage());
            } catch (SeckillException e) {
                logger.error(e.getMessage());
            }
        } else {
            // 秒杀未开启
            logger.warn("expose={}", exposer);
        }
    }
}