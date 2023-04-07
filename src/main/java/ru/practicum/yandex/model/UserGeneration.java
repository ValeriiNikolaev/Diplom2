package ru.practicum.yandex.model;

import com.github.javafaker.Faker;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.Locale;


public class UserGeneration {

    private final static Faker faker = new Faker(Locale.forLanguageTag("ru"));

    public static String randomName() {
        return faker.name().fullName();
    }

    public static String randomEmail() {
        return String.format("%s@%s.ru", RandomStringUtils.randomAlphanumeric(5, 10), RandomStringUtils.randomAlphanumeric(5, 10)).toLowerCase();
    }

    public static String randomPassword() {
        return RandomStringUtils.randomAlphanumeric(8, 16) + "$";
    }

}
