package com.realestate.ai.service;

import com.realestate.ai.dto.PropertySearchRequest;
import com.realestate.ai.model.Property;
import com.realestate.ai.model.PropertyData;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class PropertySearchService {

    public List<Property> searchProperties(
            PropertySearchRequest request
    ){
        List<Property> properties = PropertyData.getProperties();
        return properties.stream()
                .filter(property-> request.propertyType() == null ||
                        property.propertyType().equalsIgnoreCase(request.propertyType()))
                .filter(property-> request.bedrooms() == null ||
                        property.bedrooms().equals(request.bedrooms()))
                .filter(property -> request.city() == null ||
                        property.city().equalsIgnoreCase(request.city()))
                .filter(property -> request.budget() == null ||
                        property.price()<=request.budget())
                .filter(property -> request.location() == null ||
                        property.location().equalsIgnoreCase(request.location()))
                .toList();
    }
}
