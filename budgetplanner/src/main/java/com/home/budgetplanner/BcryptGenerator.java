package com.home.budgetplanner;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.binding.message.MessageBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BcryptGenerator {
        public static void main(String[] args) {

                int i = 0;
                while (i < 5) {
                        String password = "hortega";
                        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                        String hashedPassword = passwordEncoder.encode(password);

                        System.out.println(hashedPassword);
                        i++;
                }
                
                
                Pattern p = Pattern.compile("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{4,8}$");
                Matcher m = p.matcher("hF8");

                if (!m.matches()) {
                    
                   System.out.println("no se encontro coincidencia");

                } else {
                    
                    System.out.println("El password es correcto");

                    
                }
                
                
                Pattern pemail = Pattern.compile("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");

                
                Matcher memail = pemail.matcher("asdfasdf@gmail.com");

                if (!memail.find()) {
                    
                   System.out.println("no se encontro email");

                } else {
                    
                    System.out.println("El email es correcto");

                    
                }
                

          }
}