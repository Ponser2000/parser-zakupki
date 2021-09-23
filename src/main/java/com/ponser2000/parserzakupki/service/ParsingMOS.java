package com.ponser2000.parserzakupki.service;

import com.ponser2000.parserzakupki.data.chrome.RequestUrlMOS;
import com.ponser2000.parserzakupki.service.dto.FieldsOrder;
import com.ponser2000.parserzakupki.service.dto.Order;
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
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Service;

/**
 * @author Sergey Ponomarev on 16.09.2021
 * @project parser-zakupki/com.ponser2000.parserzakupki.service
 */
@Service
public class ParsingMOS {
  private final RequestUrlMOS requestUrl;

  public ParsingMOS() {
    this.requestUrl = new RequestUrlMOS();
  }

  @SneakyThrows
  public void parsingOrders(LocalDateTime today, WebDriver webDriver,List<String> files){
    List<Order> ordersList = new ArrayList<>();

    String publishDateTo = today.format(DateTimeFormatter.ofPattern("dd.MM.uuuu"));
    String publishDateFrom = today.minusDays(0).format(DateTimeFormatter.ofPattern("dd.MM.uuuu"));

    String searchPhrase = SearchingPhrase.NONE.getSearchingPhrase();

    String url = requestUrl.get(1,ProjectConstants.RECORDS_PER_PAGE,publishDateFrom,publishDateTo,searchPhrase);

    webDriver.get(url);

    Thread.sleep(5000);
    Document document = Jsoup.parse(webDriver.getPageSource());

    Elements elementsSpanPaginator = document.getElementsByAttributeValue("type","pageItem");

    int pages = elementsSpanPaginator.size() > 0 ? Integer.parseInt(
          Objects.requireNonNull(elementsSpanPaginator.last()).ownText()) : 1;

    for (int i = 1; i < pages+1; i++) {

      url = requestUrl.get(i,ProjectConstants.RECORDS_PER_PAGE,publishDateFrom,publishDateTo, searchPhrase);

      webDriver.get(url);
      Thread.sleep(5000);
      document = Jsoup.parse(webDriver.getPageSource());

      Elements elementsByAttrubute = document.getElementsByAttributeValue("class",
          "PublicListStyles__PublicListContentContainer-sc-1q0smku-1 foobhj").get(0).children();

      for (Element element : elementsByAttrubute) {

        Map<FieldsOrder,String> fieldsOrder = new HashMap<>();

        Elements numDescr = element.getElementsByAttributeValue("class",
            "ui header CardStyles__MainInfoNameHeader-sc-18miw4v-7 drFPNq");

        String link = numDescr.size() > 0 ? numDescr.get(0).getElementsByTag("a").get(0).attributes().get("href")
            : "";

        String number = link.split("/")[link.split("/").length-1];

        String objectDescr = numDescr.size() > 0 ? numDescr.get(0).text() : "";

        Elements zakazchikDescr = element.getElementsByAttributeValue("class",
            "ui tiny header PurchaseCardStyles__MainInfoCustomerHeader-sc-3hfhop-0 dzCDib");
        String zakazchik =
            zakazchikDescr.size() > 0 ? zakazchikDescr.get(0).getElementsByTag("a").get(0).text()
                : "-----";

        Elements purchaseMethodDescr = element.getElementsByAttributeValue("class","CardStyles__FlexContainer-sc-18miw4v-0 gYQAXs CardStyles__MainInfoTypeHeader-sc-18miw4v-2 iOqMyw");
        String purchaseMethod = purchaseMethodDescr.size() > 0 ? purchaseMethodDescr.get(0).text() : "";

        Elements priceDescr = element.getElementsByAttributeValue("class", "ui blue header CardStyles__PriceInfoNumber-sc-18miw4v-8 jzBqrB");

        Double price = priceDescr.size() > 0 ?  (new PriceParse()).toDouble(priceDescr.get(0).ownText()): 0.0;

        Elements datesDescr = element.getElementsByAttributeValue("class", "CardStyles__AdditionalInfoContainer-sc-18miw4v-9 irbRxZ").get(0).children();

        String law = datesDescr.size() > 1 ? datesDescr.get(1).text() : "";

        purchaseMethod = law + ", " + purchaseMethod;

        String razmeschenie = datesDescr.size() > 3 ? datesDescr.get(2).text().split(" ")[1] : "00.00.0000";
        String updated = datesDescr.size()>2 ? datesDescr.get(1).ownText().replaceAll("\n","") : "00.00.0000";
        String okonchanie = datesDescr.size() > 3 ? datesDescr.get(2).text().split(" ")[3] : "00.00.0000";

        fieldsOrder.put(FieldsOrder.NUMBER,number);
        fieldsOrder.put(FieldsOrder.URL,link);
        fieldsOrder.put(FieldsOrder.PURCHASE_METHOD,purchaseMethod);
        fieldsOrder.put(FieldsOrder.OBJECT_DESCRIPTION,objectDescr);
        fieldsOrder.put(FieldsOrder.CUSTOMER, zakazchik);
        fieldsOrder.put(FieldsOrder.DATE_OF_ALLOCATED, razmeschenie);
        fieldsOrder.put(FieldsOrder.DATE_OF_UPDATED, updated);
        fieldsOrder.put(FieldsOrder.DATE_OF_ENDED, okonchanie);

        Order order = new Order(fieldsOrder, price);
        ordersList.add(order);
      }
    }

    String tmpDir = SystemUtils.JAVA_IO_TMPDIR;
    String fileName = SystemUtils.IS_OS_WINDOWS ? tmpDir + "orderEIS.xls" : tmpDir + "/" + "orderMOS.xls";

    ExelWorker exelWorker = new ExelWorker();
    exelWorker.createWorkbook(ordersList,fileName);

    files.add(fileName);

  }
}
