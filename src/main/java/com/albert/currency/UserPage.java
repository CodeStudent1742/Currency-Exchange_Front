package com.albert.currency;

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

@Route("user")
@CssImport("./styles/shared-styles.css")
public class UserPage extends AppLayout {

    public UserPage() {
        createHeader();
        createDrawer();
    }
    private void createHeader() {
        H1 title = new H1("Wybór użytkownika");
        HorizontalLayout header  = new HorizontalLayout(new DrawerToggle(), title);
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
        Anchor cantor = new Anchor("cantor", "Cantor");
        Anchor exchange = new Anchor("exchange", "Historia wymian");
        VerticalLayout drawer = new VerticalLayout(main, account, cart,cantor,exchange);
        addToDrawer(drawer);
    }
}
