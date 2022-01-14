package com.ponser2000.parserzakupki.utils;

import lombok.experimental.UtilityClass;

/**
 * @author Sergey Ponomarev on 21.09.2021
 * @project parser-zakupki/com.ponser2000.parserzakupki.utils
 */
@UtilityClass
public class ProjectConstants {

    public static final int RECORDS_PER_PAGE = 50;
    public static final String URL_EIS = "https://zakupki.gov.ru/";
    public static final String URL_MOS = "https://zakupki.mos.ru/";
    public static final String FROM_ADDRESS = "from email address";
    public static final String[] TO_ADDRESS = {
            "to email address 1",
            "to email address 2",
            "to email address 3",
            "to email address 4"
    };
}
