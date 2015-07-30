package com.wayruha.validateFxmlInput;

import javafx.scene.control.TextField;

/**
 * Created by Roman on 27.07.2015.
 */
public class NumberTextField extends TextField {
    public NumberTextField() {
        this.setPromptText("Номер колонки");
    }

    @Override
    public void replaceText(int start, int end, String text) {
        if(text.matches("[0-9]") || text.isEmpty())
        super.replaceText(start, end, text);
    }

    @Override
    public void replaceSelection(String replacement) {
        super.replaceSelection(replacement);
    }
}
