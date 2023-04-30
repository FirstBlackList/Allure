package ru.netology.test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.Epic;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.data.DataGenerator;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static ru.netology.data.DataGenerator.clearDate;

class DeliveryTest {

    private static final DataGenerator.UserInfo user = DataGenerator.Registration.generateUser("RU");
    private static final String dateMeeting = DataGenerator.generateDate(8);
    private static final String newDateMeeting = DataGenerator.generateDate(10);

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }


    @Test
    @Epic("Allure examples")
    @DisplayName("Should Date of the meeting has been changed")
    public void shouldDateOfTheMeetingHasBeenChanged() {
        $("[data-test-id=\"city\"] input").setValue(user.getCity());
        clearDate();
        $("[data-test-id=\"date\"] input").setValue(dateMeeting);
        $("[data-test-id=\"name\"] input").setValue(user.getName());
        $("[data-test-id=\"phone\"] input").setValue(user.getPhone());
        $("[data-test-id=\"agreement\"]").click();
        $(byText("Запланировать")).click();
        $("[data-test-id=\"success-notification\"]")
                .shouldHave(Condition.text
                        ("Успешно! Встреча успешно запланирована на " + dateMeeting), Duration.ofSeconds(12));
        clearDate();
        $("[data-test-id=\"date\"] input").setValue(newDateMeeting);
        $(byText("Запланировать")).click();
        $("[data-test-id=\"replan-notification\"]")
                .shouldHave(Condition.text
                        ("Необходимо подтверждение У вас уже запланирована встреча на другую дату. Перепланировать?"));
        $(byText("Перепланировать")).click();
        $("[data-test-id=\"success-notification\"]")
                .shouldHave(Condition.text
                        ("Успешно! Встреча успешно запланирована на " + newDateMeeting), Duration.ofSeconds(12));
    }

    @Test
    @Epic("Allure examples")
    @DisplayName("Should Name not selected")
    public void shouldNameNotSelected() {
        $("[data-test-id=\"city\"] input").setValue(user.getCity());
        clearDate();
        $("[data-test-id=\"date\"] input").setValue(dateMeeting);
        $("[data-test-id=\"phone\"] input").setValue(user.getPhone());
        $("[data-test-id=\"agreement\"]").click();
        $(byText("Запланировать")).click();
        $("[data-test-id=\"name\"] .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    @Epic("Allure examples")
    @DisplayName("Should Phone not selected")
    public void shouldPhoneNotSelected() {
        $("[data-test-id=\"city\"] input").setValue(user.getCity());
        clearDate();
        $("[data-test-id=\"date\"] input").setValue(dateMeeting);
        $("[data-test-id=\"name\"] input").setValue(user.getName());
        $("[data-test-id=\"agreement\"]").click();
        $(byText("Запланировать")).click();
        $("[data-test-id=\"phone\"] .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    @Epic("Allure examples")
    @DisplayName("Should delivery to the selected city is not available")
    public void shouldDeliveryToTheSelectedCityIsNotAvailable() {
        $("[data-test-id=\"city\"] input").setValue("Moscow");
        clearDate();
        $("[data-test-id=\"date\"] input").setValue(dateMeeting);
        $("[data-test-id=\"name\"] input").setValue(user.getName());
        $("[data-test-id=\"phone\"] input").setValue(user.getPhone());
        $("[data-test-id=\"agreement\"]").click();
        $(byText("Запланировать")).click();
        $("[data-test-id=\"city\"] .input__sub").shouldHave(exactText("Доставка в выбранный город недоступна"));
    }

    @Test
    @Epic("Allure examples")
    @DisplayName("Should Delivery to the selected city is not available_plusPoint")
    public void shouldDeliveryToTheSelectedCityIsNotAvailable_plusPoint() {
        $("[data-test-id=\"city\"] input").setValue(user.getCity() + ".");
        clearDate();
        $("[data-test-id=\"date\"] input").setValue(dateMeeting);
        $("[data-test-id=\"name\"] input").setValue(user.getName());
        $("[data-test-id=\"phone\"] input").setValue(user.getPhone());
        $("[data-test-id=\"agreement\"]").click();
        $(byText("Запланировать")).click();
        $("[data-test-id=\"city\"] .input__sub").shouldHave(exactText("Доставка в выбранный город недоступна"));
    }

    @Test
    @Epic("Allure examples")
    @DisplayName("Should Name incorrect")
    void shouldNameIncorrect() {

        $("[data-test-id=\"city\"] input").setValue(user.getCity());
        clearDate();
        $("[data-test-id=\"date\"] input").setValue(dateMeeting);
        $("[data-test-id=\"name\"] input").setValue(user.getName() + " Yes");
        $("[data-test-id=\"phone\"] input").setValue(user.getPhone());
        $("[data-test-id=\"agreement\"]").click();
        $(byText("Запланировать")).click();
        $("[data-test-id=\"name\"] .input__sub")
                .shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    @Epic("Allure examples")
    @DisplayName("Should Check Box is not pressed")
    void shouldCheckBoxIsNotPressed() {

        $("[data-test-id=\"city\"] input").setValue(user.getCity());
        clearDate();
        $("[data-test-id=\"date\"] input").setValue(dateMeeting);
        $("[data-test-id=\"name\"] input").setValue(user.getName());
        $("[data-test-id=\"phone\"] input").setValue(user.getPhone());
        $(byText("Запланировать")).click();
        $x("//label[contains(@class, 'checkbox')]")
                .shouldBe(visible)
                .shouldHave(cssValue("color", "rgba(255, 92, 92, 1)")
                        , cssClass("input_invalid"), visible);
    }
}
