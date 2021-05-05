package com.app.vacci;

import com.app.vacci.dto.Session;

import java.awt.*;
import java.awt.TrayIcon.MessageType;
import java.util.List;

public class TrayIconNotification {

    public void displayTray(List<Session> sessions) throws AWTException {
        //Obtain only one instance of the SystemTray object
        SystemTray tray = SystemTray.getSystemTray();
        //If the icon is a file
        Image image = Toolkit.getDefaultToolkit().createImage("icon.png");
        //Alternative (if the icon is on the classpath):
        //Image image = Toolkit.getDefaultToolkit().createImage(getClass().getResource("icon.png"));
        TrayIcon trayIcon = new TrayIcon(image, "Vaccination Availability ");
        trayIcon.setImageAutoSize(true);
        trayIcon.setToolTip("Vaccination Availability ");
        tray.add(trayIcon);

        trayIcon.displayMessage("Vaccination Availability ", getPopUpText(sessions), MessageType.INFO);
    }

    String getPopUpText(List<Session> sessions) {
        StringBuilder stringBuilder = new StringBuilder();
        sessions.forEach(session -> stringBuilder.append(session.getDate()).append(" : ").append(session.getAvailableCapacity()).append(" \n "));
        return stringBuilder.toString();
    }
}