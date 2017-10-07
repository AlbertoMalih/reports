package com.example.reports.utils;

import android.app.Activity;
import android.util.Log;

import com.example.reports.MainActivity;
import com.example.reports.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Created by Олег on 01.10.2017.
 */

public class Utils {

    public static CharSequence[] convertIntegerMonthsToString(Activity activity, Integer[] months) {
        Arrays.sort(months);
        CharSequence[] monthsInStrings = activity.getResources().getStringArray(R.array.months);
        CharSequence[] result = new CharSequence[months.length];
        for (int i = 0; i < months.length; i++) {
            result[i] = monthsInStrings[months[i]];//- 1 where months have from 1
        }
        return result;
    }

    public static List<String> convertIntegerToCharSequense(Collection<Integer> integers) {
        List<String> result = new ArrayList<>(integers.size());
        for (Integer integerObject : integers) {
            Log.d(MainActivity.TAG, integerObject + "  - integer ");
            result.add(String.valueOf(integerObject));
        }
        return result;
    }

        public static List<Integer> convertStringToInteger(List<String> charSequences) {
        List<Integer> result = new ArrayList<>(charSequences.size());
        for (String charSequence : charSequences) {
            Log.d(MainActivity.TAG, charSequence + "  - string ");
            result.add(Integer.parseInt(charSequence));
        }
        return result;
    }
}
