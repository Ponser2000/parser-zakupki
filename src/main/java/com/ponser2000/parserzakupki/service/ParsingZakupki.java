package com.ponser2000.parserzakupki.service;

import com.ponser2000.parserzakupki.service.jsoup.impl.JsoupFacadeServiceImpl;
import com.ponser2000.parserzakupki.service.smtp.impl.EmailServiceImpl;
import java.time.LocalDateTime;
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
  private final EmailServiceImpl emailSender ;

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


  //@Scheduled(fixedRate = 30000)
  @Scheduled(cron = "0 15 5 */1 * *", zone = "Europe/Moscow")
  public void handlePage() throws InterruptedException {
    LocalDateTime today = LocalDateTime.now();
    log.info("Starting parsing EIS");
    //today = LocalDateTime.now();
    //System.out.println(today);
    ParsingEIS parsingEIS = new ParsingEIS();
    parsingEIS.parsingOrders(jsoup, emailSender);
    log.info("Ended parsing EIS");

    log.info("Starting parsing MOS");
    //today = LocalDateTime.now();
    //System.out.println(today);
    ParsingMOS parsingMOS = new ParsingMOS();
    parsingMOS.parsingOrders(jsoup, emailSender,webDriver);
    log.info("Ended parsing MOS");
  }
}
