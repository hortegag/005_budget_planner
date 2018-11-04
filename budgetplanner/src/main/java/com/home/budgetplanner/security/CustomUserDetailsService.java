package com.home.budgetplanner.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.home.budgetplanner.entity.Groups;
import com.home.budgetplanner.entity.People;
import com.home.budgetplanner.entity.Roles;
import com.home.budgetplanner.repository.PagingPeopleRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service("customUserDetailsService")
@Transactional(readOnly = true)
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private PagingPeopleRepository pagingPeopleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        People people = pagingPeopleRepository.findByUsername(username);

//        if (people != null) {
//            System.out.println("------------------------------->people" + people.toString());
//        } else {
//            System.out.println("nnot found");
//
//        }
//
//        
//        
//        
//        System.out.println("----------------------------------" + getAuthorities(people.getGroups()));
//
//        UserBuilder builder = null;
//        if (people != null) {
//            builder = org.springframework.security.core.userdetails.User.withUsername(username);
//            builder.password((people.getPassword()));
//            
//            builder.roles("ADMIN","FLOWS");
//            
//        }
//        
//        
        
        if (people == null) {
            throw new UsernameNotFoundException("Invalid username/password.");
        }

//        return  builder.build();// new User

          return new org.springframework.security.core.userdetails.User(people.getUsername(), people.getPassword(), true, true, true, true,getAuthorities(people.getGroups()));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Collection<Groups> groups) {

        return getGrantedAuthorities(getPrivileges(groups));
    }

    private List<String> getPrivileges(Collection<Groups> groups) {

        List<String> privileges = new ArrayList<>();
        List<Roles> collection = new ArrayList<>();
        for (Groups group : groups) {
            collection.addAll(group.getRoles());
        }
        for (Roles item : collection) {
            privileges.add(item.getName());
        }
        return privileges;
    }

    private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        return authorities;
    }

}
