package com.albert.Views;

import com.albert.domain.dto.UserDto;
import com.albert.form.UserForm;
import com.albert.service.UserService;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.Route;

@Route("user")
@CssImport("./styles/shared-styles.css")
public class UserPage extends AppLayout {
    private Label selectedUserLabel = new Label("");
    private Label selectedUserTextLabel = new Label("Wybrany użytkownik:");
    private Button addDeleteUser = new Button("Dodaj/Usuń użytkownika");
    private Grid<UserDto> grid = new Grid<>(UserDto.class);
    private UserService userService = UserService.getInstance();
    private UserForm form = new UserForm(this);
    public static UserDto selectedUser;

    public UserPage() {
        HeaderView headerView = new HeaderView("Wybór użytkownika");
        addToNavbar(headerView);

        DrawerView drawerView = new DrawerView();
        addToDrawer(drawerView);

        form.setUserDto(null);
        addMainContent();

        refresh();

    }
    private void addMainContent() {
        H2 viewTitle = new H2("Użytkownicy");
        grid.setColumns("userName", "userId", "cartId", "accountId");
        grid.asSingleSelect().addValueChangeListener(event -> {
            selectedUser = grid.asSingleSelect().getValue();
            if (selectedUser != null) {
                selectedUserLabel.setText(selectedUser.getUserName());
            }
        });
        HorizontalLayout mainContent = new HorizontalLayout(grid, form);
        addDeleteUser.addClickListener(e -> {
            selectedUserLabel.setText("");
            grid.asSingleSelect().clear();
            form.setUserDto(new UserDto());
        });

        HorizontalLayout toolbar = new HorizontalLayout(addDeleteUser,selectedUserTextLabel, selectedUserLabel);
        toolbar.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);

        Div content = new Div();
        content.add(viewTitle, toolbar, mainContent);
        setContent(content);
    }

    public void refresh() {
        grid.setItems(userService.getUsers());
    }

    public static UserDto getSelectedUser() {
        return selectedUser;
    }
}
