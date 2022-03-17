package com.mjp.seckill.listener;

import com.mjp.seckill.service.OrderService;
import com.mjp.seckill.utils.RedisConstant;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

import javax.annotation.Resource;

/**
 * @version 1.0
 * @developDate 2022/1/13
 * @developAuthor MinJianPeng
 */
public class RedisKeyExpireListener extends KeyExpirationEventMessageListener {
    @Resource
    private OrderService orderService;

    public RedisKeyExpireListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String msg = message.toString();
        if (!msg.contains(":")) {
            return;
        }
        String[] split = msg.split(":");
        if (split.length > 2) {
            return;
        }
        if (RedisConstant.PRODUCT.equals(split[0]+":")) {
            orderService.synData(Long.valueOf(split[1]));
        }
    }
}
