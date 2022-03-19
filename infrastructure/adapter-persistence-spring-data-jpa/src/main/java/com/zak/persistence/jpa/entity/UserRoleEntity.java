package com.zak.persistence.jpa.entity;

import com.zak.domain.enums.EUserRole;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Table(name = "roles")
public class UserRoleEntity  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private EUserRole name;

    private String label;

    public UserRoleEntity() {

    }

    public UserRoleEntity(EUserRole name) {
        this.name = name;
    }
    public UserRoleEntity(EUserRole name, String label) {
        this.name = name;
        this.label = label;
    }

}
