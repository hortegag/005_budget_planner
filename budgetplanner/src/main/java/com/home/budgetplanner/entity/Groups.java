package com.home.budgetplanner.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;

import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;
import lombok.Setter;
import lombok.ToString;

@Entity()
@Data()
@Table(name = "groups")
@NoArgsConstructor
@AllArgsConstructor
// @EqualsAndHashCode
public class Groups implements Serializable {

    @Id
    @GeneratedValue
    // @Setter(AccessLevel.NONE)
    @Column(name = "id_group")
    private Long               id;
    private String             name;

    @EqualsAndHashCode.Exclude
    private String             description;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToMany
    @JoinTable(name = "groups_roles", joinColumns = @JoinColumn(name = "id_group"), inverseJoinColumns = @JoinColumn(name = "id_rol"))
    private Collection<Roles>  roles;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToMany
    @JoinTable(name = "users_groups", joinColumns = @JoinColumn(name = "id_group"), inverseJoinColumns = @JoinColumn(name = "id_person"))
    private Collection<People> people;

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        Groups other = null;
        if (getClass() != obj.getClass()) {
            

            ArrayList<Groups> test = new ArrayList<>();
            
            
            if (test.getClass() == obj.getClass()){
                
                test = ((java.util.ArrayList) obj);
                
                if (getClass() != test.get(0).getClass()) {
                    return false;
                } else {
                    other = (Groups) test.get(0);
                }
                
            } else {
                return false;
            }
         
        } else {
            other = (Groups) obj;
        }
       // final Groups other = (Groups) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

}
