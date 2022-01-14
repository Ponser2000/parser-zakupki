package com.ponser2000.parserzakupki.data.chrome;

import static com.ponser2000.parserzakupki.utils.ProjectConstants.URL_EIS;

/**
 * @author Sergey Ponomarev on 08.09.2021
 * @project parser-zakupki/com.ponser2000.parserzakupki.data.chrome
 */
public class RequestUrlEIS {

    public String get(int page, int recordsPerPage, String publishDateFrom, String publishDateTo, String searchPhrase) {
        return URL_EIS + "epz/order/extendedsearch/results.html?" +
                "searchString=" + searchPhrase + "&" +
                "morphology=on&" +
                "search-filter=%D0%94%D0%B0%D1%82%D0%B5+%D1%80%D0%B0%D0%B7%D0%BC%D0%B5%D1%89%D0%B5%D0%BD%D0%B8%D1%8F&" +
                "pageNumber=" + page + "&" +
                "sortDirection=false&" +
                "recordsPerPage=_" + recordsPerPage + "&" +
                "showLotsInfoHidden=false&" +
                "savedSearchSettingsIdHidden=&" +
                "sortBy=PUBLISH_DATE&" +
                "fz44=on&fz223=on&fz94=on&af=on&" +
                "placingWayList=&" +
                "selectedLaws=&" +
                "priceFromGeneral=&" +
                "priceFromGWS=&" +
                "priceFromUnitGWS=&" +
                "priceToGeneral=&" +
                "priceToGWS=&" +
                "priceToUnitGWS=&" +
                "currencyIdGeneral=-1&" +
//        "publishDateFrom=" + publishDateFrom+"&"+
//        "publishDateTo=" + publishDateTo+"&"+
                "updateDateFrom=" + publishDateFrom + "&" +
                "updateDateTo=" + publishDateTo + "&" +
                "applSubmissionCloseDateFrom=&" +
                "applSubmissionCloseDateTo=&" +
                "customerIdOrg=&" +
                "customerFz94id=&" +
                "customerTitle=&" +
                "customerPlace=5277349%2C5277353%2C8408974%2C8408975&" +
                "customerPlaceCodes=01000000000%2C23000000000%2C91000000000%2C92000000000&" +
                "okpd2Ids=&okpd2IdsCodes=&OrderPlacementSmallBusinessSubject=on&OrderPlacementRnpData=on&OrderPlacementExecutionRequirement=on&orderPlacement94_0=0&orderPlacement94_1=0&orderPlacement94_2=0";
    }
}
