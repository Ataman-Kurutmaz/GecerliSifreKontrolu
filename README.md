Bu kod, bir şifre doğrulama sistemini uygulamak için **Decorator Design Pattern** kullanmaktadır. Bu desen, şifre doğrulama kurallarını zincirleme bir şekilde bir araya getirir ve her kuralın kendi doğrulama mantığını sağladığı modüler bir yapı oluşturur. Şifreyi geçerli kılmak için birden fazla doğrulama kurallı kontrol edilir.

Aşağıda, her bir sınıfın ve işleyişin detaylı açıklamasını bulabilirsiniz.

### **1. `Validator` Arayüzü:**
```java
public interface Validator {
    boolean validate(String password);
}
```
Bu arayüz, tüm doğrulama işlemlerinin ortak bir `validate` metoduna sahip olmasını sağlar. Her doğrulama sınıfı bu arayüzü implement eder.

### **2. `BaseValidator` Sınıfı (Decorator):**
```java
public abstract class BaseValidator implements Validator {
    protected Validator validator;
    protected boolean validationResult = false;

    public BaseValidator(Validator validator) {
        this.validator = validator;
    }
}
```
`BaseValidator` sınıfı, tüm doğrulama sınıfları için temel sınıftır.
- **`validator`:** Zincirleme dekoratörler arasındaki geçişi sağlar. Bu, bir önceki doğrulamanın sonucunu alarak sonraki doğrulama işleminin yapılmasına olanak tanır.
- **`validationResult`:** Doğrulamanın geçerli olup olmadığını tutar.
- **`BaseValidator` Constructor:** Yeni doğrulama sınıfı (`validator`) eklemek için bir parametre alır. Bu, dekoratörler arasında geçişi sağlar.

### **3. `FirstCharacterUpperCaseValidator` Sınıfı:**
```java
public class FirstCharacterUpperCaseValidator extends BaseValidator {

    public FirstCharacterUpperCaseValidator(Validator validator) {
        super(validator);
    }

    @Override
    public boolean validate(String password) {
        if (Character.isUpperCase(password.charAt(0))) {
            validationResult = true;
        } else {
            System.out.println("Şifrenin Ilk Karakteri Büyük Harfle Başlamalı !");
        }
        if (this.validator != null) {
            return validator.validate(password) && validationResult;
        }
        return validationResult;
    }
}
```
Bu sınıf, şifrenin **ilk karakterinin büyük harf** olup olmadığını kontrol eder. Eğer ilk karakter büyük harfse, `validationResult` doğru (true) olur; aksi takdirde hata mesajı yazdırılır.
- Eğer bu sınıf başka bir doğrulama sınıfına bağlıysa, doğrulama devam eder.
- Eğer geçerli değilse, hata mesajı yazdırılır.

### **4. `LengthValidator` Sınıfı:**
```java
public class LengthValidator extends BaseValidator {

    public LengthValidator(Validator validation) {
        super(validation);
    }

    @Override
    public boolean validate(String password) {
        if (password.length() > 8) {
            validationResult = true;
        } else {
            System.out.println("Şifre 8 Karakterden Büyük Olmalı");
        }
        if (validator != null) {
            return validator.validate(password) && validationResult;
        }
        return validationResult;
    }
}
```
Bu sınıf, şifrenin **uzunluğunun 8 karakterden büyük** olup olmadığını kontrol eder. Eğer şifre uzunluğu yeterliyse, `validationResult` doğru olur; aksi takdirde hata mesajı yazdırılır.

### **5. `LastCharacterQuestionMarkValidator` Sınıfı:**
```java
public class LastCharacterQuestionMarkValidator extends BaseValidator {

    public LastCharacterQuestionMarkValidator(Validator validator) {
        super(validator);
    }

    @Override
    public boolean validate(String password) {
        if (password.charAt(password.length() - 1) == '?') {
            validationResult = true;
        } else {
            System.out.println("Şifrenin Son Karakteri '?' Olmalı !");
        }
        if (validator != null) {
            return validator.validate(password) && validationResult;
        }
        return validationResult;
    }
}
```
Bu sınıf, şifrenin **son karakterinin '?'** olup olmadığını kontrol eder. Eğer son karakter doğruysa, `validationResult` doğru olur; aksi takdirde hata mesajı yazdırılır.

