package com.albert.Views;

import com.albert.domain.dto.TransactionDto;
import com.albert.form.CartForm;
import com.albert.service.CartService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import static com.albert.Views.UserPage.selectedUser;

@Route("cart")
@CssImport("./styles/shared-styles.css")
public class CartPage extends AppLayout {

    private Grid<TransactionDto> transactionGrid = new Grid<>(TransactionDto.class);
    private CartService cartService = CartService.getInstance();

    public CartPage() {
        HeaderView headerView = new HeaderView();
        addToNavbar(headerView);

        DrawerView drawerView = new DrawerView();
        addToDrawer(drawerView);
        addMainContent();
    }

    private void addMainContent() {
        if (selectedUser != null) {
            H2 viewTitle = new H2("Lista transakcji");
            transactionGrid.setColumns("transactionId", "exchangeOperation", "transactionVolume", "transactionValue");
            transactionGrid.setItems(cartService.getTransactionInCart(selectedUser.getCartId()));

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

            Button removeButton = new Button("Usuń z koszyka");
            removeButton.setEnabled(false);
            transactionGrid.asSingleSelect().addValueChangeListener(event -> removeButton.setEnabled(event.getValue() != null));
            removeButton.addClickListener(event -> confirmDialog.open());

            Button makeOrderButton = new Button("Dokonaj Wymiany");
            Dialog makeOrderDialog = new Dialog();
            VerticalLayout makeOrderDialogLayout = new VerticalLayout();
            makeOrderDialogLayout.add(new H3("Czy na pewno chcesz dokonać wymiany?"));
            Button makeOrderConfirmButton = new Button("Potwierdź");
            makeOrderConfirmButton.addClickListener(event -> {
                cartService.makeExchangeOrderFromCart(selectedUser.getCartId());
                makeOrderDialog.close();
                UI.getCurrent().getPage().reload();
            });
            Button makeOrderCancelButton = new Button("Anuluj");
            makeOrderCancelButton.addClickListener(event -> makeOrderDialog.close());
            makeOrderDialogLayout.add(new HorizontalLayout(makeOrderConfirmButton, makeOrderCancelButton));
            makeOrderDialog.add(makeOrderDialogLayout);
            makeOrderDialog.setWidth("600px");
            makeOrderDialog.setHeight("300px");
            makeOrderButton.addClickListener(event -> makeOrderDialog.open());
             HorizontalLayout buttonLayout = new HorizontalLayout(removeButton, makeOrderButton);

            CartForm cartForm = new CartForm(transactionGrid);
            VerticalLayout mainContent = new VerticalLayout(viewTitle, transactionGrid, buttonLayout, cartForm);
            mainContent.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
            mainContent.setAlignItems(FlexComponent.Alignment.CENTER);
            mainContent.setSpacing(true);
            mainContent.setSizeFull();
            mainContent.addClassName("main-content");
            setContent(mainContent);
        }
    }
}
