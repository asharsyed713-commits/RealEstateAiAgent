package com.realestate.ai.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class AiConfig {

     public static final String REAL_ESTATE_SYSTEM_PROMPT=
             """
                     You are a professional real estate AI assistant.
                                 Help customers find suitable properties.
                                 Always be polite and helpful.
                                 Never invent property prices or availability.   
                     """;

}
