package Domain.Validators;

import Domain.DriveTest;

/**
 * DriveTest-specific implementation of the Validator interface.
 */
public class DriveTestValidator implements Validator<DriveTest>{

    /**
     * Validates the given DriveTest entity.
     * @param entity
     *      DriveTest entity, must not be null.
     * @throws ValidatorException
     *      if the given entity is not valid.
     */
    @Override
    public void validate(DriveTest entity) throws ValidatorException {
        if(entity.getRating()>10 || entity.getRating()<0){
            throw new ValidatorException("Rating must be between 0 and 10!");
        }
    }

    @Override
    public Class<DriveTest> getType() {
        return DriveTest.class;
    }
}
