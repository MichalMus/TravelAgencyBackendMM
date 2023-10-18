package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//@ToString
//@EqualsAndHashCode
@Entity
public class TravelPurchaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //sprawdzić pole poniżej - czy musi być przy tej relacji również to samo odniesienie w drugiej klasie
    @ManyToOne
    @JoinColumn(name = "travel_model_id")
    private TravelModel travelModel;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    @JsonIgnore
    private Set<PersonsIdModel> personsIds = new HashSet<>();

    @Column(name = "adults_number")
    private Byte adultsNumber;

    @Column(name = "children_number")
    private Byte childrenNumber;


}
