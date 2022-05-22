package ru.netology.test;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import ru.netology.data.DataHelper;
import ru.netology.page.LoginPageV1;
import ru.netology.page.TransferMoneyPage;

import static com.codeborne.selenide.Selenide.open;

public class MoneyTransferTest {
    @Test
    void shouldTransferMoneyBetweenOwnCardsV1() {
        open("http://localhost:9999");
        Configuration.holdBrowserOpen = true;
        var loginPage = new LoginPageV1();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.authoriseUser(authInfo);
        var verificationCode = DataHelper.getVerificationCode(authInfo);
        var dashboardPage = verificationPage.validateUser(verificationCode);
        var initialBalance = dashboardPage.getCardBalance("0001");
        var transferMoneyPage = dashboardPage.transferMoney("0001");
        var dashboardPage2 = transferMoneyPage.transferMoney("500", "5559 0000 0000 0002");
        var balanceAfterTransfer = dashboardPage2.getCardBalance("0001");

        assertEquals(initialBalance + 500, balanceAfterTransfer);
    }

    @Test
    void shouldTransferMoneyBetweenOwnCardsV2() {
        open("http://localhost:9999");
        Configuration.holdBrowserOpen = true;
        var loginPage = new LoginPageV1();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.authoriseUser(authInfo);
        var verificationCode = DataHelper.getVerificationCode(authInfo);
        var dashboardPage = verificationPage.validateUser(verificationCode);
        var initialBalance = dashboardPage.getCardBalance("0002");
        var transferMoneyPage = dashboardPage.transferMoney("0002");
        var dashboardPage2 = transferMoneyPage.transferMoney("500", "5559 0000 0000 0001");
        var balanceAfterTransfer = dashboardPage2.getCardBalance("0002");

        assertEquals(initialBalance + 500, balanceAfterTransfer);
    }

    @Test
    void shouldNotTransferMoneyBetweenTheSameCard() {
        open("http://localhost:9999");
        Configuration.holdBrowserOpen = true;
        var loginPage = new LoginPageV1();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.authoriseUser(authInfo);
        var verificationCode = DataHelper.getVerificationCode(authInfo);
        var dashboardPage = verificationPage.validateUser(verificationCode);
        var initialBalance = dashboardPage.getCardBalance("0002");
        var transferMoneyPage = dashboardPage.transferMoney("0002");
        var dashboardPage2 = transferMoneyPage.transferMoney("500", "5559 0000 0000 0002");
        var balanceAfterTransfer = dashboardPage2.getCardBalance("0002");

        assertEquals(initialBalance, balanceAfterTransfer);
    }
}
