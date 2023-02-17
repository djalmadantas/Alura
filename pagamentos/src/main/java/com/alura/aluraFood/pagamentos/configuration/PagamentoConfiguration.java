package com.alura.aluraFood.pagamentos.configuration;

import com.alura.aluraFood.pagamentos.client.PedidoClient;
import org.modelmapper.ModelMapper;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableEurekaClient
@EnableFeignClients(basePackageClasses = PedidoClient.class)
public class PagamentoConfiguration
{
    @Bean
    public ModelMapper modelMapper()
    {
        return new ModelMapper();
    }
}
