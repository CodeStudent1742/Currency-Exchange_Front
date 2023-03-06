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
        HeaderView headerView = new HeaderView();
        addToNavbar(headerView);

        DrawerView drawerView = new DrawerView();
        addToDrawer(drawerView);

        addMainContent();
    }

    private void addMainContent() {
        H2 viewTitle = new H2("Opis strony");
        viewTitle.getStyle().set("text-align", "center");
        Paragraph entry = new Paragraph(
                "Jest to prototypowa/podglądowa strona stworzona przy pomocy frameworku Vaadin w języku Java.\n" +
                        "Strona ma na celu sprawdzenie i zaprezentowanie działania REST API uproszczonego Kantoru Wymiany Walut.");
        entry.getStyle().set("white-space", "pre-line");
        Paragraph frontEntry = new Paragraph("Część \"FrontEndowa\" zawiera:");
        frontEntry.getStyle().set("text-align", "center").set("font-weight", "bold");
        Paragraph front = new Paragraph(
                "Stronę dodawania/usuwania i wybierania użytkownika pod przyciskiem w prawym rogu \"Wybierz użytkownika\".\n" +
                        "Stronę Konta symulująca wpłacanie i wypłacanie środków.\n" +
                        "Stronę Kantoru pozwalająca wyświetlić obecne kursy oraz stworzyć tranzakcje.\n" +
                        "Stronę Koszyka wyświetlająca tranzakcje w koszyku, umożliwiająca dodanie/usunięcie tranzakcji oraz wykonanie operacji wymiany walut.\n" +
                        "Stronę Historii wymian prezentującą dokonane wymiany.");
        front.getStyle().set("white-space", "pre-line");
        Paragraph backEntry = new Paragraph("Część \"BackEndowa\" składa się między innymi z:");
        backEntry.getStyle().set("text-align", "center").set("font-weight", "bold");
        Paragraph back = new Paragraph(
                "Endpointów wykorzystujących żądania GET,POST,PUT,DELETE \n" +
                        "Pobierania danych z API NBP oraz GooglePlace(ta wersja strony nie zawiera funkcjonalności Google)\n" +
                        "Schedulera pobierającego kursy NBP i na ich bazie przeliczający kursy kantoru.\n" +
                        "Operacji na bazie ORM MYSQL \n" +
                        "Testów jednostkowych\n" +
                        "Wykorzystuje wzorce projektowe Builder oraz Fasada\n" +
                        "Korzysta z Spring i Hibernate");
        back.getStyle().set("white-space", "pre-line");

        Div content = new Div();
        content.add(viewTitle, entry, frontEntry, front, backEntry, back);
        setContent(content);
    }
}
