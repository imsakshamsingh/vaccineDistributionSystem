package com.covid.vaccine.repo;

import com.covid.vaccine.model.Patient;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface PatientRepo extends MongoRepository<Patient,String> {

    public List<Patient> findByName(String name);
    public List<Patient> findByVaccinated(Boolean isVaccinated);

    @Query(value = "{hospitalId : ?0}", fields = "{ 'name' : 1, 'age' : 1, 'city' : 1, 'noOfTimesVaccinated':1 , 'vaccinated':1}")
    public List<Object> findByHospitalId(String hospitalId);
}
