package ru.netology.data;

import com.github.javafaker.Faker;
import lombok.Value;
import org.openqa.selenium.Keys;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;

import static com.codeborne.selenide.Selenide.$;

public class DataGenerator {
    private DataGenerator() {
    }

    public static String generateDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    public static void clearDate() {
        $("[data-test-id=\"date\"] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
    }

    public static String generateCity() {
        String[] randomCity = new String[]{"Нижний Новгород", "Великий Новгород", "Новосибирск", "Москва", "Санкт-Петербург",
                "Краснодар", "Калининград", "Рязань", "Омск", "Оренбург", "Орёл", "Пенза", "Псков",
                "Ростов-на-Дону", "Рязань", "Самара", "Саратов", "Южно-Сахалинск", "Екатеринбург", "Смоленск",
                "Тамбов", "Тверь", "Томск", "Тула", "Тюмень", "Ульяновск", "Челябинск"};
        return randomCity[new Random().nextInt(randomCity.length)];
    }

    public static String generateName(String locale) {
        return new Faker(new Locale(locale)).name().fullName();
    }

    public static String generatePhone(String locale) {
        return new Faker(new Locale(locale)).phoneNumber().phoneNumber();
    }

    public static class Registration {
        private Registration() {
        }

        public static UserInfo generateUser(String value) {
            return new UserInfo(generateCity(), generateName(value), generatePhone(value));
        }
    }

    @Value
    public static class UserInfo {
        String city;
        String name;
        String phone;
    }
}
