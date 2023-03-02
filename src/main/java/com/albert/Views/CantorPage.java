package com.albert.Views;

import com.albert.domain.dto.CantorDto;
import com.albert.domain.dto.ExchangeOperation;
import com.albert.domain.dto.TransactionDto;
import com.albert.form.TransactionForm;
import com.albert.service.CantorService;
import com.albert.service.CartService;
import com.albert.service.TransactionService;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Anchor;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import static com.albert.Views.UserPage.selectedUser;

@Route("cantor")
@CssImport("./styles/shared-styles.css")
public class CantorPage extends AppLayout {
    TransactionForm transactionForm = new TransactionForm();
    CartService cartService = CartService.getInstance();
    TransactionService transactionService = TransactionService.getInstance();
    private CantorService cantorService = CantorService.getInstance();

    public CantorPage() {
        createHeader();
        createDrawer();
        addMainContent();
    }

    private void createHeader() {
        H1 title = new H1("Kantor - wymiana walut");
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
        VerticalLayout drawer = new VerticalLayout(main,account, cart, cantor, exchange);
        addToDrawer(drawer);
    }

    private void addMainContent() {

        Grid<CantorDto> cantorGrid = new Grid<>();
        cantorGrid.setItems(cantorService.getCantor());

        cantorGrid.addColumn(CantorDto::getRatesCheckDate).setHeader("Data kursu");
        cantorGrid.addColumn(CantorDto::getPurchaseRateEUR).setHeader("Kupno EUR");
        cantorGrid.addColumn(CantorDto::getSellingRateEUR).setHeader("Sprzedaż EUR");
        cantorGrid.addColumn(CantorDto::getPurchaseRateUSD).setHeader("Kupno USD");
        cantorGrid.addColumn(CantorDto::getSellingRateUSD).setHeader("Sprzedaż USD");
        cantorGrid.addColumn(CantorDto::getPurchaseRateGBP).setHeader("Kupno GBP");
        cantorGrid.addColumn(CantorDto::getSellingRateGBP).setHeader("Sprzedaż GBP");
        cantorGrid.addColumn(CantorDto::getPurchaseRateCHF).setHeader("Kupno CHF");
        cantorGrid.addColumn(CantorDto::getSellingRateCHF).setHeader("Sprzedaż CHF");


        if (selectedUser != null) {
            VerticalLayout mainContent = new VerticalLayout(cantorGrid, transactionForm);
            mainContent.setAlignItems(FlexComponent.Alignment.STRETCH);
            setContent(mainContent);

            transactionForm.setAddListener(this::addTransaction);
            transactionForm.setCancelListener(event -> transactionForm.setVisible(false));
        } else {
            HorizontalLayout mainContent = new HorizontalLayout(cantorGrid);
            Div content = new Div(mainContent);
            setContent(content);
        }
    }

    private void addTransaction(ClickEvent<Button> event) {
        ExchangeOperation operation = transactionForm.getOperation();
        Double volume = transactionForm.getVolume();
        Long cartId = selectedUser.getCartId();

        TransactionDto transaction = new TransactionDto(operation, volume, cartId);
        transactionService.createTransaction(transaction);
        cartService.addTransactionToCart(selectedUser.getCartId(), transaction.getTransactionId());

        showNotification("Tranzakcja dodana do koszyka");

        transactionForm.clear();

//        getUI().ifPresent(ui -> ui.getPage().reload());
    }
    private void showNotification(String message) {
        Notification notification = new Notification(message, 4000);
        notification.open();
    }
}
