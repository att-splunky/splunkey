package com.techm.att.splunk.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.techm.att.bone.spring.RequestScopedComponent;
import com.techm.att.splunk.domain.City;
import com.techm.att.splunk.repo.CityRepo;

@RequestScopedComponent("citiesBean")
public class CitiesBean {
    
    @Autowired
    private CityRepo cityRepo;
    
    private List<City> cities;
    
    public List<City> getCities() {
        if (cities == null) {
            cities = cityRepo.findAll();
        }
        
        return cities;
    }
}
