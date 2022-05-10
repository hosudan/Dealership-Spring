package Service;

import Domain.DriveTest;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public interface DriveTestService extends Service {

    public CompletableFuture<Boolean> addDriveTest(DriveTest dt);
    public CompletableFuture<Set<DriveTest>> getAllDriveTests();
    public CompletableFuture<Boolean> populateDriveTests();
    public CompletableFuture<Boolean> deleteDriveTest(DriveTest dt);
    public CompletableFuture<Boolean> updateDriveTest(DriveTest dt);
    public CompletableFuture<Set<DriveTest>> filterDriveTestByCNP(String cnp);
    public CompletableFuture<Set<DriveTest>> filterDriveTestByRating(Integer rating);
    public CompletableFuture<Set<DriveTest>> filterDriveTestByVIN(String vin);

}
