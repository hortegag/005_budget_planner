package com.home.budgetplanner.controller.dtos;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.home.budgetplanner.entity.Groups;
import com.home.budgetplanner.entity.IdentificationType;
import com.home.budgetplanner.entity.People;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
// @NoArgsConstructor
//@AllArgsConstructor
public class PeopleDTO implements Serializable {

    private Long                          id;

    private String                        name;

    private String                        lastName;

    private String                        email;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate                     bornDate;

    private String                        identification;

    private String                        identificationType;

    private String                        bornDateString;

    private List<String>                  groups;

    private String                        enabled;

    private String                        username;

    private String                        password;

    // private static final SimpleDateFormat dateFormatter = new
    // SimpleDateFormat("dd/MM/yyyy hh:mm aaa");
    private static final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");

    public static PeopleDTO build(People people) {

        // DateTimeFormatter formatter =
        // DateTimeFormatter.ofPattern(ISO_LOCAL_DATE);
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;

        String text = people.getBornDate().format(formatter);

        List<String> groupsOfPeople = new ArrayList<>();

        for (Groups group : people.getGroups()) {

            groupsOfPeople.add(group.getId().toString());
        }

        return new PeopleDTO(people.getId(), people.getName(), people.getLastName(), people.getEmail(), people.getBornDate(),
                people.getIdentification(), (people.getIdentificationType() != null) ? people.getIdentificationType().getName() : null, text,
                groupsOfPeople, people.getEnabled(), people.getUsername(), people.getPassword());

    }

    public PeopleDTO(Long id, String name, String lastName, String email, LocalDate bornDate, String identification, String identificationType,
            String bornDateString, List<String> groups, String enabled, String username, String password) {
        super();
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.bornDate = bornDate;
        this.identification = identification;
        this.identificationType = identificationType;
        this.bornDateString = bornDateString;
        this.groups = groups;
        this.enabled = enabled;
        this.username = username;
        this.password = password;
    }

    public static People dtoToEntity(PeopleDTO peopleDTO, List<Groups> groups, IdentificationType identificationType) {

        People people = new People(peopleDTO.getId(), peopleDTO.getName(), peopleDTO.getLastName(), peopleDTO.getEmail(), peopleDTO.getBornDate(),
                peopleDTO.getIdentification(), peopleDTO.getUsername(), peopleDTO.getPassword(), peopleDTO.getEnabled(), identificationType, groups);

        return people;

    }

}
