package com.realestate.ai.controller;

import com.realestate.ai.config.AiConfig;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.template.st.StTemplateRenderer;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

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
                You are a professional real estate content writer.
                
                Your task is to write and attractive property description
                using the following property details.
                
                Property Type: {propertyType}
                Location: {city}
                Price: {budget}
                
                Requirements:
                        1. Use simple and professional English.
                        2. Make the description attractive to potential buyers.
                        3. Keep the description under 150 words.
                        4. Do not invent amenities, facilities or property features.
                        5. Use only the information provided above.
                
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

    @GetMapping(value = "/stream",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> streamChat(@RequestParam String message) {
        return chatClient.prompt()
                .system(AiConfig.GENERAL_SYSTEM_PROMPT)
                .user(message)
                .stream()
                .content();
    }
}
