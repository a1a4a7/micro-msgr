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
    private static final String SESSION_PREFIX = "session:"; // 使用统一的键前缀

    private final RedisTemplate<Object, Object> redisTemplate;

    public SessionService(RedisTemplate<Object, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void saveSession(Session session) {
        String key = SESSION_PREFIX + session.getSessionId();
        logger.info("Saving session: " + session);
        redisTemplate.opsForValue().set(key, session);
        Session savedSession = (Session) redisTemplate.opsForValue().get(key);
        logger.info("Session saved in Redis: " + savedSession);
    }

    public Session getSession(String sessionId) {
        String key = SESSION_PREFIX + sessionId;
        Session session = (Session) redisTemplate.opsForValue().get(key);
        logger.info("Retrieved session: " + session);
        if (session == null) {
            throw new SessionNotFoundException("Session with id " + sessionId + " not found.");
        }
        return session;
    }

    public List<Session> getAllSessions() {
        ScanOptions options = ScanOptions.scanOptions().match(SESSION_PREFIX + "*").count(1000).build();
        Cursor<byte[]> cursor = redisTemplate.executeWithStickyConnection(redisConnection -> redisConnection.scan(options));

        List<Session> sessions = new ArrayList<>();
        while (cursor.hasNext()) {
            String key = new String(cursor.next());
            Session session = (Session) redisTemplate.opsForValue().get(key);
            if (session != null) {
                sessions.add(session);
            }
        }
        logger.info("All retrieved sessions: " + sessions);
        return sessions;
    }

    public List<String> getAllKeys() {
        ScanOptions options = ScanOptions.scanOptions().match("*").count(1000).build();
        Cursor<byte[]> cursor = redisTemplate.executeWithStickyConnection(redisConnection -> redisConnection.scan(options));

        List<String> keys = new ArrayList<>();
        while (cursor.hasNext()) {
            keys.add(new String(cursor.next()));
        }
        logger.info("All retrieved keys: " + keys);
        return keys;
    }

    public void updateSession(Message message) {
        Session session = getSession(message.getSender());
        if (session != null) {
            session.setLastAccessTime(System.currentTimeMillis());
            saveSession(session);
        }
    }
}
