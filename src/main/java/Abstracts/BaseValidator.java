package Abstracts;

public abstract class BaseValidator<T> implements Validator<T>{

    protected Validator<T> validator;
    protected  boolean validationResult;

    public BaseValidator(Validator<T> validator){
        this.validator = validator;
    }
}
