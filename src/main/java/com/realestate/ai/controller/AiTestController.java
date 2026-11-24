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
                .system("You are a professional real estate AI assistant. " +
                        "Help customers find suitable properties. " +
                        "Always be polite and never invent property prices or availability.")
                .user("I am looking for a 2BHK apartment in Hyderabad answer in 50 words.")
                .call()
                .content();

        return response;
    }
}
