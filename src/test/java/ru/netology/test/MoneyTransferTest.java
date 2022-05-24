package ru.netology.test;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import ru.netology.data.DataHelper;
import ru.netology.page.LoginPage;
import ru.netology.page.TransferMoneyPage;

import static com.codeborne.selenide.Selenide.open;

public class MoneyTransferTest {
    @Test
    void shouldTransferMoneyBetweenOwnCardsV1() {
        open("http://localhost:9999");
        Configuration.holdBrowserOpen = true;
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.authoriseUser(authInfo);
        var verificationCode = DataHelper.getVerificationCode(authInfo);
        var dashboardPage = verificationPage.validateUser(verificationCode);
        DataHelper.CardInfo firstCardData = DataHelper.getFirstCardInfo();
        DataHelper.CardInfo secondCardData = DataHelper.getSecondCardInfo();
        var initialBalance1 = dashboardPage.getCardBalance(firstCardData.getShortNumber());
        var initialBalance2 = dashboardPage.getCardBalance(secondCardData.getShortNumber());
        var transferMoneyPage = dashboardPage.transferMoney(firstCardData.getShortNumber());
        var dashboardPage2 = transferMoneyPage.transferMoney("500", secondCardData.getFullNumber());
        var balanceAfterTransfer1 = dashboardPage2.getCardBalance(firstCardData.getShortNumber());
        var balanceAfterTransfer2 = dashboardPage2.getCardBalance(secondCardData.getShortNumber());

        assertEquals(initialBalance1 + 500, balanceAfterTransfer1);
        assertEquals(initialBalance2 - 500, balanceAfterTransfer2);
    }

    @Test
    void shouldTransferMoneyBetweenOwnCardsV2() {
        open("http://localhost:9999");
        Configuration.holdBrowserOpen = true;
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.authoriseUser(authInfo);
        var verificationCode = DataHelper.getVerificationCode(authInfo);
        var dashboardPage = verificationPage.validateUser(verificationCode);
        DataHelper.CardInfo firstCardData = DataHelper.getFirstCardInfo();
        DataHelper.CardInfo secondCardData = DataHelper.getSecondCardInfo();
        var initialBalance1 = dashboardPage.getCardBalance(secondCardData.getShortNumber());
        var initialBalance2 = dashboardPage.getCardBalance(firstCardData.getShortNumber());
        var transferMoneyPage = dashboardPage.transferMoney(secondCardData.getShortNumber());
        var dashboardPage2 = transferMoneyPage.transferMoney("500", firstCardData.getFullNumber());
        var balanceAfterTransfer1 = dashboardPage2.getCardBalance(secondCardData.getShortNumber());
        var balanceAfterTransfer2 = dashboardPage2.getCardBalance(firstCardData.getShortNumber());

        assertEquals(initialBalance1 + 500, balanceAfterTransfer1);
        assertEquals(initialBalance2 - 500, balanceAfterTransfer2);
    }

    @Test
    void shouldNotTransferMoneyBetweenTheSameCard() {
        open("http://localhost:9999");
        Configuration.holdBrowserOpen = true;
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.authoriseUser(authInfo);
        var verificationCode = DataHelper.getVerificationCode(authInfo);
        var dashboardPage = verificationPage.validateUser(verificationCode);
        DataHelper.CardInfo firstCardData = DataHelper.getFirstCardInfo();
        DataHelper.CardInfo secondCardData = DataHelper.getSecondCardInfo();
        var initialBalance = dashboardPage.getCardBalance(secondCardData.getShortNumber());
        var transferMoneyPage = dashboardPage.transferMoney(secondCardData.getShortNumber());
        var dashboardPage2 = transferMoneyPage.transferMoney("500", secondCardData.getFullNumber());
        var balanceAfterTransfer = dashboardPage2.getCardBalance(secondCardData.getShortNumber());

        assertEquals(initialBalance, balanceAfterTransfer);
    }

    @Test
    void shouldNotTransferMoneyBetweenOwnCardsWithSumAboveAmount() {
        open("http://localhost:9999");
        Configuration.holdBrowserOpen = true;
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.authoriseUser(authInfo);
        var verificationCode = DataHelper.getVerificationCode(authInfo);
        var dashboardPage = verificationPage.validateUser(verificationCode);
        DataHelper.CardInfo firstCardData = DataHelper.getFirstCardInfo();
        DataHelper.CardInfo secondCardData = DataHelper.getSecondCardInfo();
        var transferMoneyPage = dashboardPage.transferMoney(secondCardData.getShortNumber());
        transferMoneyPage.transferMoney("50000", firstCardData.getFullNumber());
        var errorMessage = transferMoneyPage.findErrorMessage();

        errorMessage.shouldBe(Condition.visible);
    }
}
