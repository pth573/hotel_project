package com.project.hotel.model.entity;

import com.project.hotel.model.enumType.BedType;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "bed")
public class Bed {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private BedType bedType;
    private int bedNumber;

    @ManyToMany(mappedBy = "beds")
    private List<RoomGroup> roomGroups;

    public Bed(BedType bedType, int bedNumber) {
        this.bedType = bedType;
        this.bedNumber = bedNumber;
    }

    @Override
    public String toString() {
        return "Bed{" +
                "id=" + id +
                ", bedType=" + bedType +
                ", bedNumber=" + bedNumber +
                '}';
    }
}
