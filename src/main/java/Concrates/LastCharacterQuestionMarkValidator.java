package Concrates;

import Abstracts.BaseValidator;
import Abstracts.Validator;

public class LastCharacterQuestionMarkValidator extends BaseValidator<String> {


    public LastCharacterQuestionMarkValidator(Validator<String> validator) {
        super(validator);
    }

    @Override
    public boolean validate(String password) {

        if((password.charAt(password.length() - 1) == '?')){
            validationResult = true;
        }else{
            System.out.println("Şifrenin Son Karakteri '?' Olmalı !" );
        }
        if(validator != null){
           return validator.validate(password) && validationResult;
        }
        return validationResult;
    }
}
