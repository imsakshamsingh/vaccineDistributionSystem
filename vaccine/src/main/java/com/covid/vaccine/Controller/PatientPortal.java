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
import java.util.regex.Pattern;

@RestController
@RequestMapping("/patient")
public class PatientPortal {

    @Autowired
    PatientRepo patientRepo;

    @Autowired
    HospitalRepo hospitalRepo;


    @GetMapping("/all")
    public List<Patient> getAllPatients() {
        return patientRepo.findAll();
    }

    @PostMapping("/enroll")
    public String enrollPatient(@RequestBody Patient patient) {
        Hospital finalHospital = null;
        if (patient.getCity() != null && patient.getAge() != 0 && patient.getName() != null && isValidMobileNo(patient.getMobileNo()) && isValidEmailAddress(patient.getEmail())) {
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
                patient.setVaccinated(true);
                patient.setNoOfTimesVaccinated(1);
                patientRepo.save(patient);
                return "Patient Enrolled Successfully in this hospital: " + finalHospital.getName();

            } else {
                patient.setVaccinated(false);
                patient.setNoOfTimesVaccinated(0);
                patientRepo.save(patient);
                return "Patient Queued, Currently no vaccines available, you will get a notification when there are some" +
                        "vaccines available";

            }
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing Mandatory Patient Details");
        }


    }

    private boolean isValidMobileNo(String mobileNo) {
        if (mobileNo == null) return false;
        String regex = "([0]|\\+91)?[7-9][0-9]{9}";
        return mobileNo.matches(regex);
    }

    private boolean isValidEmailAddress(String email) {
        if (email == null) return false;
        String regex = "[a-zA-Z0-9]+[@]{1}(gmail.com)";
        return email.matches(regex);
    }

    @GetMapping("/vaccinated/{isVaccinated}")
    public List<Patient> getPatients(@PathVariable("isVaccinated") String isVaccinated) {
        if ("yes".equals(isVaccinated)) {
            return patientRepo.findByVaccinated(true);
        } else if ("no".equals(isVaccinated)) {
            return patientRepo.findByVaccinated(false);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect isVaccinated Value");
        }
    }


}
