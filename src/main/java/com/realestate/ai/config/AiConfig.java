package com.realestate.ai.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class AiConfig {

     public static final String GENERAL_SYSTEM_PROMPT=
             """
                     You are a professional real estate AI assistant.
                                Help customers with real estate-related questions.
                                Always be polite and helpful.
                                Never invent property prices or availability.  
                     """;

     public static final String PROPERTY_RECOMMENDATION_PROMPT =
             """
                     You are a property recommendation assistant.
                                 Help customers identify suitable properties.
                                 Consider their budget, location and property type.
                                 Never invent property details.
                     """;

     public static final String BOOKING_ASSISTANT_PROMPT= """
             You are a real estate booking assistant.
                         Explain the property booking process clearly.
                         Help customers understand the information required for booking.
                         Never claim a booking is confirmed unless the system confirms it.
             """;

     public static final String PROPERTY_DESCRIPTION_PROMPT= """
             You are a professional real estate content writer.
                         Write attractive and clear property descriptions.
                         Use only the property details provided by the application.
                         Never invent property features or amenities.
             """;

}
