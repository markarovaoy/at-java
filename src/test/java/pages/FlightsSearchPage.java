package pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class FlightsSearchPage {
    SelenideElement
            fromCity = $x("//*[@id='departureCity']"),
            toCity = $("#arrivalCity"),
            flightDate = $("#departureDate"),
            findButton = $x("//button[.='Найти']"),
            message = $("#searchMessage");


    String errorMessageEmptyDate = "Пожалуйста, укажите дату вылета.";

    @Step ("Поиск рейса")
    public void findFlights(String from, String to, String date) {
        fromCity.selectOption(from);
        toCity.selectOption(to);
        flightDate.setValue(date);
        findButton.click();
    }
    @Step ("Проверка сообщения о незаданной дате")
    public void verifyEmptyDate(){
        //Проверяет, что есть сообщение о том, что дата не задана
        message.shouldHave(text(errorMessageEmptyDate));
    }

}
