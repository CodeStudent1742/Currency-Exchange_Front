package com.albert.Views;

import com.albert.domain.Cantor;
import com.albert.service.AccountService;
import com.albert.service.CantorService;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("cantor")
@CssImport("./styles/shared-styles.css")
public class CantorPage extends AppLayout {

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
        Anchor exchange = new Anchor("exchange", "Historia wymian");
        VerticalLayout drawer = new VerticalLayout(main, account, cart, exchange);
        addToDrawer(drawer);
    }

    private void addMainContent() {

        Grid<Cantor> cantorGrid = new Grid<>();
        cantorGrid.setItems(cantorService.getCantor());

        cantorGrid.addColumn(Cantor::getRatesCheckDate).setHeader("Data sprawdzenia");
        cantorGrid.addColumn(Cantor::getPurchaseRateEUR).setHeader("Kurs kupna EUR");
        cantorGrid.addColumn(Cantor::getSellingRateEUR).setHeader("Kurs sprzedaży EUR");
        cantorGrid.addColumn(Cantor::getPurchaseRateUSD).setHeader("Kurs kupna USD");
        cantorGrid.addColumn(Cantor::getSellingRateUSD).setHeader("Kurs sprzedaży USD");
        cantorGrid.addColumn(Cantor::getPurchaseRateGBP).setHeader("Kurs kupna GBP");
        cantorGrid.addColumn(Cantor::getSellingRateGBP).setHeader("Kurs sprzedaży GBP");
        cantorGrid.addColumn(Cantor::getPurchaseRateCHF).setHeader("Kurs kupna CHF");
        cantorGrid.addColumn(Cantor::getSellingRateCHF).setHeader("Kurs sprzedaży CHF");

        HorizontalLayout mainContent = new HorizontalLayout(cantorGrid);
        Div content = new Div(mainContent);
        setContent(content);
    }

}
