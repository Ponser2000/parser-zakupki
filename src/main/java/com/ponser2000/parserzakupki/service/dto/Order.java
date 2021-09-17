package com.ponser2000.parserzakupki.service.dto;

import java.util.Map;

/**
 * @author Sergey Ponomarev on 06.09.2021
 * @project parser-zakupki/com.ponser2000.parserzakupki.service.dto
 */
public final record Order (
    Map<FieldsOrder,String> fieldsOrder,
    Double price

    ) {
}
