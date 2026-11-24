package com.realestate.ai.controller;

import com.realestate.ai.config.AiConfig;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.template.st.StTemplateRenderer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

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

    @GetMapping("/describe")
    public String describeProperty(
            @RequestParam String propertyType,
            @RequestParam  String city,
            @RequestParam String budget) {

        String templateText = """
                Write an attractive real estate description 
                for a {propertyType} located in {city}.
                Its price is {budget}.
                Use a professional and customer-friendly tone.
                """;

        PromptTemplate template = PromptTemplate.builder()
                .renderer(StTemplateRenderer.builder().build())
                .template(templateText)
                .build();

        Prompt prompt = template.create(Map.of("propertyType", propertyType,
                "city", city, "budget", budget));

        return chatClient.prompt(prompt)
                .call()
                .content();
    }
}
