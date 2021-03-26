package com.covid.vaccine.controller;

import com.covid.vaccine.model.Hospital;
import com.covid.vaccine.model.Patient;
import com.covid.vaccine.repo.HospitalRepo;
import com.covid.vaccine.repo.PatientRepo;
import com.covid.vaccine.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/hospital")
public class HospitalPortal {

    @Autowired
    HospitalRepo hospitalRepo;

    @Autowired
    PatientRepo patientRepo;

    @Value("${spring.mail.username}")
    String hospitalEmail;

    @Autowired
    CommonUtils utils;

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
    public List<Hospital> getHospitals() {
        return hospitalRepo.findAll();
    }

    @GetMapping("patients/{hospitalId}")
    public List<Patient> getPatients(@PathVariable("hospitalId") String hospitalId) {

        List<Patient> result = patientRepo.findByHospitalId(hospitalId);
        if (result == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No Patients Found for Hospital ID: " + hospitalId);
        return result;
    }

    @GetMapping("/patients/notify/{hospitalId}")
    public String notifyPatientsViaEmail(@PathVariable("hospitalId") String hospitalId) {
        List<Patient> patients = patientRepo.findByHospitalId(hospitalId);
        if (patients == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No Patients Found for Hospital ID: " + hospitalId);
        List<Patient> mailsSentToThesePatients = new ArrayList<>();
        Map<String, String> emailProps;
        try {
            for (Patient patient : patients) {
                emailProps = new HashMap<>();
                if (patient.getEmail() == null) continue;
                emailProps.put("from", hospitalEmail);
                emailProps.put("to", patient.getEmail());
                emailProps.put("subject", "Appointment Due for Vaccination");
                emailProps.put("name", patient.getName());
                Hospital hospital = hospitalRepo.findById(patient.getHospitalId()).get();
                emailProps.put("location", hospital.getName());
                utils.sendEmail(emailProps);
                mailsSentToThesePatients.add(patient);
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage());
        }

        StringBuilder res = new StringBuilder();
        if (mailsSentToThesePatients.size() == 0)
            res.append("No Patients were notified");
        else
            res.append("Notified these patients via email: ").append(mailsSentToThesePatients.stream().map(Patient::getName).collect(Collectors.toList()));

        return res.toString();
    }

    @PatchMapping("/patient/vaccinated/{patientId}")
    Patient updatePatient(@PathVariable("patientId") String patientId) {
        Optional<Patient> result = patientRepo.findById(patientId);
        Patient patient;
        if (result.isPresent()) {
            patient = result.get();
            int noOfTimesVaccianted = patient.getNoOfTimesVaccinated();
            if (noOfTimesVaccianted == 2) return patient;
            noOfTimesVaccianted++;
            patient.setNoOfTimesVaccinated(noOfTimesVaccianted);
            patient.setVaccinated(Boolean.TRUE);
            patientRepo.save(patient);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient Not Found with this Id: " + patientId);
        }
        return patient;
    }

}
