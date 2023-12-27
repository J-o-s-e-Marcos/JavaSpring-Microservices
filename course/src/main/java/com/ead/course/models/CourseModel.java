package com.ead.course.models;


import com.ead.course.enums.CourseLevel;
import com.ead.course.enums.CourseStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)// a nivel de classe, ao passar para json, oculta os campos nulls
@Entity
@Table(name = "TB_COURSES")
public class CourseModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)// gera id auto
    private UUID courseId;
    @Column(nullable = false, length = 150)
    private String name;
    @Column(nullable = false, length = 250)
    private String description;
    @Column
    private String imageUrl;
    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime creationDate;
    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime lastUpdateDate;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CourseStatus courseStatus;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CourseLevel courseLevel;
    @Column(nullable = false)
    private UUID userInstructor;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY) //cascade = CascadeType.ALL, orphanRemoval = true. cascadeAll = Vincula todos modulos em cascata, se um curso for deletado o jpa deleta todos os modulos vinciulados a ele.
    // OrphanRemoval = true  - modulos sem vinculo ao curso também será deletado. por criarem diversas SQL de deleção, afeta a performace e cria gargalo na base
    @Fetch(FetchMode.SUBSELECT) // 1 consulta para course e 1 para demais modulos. Se não for definido o jpa utiliza o join como default, mas não ignora o fetchtype caso esteja definido
    //@OnDelete(action = OnDeleteAction.CASCADE) define ação de deleções de associações. delega ao banco de dados as deleções das associações. diferente do cascadeType, gera apenas 2 deleções, 1 curso e 1 modulos. mas ainda sem o controle do que está sendo deletado
    private Set<ModuleModel> modules;




}