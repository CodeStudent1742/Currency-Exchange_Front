package com.albert.Views;

import com.albert.domain.dto.AccountDto;
import com.albert.domain.dto.UserDto;
import com.albert.service.AccountService;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

import java.math.BigDecimal;

@Route("account")
@CssImport("./styles/shared-styles.css")
public class AccountPage extends AppLayout {

    private UserDto selectedUser = UserPage.getSelectedUser();
    private AccountService accountService = AccountService.getInstance();

    private Label selectedUserLabel = new Label("");
    private Label balancePLNLabel = new Label("");
    private Label balanceEURLabel = new Label("");
    private Label balanceUSDLabel = new Label("");
    private Label balanceCHFLabel = new Label("");
    private Label balanceGBPLabel = new Label("");

    public AccountPage() {
        HeaderView headerView = new HeaderView("Strona konta");
        addToNavbar(headerView);

        DrawerView drawerView = new DrawerView();
        addToDrawer(drawerView);

        addMainContent();
    }

    private void addMainContent() {
        if (selectedUser != null) {
            H2 viewTitle = new H2("Saldo użytkownika " + selectedUser.getUserName());

            HorizontalLayout balances = new HorizontalLayout(
                    new VerticalLayout(new H3("PLN"), balancePLNLabel),
                    new VerticalLayout(new H3("EUR"), balanceEURLabel),
                    new VerticalLayout(new H3("USD"), balanceUSDLabel),
                    new VerticalLayout(new H3("CHF"), balanceCHFLabel),
                    new VerticalLayout(new H3("GBP"), balanceGBPLabel)
            );


            TextField valueField = new TextField("Kwota");
            ComboBox<String> currencyBox = new ComboBox<>("Waluta", "PLN", "EUR", "USD", "CHF", "GBP");
            Button putButton = new Button("Wpłać", event -> {
                if (selectedUser != null) {
                    accountService.putIntoAccount(selectedUser.getAccountId(), currencyBox.getValue(), Double.parseDouble(valueField.getValue()));
                    refresh();
                    currencyBox.setValue(null);
                    valueField.setValue("0");
                }
            });

            Button withdrawButton = new Button("Wypłać", event -> {
                if (selectedUser != null) {
                    String currency = currencyBox.getValue();
                    Double value = Double.parseDouble(valueField.getValue());
                    AccountDto account = accountService.getAccount(selectedUser.getAccountId());
                    BigDecimal balance = account.getBalanceForCurrency(currency);

                    if (BigDecimal.valueOf(value).compareTo(balance) > 0) {
                        showNotification("Próbujesz wypłacić więcej niż masz na koncie w danej walucie.");
                    } else {
                        accountService.withdrawFromAccount(selectedUser.getAccountId(), currency, value);
                        refresh();
                        currencyBox.setValue(null);
                        valueField.setValue("0");
                    }
                }
            });
           HorizontalLayout buttonsLayout = new HorizontalLayout(putButton, withdrawButton);
            HorizontalLayout form = new HorizontalLayout(valueField, currencyBox);
            VerticalLayout layout = new VerticalLayout(form,buttonsLayout);
            Div content = new Div(viewTitle, selectedUserLabel, balances, layout);
            setContent(content);
            refresh();
        }
    }

    public void refresh() {
        if (selectedUser != null) {
            AccountDto account = accountService.getAccount(selectedUser.getAccountId());
            balancePLNLabel.setText(account.getBalancePLN().toString());
            balanceEURLabel.setText(account.getBalanceEUR().toString());
            balanceUSDLabel.setText(account.getBalanceUSD().toString());
            balanceCHFLabel.setText(account.getBalanceCHF().toString());
            balanceGBPLabel.setText(account.getBalanceGBP().toString());
        }
    }

    private void showNotification(String message) {
        Notification notification = new Notification(message, 4000);
        notification.open();
    }
}