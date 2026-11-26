package com.realestate.ai.dto;

public record PropertySearchRequest(
        String propertyType,
        Integer bedrooms,
        String city,
        Double budget,
        String location

) {


}
