package Domain.Validators;

import Domain.Car;

/**
 * Car-specific implementation of the Validator interface.
 */
public class CarValidator implements Validator<Car>{

    /**
     * Validates the given Car entity.
     * @param entity
     *      Car entity, must not be null.
     * @throws ValidatorException
     *      if the given entity is not valid.
     */
    @Override
    public void validate(Car entity) throws ValidatorException {
        if(entity.getVINNumber().length()!=17){
            throw new ValidatorException("VIN Number does not have the required number of characters!");
        }
    }

    @Override
    public Class<Car> getType() {
        return Car.class;
    }
}
