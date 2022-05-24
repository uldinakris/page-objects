package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Selenide.$;

public class TransferMoneyPage {
    private SelenideElement sumField = $("span[data-test-id=amount] input");
    private SelenideElement from = $("span[data-test-id=from] input");
    private SelenideElement transferButton = $("button[data-test-id=action-transfer]");
    private SelenideElement errorMessage = $("span[contains(., 'Недостаточно средств на балансе')]");

    public TransferMoneyPage() {
        sumField.shouldBe(Condition.visible);
    }


    public DashboardPage transferMoney(String transfer, String cardNumber) {
        sumField.setValue(transfer);
        from.setValue(cardNumber);
        transferButton.click();
        return new DashboardPage();
    }

    public SelenideElement findErrorMessage() {
        return errorMessage;
    }

}
