package com.ponser2000.parserzakupki.service;

import com.ponser2000.parserzakupki.data.chrome.RequestUrlEIS;
import com.ponser2000.parserzakupki.service.dto.FieldsOrder;
import com.ponser2000.parserzakupki.service.dto.Order;
import com.ponser2000.parserzakupki.service.jsoup.impl.JsoupFacadeServiceImpl;
import com.ponser2000.parserzakupki.utils.ExelWorker;
import com.ponser2000.parserzakupki.utils.PriceParse;
import com.ponser2000.parserzakupki.utils.ProjectConstants;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import lombok.SneakyThrows;
import org.apache.commons.lang3.SystemUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

/**
 * @author Sergey Ponomarev on 16.09.2021
 * @project parser-zakupki/com.ponser2000.parserzakupki.service
 */
@Service
public class ParsingEIS {

    private final RequestUrlEIS requrstUrl;


    public ParsingEIS() {
        this.requrstUrl = new RequestUrlEIS();
    }

    @SneakyThrows
    public void parsingOrders(LocalDateTime today, JsoupFacadeServiceImpl jsoup, List<String> files) {
        List<Order> ordersList = new ArrayList<>();

        String publishDateTo = today.format(DateTimeFormatter.ofPattern("dd.MM.uuuu"));
        String publishDateFrom = today.minusDays(0).format(DateTimeFormatter.ofPattern("dd.MM.uuuu"));

        String searchPhrase = SearchingPhrase.NONE.getSearchingPhrase();

        String url = requrstUrl.get(1, ProjectConstants.RECORDS_PER_PAGE, publishDateFrom, publishDateTo, searchPhrase);

        Document document = jsoup.parsePageToDocument(url);

        Elements elementsSpanPaginator = document.select("span.link-text");

        int pages = elementsSpanPaginator.size() > 0 ? Integer.parseInt(
                Objects.requireNonNull(elementsSpanPaginator.last()).ownText()) : 1;

        for (int i = 1; i < pages + 1; i++) {

            url = requrstUrl.get(i, ProjectConstants.RECORDS_PER_PAGE, publishDateFrom, publishDateTo, searchPhrase);

            document = jsoup.parsePageToDocument(url);
            Elements elementsByAttrubute = document.getElementsByAttributeValue("class",
                    "search-registry-entry-block box-shadow-search-input");

            for (Element element : elementsByAttrubute) {

                Map<FieldsOrder, String> fieldsOrder = new HashMap<>();

                Elements numDescr = element.getElementsByAttributeValue("class",
                        "registry-entry__header-mid__number");
                String number = numDescr.size() > 0 ? numDescr.get(0).getElementsByTag("a").get(0).ownText()
                        : "0000000";

                String link = numDescr.size() > 0 ? numDescr.get(0).getElementsByTag("a").get(0).attributes().get("href")
                        : "";

                link = "https://zakupki.gov.ru/" + link.replaceAll("https://zakupki.gov.ru/", "");

                Elements objDescr = element.getElementsByAttributeValue("class",
                        "registry-entry__body-value");
                String objectDescr = objDescr.size() > 0 ? objDescr.get(0).text() : "";

                Elements zakazchikDescr = element.getElementsByAttributeValue("class",
                        "registry-entry__body-href");
                String zakazchik =
                        zakazchikDescr.size() > 0 ? zakazchikDescr.get(0).getElementsByTag("a").get(0).ownText()
                                : "-----";

                Elements purchaseMethodDescr = element.getElementsByAttributeValue("class", "col-9 p-0 registry-entry__header-top__title text-truncate");
                String purchaseMethod = purchaseMethodDescr.size() > 0 ? purchaseMethodDescr.get(0).text() : "";

                Elements priceDescr = element.getElementsByAttributeValue("class", "price-block__value");

                Double price = priceDescr.size() > 0 ? (new PriceParse()).toDouble(priceDescr.get(0).ownText()) : 0.0;

                Elements datesDescr = element.getElementsByAttributeValue("class", "data-block__value");

                String razmeschenie = datesDescr.size() > 2 ? datesDescr.get(0).ownText().replaceAll("\n", "") : "00.00.0000";
                String updated = datesDescr.size() > 2 ? datesDescr.get(1).ownText().replaceAll("\n", "") : "00.00.0000";
                String okonchanie = datesDescr.size() > 2 ? datesDescr.get(2).ownText().replaceAll("\n", "") : "00.00.0000";


                fieldsOrder.put(FieldsOrder.NUMBER, number);
                fieldsOrder.put(FieldsOrder.URL, link);
                fieldsOrder.put(FieldsOrder.PURCHASE_METHOD, purchaseMethod);
                fieldsOrder.put(FieldsOrder.OBJECT_DESCRIPTION, objectDescr);
                fieldsOrder.put(FieldsOrder.CUSTOMER, zakazchik);
                fieldsOrder.put(FieldsOrder.DATE_OF_ALLOCATED, razmeschenie);
                fieldsOrder.put(FieldsOrder.DATE_OF_UPDATED, updated);
                fieldsOrder.put(FieldsOrder.DATE_OF_ENDED, okonchanie);

                Order order = new Order(fieldsOrder, price);
                ordersList.add(order);
            }
        }

        ExelWorker exelWorker = new ExelWorker();

        String tmpDir = SystemUtils.JAVA_IO_TMPDIR;
        String fileName = SystemUtils.IS_OS_WINDOWS ? tmpDir + "orderEIS.xls" : tmpDir + "/" + "orderEIS.xls";

        files.add(fileName);

        exelWorker.createWorkbook(ordersList, fileName);
    }

}
