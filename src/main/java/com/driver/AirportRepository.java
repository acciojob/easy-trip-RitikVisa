package com.driver;

import com.driver.model.Airport;
import com.driver.model.City;
import com.driver.model.Flight;
import com.driver.model.Passenger;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Repository

public class AirportRepository {

    //airport hashmap contains name and airport object
    private HashMap<String, Airport> airportDb = new HashMap<>();
    private HashMap<Integer, Flight> flightMap = new HashMap<>();
    private HashMap<Integer, Passenger> passengerMap = new HashMap<>();

    private HashMap<Integer, List<Passenger>> flightPassengerMap = new HashMap<>();

    private HashMap<String, List<Flight>> airportNameFlightDb = new HashMap<>();



    public AirportRepository() {
//        this.airportDb = airportDb;
//        this.flightMap = flightMap;
//        this.passengerMap = passengerMap;
//        this.flightPassengerMap = flightPassengerMap;
//        this.airportNameFlightDb = airportNameFlightDb;
        this.airportDb = new HashMap<String, Airport>();
        this.flightMap = new HashMap<Integer,Flight>();
        this.passengerMap = new HashMap<Integer, Passenger>();
        this.airportNameFlightDb= new HashMap<String, List<Flight>>();
        this.flightPassengerMap = new HashMap<Integer, List<Passenger>>();

    }

    public String addAirport(Airport airport) {

//        if(airport.getNoOfTerminals()>=max){
//            if(airport.getNoOfTerminals()>max){
//                max = airport.getNoOfTerminals();
//                largestAirport = airport.getAirportName();
//            }else if(airport.getNoOfTerminals()==max){
//                String a = airport.getAirportName();
//                if(a.compareTo(largestAirport)==-1){
//                    largestAirport= a;
//                }
//            }
//
//        }


        this.airportDb.put(airport.getAirportName(), airport);


        return "SUCCESS";
    }

    public String addFlight(Flight flight) {

        this.flightMap.put(flight.getFlightId(), flight);

        return "SUCCESS";
    }

    public String addPassenger(Passenger passenger) {
        this.passengerMap.put(passenger.getPassengerId(), passenger);

        return "SUCCESS";
    }

    public String bookATicket(Integer flightId, Integer passengerId) {

        List<Passenger> passengerIds = new ArrayList<>();

        passengerIds.add(passengerMap.get(passengerId));

        if(!flightPassengerMap.containsKey(flightId)){
            flightPassengerMap.put(flightId,passengerIds);
            return "SUCCESS";
        }else{
            if(flightPassengerMap.get(flightId).contains(passengerMap.get(passengerId))){
                return "FAILURE";
            }else{
             flightPassengerMap.get(flightId).add(passengerMap.get(passengerId));
             return "SUCCESS";
            }



        }


//      City fcity =  flightMap.get(flightId).getFromCity();
//      City tcity = flightMap.get(flightId).getToCity();
//
//        for (Map.Entry<String, Airport> entry : airportDb.entrySet()) {
//
//           Airport airport = entry.getValue();
//           if(airport.getCity().equals(fcity) || airport.getCity().equals(tcity)){
//              if(airportNameFlightDb.containsKey(airport.getAirportName())){
//                  airportNameFlightDb.get(airport.getAirportName()).add(flightMap.get(flightId));
//              }else {
//                  List<Flight> f = new ArrayList<>();
//                  f.add(flightMap.get(flightId));
//                  airportNameFlightDb.put(airport.getAirportName(), f);
//              }
//           }
//
//        }

    }

    public String cancelATicket(Integer flightId, Integer passengerId) {


        if (flightPassengerMap.containsKey(flightId)) {
            if (flightPassengerMap.get(flightId).contains(passengerMap.get(passengerId))) {
                flightPassengerMap.get(flightId).remove(passengerMap.get(passengerId));
                return "SUCCESS";
            } else {
                return "FAILURE";
            }
        }

        return "FAILURE";

    }

    public String getLargestAirportName() {


        return "nuuuu";
    }

    public double getShortestDurationOfPossibleBetweenTwoCities(City fromCity, City toCity) {
        double shortesttime = Double.MAX_VALUE;


        Collection<Flight> values = flightMap.values();

        // Creating an ArrayList of values
        ArrayList<Flight> flightList = new ArrayList<>(values);

        for(Flight f : flightList){
            if(f.getFromCity().equals(fromCity) &&  f.getToCity().equals(toCity) ){
                if (shortesttime > f.getDuration()) {
                    shortesttime = f.getDuration();
                }
            }
        }

        if (shortesttime == Double.MAX_VALUE) {
            return -1;
        }


        return shortesttime;
    }

    public int getNumberOfPeopleOn(Date date, String airportName) {

        int numOfpeople = 0;


           List<Flight> flights= airportNameFlightDb.get(airportName);
           if(flights.isEmpty()){
               return 0;
           }

        for (Flight f : flights) {
                if (date == f.getFlightDate()) {
                    int fid = f.getFlightId();
                    numOfpeople += flightPassengerMap.get(fid).size();
                }
            }





//        if (!airportNameFlightDb.get(airportName).isEmpty()) {
//            List<Flight> f = airportNameFlightDb.get(airportName);
//            for (Flight flight : f) {
//                if (date == flight.getFlightDate()) {
//                    int fid = flight.getFlightId();
//                    numOfpeople += flightPassengerMap.get(fid).size();
//                }
//            }
//        }


        return numOfpeople;
    }

    public int calculateFlightFare(Integer flightId) {

        //Calculation of flight prices is a function of number of people who have booked the flight already.
        //Price for any flight will be : 3000 + noOfPeopleWhoHaveAlreadyBooked*50
        //Suppose if 2 people have booked the flight already : the price of flight for the third person will be 3000 + 2*50 = 3100
        //This will not include the current person who is trying to book, he might also be just checking price

        int passengersCount = flightPassengerMap.get(flightId).size();
        int alreadybookedpeople = passengersCount * 50;
        int flightPrice = 3000 + alreadybookedpeople;

        return flightPrice;

    }

    public int countOfBookingsDoneByPassengerAllCombined(Integer passengerId) {

        int count = 0;

            for (Map.Entry<Integer, List<Passenger>> entry : flightPassengerMap.entrySet()) {

                if (entry.getValue().contains(passengerMap.get(passengerId))) {
                    count++;
                }

            }

        //Tell the count of flight bookings done by a passenger: This will tell the total count of flight bookings done by a passenger :
        return count;
    }

    public String getAirportNameFromFlightId(Integer flightId) {

        //We need to get the starting airportName from where the flight will be taking off (Hint think of City variable if that can be of some use)
        //return null incase the flightId is invalid or you are not able to find the airportName



        for (Map.Entry<String, List<Flight>> entry : airportNameFlightDb.entrySet()) {

            String airportname = entry.getKey();
            Airport airport = airportDb.get(airportname);

            Flight f = flightMap.get(flightId);

            if (entry.getValue().contains(f)) {
                if (flightMap.get(flightId).getFromCity().equals(airport.getCity())) {
                    return airportname;
                }

            }else{
                return null;
            }
        }


        return null;
    }

    public int calculateRevenueOfAFlight(Integer flightId) {

        //Calculate the total revenue that a flight could have
        //That is of all the passengers that have booked a flight till now and then calculate the revenue
        //Revenue will also decrease if some passenger cancels the flight
        int ans = 3000;
        int bookedpassengers = flightPassengerMap.get(flightId).size()-1;
        if(bookedpassengers < 0){
            return 0;
        }

        ans += bookedpassengers * 3050;

        return ans;

    }
}
