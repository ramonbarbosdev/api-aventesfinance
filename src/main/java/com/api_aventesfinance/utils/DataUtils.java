package com.api_aventesfinance.utils;

import java.time.LocalDate;

public class DataUtils {

    public static String formatarAnoMes(LocalDate data) {

        String anoMes = String.format("%04d%02d", data.getYear(),
                data.getMonthValue());

        return anoMes;
      
    }

}
