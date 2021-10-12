package com.ponser2000.parserzakupki.service;

/**
 * @author Sergey Ponomarev on 15.09.2021
 * @project parser-zakupki/com.ponser2000.parserzakupki.service
 */
public enum SearchingPhrase {
    NONE(""),
    MFU("картридж");

    private String searchingPhrase;

    SearchingPhrase(String searchingPhrase) {
        this.searchingPhrase = searchingPhrase;
    }

    public String getSearchingPhrase() {
        return searchingPhrase;
    }

}
