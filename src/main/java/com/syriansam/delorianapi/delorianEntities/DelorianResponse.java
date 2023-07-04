package com.syriansam.delorianapi.delorianEntities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

@JsonFormat
@Getter
@Setter
public class DelorianResponse {
    private double total;
}
