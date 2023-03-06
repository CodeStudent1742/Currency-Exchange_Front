package com.albert.Views;

import com.albert.domain.dto.GoogleNearbyDto;
import com.albert.service.GoogleNearbyService;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.router.Route;

import java.util.List;

@Route("nearby")
@CssImport("./styles/shared-styles.css")
public class GoogleNearbyPage extends AppLayout {

    public GoogleNearbyPage() {
        HeaderView headerView = new HeaderView();
        addToNavbar(headerView);

        DrawerView drawerView = new DrawerView();
        addToDrawer(drawerView);
        addMainContent();
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
