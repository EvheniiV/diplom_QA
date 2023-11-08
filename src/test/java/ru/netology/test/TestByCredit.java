package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.data.DataHelper;
import ru.netology.data.SqlHelper;
import ru.netology.pages.MainPage;
import ru.netology.pages.PaymentFormPageCredit;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestByCredit {

    private PaymentFormPageCredit paymentFormPageCredit;
    private MainPage mainPage;

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @BeforeEach
    public void setUp() {
        mainPage = open("http://localhost:8080/", MainPage.class);
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @AfterEach
    void clearDatabaseTables() {
        open("http://localhost:8080/");
        SqlHelper.clearTables();
    }

    @Test

    public void shouldAllowPurchaseWithApprovedCard() {
        paymentFormPageCredit = mainPage.payWithCreditCard().clear();
        var cardNumber = DataHelper.getApprovedCardNumber();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var cardOwner = DataHelper.getValidOwner();
        var code = DataHelper.getValidCode();
        paymentFormPageCredit.fillForm(cardNumber, month, year, cardOwner, code);
        paymentFormPageCredit.waitForSuccessedNotification();
        assertEquals(DataHelper.getApprovedCardStatus(), SqlHelper.findCreditStatus());
    }

    @Test

    public void shouldAllowPurchaseWithDeclinedCard() {
        paymentFormPageCredit = mainPage.payWithCreditCard().clear();
        var cardNumber = DataHelper.getDeclinedCardNumber();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var cardOwner = DataHelper.getValidOwner();
        var code = DataHelper.getValidCode();
        paymentFormPageCredit.fillForm(cardNumber, month, year, cardOwner, code);
        paymentFormPageCredit.waitForFailedNotification();
        assertEquals(DataHelper.getDeclinedCardStatus(), SqlHelper.findCreditStatus());
    }

    @Test

    public void shouldAllowPurchaseWithEmptyFieldCard() {
        paymentFormPageCredit = mainPage.payWithCreditCard().clear();
        var cardNumber = DataHelper.getEmptyString();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var cardOwner = DataHelper.getValidOwner();
        var code = DataHelper.getValidCode();
        paymentFormPageCredit.fillForm(cardNumber, month, year, cardOwner, code);
        paymentFormPageCredit.waitForWrongFormatMessage();
    }

    @Test

    public void shouldAllowPurchaseWith15DigitsCard() {
        paymentFormPageCredit = mainPage.payWithCreditCard().clear();
        var cardNumber = DataHelper.getCardNumberWith15Digits();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var cardOwner = DataHelper.getValidOwner();
        var code = DataHelper.getValidCode();
        paymentFormPageCredit.fillForm(cardNumber, month, year, cardOwner, code);
        paymentFormPageCredit.waitForWrongFormatMessage();
    }

    @Test

    public void shouldAllowPurchaseWithNonExistingCard() {
        paymentFormPageCredit = mainPage.payWithCreditCard().clear();
        var cardNumber = DataHelper.getRandomCardNumber();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var cardOwner = DataHelper.getValidOwner();
        var code = DataHelper.getValidCode();
        paymentFormPageCredit.fillForm(cardNumber, month, year, cardOwner, code);
        paymentFormPageCredit.waitForFailedNotification();
    }

    @Test

    public void shouldAllowPurchaseWithEmptyFieldMonth() {
        paymentFormPageCredit = mainPage.payWithCreditCard().clear();
        var cardNumber = DataHelper.getApprovedCardNumber();
        var month = DataHelper.getEmptyString();
        var year = DataHelper.getValidYear();
        var cardOwner = DataHelper.getValidOwner();
        var code = DataHelper.getValidCode();
        paymentFormPageCredit.fillForm(cardNumber, month, year, cardOwner, code);
        paymentFormPageCredit.waitForWrongFormatMessage();
    }

    @Test

    public void shouldAllowPurchaseWithDataLessThenLimitFieldMonth() {
        paymentFormPageCredit = mainPage.payWithCreditCard().clear();
        var cardNumber = DataHelper.getApprovedCardNumber();
        var month = DataHelper.getLessThenLimitMonth();
        var year = DataHelper.getValidYear();
        var cardOwner = DataHelper.getValidOwner();
        var code = DataHelper.getValidCode();
        paymentFormPageCredit.fillForm(cardNumber, month, year, cardOwner, code);
        paymentFormPageCredit.waitForWrongCardExpirationMessage();
    }

    @Test

    public void shouldAllowPurchaseWithDataOverThenLimitFieldMonth() {
        paymentFormPageCredit = mainPage.payWithCreditCard().clear();
        var cardNumber = DataHelper.getApprovedCardNumber();
        var month = DataHelper.getOverLimitMonth();
        var year = DataHelper.getValidYear();
        var cardOwner = DataHelper.getValidOwner();
        var code = DataHelper.getValidCode();
        paymentFormPageCredit.fillForm(cardNumber, month, year, cardOwner, code);
        paymentFormPageCredit.waitForWrongCardExpirationMessage();
    }

    @Test

    public void shouldAllowPurchaseWithEmptyFieldYear() {
        paymentFormPageCredit = mainPage.payWithCreditCard().clear();
        var cardNumber = DataHelper.getApprovedCardNumber();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getEmptyString();
        var cardOwner = DataHelper.getValidOwner();
        var code = DataHelper.getValidCode();
        paymentFormPageCredit.fillForm(cardNumber, month, year, cardOwner, code);
        paymentFormPageCredit.waitForWrongFormatMessage();
    }

    @Test

    public void shouldAllowPurchaseWithDataLessThenLimitFieldYear() {
        paymentFormPageCredit = mainPage.payWithCreditCard().clear();
        var cardNumber = DataHelper.getApprovedCardNumber();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getLessThenLimitYear();
        var cardOwner = DataHelper.getValidOwner();
        var code = DataHelper.getValidCode();
        paymentFormPageCredit.fillForm(cardNumber, month, year, cardOwner, code);
        paymentFormPageCredit.waitForCardExpiredMessage();
    }

    @Test

    public void shouldAllowPurchaseWithDataOverThenLimitFieldYear() {
        paymentFormPageCredit = mainPage.payWithCreditCard().clear();
        var cardNumber = DataHelper.getApprovedCardNumber();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getOverLimitYear();
        var cardOwner = DataHelper.getValidOwner();
        var code = DataHelper.getValidCode();
        paymentFormPageCredit.fillForm(cardNumber, month, year, cardOwner, code);
        paymentFormPageCredit.waitForWrongCardExpirationMessage();
    }

    @Test

    public void shouldAllowPurchaseWithCyrillicInOwner() {
        paymentFormPageCredit = mainPage.payWithCreditCard().clear();
        var cardNumber = DataHelper.getApprovedCardNumber();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var cardOwner = DataHelper.getCyrillicDataOwner();
        var code = DataHelper.getValidCode();
        paymentFormPageCredit.fillForm(cardNumber, month, year, cardOwner, code);
        paymentFormPageCredit.waitForWrongFormatMessage();
    }

    @Test

    public void shouldAllowPurchaseWithSpecialCharsInOwner() {
        paymentFormPageCredit = mainPage.payWithCreditCard().clear();
        var cardNumber = DataHelper.getApprovedCardNumber();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var cardOwner = DataHelper.getOwnerWithSpecialChars();
        var code = DataHelper.getValidCode();
        paymentFormPageCredit.fillForm(cardNumber, month, year, cardOwner, code);
        paymentFormPageCredit.waitForWrongFormatMessage();
    }

    @Test

    public void shouldAllowPurchaseWithDigitsInOwner() {
        paymentFormPageCredit = mainPage.payWithCreditCard().clear();
        var cardNumber = DataHelper.getApprovedCardNumber();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var cardOwner = DataHelper.getOwnerWithDigits();
        var code = DataHelper.getValidCode();
        paymentFormPageCredit.fillForm(cardNumber, month, year, cardOwner, code);
        paymentFormPageCredit.waitForWrongFormatMessage();
    }

    @Test

    public void shouldAllowPurchaseWithEmptyFieldInOwner() {
        paymentFormPageCredit = mainPage.payWithCreditCard().clear();
        var cardNumber = DataHelper.getApprovedCardNumber();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var cardOwner = DataHelper.getEmptyString();
        var code = DataHelper.getValidCode();
        paymentFormPageCredit.fillForm(cardNumber, month, year, cardOwner, code);
        paymentFormPageCredit.waitForMandatoryFieldMessage();
    }

    @Test

    public void shouldAllowPurchaseWithEmptyDataInCode() {
        paymentFormPageCredit = mainPage.payWithCreditCard().clear();
        var cardNumber = DataHelper.getApprovedCardNumber();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var cardOwner = DataHelper.getValidOwner();
        var code = DataHelper.getEmptyString();
        paymentFormPageCredit.fillForm(cardNumber, month, year, cardOwner, code);
        paymentFormPageCredit.waitForWrongFormatMessage();
    }

    @Test

    public void shouldAllowPurchaseWith2DigitsDataInCode() {
        paymentFormPageCredit = mainPage.payWithCreditCard().clear();
        var cardNumber = DataHelper.getApprovedCardNumber();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var cardOwner = DataHelper.getValidOwner();
        var code = DataHelper.getInvalidFormatCode();
        paymentFormPageCredit.fillForm(cardNumber, month, year, cardOwner, code);
        paymentFormPageCredit.waitForWrongFormatMessage();
    }


}
