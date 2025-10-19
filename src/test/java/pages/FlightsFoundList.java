package pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FlightsFoundList {
    SelenideElement
            firstRegButton = $x("//button[@class='register-btn']"),
            flightsCount = $x("//div[@id='flightsCount']"),
            flightsTableNot = $x("//table[@id='flightsTable']")
                    .find(By.xpath(".//td[@colspan='7']")),
            newSearch = $x("//button[@class='new-search-btn']");

    String
            errorMessageNoFlights = "Рейсов по вашему запросу не найдено.",
            nullMessageFlights = "Найдено рейсов: 0";

//    ElementsCollection
//        flightsList = $$x("//table[@id='flightsContainer']/tbody/tr/td");

    @Step("Выбор первого найденного рейса")
    public void chooseFirstFlight() {
        firstRegButton.click();
    }

    @Step("Проверка успешного поиска рейсов")
    public void verifySuccessfullSearch() {
        flightsCount.shouldNotHave(text(nullMessageFlights));
        //assertEquals(7, flightsList.size());
        firstRegButton.shouldBe(visible);
    }
    @Step("Проверка неуспешного поиска рейсов")
    public void verifyUnsuccessfullSearch() {
        flightsCount.shouldHave(text(nullMessageFlights));
        flightsTableNot.shouldHave(text(errorMessageNoFlights));
    }
    @Step("Переход к новому поиску")
    public void newSearch() {
        newSearch.click();
    }

}

