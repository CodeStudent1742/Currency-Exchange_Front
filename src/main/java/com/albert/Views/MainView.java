package com.albert.Views;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.router.Route;


@Route("")
@CssImport("./styles/shared-styles.css")
public class MainView extends AppLayout {

    public MainView() {
        HeaderView headerView = new HeaderView("Kantor internetowy");
        addToNavbar(headerView);

        DrawerView drawerView = new DrawerView();
        addToDrawer(drawerView);

        addMainContent();
    }

    private void addMainContent() {
        H2 viewTitle = new H2("Opis strony");
        viewTitle.getStyle().set("text-align", "center");
        Paragraph entry = new Paragraph(
                "Jest to prototypowa/poglądowa strona stworzona przy użyciu frameworka Vaadin w języku Java.\n" +
                        "Celem strony jest sprawdzenie i zaprezentowanie działania uproszczonego API REST Kantoru Wymiany Walut.");
        entry.getStyle().set("white-space", "pre-line");
        Paragraph frontEntry = new Paragraph("Część \"FrontEndowa\" zawiera:");
        frontEntry.getStyle().set("text-align", "center").set("font-weight", "bold");
        Paragraph front = new Paragraph(
                "- Stronę pozwalającą na dodawanie, usuwanie i wybieranie użytkowników, dostępną pod przyciskiem \"Wybierz użytkownika\" w prawym górnym rogu.\n" +
                        "- Stronę konta, pozwalającą na symulację wpłacania i wypłacania środków.\n" +
                        "- Stronę kantoru, umożliwiającą wyświetlenie obecnych kursów oraz wykonanie transakcji wymiany walut.\n" +
                        "- Stronę koszyka, wyświetlającą transakcje w koszyku, pozwalającą na dodanie/usunięcie transakcji oraz wykonanie operacji wymiany walut.\n" +
                        "- Stronę historii wymian, prezentującą dokonane wymiany.\n");
        front.getStyle().set("white-space", "pre-line");
        Paragraph backEntry = new Paragraph("Część związana z \"BackEndem\" obejmuje m.in.:");
        backEntry.getStyle().set("text-align", "center").set("font-weight", "bold");
        Paragraph back = new Paragraph(
                "- Endpointy korzystające z żądań typu GET, POST, PUT, DELETE\n" +
                        "- Pobieranie danych z API NBP oraz Google Places\n" +
                        "- Scheduler pobierający kursy z NBP i przeliczający na ich podstawie kursy kantoru\n" +
                        "- Operacje na bazie danych za pomocą ORM MYSQL\n" +
                        "- Testy jednostkowe\n" +
                        "- Wykorzystanie wzorców projektowych Builder oraz Fasada\n" +
                        "- Wykorzystanie frameworków Spring i Hibernate");
        back.getStyle().set("white-space", "pre-line");

        Div content = new Div();
        content.add(viewTitle, entry, frontEntry, front, backEntry, back);
        setContent(content);
    }
}


