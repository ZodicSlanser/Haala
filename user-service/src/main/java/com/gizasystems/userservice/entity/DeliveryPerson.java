package com.gizasystems.userservice.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "delivery_persons")
public class DeliveryPerson extends User {

    private Boolean availability;


}
