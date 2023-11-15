package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.data.DataHelper;
import ru.netology.data.SqlHelper;
import ru.netology.pages.MainPage;
import ru.netology.pages.PaymentFormPageDebit;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestByDebit {

    private PaymentFormPageDebit paymentFormPageDebit;
    private MainPage mainPage;

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @BeforeEach
    public void setUp() {
        mainPage = open(System.getProperty("sut.url"), MainPage.class);
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @AfterEach
    void clearDatabaseTables() {
        SqlHelper.clearTables();
    }

    @Test

    public void shouldAllowPurchaseWithApprovedCard() {
        paymentFormPageDebit = mainPage.payWithDebitCard();
        paymentFormPageDebit.clearFields();
        var cardNumber = DataHelper.getApprovedCardNumber();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var cardOwner = DataHelper.getValidOwner();
        var code = DataHelper.getValidCode();
        paymentFormPageDebit.fillForm(cardNumber, month, year, cardOwner, code);
        paymentFormPageDebit.waitForSuccessedNotification();
        assertEquals(DataHelper.getApprovedCardStatus(), SqlHelper.findPayStatus());
    }

    @Test

    public void shouldAllowPurchaseWithDeclinedCard() {
        paymentFormPageDebit = mainPage.payWithDebitCard();
        paymentFormPageDebit.clearFields();
        var cardNumber = DataHelper.getDeclinedCardNumber();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var cardOwner = DataHelper.getValidOwner();
        var code = DataHelper.getValidCode();
        paymentFormPageDebit.fillForm(cardNumber, month, year, cardOwner, code);
        paymentFormPageDebit.waitForFailedNotification();
        assertEquals(DataHelper.getDeclinedCardStatus(), SqlHelper.findPayStatus());
    }

    @Test

    public void shouldAllowPurchaseWithEmptyFieldCard() {
        paymentFormPageDebit = mainPage.payWithDebitCard();
        paymentFormPageDebit.clearFields();
        var cardNumber = DataHelper.getEmptyString();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var cardOwner = DataHelper.getValidOwner();
        var code = DataHelper.getValidCode();
        paymentFormPageDebit.fillForm(cardNumber, month, year, cardOwner, code);
        paymentFormPageDebit.waitForWrongFormatMessage();
        assertEquals(0, SqlHelper.getOrderEntityCount());
    }

    @Test
    public void shouldAllowPurchaseWith15DigitsCard() {
        paymentFormPageDebit = mainPage.payWithDebitCard();
        paymentFormPageDebit.clearFields();
        var cardNumber = DataHelper.getCardNumberWith15Digits();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var cardOwner = DataHelper.getValidOwner();
        var code = DataHelper.getValidCode();
        paymentFormPageDebit.fillForm(cardNumber, month, year, cardOwner, code);
        paymentFormPageDebit.waitForWrongFormatMessage();
    }

    @Test
    public void shouldAllowPurchaseWithNonExistingCard() {
        paymentFormPageDebit = mainPage.payWithDebitCard();
        paymentFormPageDebit.clearFields();
        var cardNumber = DataHelper.getRandomCardNumber();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var cardOwner = DataHelper.getValidOwner();
        var code = DataHelper.getValidCode();
        paymentFormPageDebit.fillForm(cardNumber, month, year, cardOwner, code);
        paymentFormPageDebit.waitForFailedNotification();
        assertEquals(0, SqlHelper.getOrderEntityCount());
    }

    @Test
    public void shouldAllowPurchaseWithEmptyFieldMonth() {
        paymentFormPageDebit = mainPage.payWithDebitCard();
        paymentFormPageDebit.clearFields();
        var cardNumber = DataHelper.getApprovedCardNumber();
        var month = DataHelper.getEmptyString();
        var year = DataHelper.getValidYear();
        var cardOwner = DataHelper.getValidOwner();
        var code = DataHelper.getValidCode();
        paymentFormPageDebit.fillForm(cardNumber, month, year, cardOwner, code);
        paymentFormPageDebit.waitForWrongFormatMessage();
    }

    @Test
    public void shouldAllowPurchaseWithDataLessThenLimitFieldMonth() {
        paymentFormPageDebit = mainPage.payWithDebitCard();
        paymentFormPageDebit.clearFields();
        var cardNumber = DataHelper.getApprovedCardNumber();
        var month = DataHelper.getLessThenLimitMonth();
        var year = DataHelper.getValidYear();
        var cardOwner = DataHelper.getValidOwner();
        var code = DataHelper.getValidCode();
        paymentFormPageDebit.fillForm(cardNumber, month, year, cardOwner, code);
        paymentFormPageDebit.waitForWrongCardExpirationMessage();
    }

    @Test
    public void shouldAllowPurchaseWithDataOverThenLimitFieldMonth() {
        paymentFormPageDebit = mainPage.payWithDebitCard();
        paymentFormPageDebit.clearFields();
        var cardNumber = DataHelper.getApprovedCardNumber();
        var month = DataHelper.getOverLimitMonth();
        var year = DataHelper.getValidYear();
        var cardOwner = DataHelper.getValidOwner();
        var code = DataHelper.getValidCode();
        paymentFormPageDebit.fillForm(cardNumber, month, year, cardOwner, code);
        paymentFormPageDebit.waitForWrongCardExpirationMessage();
    }

    @Test
    public void shouldAllowPurchaseWithEmptyFieldYear() {
        paymentFormPageDebit = mainPage.payWithDebitCard();
        paymentFormPageDebit.clearFields();
        var cardNumber = DataHelper.getApprovedCardNumber();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getEmptyString();
        var cardOwner = DataHelper.getValidOwner();
        var code = DataHelper.getValidCode();
        paymentFormPageDebit.fillForm(cardNumber, month, year, cardOwner, code);
        paymentFormPageDebit.waitForWrongFormatMessage();
    }

    @Test
    public void shouldAllowPurchaseWithDataLessThenLimitFieldYear() {
        paymentFormPageDebit = mainPage.payWithDebitCard();
        paymentFormPageDebit.clearFields();
        var cardNumber = DataHelper.getApprovedCardNumber();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getLessThenLimitYear();
        var cardOwner = DataHelper.getValidOwner();
        var code = DataHelper.getValidCode();
        paymentFormPageDebit.fillForm(cardNumber, month, year, cardOwner, code);
        paymentFormPageDebit.waitForCardExpiredMessage();
    }

    @Test
    public void shouldAllowPurchaseWithDataOverThenLimitFieldYear() {
        paymentFormPageDebit = mainPage.payWithDebitCard();
        paymentFormPageDebit.clearFields();
        var cardNumber = DataHelper.getApprovedCardNumber();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getOverLimitYear();
        var cardOwner = DataHelper.getValidOwner();
        var code = DataHelper.getValidCode();
        paymentFormPageDebit.fillForm(cardNumber, month, year, cardOwner, code);
        paymentFormPageDebit.waitForWrongCardExpirationMessage();
    }

    @Test
    public void shouldAllowPurchaseWithCyrillicInOwner() {
        paymentFormPageDebit = mainPage.payWithDebitCard();
        paymentFormPageDebit.clearFields();
        var cardNumber = DataHelper.getApprovedCardNumber();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var cardOwner = DataHelper.getCyrillicDataOwner();
        var code = DataHelper.getValidCode();
        paymentFormPageDebit.fillForm(cardNumber, month, year, cardOwner, code);
        paymentFormPageDebit.waitForWrongFormatMessage();
    }

    @Test
    public void shouldAllowPurchaseWithSpecialCharsInOwner() {
        paymentFormPageDebit = mainPage.payWithDebitCard();
        paymentFormPageDebit.clearFields();
        var cardNumber = DataHelper.getApprovedCardNumber();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var cardOwner = DataHelper.getOwnerWithSpecialChars();
        var code = DataHelper.getValidCode();
        paymentFormPageDebit.fillForm(cardNumber, month, year, cardOwner, code);
        paymentFormPageDebit.waitForWrongFormatMessage();
    }

    @Test
    public void shouldAllowPurchaseWithDigitsInOwner() {
        paymentFormPageDebit = mainPage.payWithDebitCard();
        paymentFormPageDebit.clearFields();
        var cardNumber = DataHelper.getApprovedCardNumber();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var cardOwner = DataHelper.getOwnerWithDigits();
        var code = DataHelper.getValidCode();
        paymentFormPageDebit.fillForm(cardNumber, month, year, cardOwner, code);
        paymentFormPageDebit.waitForWrongFormatMessage();
    }

    @Test
    public void shouldAllowPurchaseWithEmptyFieldInOwner() {
        paymentFormPageDebit = mainPage.payWithDebitCard();
        paymentFormPageDebit.clearFields();
        var cardNumber = DataHelper.getApprovedCardNumber();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var cardOwner = DataHelper.getEmptyString();
        var code = DataHelper.getValidCode();
        paymentFormPageDebit.fillForm(cardNumber, month, year, cardOwner, code);
        paymentFormPageDebit.waitForMandatoryFieldMessage();
    }

    @Test
    public void shouldAllowPurchaseWithEmptyDataInCode() {
        paymentFormPageDebit = mainPage.payWithDebitCard();
        paymentFormPageDebit.clearFields();
        var cardNumber = DataHelper.getApprovedCardNumber();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var cardOwner = DataHelper.getValidOwner();
        var code = DataHelper.getEmptyString();
        paymentFormPageDebit.fillForm(cardNumber, month, year, cardOwner, code);
        paymentFormPageDebit.waitForWrongFormatMessage();
    }

    @Test
    public void shouldAllowPurchaseWith2DigitsDataInCode() {
        paymentFormPageDebit = mainPage.payWithDebitCard();
        paymentFormPageDebit.clearFields();
        var cardNumber = DataHelper.getApprovedCardNumber();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var cardOwner = DataHelper.getValidOwner();
        var code = DataHelper.getInvalidFormatCode();
        paymentFormPageDebit.fillForm(cardNumber, month, year, cardOwner, code);
        paymentFormPageDebit.waitForWrongFormatMessage();
    }


}
