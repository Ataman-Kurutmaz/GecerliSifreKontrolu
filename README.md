## ✅ **1. `Validator` Arayüzü (interface)**

```java
public interface Validator<T> {
    boolean validate(T arg);
}
```

Bu arayüz, tüm doğrulayıcıların (validator’ların) uyması gereken ortak kuralları tanımlar:

- `validate(T arg)` → Verilen girdi (`arg`) geçerli mi diye kontrol eder.
- `T` tipi generic’tir, yani sadece `String` değil, diğer tipler için de kullanılabilir hale gelir.

---

## 📦 **2. `BaseValidator` (Soyut Sınıf)**

```java
public abstract class BaseValidator<T> implements Validator<T> {
    protected Validator<T> validator;
    protected boolean validationResult;

    public BaseValidator(Validator<T> validator) {
        this.validator = validator;
    }
}
```

Bu sınıf, tüm doğrulayıcılar için ortak temel özellikleri barındırır:

- `validator`: Zincirleme yapıdaki bir sonraki doğrulayıcıyı gösterir.
- `validationResult`: Bu validator’ın doğrulamasının sonucu (true/false).

> Yani, bir doğrulayıcı çalıştıktan sonra zincirin devam etmesini sağlar.

---

## 🧱 **3. Somut Validator Sınıfları (Kuralları Gerçekleyen Sınıflar)**

### 🔡 FirstCharacterUpperCaseValidator

```java
if(Character.isUpperCase(password.charAt(0)))
```

- Şifrenin ilk karakterinin büyük harf olup olmadığını kontrol eder.
- Değilse: `System.out.println("Şifrenin Ilk Karakteri Büyük Harfle Başlamalı !");`

---

### 🔠 SpaceCharacterValidator

```java
if (password.contains(" "))
```

- Şifre boşluk karakteri içeriyor mu diye kontrol eder.
- İçermiyorsa: `System.out.println("Şifre Boşluk Karakteri Içermeli !");`

---

### 🔢 LengthValidator

```java
if((password.length() > 8))
```

- Şifrenin uzunluğu 8’den büyük mü?
- Küçükse: `System.out.println("Şifre 8 Karakterden Büyük Olmalı");`

---

### ❓ LastCharacterQuestionMarkValidator

```java
if(password.charAt(password.length() - 1) == '?')
```

- Şifrenin son karakteri `?` mi?
- Değilse: `System.out.println("Şifrenin Son Karakteri '?' Olmalı !");`

---

## 🔗 Zincirleme Yapı Nasıl İşliyor?

Her doğrulayıcıdan sonra şu kod var:

```java
if (validator != null) {
    return validator.validate(password) && validationResult;
}
return validationResult;
```

Bu ne demek?

- Eğer başka bir validator (bir sonraki kural) varsa → onu da çalıştır.
- Zincirdeki tüm validator’lar **true** dönüyorsa → şifre geçerli.
- Herhangi biri başarısızsa (false dönerse) → zincir orada kırılır, şifre geçersiz olur.

---

## 🧪 Kullanım Örneği

Main sınıfında bu validator zinciri şöyle oluşturulabilir:

```java
Validator validator = new FirstCharacterUpperCaseValidator(
                          new LengthValidator(
                              new SpaceCharacterValidator(
                                  new LastCharacterQuestionMarkValidator(null))));

String password = "Example Password?";
boolean result = validator.validate(password);

System.out.println("Şifre geçerli mi? " + result);
```

> Zincirde önce ilk harf büyük mü, sonra uzunluk, sonra boşluk var mı, en sonunda da `?` ile bitiyor mu diye bakılır.

---

## 🎯 Özetle

Bu yapı sayesinde:

| **Avantaj**                     | **Açıklama**                                                                 |
|----------------------------------|------------------------------------------------------------------------------|
| **Modülerlik**                   | Her kural ayrı bir sınıfta, bağımsız.                                       |
| **Zincirleme (Chain of Responsibility)** | Kurallar sırayla uygulanıyor.                                               |
| **Genişletilebilirlik**         | Yeni kural eklemek için sadece bir sınıf yazmak yeterli.                    |
| **Kolay okunabilirlik**         | Her validator sadece tek bir iş yapıyor.                                    |
| **Yeniden kullanılabilirlik**   | Aynı validator farklı projelerde tekrar kullanılabilir.                     |

---

İstersen bu yapıyı bir `main()` sınıfıyla test edelim ya da daha fazla kural ekleyelim. Yardımcı olayım mı? 😊