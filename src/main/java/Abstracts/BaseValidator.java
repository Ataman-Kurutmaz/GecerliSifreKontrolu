package Abstracts;

public abstract class BaseValidator implements Validator{

    protected Validator validator;
    protected  boolean validationResult = false;

    public BaseValidator(Validator validator){
        this.validator = validator;
    }
}
