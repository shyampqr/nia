package com.nextgenz.controller;

import com.nextgenz.service.GeminiService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ChatController {

    @Autowired
    private GeminiService geminiService;

    // Simple in-memory chat history per session (for demo)
    // In production, use a database or specialized chat service
    private static final String CHAT_HISTORY_KEY = "chatHistory";

    @GetMapping("/chat")
    public String chatPage(HttpSession session, Model model) {
        String user = (String) session.getAttribute("authenticatedUser");
        if (user == null) {
            return "redirect:/login";
        }

        List<String> history = (List<String>) session.getAttribute(CHAT_HISTORY_KEY);
        if (history == null) {
            history = new ArrayList<>();
            session.setAttribute(CHAT_HISTORY_KEY, history);
        }

        model.addAttribute("chatHistory", history);
        model.addAttribute("user", user);
        return "chat";
    }

    @PostMapping("/chat/send")
    public String sendMessage(@RequestParam("message") String message, HttpSession session) {
        String user = (String) session.getAttribute("authenticatedUser");
        if (user == null) {
            return "redirect:/login";
        }

        List<String> history = (List<String>) session.getAttribute(CHAT_HISTORY_KEY);
        if (history == null) {
            history = new ArrayList<>();
            session.setAttribute(CHAT_HISTORY_KEY, history);
        }

        // Add user message to history
        history.add("You: " + message);

        // Get response from Gemini
        String response = geminiService.generateResponse(message);
        
        // Add bot response to history
        history.add("Gemini: " + response);

        return "redirect:/chat";
    }
}
