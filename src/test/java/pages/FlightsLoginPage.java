package pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
public class FlightsLoginPage {
    SelenideElement Username, Password, LoginButton, Message, LogoutButton;
    String successMessage, errorMessage, lockMessage;
    public FlightsLoginPage() {
        Username = $("#username");
        Password = $("#password");
        LoginButton = $("#loginButton");
        LogoutButton = $("#logoutButton");
        Message = $("#message");

        successMessage = "Вход в систему выполнен успешно! Загрузка...";
        errorMessage = "Неверное имя пользователя или пароль.";
        lockMessage = "Пользователь заблокирован.";
    }

    @Step("Вход в систему")
    public void login(String username, String password) {
        Username.setValue(username);
        Password.setValue(password);
        LoginButton.click();
    }
    @Step("Проверка успешного логина")
    public void verify_successful_login() {
        Message.shouldHave(text(successMessage));
    }
    @Step("Проверка неуспешного логина")
    public void verify_wrong_username_or_password() {
        Message.shouldHave(text(errorMessage));
    }
    @Step("Проверка входа заблокированным пользователем")
    public void verify_locked_user() {
        Message.shouldHave(text(lockMessage));
    }

    @Step("Выход из системы")
    public void logout() {
        LogoutButton.click();
    }
}


