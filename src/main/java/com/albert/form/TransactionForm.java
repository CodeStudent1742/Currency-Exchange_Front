package com.albert.form;

import com.albert.domain.dto.ExchangeOperation;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;

public class TransactionForm extends Div {
    private ComboBox<ExchangeOperation> operationSelect = new ComboBox<>("Exchange Operation");
    private TextField volumeInput = new TextField("Transaction Volume");
    private TextField valueInput = new TextField("Transaction Value");
    private Button addButton = new Button("Add");
    private Button cancelButton = new Button("Cancel");

    public TransactionForm() {
        operationSelect.setItems(ExchangeOperation.values());

        HorizontalLayout buttonLayout = new HorizontalLayout(addButton, cancelButton);
        add(operationSelect, volumeInput, valueInput, buttonLayout);
    }

    public void setAddListener(ComponentEventListener<ClickEvent<Button>> listener) {
        addButton.addClickListener(listener);
    }

    public void setCancelListener(ComponentEventListener<ClickEvent<Button>> listener) {
        cancelButton.addClickListener(listener);
    }

    public ExchangeOperation getOperation() {
        return operationSelect.getValue();
    }

    public Double getVolume() {
        return Double.parseDouble(volumeInput.getValue());
    }

    public Double getValue() {
        return Double.parseDouble(valueInput.getValue());
    }
}

