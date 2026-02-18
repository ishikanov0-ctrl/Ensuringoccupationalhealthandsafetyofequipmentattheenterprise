package com.example.ensuringoccupationalhealthandsafetyofequipmentattheenterprise;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class DateTextWatcher implements TextWatcher {
    private EditText editText;
    private boolean isUpdating = false;
    private String lastValidText = "";

    public DateTextWatcher(EditText editText) {
        this.editText = editText;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {}

    @Override
    public void afterTextChanged(Editable s) {
        if (isUpdating) {
            return;
        }

        String input = s.toString().replaceAll("[^0-9]", "");

        if (input.length() > 8) {
            input = input.substring(0, 8);
        }

        StringBuilder formatted = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            if (i == 2 || i == 4) {
                formatted.append("-");
            }
            formatted.append(input.charAt(i));
        }

        isUpdating = true;
        editText.setText(formatted.toString());
        editText.setSelection(formatted.length());
        isUpdating = false;

        lastValidText = formatted.toString();
    }

    public String getLastValidText() {
        return lastValidText;
    }
}