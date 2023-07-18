package com.unper.samper.model;

import javax.persistence.*;

import com.unper.samper.model.common.Audit;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Builder
@Table(name = "student", schema = "public")
public class Student extends Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Double NIM;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = User.class)
    @JoinColumn(name = "user_id")
    private User user; 
}
