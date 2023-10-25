package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//@ToString
//@EqualsAndHashCode
@Entity
public class CityModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "city_name")
    private String cityName;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private CountryModel countryModel;

//    @OneToMany(fetch = FetchType.LAZY,cascade =CascadeType.ALL,mappedBy = "cityModel")
//    @JsonIgnore
//    private Set<HotelModel> hotels = new HashSet<>();
//
//    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL,mappedBy = "cityModel")
//    @JsonIgnore
//    private Set<AirportModel> airports = new HashSet<>();



}
