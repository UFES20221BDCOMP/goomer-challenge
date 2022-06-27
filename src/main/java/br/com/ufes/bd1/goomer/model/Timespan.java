package br.com.ufes.bd1.goomer.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter @Setter
@Entity
@Table(name = "timespan")
public class Timespan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "weekday_start", columnDefinition = "VARCHAR(3)", nullable = false)
    private Weekday weekdayStart;

    @Enumerated(EnumType.STRING)
    @Column(name = "weekday_end", columnDefinition = "VARCHAR(3)", nullable = false)
    private Weekday weekdayEnd;

    @Column(name = "time_start", columnDefinition = "VARCHAR(5)", nullable = false)
    private String timeStart;

    @Column(name = "time_end", columnDefinition = "VARCHAR(5)", nullable = false)
    private String timeEnd;
}
