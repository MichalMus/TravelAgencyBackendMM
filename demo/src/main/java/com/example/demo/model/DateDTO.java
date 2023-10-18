package com.example.demo.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
public class DateDTO {
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date date1;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date date2;

}
