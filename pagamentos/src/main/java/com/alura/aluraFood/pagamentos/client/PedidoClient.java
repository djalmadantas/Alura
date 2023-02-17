package com.alura.aluraFood.pagamentos.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "pedidos-ms") //Mesmo nome informado no atributo spring.application.name
public interface PedidoClient
{
    @RequestMapping(method = RequestMethod.PUT, value = "/pedidos/{id}/pago")
    void atualizaPagamento(@PathVariable(name = "id") Long id);
}
