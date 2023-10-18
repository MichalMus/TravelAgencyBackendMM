package com.example.demo.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//@ToString
@EqualsAndHashCode
@Entity
public class AirportModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "airport_name")
    private String airPortName;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private CityModel cityModel;


}
