package com.albert.form;

import com.albert.Views.UserPage;
import com.albert.domain.dto.NewUserDto;
import com.albert.domain.dto.UserDto;
import com.albert.service.UserService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;

public class UserForm extends FormLayout {
    private UserPage userPage;
    private UserService service = UserService.getInstance();
    private TextField userName = new TextField("User Name");
    private Button save = new Button("Save");
    private Button delete = new Button("Delete");
    private Binder<UserDto> binder = new Binder<UserDto>(UserDto.class);

    public UserForm(UserPage userPage) {
        HorizontalLayout buttons = new HorizontalLayout(save, delete);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        add(userName, buttons);
        binder.bindInstanceFields(this);
        this.userPage = userPage;

        save.addClickListener(event -> saveUser());
        delete.addClickListener(event -> deleteUser());
    }

    public void setUserDto(UserDto userDto) {
        binder.setBean(userDto);

        if (userDto == null) {
            setVisible(false);
        } else {
            setVisible(true);
            userName.focus();
        }
    }

    private void saveUser() {
        UserDto userDto = binder.getBean();
        service.save(mapToNewUser(userDto));
        userPage.refresh();
        setUserDto(null);
    }

    private void deleteUser() {
        UserDto userDto = binder.getBean();
        service.delete(userDto.getUserName());
        userPage.refresh();
        setUserDto(null);
    }
    public NewUserDto mapToNewUser(UserDto userDto){
        return new NewUserDto(userDto.getUserName());
    }
}


