package com.devsuperior.demo.services;

import org.springframework.stereotype.Service;

import com.devsuperior.demo.dto.EventDTO;
import com.devsuperior.demo.entities.City;
import com.devsuperior.demo.entities.Event;
import com.devsuperior.demo.repositories.EventRepository;
import com.devsuperior.demo.services.exceptions.ResourceNotFoundException;

@Service
public class EventService {
    private final EventRepository repository;

    public EventService(EventRepository repository) {
        this.repository = repository;
    }
    public EventDTO update(Long id, EventDTO dto) {
        var entity = repository.findById(id).orElseThrow(()->new ResourceNotFoundException("Evento não encontrado!"));
        copyDtoToEntity(dto, entity);
        return new EventDTO(repository.save(entity));
    }

    private void copyDtoToEntity(EventDTO dto, Event entity) {
        var city = new City();
        city.setId(dto.getCityId());
        entity.setCity(city);
        entity.setDate(dto.getDate());
        entity.setName(dto.getName());
        entity.setUrl(dto.getUrl());
    }
}
