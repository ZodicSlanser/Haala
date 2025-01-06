package com.gizasystems.restaurantservice.entites;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    private String name;



    @OneToMany(cascade = CascadeType.ALL, mappedBy = "restaurant", fetch = FetchType.EAGER)
    @JsonManagedReference
    @NonNull
    private List<Item> items;

    @ManyToMany
    @NonNull
    private List<Owner> owners;
}
