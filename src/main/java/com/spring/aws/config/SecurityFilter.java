package com.spring.aws.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SecurityFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        String token = httpServletRequest.getHeader("token");

        if(token == null || token.isEmpty() || !token.equalsIgnoreCase("ABCDE1234")){ // El ejemplo este no puede ser utilizado a escala profesional

            ObjectMapper mapper = new ObjectMapper();
            ObjectNode error = mapper.createObjectNode();
            error.put("Error", "Token invalido o token vacío");

            httpServletResponse.setStatus(401); // Es el codigo http de "no autorizado"
            httpServletResponse.setContentType("application/json"); //Para indicar de que la respuesta va a ser un JSON

            httpServletResponse.getOutputStream().write(mapper.writeValueAsBytes(error)); // Lo va a escribir como una secuencia de bites
            httpServletResponse.getOutputStream().flush(); // Con flush nos aseguramos de que todos los bites se impriman
        } else{
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }
}