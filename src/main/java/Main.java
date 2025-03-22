import Concrates.FirstCharacterUpperCaseValidator;
import Concrates.LastCharacterQuestionMarkValidator;
import Concrates.LengthValidator;
import Concrates.SpaceCharacterValidator;

import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        String passwordInput = "";

        do{
           System.out.print("Lutfen Şifer Giriniz: ");
           passwordInput = scanner.nextLine();
        }while (!new FirstCharacterUpperCaseValidator(new SpaceCharacterValidator(new LengthValidator(new LastCharacterQuestionMarkValidator(null)))).validate(passwordInput));
    }
}
