package com.app.vacci;

import com.app.vacci.dto.Response;
import com.app.vacci.dto.Session;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.awt.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@AllArgsConstructor
public class Availability implements Runnable {

    private static final String URL = "https://cdn-api.co-vin.in/api/v2/appointment/sessions/public/calendarByPin?pincode=211004&date=05-05-2021";
    private String pin;
    private String date;

    @SneakyThrows
    @Override
    public void run() {
        checkAvailability();
    }

    public void checkAvailability() throws AWTException {
        Response response = getAvailability(pin, date);
        List<Session> sessionList = new ArrayList<>();
        response.getCenters()
                .stream()
                .forEach(c -> sessionList.addAll(
                        c.getSessions()
                                .stream().filter(session -> session.getAvailableCapacity() > 0)
                                .collect(Collectors.toList())));
        log.info(sessionList.toString());
        if (SystemTray.isSupported() && !sessionList.isEmpty()) {
            TrayIconNotification td = new TrayIconNotification();
            td.displayTray(sessionList);
        } else {
            log.info("Nothing available for pin -{}, and date -{}", pin, date);
        }
    }

    public Response getAvailability(String pin, String date) {
        RestTemplate restTemplate = new RestTemplate();
        System.out.print(Instant.now().toString());
        String resourceUrl
                = URL + "?pincode=" + pin + "&date=" + date;
        ResponseEntity<Response> response
                = restTemplate.getForEntity(resourceUrl, Response.class);
        return response.getBody();
    }


}
