package com.chaycao.seckill.dao;

import com.chaycao.seckill.entity.SuccessKilled;
import org.apache.ibatis.annotations.Param;

/**
 * @author chaycao
 * @description
 * @date 2018-06-24 16:18.
 */
public interface SuccessKilledDao {

    /**
     * 插入购买明细，可过滤重复
     * @param seckillId
     * @param userPhone
     * @return 插入数量
     */
    int insertSuccessKilled(@Param("seckillId") long seckillId, @Param("userPhone") long userPhone);

    /**
     * 根据ID查询SuccessKilled并携带秒杀产品对象实体
     * @param seckilled
     * @return
     */
    SuccessKilled queryByIdWithSeckill(@Param("seckillId") long seckilled, @Param("userPhone") long userPhone);
}
