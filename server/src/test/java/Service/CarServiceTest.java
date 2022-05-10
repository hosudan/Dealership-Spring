package Service;

import Domain.Car;
import Domain.Validators.CarValidator;
import Repository.InMemoryRepository;
import org.junit.Assert;
import org.junit.Test;

import java.util.Random;

public class CarServiceTest {

    public String str_gen(Integer length){
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = length;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        return generatedString;
    }

    @Test
    public void getLastId() {
        InMemoryRepository car = new InMemoryRepository(new CarValidator());
        Car testcar= new Car(str_gen(17),str_gen(5),str_gen(7));
        CarService cs = new CarService(car);
        testcar.setId(1L);
        cs.addEntity(testcar);
        Car testcar1= new Car(str_gen(17),str_gen(5),str_gen(7));
        testcar1.setId(2L);
        cs.addEntity(testcar1);
        Assert.assertEquals(Long.valueOf(cs.getLastId()),Long.valueOf(2L));
    }

    @Test
    public void getVINByID() {
        InMemoryRepository car = new InMemoryRepository(new CarValidator());
        String vin = str_gen(17);
        Car testcar= new Car(vin,str_gen(5),str_gen(7));
        CarService cs = new CarService(car);
        testcar.setId(1L);
        cs.addEntity(testcar);
        Assert.assertTrue(cs.getVINByID(1L).equals(vin));
    }
}