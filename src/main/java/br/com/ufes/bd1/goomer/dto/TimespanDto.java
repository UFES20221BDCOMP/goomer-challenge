package br.com.ufes.bd1.goomer.dto;

import br.com.ufes.bd1.goomer.model.Timespan;
import br.com.ufes.bd1.goomer.model.Weekday;
import br.com.ufes.bd1.goomer.validation.TimeIntervalFormat;
import br.com.ufes.bd1.goomer.validation.WeekdaysIntervalFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class TimespanDto {

    @WeekdaysIntervalFormat(message = "invalid format --must be like 'monday-friday'")
    @NotBlank(message = "weekdays interval must be provided")
    @JsonProperty("days")
    private String days;

    @TimeIntervalFormat(message = "invalid format --expected: 'HH:mm-HH:mm' with at least a 15-minute gap between start and end")
    @NotBlank(message = "daily timespan must be provided")
    @JsonProperty("time")
    private String time;


    public Timespan toEntity() {
        Timespan timespan = new Timespan();

        String[] daysArray = days.split("-");
        Weekday weekdayStart = Weekday.fromName(daysArray[0]);
        Weekday weekdayEnd = Weekday.fromName(daysArray[1]);

        timespan.setWeekdayStart(weekdayStart);
        timespan.setWeekdayEnd(weekdayEnd);

        String[] timeArray = time.split("-");
        timespan.setTimeStart(timeArray[0]);
        timespan.setTimeEnd(timeArray[1]);

        return timespan;
    }

    public static TimespanDto fromEntity(Timespan timespan) {
        TimespanDto timespanDto = new TimespanDto();
        timespanDto.days = timespan.getWeekdayStart().getName() + "-" + timespan.getWeekdayEnd().getName();
        timespanDto.time = timespan.getTimeStart() + "-" + timespan.getTimeEnd();
        return timespanDto;
    }
}
