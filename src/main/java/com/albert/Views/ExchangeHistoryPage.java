package com.albert.Views;

import com.albert.client.UserClient;
import com.albert.domain.dto.ExchangeOrderDto;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import java.util.List;

import static com.albert.Views.UserPage.selectedUser;

@Route("exchange")
@CssImport("./styles/shared-styles.css")
public class ExchangeHistoryPage extends AppLayout {

    public ExchangeHistoryPage() {
        HeaderView headerView = new HeaderView("Historia dokonanych wymian");
        addToNavbar(headerView);

        DrawerView drawerView = new DrawerView();
        addToDrawer(drawerView);
        addMainContent();
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
