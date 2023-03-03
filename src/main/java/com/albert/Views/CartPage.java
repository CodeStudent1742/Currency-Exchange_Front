package com.albert.Views;

import com.vaadin.flow.component.dialog.Dialog;
import com.albert.domain.dto.TransactionDto;
import com.albert.form.CartForm;
import com.albert.service.CartService;
import com.albert.service.TransactionService;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;


import static com.albert.Views.UserPage.selectedUser;

@Route("cart")
@CssImport("./styles/shared-styles.css")
public class CartPage extends AppLayout {

    private Grid<TransactionDto> transactionGrid = new Grid<>(TransactionDto.class);
    private TransactionService transactionService = TransactionService.getInstance();
    private CartService cartService = CartService.getInstance();

    public CartPage() {
        createHeader();
        createDrawer();
        addMainContent();
    }

    private void createHeader() {
        H1 title = new H1("Koszyk z transakcjami");
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
        VerticalLayout drawer = new VerticalLayout(main, account, cantor, cart, exchange);
        addToDrawer(drawer);
    }

    private void addMainContent() {
        if (selectedUser != null) {
            H2 viewTitle = new H2("Lista transakcji");
            transactionGrid.setColumns("transactionId", "exchangeOperation", "transactionVolume", "transactionValue");
            transactionGrid.setItems(cartService.getTransactionInCart(selectedUser.getCartId()));

            // Formularz do usuwania transakcji z koszyka
            Dialog confirmDialog = new Dialog();
            VerticalLayout confirmDialogLayout = new VerticalLayout();
            confirmDialogLayout.add(new H3("Czy na pewno chcesz usunąć wybraną transakcję z koszyka?"));
            Button confirmButton = new Button("Potwierdź");
            confirmButton.addClickListener(event -> {
                TransactionDto selectedTransaction = transactionGrid.asSingleSelect().getValue();
                cartService.removeTransactionFromCart(selectedUser.getCartId(), selectedTransaction.getTransactionId());
                transactionGrid.setItems(cartService.getTransactionInCart(selectedUser.getCartId()));
                confirmDialog.close();
            });
            Button cancelButton = new Button("Anuluj");
            cancelButton.addClickListener(event -> confirmDialog.close());
            confirmDialogLayout.add(new HorizontalLayout(confirmButton, cancelButton));
            confirmDialog.add(confirmDialogLayout);
            confirmDialog.setWidth("600px");
            confirmDialog.setHeight("300px");

            // Dodanie przycisku "Usuń z koszyka"
            Button removeButton = new Button("Usuń z koszyka");
            removeButton.setEnabled(false);
            transactionGrid.asSingleSelect().addValueChangeListener(event -> {
                removeButton.setEnabled(event.getValue() != null);
            });
            removeButton.addClickListener(event -> {
                confirmDialog.open();
            });

            // Dodanie przycisku "Dokonaj Wymiany"
            Button makeOrderButton = new Button("Dokonaj Wymiany");
            makeOrderButton.addClickListener(event -> {
                // Perform make order operation on selectedUser's cart
                cartService.makeExchangeOrderFromCart(selectedUser.getCartId());
                makeOrderButton.getUI().ifPresent(ui -> ui.navigate("cart"));
            });

            CartForm cartForm = new CartForm(transactionGrid);
            VerticalLayout mainContent = new VerticalLayout(viewTitle, transactionGrid, removeButton, makeOrderButton, cartForm);
            mainContent.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
            mainContent.setAlignItems(FlexComponent.Alignment.CENTER);
            mainContent.setSpacing(true);
            mainContent.setSizeFull();
            mainContent.addClassName("main-content");
            setContent(mainContent);
        }
    }
}
