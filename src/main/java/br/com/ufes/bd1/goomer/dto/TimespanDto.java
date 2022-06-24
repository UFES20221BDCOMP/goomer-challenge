package br.com.ufes.bd1.goomer.dto;

import br.com.ufes.bd1.goomer.model.Timespan;
import br.com.ufes.bd1.goomer.model.Weekday;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalTime;

public class TimespanDto {

    @JsonProperty("days")
    private String days;

    @JsonProperty("time")
    private String time;


    public Timespan toEntity() {
        String[] daysArray = days.split("-");
        Weekday weekdayStart = Weekday.fromName(daysArray[0]);
        Weekday weekdayEnd = Weekday.fromName(daysArray[1]);

        String[] timeArray = time.split("-");
        String timeStart = timeArray[0];
        String timeEnd = timeArray[1];

        Timespan timespan = new Timespan();
        timespan.setWeekdayStart(weekdayStart);
        timespan.setWeekdayEnd(weekdayEnd);
        timespan.setTimeStart(LocalTime.parse(timeStart));
        timespan.setTimeEnd(LocalTime.parse(timeEnd));

        return timespan;
    }

    public static TimespanDto fromEntity(Timespan timespan) {
        TimespanDto timespanDto = new TimespanDto();
        timespanDto.days = timespan.getWeekdayStart().getName() + "-" + timespan.getWeekdayEnd().getName();
        timespanDto.time = timespan.getTimeStart().format(Timespan.TIME_FORMATTER) + "-" + timespan.getTimeEnd().format(Timespan.TIME_FORMATTER);
        return timespanDto;
    }
}
