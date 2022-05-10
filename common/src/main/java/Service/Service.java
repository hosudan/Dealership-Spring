package Service;

import Domain.Car;
import Domain.Client;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public interface Service {
    public final String SERVICE_HOST = "localhost";
    public final int SERVICE_PORT = 1234;

    public final String addCar = "add car";
    public final String deleteCar = "delete car";
    public final String updateCar = "update car";
    public final String filterCarsByManufacturer = "filter cars by manufacturer";
    public final String filterCarsByModel = "filter cars by model";
    public final String ReportMostTestedCars = "report most tested cars";
    public final String getAllCars = "get all cars";
    public final String getCarID = "get car id";
    public final String getVinById = "get vin by id";
    public final String populateCars = "populate cars";


    public final String addClient = "add client";
    public final String deleteClient = "delete client";
    public final String updateClient = "update client";
    public final String sortClientsByFirstName = "sort clients by first name";
    public final String getAllClients = "get all clients";
    public final String getClientID = "get client id";
    public final String getCnpById = "get cnp by id";
    public final String populateClients = "populate clients";


    public final String addDriveTest = "add drive test";
    public final String deleteDriveTest = "delete drive test";
    public final String updateDriveTest = "update drive test";
    public final String filterDriveTestByRating = "filter drive test by rating";
    public final String filterDriveTestsByCNP = "filter drive tests by cnp";
    public final String filterDriveTestsByVin = "filter drive test by vin";
    public final String ReportBiggestTester = "report biggest tester";
    public final String getAllDriveTests = "get all drive tests";
    public final String getDriveTestID = "get drive test id";
    public final String populateDriveTests = "populate drive tests";
}
