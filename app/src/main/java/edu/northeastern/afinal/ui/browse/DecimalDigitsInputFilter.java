package edu.northeastern.afinal.ui.browse;

import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;

public class DecimalDigitsInputFilter implements InputFilter {
    // Maximum digits after the decimal point
    private static final int MAX_DIGITS_AFTER_DECIMAL = 2;

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        StringBuilder builder = new StringBuilder(dest);
        builder.replace(dstart, dend, source.subSequence(start, end).toString());

        if (!isValid(builder.toString())) {
            return "";
        }

        return null;
    }

    private boolean isValid(String input) {
        if (TextUtils.isEmpty(input)) {
            return true;
        }

        // Regular expression to match a non-negative decimal with up to two decimal places
        String regex = "^(\\d*\\.?\\d{0," + MAX_DIGITS_AFTER_DECIMAL + "})?$";
        return input.matches(regex);
    }
}