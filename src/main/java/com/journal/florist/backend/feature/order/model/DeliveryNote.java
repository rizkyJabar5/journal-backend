package com.journal.florist.backend.feature.order.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryNote {

    @Id
    @SequenceGenerator(name = "travelSequence",
            sequenceName = "travel_sequence",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "travelSequence")
    private Long id;

    @Column(unique = true, nullable = false)
    private String gnrId;

    @OneToOne
    private Orders order;

    private boolean isPrinted;

    @CreatedBy
    @Column(nullable = false)
    private String createdBy;

    @CreatedDate
    @Column(nullable = false)
    private Date createdAt = new Date(System.currentTimeMillis());

    @PrePersist
    private void randomGNRId(){
        if(Objects.isNull(this.gnrId)) {
            setGnrId(RandomStringUtils.randomNumeric(8));
        }
    }
}
