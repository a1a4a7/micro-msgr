package com.realtime.sessionserv;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SessionService {

    private static final Logger logger = LoggerFactory.getLogger(SessionService.class);

    private final RedisTemplate<String, Session> redisTemplate;

    public SessionService(RedisTemplate<String, Session> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void saveSession(Session session) {
        logger.info("Saving session: " + session);
        redisTemplate.opsForValue().set(session.getSessionId(), session);
        Session savedSession = redisTemplate.opsForValue().get(session.getSessionId());
        logger.info("Session saved in Redis: " + savedSession);
    }

    public Session getSession(String sessionId) {
        Session session = redisTemplate.opsForValue().get(sessionId);
        logger.info("Retrieved session: " + session);
        return session;
    }

    public List<Session> getAllSessions() {
        ScanOptions options = ScanOptions.scanOptions().match("*").count(1000).build();
        Cursor<String> cursor = redisTemplate.scan(options);
        List<Session> sessions = new ArrayList<>();
        while (cursor.hasNext()) {
            String key = cursor.next();
            Session session = redisTemplate.opsForValue().get(key);
            if (session != null) {
                sessions.add(session);
            }
        }
        logger.info("All retrieved sessions: " + sessions);
        return sessions;
    }

    public void updateSession(Message message) {
        Session session = getSession(message.getSender());
        if (session != null) {
            session.setLastAccessTime(System.currentTimeMillis());
            saveSession(session);
        }
    }
}
