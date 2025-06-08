package br.com.ufv.inf311.solicitaedu;

public class CalendarEvent {
    public int startHour, startMin,
               endHour, endMin;

    public int day, month, year;

    public CalendarEvent(String datePart, String startTimeStr, String endTimeStr) {
        String[] dateParts = datePart.split("/");
        day = Integer.parseInt(dateParts[0]);
        month = Integer.parseInt(dateParts[1]);
        year = Integer.parseInt(dateParts[2]);

        String[] startTimeParts = startTimeStr.split(":");
        startHour = Integer.parseInt(startTimeParts[0]);
        startMin  = Integer.parseInt(startTimeParts[1]);

        String[] endTimeParts = endTimeStr.split(":");
        endHour = Integer.parseInt(endTimeParts[0]);
        endMin  = Integer.parseInt(endTimeParts[1]);
    }
}
