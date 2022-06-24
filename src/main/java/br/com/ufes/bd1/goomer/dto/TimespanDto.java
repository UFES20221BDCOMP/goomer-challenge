package br.com.ufes.bd1.goomer.dto;

import br.com.ufes.bd1.goomer.model.Timespan;
import br.com.ufes.bd1.goomer.model.Weekday;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TimespanDto {

    @JsonProperty("days")
    private String days;

    @JsonProperty("time")
    private String time;


    public Timespan toEntity() {
        Timespan timespan = new Timespan();

        try {
            String[] daysArray = days.split("-");
            Weekday weekdayStart = Weekday.fromName(daysArray[0]);
            Weekday weekdayEnd = Weekday.fromName(daysArray[1]);

            timespan.setWeekdayStart(weekdayStart);
            timespan.setWeekdayEnd(weekdayEnd);
        }
        catch (Exception e) {
            throw new IllegalArgumentException("Invalid 'days' format");
        }

        try {
            String[] timeArray = time.split("-");
            LocalTime.parse(timeArray[0], DateTimeFormatter.ofPattern("HH:mm"));
            LocalTime.parse(timeArray[1], DateTimeFormatter.ofPattern("HH:mm"));

            timespan.setTimeStart(timeArray[0]);
            timespan.setTimeEnd(timeArray[1]);
        }
        catch (Exception e) {
            throw new IllegalArgumentException("Invalid time format");
        }

        return timespan;
    }

    public static TimespanDto fromEntity(Timespan timespan) {
        TimespanDto timespanDto = new TimespanDto();
        timespanDto.days = timespan.getWeekdayStart().getName() + "-" + timespan.getWeekdayEnd().getName();
        timespanDto.time = timespan.getTimeStart() + "-" + timespan.getTimeEnd();
        return timespanDto;
    }
}
