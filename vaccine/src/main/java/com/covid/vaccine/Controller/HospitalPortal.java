package com.covid.vaccine.Controller;

import com.covid.vaccine.model.Hospital;
import com.covid.vaccine.repo.HospitalRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/hospital")
public class HospitalPortal {

    @Autowired
    HospitalRepo hospitalRepo;

    @PostMapping("/addHospital")
    public String addHospital(@RequestBody Hospital hospital){
        if(hospital.getAddress()!=null && hospital.getName()!=null && hospital.getVaccinesInStock()!=0){
            hospitalRepo.save(hospital);
            return "Hospital Added Successfully";
        } else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing Mandatory Hospital Details");
        }
    }
}
