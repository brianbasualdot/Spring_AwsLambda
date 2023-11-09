package com.spring.aws.config;

import com.spring.aws.domain.Character;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

@Configuration
public class AwsLambdaConfig {

    // Desde aui accedemos a la capa de seguridad del SECURITY FILTER
    @Bean
    public Filter getFilter(){
        return new SecurityFilter();
    }

    // Peticion en forma de GET
    @Bean(name = "saludar")
    public Supplier<String> greeting(){
        return () -> "Hiii, World!";
    }

    // Peticion en forma POST
    @Bean
    public Consumer<String> printParam(){
        return (param) -> {
            System.out.println(param);
        };
    }
    // Se puede hacer peticion con AMBAS funciones
    @Bean
    public Function<String, String> receiveParam(){
        return (param) -> {
            String name = param.toUpperCase();
            return name;
        };
    }

    // Generar una respuesta a JSON
    @Bean
    public Supplier<Map<String, String>> createCharacter(){
        return () -> {
            Map<String, String> character = new HashMap<>();
            character.put("name", "Goku");
            character.put("healthPoints", "100000");
            character.put("skills", "Kame Hame Ha!");

            return character;
        };
    }

    // Recibir a JSON y retornar un String
    @Bean
    public Function<Map<String, Object>, String> receiveCharacter(){
        return (param) -> {
            param.forEach( (key, value) -> System.out.println(key + " - " + value));
            return "Personaje recibido";
        };
    }

    // Recibe un OBJECT y retorna otro OBJECT
    @Bean
    public Function<Character, Character> receiveAnObject(){
        return (param) -> param;
    }

    // Recibe un JSON y retorna otro JSON
    @Bean
    public Function<Map<String, Object>, Map<String, Object>> processCharacters(){
        return (param) -> {
            Map<String, Object> mapCharacter = param;

            mapCharacter.forEach( (key, value) -> System.out.println(key + " - " + value) );

            Map<String, Object> newCharacter = new HashMap<>();
            newCharacter.put("name", "Krillin");
            newCharacter.put("healthPoints", 70);
            newCharacter.put("skills", new String[]{"Ki en zan!", "Kame Hame Ha!"});

            return newCharacter;
        };
    }
}
