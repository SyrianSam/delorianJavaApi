package com.syriansam.delorianapi.delorianEntities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Movie {

    public static final double b2fPrice = 15.0;
    public static final double otherPrice = 20.0;

    String name;
    Double price;

}
