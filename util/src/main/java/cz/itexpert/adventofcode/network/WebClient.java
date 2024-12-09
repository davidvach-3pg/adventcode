package cz.itexpert.adventofcode.network;

import java.net.*;
import java.net.http.HttpClient;
import java.time.Duration;

public class WebClient {
  public static HttpClient getClient() {
    String session = "53616c7465645f5fb8b54bc2245630cdc64b241fb45d6a6eca856eae375086e8a7cfb5020845c7b2523863ed603275502ea4f4a6babb54d16035a78a6fa37ae4";
    CookieHandler.setDefault(new CookieManager());

    HttpCookie sessionCookie = new HttpCookie("session", session);
    sessionCookie.setPath("/");
    sessionCookie.setVersion(0);

    try {
      ((CookieManager) CookieHandler.getDefault()).getCookieStore().add(new URI("https://adventofcode.com"), sessionCookie);
    } catch (URISyntaxException e) {
      throw new IllegalStateException(e);
    }

    return HttpClient.newBuilder()
        .cookieHandler(CookieHandler.getDefault())
        .connectTimeout(Duration.ofSeconds(10))
        .build();
  }
}
