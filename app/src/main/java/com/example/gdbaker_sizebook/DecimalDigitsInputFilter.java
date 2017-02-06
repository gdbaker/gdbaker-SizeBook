package com.example.gdbaker_sizebook;

import android.text.InputFilter;
import android.text.Spanned;

import java.util.regex.Matcher;
import java.util.regex.Pattern;




/**
 * Taken from http://stackoverflow.com/questions/5357455/limit-decimal-places-in-android-edittext
 * Feb 5, 2017 11:45
 *
 * Provides a way to limit decimal places
 */
public class DecimalDigitsInputFilter implements InputFilter {

    Pattern mPattern;

    public DecimalDigitsInputFilter(int digitsBeforeZero,int digitsAfterZero) {
        mPattern=Pattern.compile("[0-9]{0," + (digitsBeforeZero-1) + "}+((\\.[0-9]{0," + (digitsAfterZero-1) + "})?)||(\\.)?");
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

        Matcher matcher=mPattern.matcher(dest);
        if(!matcher.matches())
            return "";
        return null;
    }

}
