package com.covid.vaccine.repo;

import com.covid.vaccine.model.Hospital;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface HospitalRepo extends MongoRepository<Hospital, String> {
    public List<Hospital> findByName(String name);
    public List<Hospital> findByCity(String city);
    public Optional<Hospital> findById(String id);
}
