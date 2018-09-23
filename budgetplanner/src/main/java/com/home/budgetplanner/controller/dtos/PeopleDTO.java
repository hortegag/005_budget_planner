package com.home.budgetplanner.controller.dtos;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.home.budgetplanner.entity.People;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PeopleDTO {

    private Long                          id;

    private String                        name;

    private String                        lastName;

    private String                        email;

    private LocalDate                     bornDate;

    private String                        identification;

    private String                        identificationType;

    private String                        bornDateString;
    // private static final SimpleDateFormat dateFormatter = new
    // SimpleDateFormat("dd/MM/yyyy hh:mm aaa");
    private static final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");

    public static PeopleDTO build(People people) {

        // DateTimeFormatter formatter =
        // DateTimeFormatter.ofPattern(ISO_LOCAL_DATE);
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;

        String text = people.getBornDate().format(formatter);

        return new PeopleDTO(people.getId(), people.getName(), people.getLastName(), people.getEmail(), people.getBornDate(),
                people.getIdentification(), (people.getIdentificationType() != null) ? people.getIdentificationType().getName() : null, text);

    }

}
