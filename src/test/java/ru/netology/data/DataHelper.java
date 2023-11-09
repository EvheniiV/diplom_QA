package ru.netology.data;

import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataHelper {

    private static final Faker faker = new Faker(new Locale("en"));

    public static String getApprovedCardNumber() {
        return "4444444444444441";
    }

    public static String getApprovedCardStatus() {
        return "APPROVED";
    }

    public static String getDeclinedCardNumber() {
        return "4444444444444442";
    }

    public static String getDeclinedCardStatus() {
        return "DECLINED";
    }

    public static String getRandomCardNumber() {
        return faker.business().creditCardNumber();
    }

    public static String getCardNumberWith15Digits() {
        return "4444 4444 4444 444";
    }

    public static String getEmptyString() {
        return "";
    }

    public static String getValidMonth() {
        String validMonth = LocalDate.now().format(DateTimeFormatter.ofPattern("MM"));
        return validMonth;
    }

    public static String getOverLimitMonth() {
        return "13";
    }

    public static String getLessThenLimitMonth() {
        return "00";
    }

    public static String getValidYear() {
        String validYear = LocalDate.now().format(DateTimeFormatter.ofPattern("yy"));
        return validYear;
    }

    public static String getLessThenLimitYear() {
        String pastYear = LocalDate.now().minusYears(1).format(DateTimeFormatter.ofPattern("yy"));
        return pastYear;
    }

    public static String getOverLimitYear() {
        String futureYear = LocalDate.now().plusYears(10).format(DateTimeFormatter.ofPattern("yy"));
        return futureYear;
    }

    public static String getValidOwner() {
        return faker.name().fullName();
    }

    public static String getCyrillicDataOwner() {
        return "Петр Иванов";
    }

    public static String getOwnerWithDigits() {
        return "123456789";
    }

    public static String getOwnerWithSpecialChars() {
        return "!№;%:?*()-+";
    }

    public static String getValidCode() {
        return faker.number().digits(3);
    }

    public static String getInvalidFormatCode() {
        return faker.number().digits(2);
    }
}
