package com.ponser2000.parserzakupki.data.chrome;

import static com.ponser2000.parserzakupki.utils.ProjectConstants.URL_MOS;

/**
 * @author Sergey Ponomarev on 16.09.2021
 * @project parser-zakupki/com.ponser2000.parserzakupki.data.chrome
 */
public class RequestUrlMOS {

    public String get(int page, int recordsPerPage, String publishDateFrom, String publishDateTo, String searchPhrase) {

        return URL_MOS + "purchase/list?" +
                "page=" + page +
                "&perPage=" + recordsPerPage +
                "&sortField=PublishDate" +
                "&sortDesc=true" +
                "&filter=%7B%22regionPaths%22%3A%5B%22.1.148668.%22%5D%2C%22publishDateGreatEqual%22%3A%22" + publishDateFrom +
                "%2000%3A00%3A00%22%2C%22publishDateLessEqual%22%3A%22" + publishDateTo +
                "%2023%3A59%3A59%22%2C%22auctionSpecificFilter%22%3A%7B%22stateIdIn%22%3A%5B19000002%5D%7D%2C%22needSpecificFilter%22%3A%7B%22stateIdIn%22%3A%5B20000002%5D%7D%2C%22" +
                "tenderSpecificFilter%22%3A%7B%22stateIdIn%22%3A%5B5%5D%7D%7D&state=%7B%22currentTab%22%3A1%7D";
    }

}
