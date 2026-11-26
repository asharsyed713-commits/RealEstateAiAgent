package com.realestate.ai.model;

public record Property (
        Long id,
        String propertyType,
        Integer bedrooms,
        String city,
        Double price,
        String location
){
}