### **6. `SpaceCharacterValidator` Sınıfı:**
```java
public class SpaceCharacterValidator extends BaseValidator {
    public SpaceCharacterValidator(Validator validator) {
        super(validator);
    }

    @Override
    public boolean validate(String password) {
        if (password.contains(" ")) {
            this.validationResult = true;
        } else {
            System.out.println("Şifre Boşluk Karakteri Içermeli !");
        }
        if (validator != null) {
            return validator.validate(password) && this.validationResult;
        }
        return validationResult;
    }
}
```
Bu sınıf, şifrenin **boşluk karakteri** içerip içermediğini kontrol eder. Eğer şifre boşluk içeriyorsa, `validationResult` doğru olur; aksi takdirde hata mesajı yazdırılır.

### **7. `Main` Sınıfı:**
```java
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String passwordInput = "";

        do {
            System.out.print("Lutfen Şifer Giriniz: ");
            passwordInput = scanner.nextLine();
        } while (!new FirstCharacterUpperCaseValidator(
                new SpaceCharacterValidator(
                        new LengthValidator(
                                new LastCharacterQuestionMarkValidator(null)
                        )
                )
        ).validate(passwordInput));
    }
}
```
- Kullanıcıdan şifre alır ve girilen şifreyi **doğrulama kuralları** zinciri ile kontrol eder.
- **Zincirleme Doğrulama:** `FirstCharacterUpperCaseValidator` ile başlayıp, sırasıyla `SpaceCharacterValidator`, `LengthValidator`, ve `LastCharacterQuestionMarkValidator` sınıfları geçerli bir şifre olup olmadığını kontrol eder. Eğer tüm doğrulamalar geçerse, şifre geçerli kabul edilir ve döngü sona erer.

### **Kullanılan Tasarım Deseni:**
- **Decorator Design Pattern:** Her doğrulama sınıfı, bir önceki doğrulama işlemini "sarar" ve yeni bir doğrulama kuralı ekler. Bu, daha modüler bir yapı ve genişletilebilirlik sağlar.

Bu kodda **Decorator Design Pattern** ve **Chain of Responsibility Pattern** tasarım desenleri birlikte kullanılmıştır. Aşağıda her bir tasarım deseninin nasıl işlediğini detaylı bir şekilde açıklayacağım.

### 1. **Decorator Design Pattern (Dekoratör Tasarım Deseni)**

#### **Tanım:**
Decorator Tasarım Deseni, bir nesnenin davranışını dinamik olarak değiştirmeyi sağlar. Bu desen, bir nesnenin üzerine yeni işlevler eklerken, mevcut nesnenin yapısını değiştirmeden onu genişletmeyi amaçlar. Bu desende, bir nesne (örneğin bir `Validator`) başka bir nesne ile sarmalanır (wrap edilir), böylece ek işlevsellik eklenir.

#### **Kodda Uygulama:**
- **`BaseValidator` Sınıfı:** Tüm doğrulama sınıflarının temel sınıfıdır. `BaseValidator` sınıfı, dekoratör deseni için temel işlevsellik sağlar. Bu sınıf, bir `Validator` nesnesini parametre olarak alır ve doğrulama mantığının sırasıyla uygulanabilmesi için `validator` nesnesine sahip olur.

- **`FirstCharacterUpperCaseValidator`, `LengthValidator`, `LastCharacterQuestionMarkValidator`, `SpaceCharacterValidator` Sınıfları:** Bu sınıflar, `BaseValidator` sınıfını extend eder (kalıtım alır) ve doğrulama kurallarını tek tek uygular. Her bir sınıf, bir önceki doğrulama sınıfını "sararak" kendi doğrulama kuralını ekler. Örneğin, `FirstCharacterUpperCaseValidator` sınıfı, `LengthValidator` sınıfının doğrulama sonucunu kontrol eder, sonra kendi kuralını ekler.

