package com.albert.form;

import com.albert.domain.dto.ExchangeOperation;
import com.albert.domain.dto.TransactionDto;
import com.albert.service.CartService;
import com.albert.service.TransactionService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;

import static com.albert.Views.UserPage.selectedUser;

public class CartForm extends FormLayout {

    private ComboBox<ExchangeOperation> exchangeOperationComboBox = new ComboBox<>("Operacja wymiany");
    private TextField transactionVolumeField = new TextField("Wolumen");

    private Button saveButton = new Button("Zapisz");
    private Button cancelButton = new Button("Anuluj");

    private TransactionDto transactionDto;

    private final TransactionService transactionService = TransactionService.getInstance();
    private final CartService cartService = CartService.getInstance();

    private Grid<TransactionDto> transactionGrid;

    public CartForm(Grid<TransactionDto> transactionGrid) {
        this.transactionGrid = transactionGrid;
        exchangeOperationComboBox.setItems(ExchangeOperation.values());
        add(exchangeOperationComboBox, transactionVolumeField, new HorizontalLayout(saveButton, cancelButton));

        setTransactionDto(null);

        saveButton.addClickListener(event -> saveTransaction());
        cancelButton.addClickListener(event -> cancel());
    }

    public void setTransactionDto(TransactionDto transactionDto) {
        this.transactionDto = transactionDto;
        if (transactionDto != null) {
            exchangeOperationComboBox.setValue(transactionDto.getExchangeOperation());
            transactionVolumeField.setValue(String.valueOf(transactionDto.getTransactionVolume()));
        } else {
            exchangeOperationComboBox.setValue(null);
            transactionVolumeField.clear();
        }
    }

    private void saveTransaction() {
        if (transactionDto == null) {
            transactionDto = new TransactionDto();
            transactionDto.setCartId(selectedUser.getCartId());
        }

        try {
            double volume = Double.parseDouble(transactionVolumeField.getValue());

            transactionDto.setExchangeOperation(exchangeOperationComboBox.getValue());
            transactionDto.setTransactionVolume(volume);

            transactionService.createTransaction(transactionDto);
            transactionGrid.setItems(cartService.getTransactionInCart(selectedUser.getCartId()));
            setTransactionDto(null);

        } catch (NumberFormatException e) {
            transactionVolumeField.setErrorMessage("Nieprawidłowy format wolumenu");
        }
    }

    private void cancel() {
        setTransactionDto(null);
    }
}




