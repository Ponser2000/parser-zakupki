package com.ponser2000.parserzakupki.utils;

/**
 * @author Sergey Ponomarev on 14.09.2021
 * @project parser-zakupki/com.ponser2000.parserzakupki.utils
 */
public class PriceParse {

    public Double toDouble(String price) {
        return Double.parseDouble(price.replaceAll(" ", "")
                .replaceAll("₽", "")
                .replaceAll(",", ".")
                .replaceAll("€", "")
                .replaceAll("\\$", ""));
    }
}
