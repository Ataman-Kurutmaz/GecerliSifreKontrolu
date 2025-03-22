package Concrates;

import Abstracts.BaseValidator;
import Abstracts.Validator;

public class LengthValidator extends BaseValidator<String> {


    public LengthValidator(Validator<String> validation) {
        super(validation);
    }

    @Override
    public boolean validate(String password) {

        if((password.length() > 8)){
            validationResult = true;
        }else{
            System.out.println("Şifre 8 Karakterden Büyük Olmalı");
        }
        if(validator != null){
           return validator.validate(password) && validationResult;
        }
        return validationResult;
    }
}
