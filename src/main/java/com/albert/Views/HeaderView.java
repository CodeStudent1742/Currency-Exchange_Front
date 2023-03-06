package com.albert.Views;

import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class HeaderView extends HorizontalLayout {

    public HeaderView(String Title) {
        H1 title = new H1(Title);
        title.getStyle().set("text-align", "center");
        Button userChoiceButton = new Button("Wybierz użytkownika");
        userChoiceButton.addClassName("user-choice-button");
        userChoiceButton.addClickListener(event -> getUI().ifPresent(ui -> ui.navigate("user")));
        this.add(new DrawerToggle(), title, userChoiceButton);
        this.setAlignItems(FlexComponent.Alignment.CENTER);
        this.expand(title);
        this.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        this.setWidth("100%");
        this.addClassName("header");
    }
}
