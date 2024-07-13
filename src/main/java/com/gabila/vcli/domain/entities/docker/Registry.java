package com.gabila.vcli.domain.entities.docker;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.Type;

@Entity(name = "registries")
@Data
public class Registry {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(unique = true)
    @NotNull
    private String name;

    @Column(columnDefinition="TEXT")
    private String description;

    @Column(length = 1024)
    @NotNull
    private String url;
}
