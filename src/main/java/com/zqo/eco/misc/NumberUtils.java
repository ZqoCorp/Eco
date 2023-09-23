package com.zqo.eco.misc;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class NumberUtils {
    public static BigDecimal parseBigDecimal(final String input) {
        try {
            return new BigDecimal(input);
        } catch (NumberFormatException e) {
            return BigDecimal.ZERO;
        }
    }

    public static String formatMoney(final BigDecimal money) {
        final String[] suffixes = {
            "", "K", "M", "B", "T", "Qa", "Qi", "Sx", "Sp", "Oc", "No", "Dc", "Ud", "Dd", "Td", "Qd", "Qt", "Qn"
        };

        int suffixIndex = 0;
        BigDecimal moneyCopy = new BigDecimal(money.toString());

        while (moneyCopy.compareTo(new BigDecimal("1000")) >= 0 && suffixIndex < suffixes.length - 1) {
            moneyCopy = moneyCopy.divide(new BigDecimal("1000"), 2, RoundingMode.HALF_UP);
            suffixIndex++;
        }

        if (suffixIndex > 0) {
            if (moneyCopy.compareTo(new BigDecimal("10")) < 0) {
                return String.format("%.1f%s", moneyCopy, suffixes[suffixIndex]);
            } else {
                return String.format("%.0f%s", moneyCopy, suffixes[suffixIndex]);
            }
        } else {
            return String.format("%.2f", moneyCopy);
        }
    }
}