- **Zincirleme:** `Main` sınıfında, doğrulama kuralları birbirine zincirlenir. Yani, her doğrulama sınıfı, kendisinden önceki doğrulama işlemini alır ve doğrulama işlemini devam ettirir.

**Örnek:**
```java
new FirstCharacterUpperCaseValidator(
    new SpaceCharacterValidator(
        new LengthValidator(
            new LastCharacterQuestionMarkValidator(null)
        )
    )
)
```
Bu kodda, doğrulama kuralları birbirine sarılarak uygulanır. Bu, **Decorator Design Pattern**’in bir örneğidir.

### 2. **Chain of Responsibility Pattern (Sorumluluk Zinciri Tasarım Deseni)**

#### **Tanım:**
Chain of Responsibility (Sorumluluk Zinciri) deseni, bir nesne zincirindeki nesnelerden birinin, bir isteği işleme sorumluluğunu üstlenmesini sağlar. Zincir üzerindeki her nesne, isteği işleyebilir ya da bir sonrakine devredebilir. Bu, istemciye bir dizi işleme noktası ile isteği yönlendirme esnekliği sağlar.

#### **Kodda Uygulama:**
- Her doğrulama sınıfı, bir önceki doğrulamanın doğrulama sonucunu alır ve kendi doğrulama mantığını uygular. Eğer doğrulama geçerli değilse, hata mesajı yazdırılır ve bir sonraki doğrulama aşamasına geçilir.

- **Sorumluluk Zinciri:** Şifre doğrulama işlemi, her bir doğrulama sınıfında bir sorumluluk gibi ele alınır. Her sınıf, bir sonraki doğrulama sınıfına geçmeden önce şifrenin geçerli olup olmadığını kontrol eder. Bu, zincirleme bir iş akışı oluşturur ve her doğrulama sınıfı yalnızca kendi sorumluluğunu üstlenir.

**Örnek:**
Şifre ilk olarak `FirstCharacterUpperCaseValidator` ile kontrol edilir, eğer geçerse zincir devam eder ve şifre `SpaceCharacterValidator`'a, ardından `LengthValidator`'a ve son olarak `LastCharacterQuestionMarkValidator`'a iletilir.

```java
new FirstCharacterUpperCaseValidator(
    new SpaceCharacterValidator(
        new LengthValidator(
            new LastCharacterQuestionMarkValidator(null)
        )
    )
)
```

Her doğrulama sınıfı, kendisinden önceki doğrulamanın sonucunu alır ve doğrulama sonucunu kontrol eder. Sonuç doğruysa bir sonraki doğrulamaya geçilir, yanlışsa işlem sonlanır.

### **Kodun Genel Yapısına İlişkin Diğer Tasarım Deseni (Abstraction/Encapsulation):**

Ayrıca, **Abstraction (Soyutlama)** ve **Encapsulation (Kapsülleme)** tasarım ilkeleri de kullanılmıştır:
- Her doğrulama sınıfı, doğrulama mantığını soyutlayarak ve sorumluluğunu kapsüller (encapsulate).
- Her sınıf yalnızca ilgili doğrulama işlevine odaklanır ve bu doğrulama mantığı `BaseValidator` sınıfı aracılığıyla soyutlanır.

---

### **Sonuç:**
Bu kodda esas olarak **Decorator Design Pattern** ve **Chain of Responsibility Pattern** tasarım desenleri kullanılmıştır:

1. **Decorator Design Pattern:** Şifre doğrulama kuralları, birbirine sarılarak (wrap) zincirleme doğrulama işlemleri oluşturur.
2. **Chain of Responsibility Pattern:** Her doğrulama sınıfı, doğrulama sürecinde sorumluluğu üstlenir ve şifreyi sırayla kontrol eder, gereksiz yere her bir kuralın doğrulama işlemini sırasıyla yapar.