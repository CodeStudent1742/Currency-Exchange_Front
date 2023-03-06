package com.albert.Views;

import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class DrawerView extends VerticalLayout {

    public DrawerView() {
        Anchor main = new Anchor("", "Strona główna");
        Anchor account = new Anchor("account", "Konto");
        Anchor cart = new Anchor("cart", "Koszyk");
        Anchor cantor = new Anchor("cantor", "Kantor");
        Anchor exchange = new Anchor("exchange", "Historia wymian");
        Anchor nearby = new Anchor("nearby", "Kantory Kraków Rynek");
        this.add(main, account, cantor, cart, exchange, nearby);
    }
}
