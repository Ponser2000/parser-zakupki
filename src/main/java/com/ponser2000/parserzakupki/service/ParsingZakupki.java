package com.ponser2000.parserzakupki.service;

import com.ponser2000.parserzakupki.service.jsoup.impl.JsoupFacadeServiceImpl;
import com.ponser2000.parserzakupki.service.smtp.impl.EmailServiceImpl;
import com.ponser2000.parserzakupki.utils.ProjectConstants;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * @author Sergey Ponomarev on 06.09.2021
 * @project parser-zakupki/com.ponser2000.parserzakupki.service
 */
@Service
@Slf4j
public class ParsingZakupki {

    private final WebDriver webDriver;
    private final JsoupFacadeServiceImpl jsoup;
    private final EmailServiceImpl emailSender;

    public ParsingZakupki(
            JsoupFacadeServiceImpl jsoup,
            EmailServiceImpl emailSender) {
        this.jsoup = jsoup;
        this.emailSender = emailSender;

        ChromeOptions options = new ChromeOptions().setHeadless(true);
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-extensions");
        options.addArguments("--whitelisted-ips=");
        options.addArguments("--no-sandbox");
        this.webDriver = new ChromeDriver(options);
    }

    @SneakyThrows
    //@Scheduled(fixedRate = 600000)
    @Scheduled(cron = "0 30 9 */1 * *", zone = "Europe/Moscow")
    public void handlePage() {
        List<String> files = new ArrayList<>();

        LocalDateTime today = LocalDateTime.now().minusDays(1);

        log.info("Starting parsing EIS");
        ParsingEIS parsingEIS = new ParsingEIS();
        parsingEIS.parsingOrders(today, jsoup, files);
        log.info("Ended parsing EIS");

        log.info("Starting parsing MOS");
        ParsingMOS parsingMOS = new ParsingMOS();
        parsingMOS.parsingOrders(today, webDriver, files);
        log.info("Ended parsing MOS");

        String subject = "Обновленные закупки за " + today.format(DateTimeFormatter.ofPattern("dd.MM.uuuu"));

        emailSender.sendEmailWithAttachment(ProjectConstants.TO_ADDRESS, subject, subject, files);
    }
}
