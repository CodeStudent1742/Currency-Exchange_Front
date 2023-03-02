package com.albert.form;

import com.albert.domain.dto.CantorDto;
import com.albert.domain.dto.ExchangeOperation;
import com.albert.service.CantorService;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;

public class TransactionForm extends Div {
    private ComboBox<ExchangeOperation> operationSelect = new ComboBox<>("Operacja wymiany");
    private TextField volumeInput = new TextField("Wolumen Tranzakcji");
    private Label valueLabel = new Label("Wartość Tranzakcji");
    private Button addButton = new Button("Dodaj");
    private Button cancelButton = new Button("Anuluj");

    public TransactionForm() {
        operationSelect.setItems(ExchangeOperation.values());

        HorizontalLayout buttonLayout = new HorizontalLayout(addButton, cancelButton);
        add(operationSelect, volumeInput, valueLabel, buttonLayout);

        // add a listener to the volume input to update the value label when the volume changes
        volumeInput.addValueChangeListener(e -> updateValueLabel());
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

    private void updateValueLabel() {
        ExchangeOperation operation = getOperation();
        Double volume = getVolume();

        if (operation != null && volume != null) {
            CantorDto cantorDto = CantorService.getInstance().getCantor();
            Double value = null;

            switch (operation) {
                case PLN_TO_EUR -> value = volume * cantorDto.getSellingRateEUR();
                case EUR_TO_PLN -> value = volume * cantorDto.getPurchaseRateEUR();
                case PLN_TO_USD -> value = volume * cantorDto.getSellingRateUSD();
                case USD_TO_PLN -> value = volume * cantorDto.getPurchaseRateUSD();
                case PLN_TO_CHF -> value = volume * cantorDto.getSellingRateCHF();
                case CHF_TO_PLN -> value = volume * cantorDto.getPurchaseRateCHF();
                case PLN_TO_GBP -> value = volume * cantorDto.getSellingRateGBP();
                case GBP_TO_PLN -> value = volume * cantorDto.getPurchaseRateGBP();
            }

            valueLabel.setText(String.format("Wartość tranzakcji: %.2f", value));
        } else {
            valueLabel.setText("Wartość tranzakcji: ");
        }
    }

    public void clear() {
        operationSelect.clear();
        volumeInput.setValue("");
        valueLabel.setText(String.format("Wartość tranzakcji:"));
    }
}
