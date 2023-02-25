package com.albert.Views;

import com.albert.domain.dto.UserDto;
import com.albert.form.UserForm;
import com.albert.service.UserService;
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

@Route("user")
@CssImport("./styles/shared-styles.css")
public class UserPage extends AppLayout {
    private Button addNewUserDto = new Button("Add new user");
    private Grid<UserDto> grid = new Grid<>(UserDto.class);
    private UserService userService = UserService.getInstance();
    private UserForm form = new UserForm(this);

    public UserPage() {
        createHeader();
        createDrawer();

        form.setUserDto(null);
        addMainContent();

        refresh();

        grid.asSingleSelect().addValueChangeListener(event -> form.setUserDto(grid.asSingleSelect().getValue()));
    }

    private void createHeader() {
        H1 title = new H1("Wybór użytkownika");
        HorizontalLayout header = new HorizontalLayout(new DrawerToggle(), title);
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
        VerticalLayout drawer = new VerticalLayout(main, account, cart, cantor, exchange);
        addToDrawer(drawer);
    }

    private void addMainContent() {
        H2 viewTitle = new H2("Użytkownicy");
        grid.setColumns("userName", "userId", "cartId", "accountId");
        HorizontalLayout mainContent = new HorizontalLayout(grid, form);
        addNewUserDto.addClickListener(e -> {
            grid.asSingleSelect().clear();
            form.setUserDto(new UserDto());
        });

        HorizontalLayout toolbar = new HorizontalLayout(addNewUserDto);
//        grid.setSizeFull();
        Div content = new Div();
        content.add(viewTitle,toolbar, mainContent);
        setContent(content);
    }

    public void refresh() {
        grid.setItems(userService.getUsers());
    }
}
