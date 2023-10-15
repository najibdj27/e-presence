package com.unper.samper.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.SQLDelete;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import com.unper.samper.model.common.Audit;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@EnableAutoConfiguration
@Getter
@Setter
@Builder
@Entity
@Table(name = "major", schema = "public", uniqueConstraints = {@UniqueConstraint(columnNames = "majorCode")})
@SQLDelete(sql = "UPDATE public.major SET is_deleted = true WHERE id=?")
@FilterDef(name = "deletedProductFilter", parameters = @ParamDef(name = "isDeleted", type = "boolean"))
@Filter(name = "deletedProductFilter", condition = "isDeleted = :isDeleted")
public class Major extends Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String majorCode;

    private String name;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = Lecture.class)
    @JoinColumn(name = "major_id")
    private Lecture majorHead;

    @Builder.Default
    @ManyToMany(mappedBy = "majors")
    private Set<Subject> subjects = new HashSet<>();

    @Builder.Default
    private Boolean isDeleted = Boolean.FALSE;
} 
