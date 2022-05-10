package Service;


import Domain.Client;
import Domain.Validators.ClientValidator;
import Repository.InMemoryRepository;
import org.junit.Assert;
import org.junit.Test;

import java.util.Random;

public class ClientServiceTest {

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
    public void getCnpByID() {
        InMemoryRepository repo = new InMemoryRepository(new ClientValidator());
        String cnp = str_gen(13);
        Client testclient= new Client(cnp,str_gen(5),str_gen(5),10);
        ClientService cs = new ClientService(repo);
        testclient.setId(1L);
        cs.addEntity(testclient);
        Assert.assertTrue(cs.getCnpByID(1L).equals(cnp));
    }
}