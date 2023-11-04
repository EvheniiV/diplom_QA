package ru.netology.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;

import java.time.Duration;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class PaymentFormPageDebit {

    private SelenideElement cardNumberField = $("[placeholder='0000 0000 0000 0000']");
    private SelenideElement monthField = $("[placeholder='08']");
    private SelenideElement yearField = $("[placeholder='22']");
    private SelenideElement cardOwnerField = $$("[class='input__control']").get(3);
    private SelenideElement codeField = $("[placeholder='999']");

    private SelenideElement continueButton = $(byText("Продолжить"));

    private SelenideElement failedNotification = $(byText("Ошибка! Банк отказал в проведении операции."));
    private SelenideElement successedNotification = $(byText("Операция одобрена Банком."));
    private SelenideElement mandatoryFieldMessage = $(byText("Поле обязательно для заполнения"));
    private SelenideElement wrongFormatMessage = $(byText("Неверный формат"));
    private SelenideElement invalidCharactersMessage = $(byText("Поле содержит недопустимые символы"));
    private SelenideElement wrongCardExpirationMessage = $(byText("Неверно указан срок действия карты"));
    private SelenideElement cardExpiredMessage = $(byText("Истёк срок действия карты"));


    public void fillForm(String cardNumber, String month, String year, String cardOwner, String code) {
        cardNumberField.sendKeys(cardNumber);
        monthField.sendKeys(month);
        yearField.sendKeys(year);
        cardOwnerField.sendKeys(cardOwner);
        codeField.sendKeys(code);
        continueButton.click();
    }

    public PaymentFormPageDebit clear() {
        clearFields();
        return new PaymentFormPageDebit();
    }

    public void clearFields() {
        cardNumberField.doubleClick().sendKeys(Keys.BACK_SPACE);
        monthField.doubleClick().sendKeys(Keys.BACK_SPACE);
        yearField.doubleClick().sendKeys(Keys.BACK_SPACE);
        cardOwnerField.doubleClick().sendKeys(Keys.BACK_SPACE);
        codeField.doubleClick().sendKeys(Keys.BACK_SPACE);
    }

    public void waitForFailedNotification() {
        failedNotification.shouldBe(Condition.visible, Duration.ofSeconds(11));
    }

    public void waitForSuccessedNotification() {
        successedNotification.shouldBe(Condition.visible, Duration.ofSeconds(11));
    }

    public void waitForMandatoryFieldMessage() {
        mandatoryFieldMessage.shouldBe(Condition.visible, Duration.ofSeconds(11));
    }

    public void waitForWrongFormatMessage() {
        wrongFormatMessage.shouldBe(Condition.visible, Duration.ofSeconds(11));
    }

    public void waitForInvalidCharactersMessage() {
        invalidCharactersMessage.shouldBe(Condition.visible, Duration.ofSeconds(11));
    }

    public void waitForWrongCardExpirationMessage() {
        wrongCardExpirationMessage.shouldBe(Condition.visible, Duration.ofSeconds(11));
    }

    public void waitForCardExpiredMessage() {
        cardExpiredMessage.shouldBe(Condition.visible, Duration.ofSeconds(11));
    }
}