package com.albert.Views;

import com.albert.domain.dto.CantorDto;
import com.albert.domain.dto.ExchangeOperation;
import com.albert.domain.dto.TransactionDto;
import com.albert.form.TransactionForm;
import com.albert.service.CantorService;
import com.albert.service.CartService;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
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
    private CantorService cantorService = CantorService.getInstance();

    public CantorPage() {
        HeaderView headerView = new HeaderView("Kantor - wymiana walut");
        addToNavbar(headerView);

        DrawerView drawerView = new DrawerView();
        addToDrawer(drawerView);

        addMainContent();
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
        cartService.addTransactionToCart(selectedUser.getCartId(), transaction);

        showNotification("Tranzakcja dodana do koszyka");

        transactionForm.clear();

    }

    private void showNotification(String message) {
        Notification notification = new Notification(message, 4000);
        notification.open();
    }
}
