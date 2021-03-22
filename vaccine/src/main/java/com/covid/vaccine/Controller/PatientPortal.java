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
@RequestMapping("/patient")
public class PatientPortal {

    @Autowired
    PatientRepo patientRepo;

    @Autowired
    HospitalRepo hospitalRepo;


    @PostMapping("/enroll")
    public String enrollPatient(@RequestBody Patient patient) {
        Hospital finalHospital = null;
        if (patient.getCity() != null && patient.getAge()!=0 && patient.getName()!=null) {
            List<Hospital> hospitals = hospitalRepo.findByCity(patient.getCity());
            String hospitalId = null;
            for (Hospital hospital : hospitals) {
                if (hospital.getVaccinesInStock() > 0) {
                    hospitalId = hospital.hospitalId;
                    finalHospital = hospital;
                    break;
                }
            }

            if (hospitalId != null) {

                patient.setHospitalId(hospitalId);
                patient.setVaccinated(false);
                patient.setNoOfTimesVaccinated(0);
                patientRepo.save(patient);
            } else{
                throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,"Insufficent Vaccines in Stock. Please try again later!");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Missing Mandatory Patient Details");
        }

        return "Patient Enrolled Successfully in this hospital: " + finalHospital.getName();
    }


    @GetMapping("/findByHospitalId/{hospitalId}")
    public List<Object> findByHospitalId(@PathVariable("hospitalId") String hospitalId){

        System.out.println("hospital id : "+ hospitalId);
        return patientRepo.findByHospitalId(hospitalId);
    }
}
