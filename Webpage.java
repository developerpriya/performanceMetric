package com.metric.performance.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="metrics")
public class Webpage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String url;
    private String date;
    private int performance;
    private double firstContentfulPaint;
    private double firstMeaningfulPaint;
    private double largestContentfulPaint;
    private double interactive;
    private double speedIndex;
    private double totalBlockingTime;
}
