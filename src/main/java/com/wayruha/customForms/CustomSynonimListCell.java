package com.wayruha.customForms;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;


public class CustomSynonimListCell extends ListCell {
    private TextField textField;

    @Override
    public void startEdit() {
        if (!isEditable() || !getListView().isEditable()) {
            return;
        }
        super.startEdit();
        if (isEditing()) {
            if (textField == null) {
                textField = new TextField(getItem().toString());
                textField.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        commitEdit(textField.getText());
                    }
                });
            }
        }

        textField.setText(getItem().toString());
        setText(null);

        setGraphic(textField);
        textField.selectAll();
    }


    @Override
    protected void updateItem(Object item, boolean empty) {
        super.updateItem(item, empty);
        if (isEmpty()) {
            setText(null);
            setGraphic(null);
        } else {
            if (!isEditing()) {
                if (textField != null) {
                    setText(textField.getText());
                } else {
                    setText(item.toString());
                }
                setGraphic(null);
            }
        }
    }
}
