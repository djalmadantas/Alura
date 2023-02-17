package com.alura.aluraFood.pedidos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class PedidoApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(PedidoApplication.class, args);
    }
}