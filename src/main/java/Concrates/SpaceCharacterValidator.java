package Concrates;

import Abstracts.BaseValidator;
import Abstracts.Validator;

public class SpaceCharacterValidator extends BaseValidator<String> {

    public SpaceCharacterValidator(Validator<String> validator) {
        super(validator);
    }


    @Override
    public boolean validate(String password) {

        if (password.contains(" ")) {
            this.validationResult = true;
        }else{
            System.out.println("Şifre Boşluk Karakteri Içermeli !");
        }
        if (validator != null) {
            return validator.validate(password) && this.validationResult;
        }
        return validationResult;
    }
}
