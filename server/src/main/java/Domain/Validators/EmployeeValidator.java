package Domain.Validators;

import Domain.Employee;

public class EmployeeValidator implements Validator<Employee>{
    @Override
    public void validate(Employee entity) throws ValidatorException {

    }

    @Override
    public Class<Employee> getType() {
        return Employee.class;
    }
}
