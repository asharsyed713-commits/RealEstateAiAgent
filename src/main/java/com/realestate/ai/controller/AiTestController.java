package com.realestate.ai.controller;

import com.realestate.ai.config.AiConfig;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ai")
public class AiTestController {

    private final ChatClient chatClient;

    public AiTestController(ChatClient.Builder chatClient) {
        this.chatClient = chatClient.build();
    }

    @GetMapping("/test")
    public String testConnection(@RequestParam String message) {
        String response = chatClient.prompt()
                .system(AiConfig.PROPERTY_DESCRIPTION_PROMPT)
                .user(message)
                .call()
                .content();

        return response;
    }
}
