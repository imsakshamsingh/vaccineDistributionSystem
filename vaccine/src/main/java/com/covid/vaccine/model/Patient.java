package com.covid.vaccine.model;

import org.springframework.data.annotation.Id;

public class Patient {
    @Id
    public String patientId;
    public String name;
    public int age;
    public String hospitalId;
    public String city;
    public Boolean vaccinated;
    public int noOfTimesVaccinated;
    public String mobileNo;

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public Patient(String name, int age, String hospitalId, String city, Boolean vaccinated, int noOfTimesVaccinated, String mobileNo) {
        this.name = name;
        this.age = age;
        this.hospitalId = hospitalId;
        this.city = city;
        this.vaccinated = vaccinated;
        this.noOfTimesVaccinated = noOfTimesVaccinated;
        this.mobileNo = mobileNo;
    }

    public Boolean getVaccinated() {
        return vaccinated;
    }

    public void setVaccinated(Boolean vaccinated) {
        this.vaccinated = vaccinated;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public int getNoOfTimesVaccinated() {
        return noOfTimesVaccinated;
    }

    public void setNoOfTimesVaccinated(int noOfTimesVaccinated) {
        this.noOfTimesVaccinated = noOfTimesVaccinated;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", hospitalId='" + hospitalId + '\'' +
                ", city='" + city + '\'' +
                ", vaccinated=" + vaccinated +
                ", noOfTimesVaccinated=" + noOfTimesVaccinated +
                ", mobileNo='" + mobileNo + '\'' +
                '}';
    }
}
