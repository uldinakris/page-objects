package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.val;

import static com.codeborne.selenide.Selenide.*;

public class DashboardPage {
    private SelenideElement heading = $("[data-test-id=dashboard]");
    private ElementsCollection cards = $$(".list__item div");
    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " р.";

    public DashboardPage() {
        heading.shouldBe(Condition.visible);
    }

    public TransferMoneyPage transferMoney(String id) {
        SelenideElement item = findCardInList(id);
        item.find("button").click();
        return new TransferMoneyPage();
    }

    public int getCardBalance(String id) {
        SelenideElement item = findCardInList(id);
        if (item != null) { return extractBalance(item.text()); } else { return 0; }
    }

    private SelenideElement findCardInList(String id) {
        SelenideElement cardItem = null;
        for (SelenideElement card : cards) {
            if (card.text().indexOf("**** **** **** " + id) != -1) {
                cardItem = card;
            }
        }
        return cardItem;
    }

    private int extractBalance(String text) {
        val start = text.indexOf(balanceStart);
        val finish = text.indexOf(balanceFinish);
        val value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }
}
