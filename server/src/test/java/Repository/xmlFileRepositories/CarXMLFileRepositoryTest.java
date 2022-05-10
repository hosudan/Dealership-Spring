package Repository.xmlFileRepositories;


import Domain.Car;
import Domain.Validators.CarValidator;
import org.junit.Assert;
import org.junit.Test;

import java.util.Optional;
import java.util.Random;
import java.util.stream.StreamSupport;

public class CarXMLFileRepositoryTest {
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
    public void findAll() {
        CarXMLFileRepository repo = new CarXMLFileRepository(new CarValidator());
        Iterable<Car> list = repo.findAll();
        Assert.assertTrue(StreamSupport.stream(list.spliterator(), false).count()>=0 && StreamSupport.stream(list.spliterator(), false).allMatch(c->c instanceof Car));
    }

    @Test
    public void save() {
        CarXMLFileRepository repo = new CarXMLFileRepository(new CarValidator());
        Iterable<Car> list = repo.findAll();
        Long current = StreamSupport.stream(list.spliterator(), false).count();
        current++;
        Car testcar= new Car(str_gen(17),str_gen(5),str_gen(7));
        testcar.setId(-1L);
        repo.save(testcar);
        Assert.assertEquals(Long.valueOf(StreamSupport.stream(repo.findAll().spliterator(), false).count()),Long.valueOf(current));
        repo.delete(-1L);
    }

    @Test
    public void findOne() {
        CarXMLFileRepository repo = new CarXMLFileRepository(new CarValidator());
        String testcarvin = str_gen(17);
        Car testcar= new Car(testcarvin,str_gen(5),str_gen(7));
        testcar.setId(-3L);
        repo.save(testcar);
        Assert.assertTrue(repo.findOne(-2L).equals(Optional.empty()));
        Car c =(Car) repo.findOne(-3L).get();
        Assert.assertEquals(c.getVINNumber(),testcarvin);
        repo.delete(-3L);
    }

    @Test
    public void delete() {
        CarXMLFileRepository repo = new CarXMLFileRepository(new CarValidator());
        Iterable<Car> list = repo.findAll();
        Long current = StreamSupport.stream(list.spliterator(), false).count();
        Car testcar= new Car(str_gen(17),str_gen(5),str_gen(7));
        testcar.setId(-4L);
        repo.save(testcar);
        repo.delete(-4L);
        Assert.assertEquals(Long.valueOf(StreamSupport.stream(repo.findAll().spliterator(), false).count()),Long.valueOf(current));
    }

    @Test
    public void update() {
        CarXMLFileRepository repo = new CarXMLFileRepository(new CarValidator());
        String testcarvin = str_gen(17);
        String model = str_gen(5);
        String manufacturer = str_gen(7);
        Car testcar= new Car(testcarvin,model,manufacturer);
        testcar.setId(-5L);
        repo.save(testcar);
        testcar.setVINNumber("55555555555555555");
        repo.update(testcar);
        Car c =(Car) repo.findOne(-5L).get();
        Assert.assertEquals(c.getVINNumber(),"55555555555555555");
        repo.delete(-5L);
    }
}