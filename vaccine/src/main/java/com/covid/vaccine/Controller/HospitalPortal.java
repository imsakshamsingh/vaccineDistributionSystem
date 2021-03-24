package com.covid.vaccine.Controller;

import com.covid.vaccine.model.Hospital;
import com.covid.vaccine.model.Patient;
import com.covid.vaccine.repo.HospitalRepo;
import com.covid.vaccine.repo.PatientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/hospital")
public class HospitalPortal {

    @Autowired
    HospitalRepo hospitalRepo;

    @Autowired
    PatientRepo patientRepo;

    @PostMapping("/addHospital")
    public String addHospital(@RequestBody Hospital hospital) {
        if (hospital.getAddress() != null && hospital.getName() != null && hospital.getVaccinesInStock() != 0) {
            hospitalRepo.save(hospital);
            return "Hospital Added Successfully";
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing Mandatory Hospital Details");
        }
    }


    @GetMapping("/all")
    public List<Hospital> getHospitals(){
        return hospitalRepo.findAll();
    }


    @GetMapping("patients/{hospitalId}")
    public List<Patient> getPatients(@PathVariable("hospitalId") String hospitalId){

        List<Patient> result = patientRepo.findByHospitalId(hospitalId);
        if(result==null) throw new ResponseStatusException(HttpStatus.NOT_FOUND,"No Patients Found for Hospital ID: "+hospitalId);
        return result;
    }

}
