package com.albert.Views;

import com.albert.client.UserClient;
import com.albert.domain.dto.ExchangeOrderDto;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import java.util.List;

import static com.albert.Views.UserPage.selectedUser;

@Route("exchange")
@CssImport("./styles/shared-styles.css")
public class ExchangeHistoryPage extends AppLayout {

    public ExchangeHistoryPage() {
        createHeader();
        createDrawer();
        addMainContent();
    }

    private void createHeader() {
        H1 title = new H1("Historia dokonanych wymian");
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
        Anchor exchange = new Anchor("exchange", "Historia wymian");
        VerticalLayout drawer = new VerticalLayout(main, account, cantor, cart, exchange);
        addToDrawer(drawer);
    }

    private void addMainContent() {
        if (selectedUser != null) {
            List<ExchangeOrderDto> exchangeOrders = UserClient.getInstance().getUserExchangeOrders(selectedUser.getUserId());

            Grid<ExchangeOrderDto> grid = new Grid<>();
            grid.setItems(exchangeOrders);
            grid.addColumn(ExchangeOrderDto::getOrderId).setHeader("Order Id");
            grid.addColumn(ExchangeOrderDto::getExchangeDate).setHeader("Exchange Date");
            grid.addColumn(ExchangeOrderDto::getExchangeStatus).setHeader("Exchange Status");
            grid.setSelectionMode(Grid.SelectionMode.NONE);

            grid.addComponentColumn(exchangeOrder -> {
                List<Long> orderTransactionIds = exchangeOrder.getOrderTransactionIds();
                VerticalLayout verticalLayout = new VerticalLayout();
                for (Long id : orderTransactionIds) {
                    Label transactionLabel = new Label("Transaction: " + id);
                    verticalLayout.add(transactionLabel);
                }
                return verticalLayout;
            }).setHeader("Order Transactions");

            setContent(grid);
        }
    }
}
