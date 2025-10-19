package pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.Alert;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
public class RegistrationPage {
    SelenideElement
            Name = $("#passengerName"),
            Passport = $("#passportNumber"),
            Mail = $("#email"),
            Phone = $("#phone"),
            RegButton = $x("//button[text()='Завершить регистрацию']"),
            RegMessage = $("#registrationMessage"),
            BackButton = $x("//button[text()='Вернуться к найденным рейсам']");

    Alert RegAlert;
    String
            alertMessage = "Бронирование завершено",
            paspMessage = "Номер паспорта должен содержать только цифры и пробелы.",
            fieldMessage = "Пожалуйста, заполните все поля.";

    @Step("Регистрация на рейс")
    public void registrationOnFlight(String name, String passport, String email, String phone) {
        if (name != null) Name.setValue(name);
        if (passport != null) Passport.setValue(passport);
        if (email != null) Mail.setValue(email);
        if (phone != null)  Phone.setValue(phone);
        RegButton.click();
    }
    @Step("Проверка успешного завершения регистрации")
    public void successfulRegistration() {
        RegAlert = switchTo().alert();
        assertTrue(RegAlert.getText().contains(alertMessage));
        RegAlert.accept();
    }
    @Step("Проверка сообщения о неверном номере паспорта")
    public void wrongPassport() {
        RegMessage.shouldHave(text(paspMessage));
    }
    @Step("Возврат к найденным рейсам")
    public void returnFoundList() {
        BackButton.click();
    }
    @Step("Проверка сообщения о необходимости заполнить поля")
    public void emptyField() {
        RegMessage.shouldHave(text(fieldMessage));
    }

}

