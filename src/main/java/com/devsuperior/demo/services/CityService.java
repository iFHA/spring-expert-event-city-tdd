package com.devsuperior.demo.services;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.demo.dto.CityDTO;
import com.devsuperior.demo.entities.City;
import com.devsuperior.demo.repositories.CityRepository;
import com.devsuperior.demo.services.exceptions.ResourceNotFoundException;

@Service
public class CityService {
    private final CityRepository repository;

    public CityService(CityRepository repository) {
        this.repository = repository;
    }
    
    @Transactional(readOnly = true)
    public List<CityDTO> findAll() {
        return repository.findAll(Sort.by("name"))
        .stream()
        .map(CityDTO::new)
        .toList();
    }

    @Transactional
    public CityDTO store(CityDTO dto) {
        dto.setId(null);
        return new CityDTO(repository.save(new City(dto)));
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id) {
        var entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Cidade não encontrada!"));
        this.repository.delete(entity);
    }

}
