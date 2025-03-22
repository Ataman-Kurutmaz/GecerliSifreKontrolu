package Concrates;

import Abstracts.BaseValidator;
import Abstracts.Validator;

public class FirstCharacterUpperCaseValidator extends BaseValidator<String> {

    public FirstCharacterUpperCaseValidator(Validator<String> validator) {
        super(validator);
    }

    @Override
    public boolean validate(String password) {

        if(Character.isUpperCase(password.charAt(0))){
            validationResult = true;
        }else {
            System.out.println("Şifrenin Ilk Karakteri Büyük Harfle Başlamalı !");
        }
        if(this.validator != null){
            return validator.validate(password) && validationResult;
        }
        return validationResult;
    }
}
