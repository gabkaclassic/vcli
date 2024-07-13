package com.gabila.vcli.domain.repositories;

import com.gabila.vcli.domain.entities.docker.Registry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistryRepository extends JpaRepository<Registry, String> {

    Page<Registry> findByNameContainingIgnoreCase(String title, Pageable pageable);
}
