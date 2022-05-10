package Domain.Validators;

import Domain.Sale;

public class SaleValidator implements Validator<Sale>{
    @Override
    public void validate(Sale entity) throws ValidatorException {

    }

    @Override
    public Class<Sale> getType() {
        return Sale.class;
    }
}
