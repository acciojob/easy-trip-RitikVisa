package com.driver;

import com.driver.model.Airport;
import com.driver.model.City;
import com.driver.model.Flight;
import com.driver.model.Passenger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AirportService {


    @Autowired
    AirportRepository repository ;
    public String addAirport(Airport airport){

        return repository.addAirport(airport);
    }
    public String addFlight( Flight flight){

        return repository.addFlight(flight);
    }

    public String addPassenger( Passenger passenger){
        return repository.addPassenger(passenger);
    }

    public String bookATicket(Integer flightId,Integer passengerId){
        return repository.bookATicket(flightId,passengerId);
    }

    public String cancelATicket(Integer flightId,Integer passengerId){
        return repository.cancelATicket(flightId,passengerId);
    }

    public String getLargestAirportName(){
        return repository.getLargestAirportName();
    }

    public double getShortestDurationOfPossibleBetweenTwoCities( City fromCity,City toCity) {
        return repository.getShortestDurationOfPossibleBetweenTwoCities(fromCity,toCity);
    }

    public int getNumberOfPeopleOn( Date date,String airportName) {

        return repository.getNumberOfPeopleOn(date,airportName);
    }

    public int calculateFlightFare(Integer flightId){
        return repository.calculateFlightFare(flightId);
    }

    public int countOfBookingsDoneByPassengerAllCombined(Integer passengerId){
        return countOfBookingsDoneByPassengerAllCombined(passengerId);
    }

    public String getAirportNameFromFlightId(Integer flightId){
        return repository.getAirportNameFromFlightId(flightId);
    }

    public int calculateRevenueOfAFlight(Integer flightId){
        return repository.calculateRevenueOfAFlight(flightId);
    }

    }
