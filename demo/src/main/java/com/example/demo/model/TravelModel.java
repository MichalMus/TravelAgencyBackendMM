package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//@EqualsAndHashCode
@Entity
public class TravelModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "start_date")
    private Date startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "number_of_days")
    private Byte numberOfDays;

    @Column(name = "hotel_type")
    @Enumerated(EnumType.STRING)
    private Type hotelType;

    @Column(name = "adult_price")
    private Integer adultPrice;

    @Column(name = "child_price")
    private Integer childPrice;

    //sprawdzić pole poniżej
    @Column(name = "promotion")
    private Boolean promotion;

    @Column(name = "adults_number")
    private Byte adultsNumber;

    @Column(name = "children_number")
    private Byte childrenNumber;

    //sprawdzxić wszystko poniżej czy potrzebne, czy dobrze, czy dobra zależność
    @ManyToOne
    private AirportModel start;

    @ManyToOne
    private AirportModel destination;

    @ManyToOne
    @JoinColumn(name = "hotel_model_id")
    private HotelModel hotelModel;

    @ManyToOne
    @JoinColumn(name = "country_model_id")
    private CountryModel countryModel;

    @ManyToOne
    @JoinColumn(name = "continent_model_id")
    private ContinentModel continentModel;


}
