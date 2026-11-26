package com.realestate.ai.model;

import java.util.List;

public class PropertyData{

    public static List<Property> getProperties(){

        return List.of(
                new Property(101L,"Apartment",
                        2,
                        "Hyderabad",
                        75000000.0,
                        "Gachibowli"),

                new Property(
                        102L,
                        "Apartment",
                        3,
                        "Hyderabad",
                        9500000.0,
                        "Madhapur"
                ),

                new Property(
                        103L,
                        "Villa",
                        2,
                        "Hyderabad",
                        8500000.0,
                        "Kondapur"
                ),

                new Property(
                        104L,
                        "Apartment",
                        2,
                        "Bangalore",
                        7000000.0,
                        "Whitefield"
                ),

                new Property(
                        105L,
                        "Villa",
                        3,
                        "Hyderabad",
                        15000000.0,
                        "Jubilee Hills"
                ),

                new Property(
                        106L,
                        "Apartment",
                        2,
                        "Hyderabad",
                        6500000.0,
                        "Miyapur"
                )
        );
    }
}
