package Domain.Validators;

import Domain.Client;

/**
 * Client-specific implementation of the
 */
public class ClientValidator implements Validator<Client>{

    /**
     * Validates the given Client entity.
     * @param entity
     *      Client entity, must not be null.
     * @throws ValidatorException
     *      if the given entity is not valid.
     */
    @Override
    public void validate(Client entity) throws ValidatorException {
        if(entity.getCnp().length()!=13){
            throw new ValidatorException("CNP does not have the required number of characters!");
        }
    }

    @Override
    public Class<Client> getType() {
        return Client.class;
    }
}
