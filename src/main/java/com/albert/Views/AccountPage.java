package com.albert.Views;

import com.albert.domain.dto.AccountDto;
import com.albert.domain.dto.UserDto;
import com.albert.service.AccountService;
import com.albert.service.UserService;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
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
        createHeader();
        createDrawer();
        addMainContent();
    }

    private void createHeader() {
        H1 title = new H1("Strona konta");
        Button userChoiceButton = new Button("Wybierz użytkownika");
        userChoiceButton.addClassName("user-choice-button");
        userChoiceButton.addClickListener(event -> userChoiceButton.getUI().ifPresent(ui -> ui.navigate("user")));
        HorizontalLayout header = new HorizontalLayout(new DrawerToggle(), title, userChoiceButton);
        header.setAlignItems(FlexComponent.Alignment.CENTER);
        header.expand(title);
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.setWidth("100%");
        header.addClassName("header");
        addToNavbar(header);
    }

    private void createDrawer() {
        Anchor main = new Anchor("", "Strona główna");
        Anchor account = new Anchor("account", "Konto");
        Anchor cart = new Anchor("cart", "Koszyk");
        Anchor cantor = new Anchor("cantor", "Kantor");
        Anchor exchange = new Anchor("exchange", "Historia_wymian");
        VerticalLayout drawer = new VerticalLayout(main,account,cantor,cart,exchange);
        addToDrawer(drawer);
    }

    private void addMainContent() {
        if (selectedUser != null) {
            H2 viewTitle = new H2("Saldo użytkownika " + selectedUser.getUserName());

            // saldo użytkownika
            HorizontalLayout balances = new HorizontalLayout(
                    new VerticalLayout(new H3("PLN"), balancePLNLabel),
                    new VerticalLayout(new H3("EUR"), balanceEURLabel),
                    new VerticalLayout(new H3("USD"), balanceUSDLabel),
                    new VerticalLayout(new H3("CHF"), balanceCHFLabel),
                    new VerticalLayout(new H3("GBP"), balanceGBPLabel)
            );


            // formularz wpłaty/wypłaty
            TextField valueField = new TextField("Kwota");
            ComboBox<String> currencyBox = new ComboBox<>("Waluta", "PLN", "EUR", "USD", "CHF", "GBP");
            Button putButton = new Button("Wpłać", event -> {
                if (selectedUser != null) {
                    accountService.putIntoAccount(selectedUser.getAccountId(), currencyBox.getValue(), Double.valueOf(valueField.getValue()));
                    refresh();
                }
            });

            Button withdrawButton = new Button("Wypłać", event -> {
                if (selectedUser != null) {
                    String currency = currencyBox.getValue();
                    Double value = Double.valueOf(valueField.getValue());
                    AccountDto account = accountService.getAccount(selectedUser.getAccountId());
                    BigDecimal balance = account.getBalanceForCurrency(currency);

                    if (BigDecimal.valueOf(value).compareTo(balance) > 0) {
                        showNotification("Próbujesz wypłacić więcej niż masz na koncie w danej walucie.");
                    } else {
                        accountService.withdrawFromAccount(selectedUser.getAccountId(), currency, value);
                        refresh();
                    }
                }
            });
            HorizontalLayout form = new HorizontalLayout(valueField, currencyBox, putButton, withdrawButton);

            Div content = new Div(viewTitle, selectedUserLabel, balances, form);
            setContent(content);
            refresh();
        }
    }

    public void refresh() {
        if (selectedUser != null) {
//            selectedUserLabel.setText("Wybrany użytkownik: " + selectedUser.getUserName());
            AccountDto account = accountService.getAccount(selectedUser.getAccountId());
            balancePLNLabel.setText(account.getBalancePLN().toString());
            balanceEURLabel.setText(account.getBalanceEUR().toString());
            balanceUSDLabel.setText(account.getBalanceUSD().toString());
            balanceCHFLabel.setText(account.getBalanceCHF().toString());
            balanceGBPLabel.setText(account.getBalanceGBP().toString());
        }
    }

    public void setSelectedUser(UserDto selectedUser) {
        this.selectedUser = selectedUser;
        refresh();
    }
    private void showNotification(String message) {
        Notification notification = new Notification(message, 4000);
        notification.open();
    }
}