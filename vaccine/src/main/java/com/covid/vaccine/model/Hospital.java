package com.covid.vaccine.model;

import org.springframework.data.annotation.Id;

public class Hospital {
    @Id
    public String hospitalId;
    public String name;
    public String address;
    public String city;
    public int vaccinesInStock;


    public Hospital(String name, String address, String city, int vaccinesInStock) {
        this.name = name;
        this.address = address;
        this.city = city;
        this.vaccinesInStock = vaccinesInStock;
    }

    @Override
    public String toString() {
        return "Hospital{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", vaccinesInStock=" + vaccinesInStock +
                '}';
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getVaccinesInStock() {
        return vaccinesInStock;
    }

    public void setVaccinesInStock(int vaccinesInStock) {
        this.vaccinesInStock = vaccinesInStock;
    }

}
