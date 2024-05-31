package com.realtime.sessionserv;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import com.realtime.sessionserv.Message;

@Service
public class SessionService {

    private final RedisTemplate<String, Session> redisTemplate;

    public SessionService(RedisTemplate<String, Session> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void saveSession(Session session) {
        redisTemplate.opsForValue().set(session.getSessionId(), session);
    }

    public Session getSession(String sessionId) {
        return redisTemplate.opsForValue().get(sessionId);
    }

    public void updateSession(Message message) {
        // 更新会话逻辑，例如更新最后活动时间
        Session session = getSession(message.getSender());
        if (session != null) {
            session.setLastAccessTime(System.currentTimeMillis());
            saveSession(session);
        }
    }
}
