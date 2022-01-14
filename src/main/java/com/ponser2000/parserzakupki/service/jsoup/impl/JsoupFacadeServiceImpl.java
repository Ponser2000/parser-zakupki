package com.ponser2000.parserzakupki.service.jsoup.impl;

import com.ponser2000.parserzakupki.data.chrome.ChromeHeaders;
import com.ponser2000.parserzakupki.service.jsoup.JsoupFacadeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.util.Map;

/**
 * @author Sergey Ponomarev on 06.09.2021
 * @project parser-zakupki/com.ponser2000.parserzakupki.service.jsoup.impl
 */
@Service
@Slf4j
public class JsoupFacadeServiceImpl implements JsoupFacadeService {


    public JsoupFacadeServiceImpl() {

    }

    @Override
    public Elements parsePage(String url) {
        return null;
    }

    @Override
    public Document parsePageToDocument(String url) {
        try {

            if (!url.startsWith("http"))
                url = "https://" + url;

            String authority = new URL(url).getAuthority();

            Map<String, String> headers = new ChromeHeaders().get();

            return Jsoup.connect(url).
                    headers(headers).
                    get();
        } catch (IOException e) {
            log.error("Caught exception while parsing {}", url);
            return new Document(StringUtils.EMPTY);
        }
    }
}
