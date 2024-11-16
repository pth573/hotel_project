package com.project.hotel.model.dto;

import com.project.hotel.model.enumType.BedType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BedDTO {
    private int id;
    private BedType bedType;
    private int bedNumber;
}
