package com.realtime.sessionserv;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sessions")
public class SessionController {

    private final SessionService sessionService;

    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @PostMapping
    public void createSession(@RequestBody Session session) {
        sessionService.saveSession(session);
    }

    @GetMapping("/{sessionId}")
    public Session getSession(@PathVariable String sessionId) {
        return sessionService.getSession(sessionId);
    }

    @GetMapping
    public List<Session> getSessions() {
        return sessionService.getAllSessions();
    }
}