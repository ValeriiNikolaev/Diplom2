package ru.practicum.yandex.model.user;

import com.github.javafaker.Faker;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.Locale;
import java.util.Random;


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

    public User newUser() {
        return new User(randomName(), randomEmail(), randomPassword());
    }

    public static String getRandomString(int length) {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder result = new StringBuilder();
        Random rnd = new Random();
        while (result.length() < length) {
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            result.append(SALTCHARS.charAt(index));
        }
        return result.toString();
    }

}
