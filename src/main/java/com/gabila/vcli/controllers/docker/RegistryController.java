package com.gabila.vcli.controllers.docker;

import com.gabila.vcli.domain.entities.docker.Registry;
import com.gabila.vcli.domain.repositories.RegistryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/docker/registries")
@RequiredArgsConstructor
public class RegistryController {

    private final RegistryRepository registryRepository;

    @GetMapping
    public ResponseEntity<List<Registry>> getRegistriesList(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "sortField", defaultValue = "name") String sortField,
            @RequestParam(name = "sortOrder", required = false) Sort.Direction sortOrder,
            @RequestParam(name = "search", required = false) String search) {

        if(sortOrder == null) {
            sortOrder = Sort.Direction.ASC;
        }
        Pageable pageable = PageRequest.of(page, size, sortOrder, sortField);

        if (search != null) {
            Page<Registry> registries = registryRepository.findByNameContainingIgnoreCase(search, pageable);
            return ResponseEntity.ok(registries.getContent());
        } else {
            Page<Registry> registries = registryRepository.findAll(pageable);
            return ResponseEntity.ok(registries.getContent());
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<Registry> getRegistryById(@PathVariable String id) {
        return registryRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Registry> createRegistry(@RequestBody Registry registry) {
        return ResponseEntity.status(HttpStatus.CREATED).body(registryRepository.save(registry));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Registry> updateRegistry(@PathVariable String id, @RequestBody Registry updatedRegistry) {
        return registryRepository.findById(id)
                .map(registry -> {
                    registry.setName(updatedRegistry.getName());
                    registry.setDescription(updatedRegistry.getDescription());
                    registry.setUrl(updatedRegistry.getUrl());
                    return ResponseEntity.ok(registryRepository.save(registry));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRegistry(@PathVariable String id) {
        return registryRepository.findById(id)
                .map(registry -> {
                    registryRepository.delete(registry);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}