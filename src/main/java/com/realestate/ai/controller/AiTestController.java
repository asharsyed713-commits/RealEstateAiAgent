package com.realestate.ai.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ai")
public class AiTestController {

    private final ChatClient chatClient;

    public AiTestController(ChatClient.Builder chatClient) {
        this.chatClient = chatClient.build();
    }

    @GetMapping("/test")
    public String testConnection(){
        String response = chatClient.prompt()
                .user("Introduce yourself as a real estate AI assistant in 3 sentences.")
                .call()
                .content();

        return response;
    }
}
