package com.realestate.ai.controller;

import com.realestate.ai.config.AiConfig;
import com.realestate.ai.dto.PropertySearchRequest;
import com.realestate.ai.model.Property;
import com.realestate.ai.service.PropertySearchService;
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

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/ai")
public class AiTestController {

    private final ChatClient chatClient;
    private final PropertySearchService propertySearchService;

    public AiTestController( ChatClient.Builder chatClient, PropertySearchService propertySearchService) {
        this.chatClient = chatClient.build();
        this.propertySearchService = propertySearchService;
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

    @GetMapping("/extract")
    public PropertySearchRequest extractPropertyDetails(@RequestParam String message){
        return chatClient.prompt()
                .system("""
                          You are a real estate search assistant.
                        
                                              Extract property search details from the user's request.
                        
                                              Fields to extract:
                                              - propertyType
                                              - bedrooms
                                              - city
                                              - budget
                                              - location
                        
                                              Rules:
                                              1. Extract only information explicitly mentioned.
                                              2. If a field is not provided, return null.
                                              3. Do not invent property details.
                                              4. Convert budget to a numeric value.
                                              5. Return data matching the requested Java type.
                        """)
                .user(message)
                .call()
                .entity(PropertySearchRequest.class);
    }

    @GetMapping("/search")
    public List<Property> searchProperties(@RequestParam String message){

        PropertySearchRequest request = chatClient.prompt()
                .system("""
                        You are a real estate search assistant.
                        
                                            Extract property search details from the user's request.
                        
                                            Fields:
                                            - propertyType
                                            - bedrooms
                                            - city
                                            - budget
                                            - location
                        
                                            Rules:
                                            1. Extract only explicitly mentioned information.
                                            2. Return null for missing fields.
                                            3. Do not invent property details.
                                            4. Convert budget into a numeric INR value.
                                            5. Return data matching the requested Java type.
                        """)
                .user(message)
                .call()
                .entity(PropertySearchRequest.class);
        return propertySearchService.searchProperties(request);
    }

}
