package com.ponser2000.parserzakupki.service.jsoup;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * @author Sergey Ponomarev on 06.09.2021
 * @project parser-zakupki/com.ponser2000.parserzakupki.service.jsoup
 */
public interface JsoupFacadeService {
  Elements parsePage(String url);
  Document parsePageToDocument(String url);
}
