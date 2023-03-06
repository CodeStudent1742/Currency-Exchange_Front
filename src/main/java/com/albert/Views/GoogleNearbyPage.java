package com.albert.Views;

import com.albert.domain.dto.GoogleNearbyDto;
import com.albert.service.GoogleNearbyService;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import java.util.List;

@Route("nearby")
@CssImport("./styles/shared-styles.css")
public class GoogleNearbyPage extends AppLayout {

    public GoogleNearbyPage() {
        createHeader();
        createDrawer();
        addMainContent();
    }

    private void createHeader() {
        H1 title = new H1("Kantory w okolicy Rynku Głównego w Krakowie");
        title.getStyle().set("text-align", "center");
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
        Anchor nearby = new Anchor("nearby", "Kantory Kraków Rynek");
        VerticalLayout drawer = new VerticalLayout(main, account, cantor, cart, exchange,nearby);
        addToDrawer(drawer);
    }
    private void addMainContent(){
        H2 viewTitle = new H2("Kantory w okolicy Rynku w Krakowie");
        viewTitle.getStyle().set("text-align", "center");
        Paragraph information = new Paragraph(
                "Docelowo w tym miejscu mozna wyświetlić lokalizację kantorow stacjonarnych, które są związane ze stroną internetową.\n" );
        information.getStyle().set("white-space", "pre-line");

        List<GoogleNearbyDto> cantorsList = GoogleNearbyService.getInstance().getCantorsNearbyKRKMainSquare();

        Grid<GoogleNearbyDto> grid = new Grid<>();
        grid.setItems(cantorsList);
        grid.addColumn(GoogleNearbyDto::getName).setHeader("Nazwa");
        grid.addColumn(GoogleNearbyDto::getVicinity).setHeader("Adres");
        grid.addColumn(GoogleNearbyDto::getRating).setHeader("Ocena");

        Div content = new Div();
        content.add(viewTitle, information, grid);
        setContent(content);
    }

}
