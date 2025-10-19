import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import pages.FlightsFoundList;
import pages.FlightsLoginPage;
import pages.FlightsSearchPage;
import pages.RegistrationPage;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

@TestMethodOrder(MethodOrderer.DisplayName.class)
public class POMFlightsTests {
    @BeforeAll
    static void beforeAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }
    @BeforeEach
    void setUp() {
        open("https://slqa.ru/cases/DeepSeekFlights/");
        getWebDriver().manage().window().maximize();
    }

    //Тест-кейсы

    @Test
    @DisplayName("POM-01. Неуспешный логин")
    void test01() {
        FlightsLoginPage login_page = new FlightsLoginPage();
        login_page.login("standard_user", "wrong_stand_pass1");
        login_page.verify_wrong_username_or_password();
    }

    @Test
    @DisplayName("POM-02. Не задана дата вылета")
    void test02() {
        FlightsLoginPage loginPage = new FlightsLoginPage();
        loginPage.login("standard_user", "stand_pass1");
        loginPage.verify_successful_login();
        FlightsSearchPage searchPage = new FlightsSearchPage();
        searchPage.findFlights("Москва","Париж", "");
        searchPage.verifyEmptyDate();
        loginPage.logout();
    }

    @Test
    @DisplayName("POM-03. Не найдены рейсы")
    void test03() {
        FlightsLoginPage loginPage = new FlightsLoginPage();
        loginPage.login("standard_user", "stand_pass1");
        loginPage.verify_successful_login();
        FlightsSearchPage searchPage = new FlightsSearchPage();
        searchPage.findFlights("Казань","Париж", "17.11.2025");
        //Добавить проверку, что не найдены рейсы
        FlightsFoundList foundList = new FlightsFoundList();
        foundList.verifyUnsuccessfullSearch();
        loginPage.logout();
    }

    @Test
    @DisplayName("POM-04. Регистрация - некорректно заполнен номер паспорта")
    void test04() {
        FlightsLoginPage loginPage = new FlightsLoginPage();
        loginPage.login("standard_user", "stand_pass1");
        loginPage.verify_successful_login();
        FlightsSearchPage searchPage = new FlightsSearchPage();
        searchPage.findFlights("Москва","Нью-Йорк", "17.11.2025");

        FlightsFoundList flightsList = new FlightsFoundList();
        flightsList.verifySuccessfullSearch();
        flightsList.chooseFirstFlight();
        //Добавить класс RegistrationPage и использовать его
        RegistrationPage registrationPage = new RegistrationPage();
        registrationPage.registrationOnFlight(null, "Левый номер", null, null);
        registrationPage.wrongPassport();
        loginPage.logout();
    }
    @Test
    @DisplayName("POM-05. Успешная регистрация")
    void test05() {
        FlightsLoginPage loginPage = new FlightsLoginPage();
        loginPage.login("standard_user", "stand_pass1");
        loginPage.verify_successful_login();
        FlightsSearchPage searchPage = new FlightsSearchPage();
        searchPage.findFlights("Москва","Нью-Йорк", "17.11.2025");

        FlightsFoundList flightsList = new FlightsFoundList();
        flightsList.verifySuccessfullSearch();
        flightsList.chooseFirstFlight();
        RegistrationPage registrationPage = new RegistrationPage();
        registrationPage.registrationOnFlight(null, null, null, null);
        registrationPage.successfulRegistration();
        loginPage.logout();
    }
    @Test
    @DisplayName("POM-06. Вход заблокированным пользователем")
    void test06() {
        FlightsLoginPage login_page = new FlightsLoginPage();
        login_page.login("locked_out_user", "lock_pass2");
        login_page.verify_locked_user();
    }

    @Test
    @DisplayName("POM-07. Воврат со страницы регистрации + новый поиск и регистрация")
    void test07() {
        FlightsLoginPage loginPage = new FlightsLoginPage();
        loginPage.login("standard_user", "stand_pass1");
        loginPage.verify_successful_login();
        FlightsSearchPage searchPage = new FlightsSearchPage();
        searchPage.findFlights("Москва","Нью-Йорк", "17.11.2025");
        FlightsFoundList flightsList = new FlightsFoundList();
        flightsList.verifySuccessfullSearch();
        flightsList.chooseFirstFlight();
        RegistrationPage registrationPage = new RegistrationPage();
        registrationPage.returnFoundList();
        flightsList.verifySuccessfullSearch();
        flightsList.newSearch();
        searchPage.findFlights("Санкт-Петербург","Нью-Йорк", "17.11.2025");
        flightsList.verifySuccessfullSearch();
        flightsList.chooseFirstFlight();
        registrationPage.registrationOnFlight("петров", "12345", "petrov@mail.ru", "322223");
        registrationPage.successfulRegistration();
        loginPage.logout();
    }
    @Test
    @DisplayName("POM-08. Отправка формы регистрации с пустыми полями")
    void test08() {
        FlightsLoginPage loginPage = new FlightsLoginPage();
        loginPage.login("standard_user", "stand_pass1");
        loginPage.verify_successful_login();
        FlightsSearchPage searchPage = new FlightsSearchPage();
        searchPage.findFlights("Москва","Нью-Йорк", "17.11.2025");

        FlightsFoundList flightsList = new FlightsFoundList();
        flightsList.verifySuccessfullSearch();
        flightsList.chooseFirstFlight();
        RegistrationPage registrationPage = new RegistrationPage();
        registrationPage.registrationOnFlight("", "", "", "");
        registrationPage.emptyField();
    }

}


