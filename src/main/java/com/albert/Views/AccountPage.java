package com.albert.Views;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("account")
@CssImport("./styles/shared-styles.css")
public class AccountPage extends AppLayout {

    public AccountPage() {
        createHeader();
        createDrawer();
    }
    private void createHeader() {
        H1 title = new H1("Strona konta");
        Button userChoiceButton = new Button("Wybierz użytkownika");
        userChoiceButton.addClassName("user-choice-button");
        userChoiceButton.addClickListener(event -> userChoiceButton.getUI().ifPresent(ui -> ui.navigate("user")));
        HorizontalLayout header  = new HorizontalLayout(new DrawerToggle(), title, userChoiceButton);
        header.setAlignItems(FlexComponent.Alignment.CENTER);
        header.expand(title);
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.setWidth("100%");
        header.addClassName("header");
        addToNavbar(header);
    }

    private void createDrawer() {
        Anchor main = new Anchor("", "Strona główna");
        Anchor cart = new Anchor("cart", "Koszyk");
        Anchor cantor = new Anchor("cantor", "Kantor");
        Anchor exchange = new Anchor("exchange", "Historia wymian");
        VerticalLayout drawer = new VerticalLayout(main, cart, cantor,exchange);
        addToDrawer(drawer);
    }
}