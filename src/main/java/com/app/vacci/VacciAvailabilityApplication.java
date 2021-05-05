package com.app.vacci;

import lombok.extern.slf4j.Slf4j;

import java.awt.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
public class VacciAvailabilityApplication {

    private static final String pin = "211004";
    private static final String date = "05-05-2021";
    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public static void main(String[] args) throws AWTException {
		scheduler.scheduleAtFixedRate(new Availability(pin, date), 30, 30, TimeUnit.SECONDS);
	}

}
