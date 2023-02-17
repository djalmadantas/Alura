package com.alura.aluraFood.pagamentos.service;

import com.alura.aluraFood.pagamentos.client.PedidoClient;
import com.alura.aluraFood.pagamentos.dto.PagamentoDto;
import com.alura.aluraFood.pagamentos.model.Pagamento;
import com.alura.aluraFood.pagamentos.model.enums.Status;
import com.alura.aluraFood.pagamentos.repository.PagamentoRepositoy;
import javax.persistence.EntityNotFoundException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class PagamentoService
{

    @Autowired
    private PagamentoRepositoy repository;

    @Autowired
    private PedidoClient pedidoClient;

    @Autowired
    private ModelMapper modelMapper;


    public Page<PagamentoDto> obterTodos(Pageable paginacao)
    {
        return repository
                .findAll(paginacao)
                .map(p -> modelMapper.map(p, PagamentoDto.class));
    }

    public PagamentoDto obterPorId(Long id)
    {
        Pagamento pagamento = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException());

        return modelMapper.map(pagamento, PagamentoDto.class);
    }

    public PagamentoDto criarPagamento(PagamentoDto dto)
    {
        Pagamento pagamento = modelMapper.map(dto, Pagamento.class);
        pagamento.setStatus(Status.CRIADO);
        repository.save(pagamento);

        return modelMapper.map(pagamento, PagamentoDto.class);
    }

    public PagamentoDto atualizarPagamento(Long id, PagamentoDto dto)
    {
        Pagamento pagamento = modelMapper.map(dto, Pagamento.class);
        pagamento.setId(id);
        pagamento = repository.save(pagamento);
        return modelMapper.map(pagamento, PagamentoDto.class);
    }

    public void excluirPagamento(Long id)
    {
        repository.deleteById(id);
    }

    @CircuitBreaker(name = "atualizaPedido", fallbackMethod = "pagamentoAutorizadoComIntegracaoPendente")
    public void confirmarPagamento(Long id)
    {
        Optional<Pagamento> pagamento = repository.findById(id);

        if (!pagamento.isPresent())
        {
            throw new EntityNotFoundException();
        }

        pagamento.get().setStatus(Status.CONFIRMADO);
        repository.save(pagamento.get());
        pedidoClient.atualizaPagamento(pagamento.get().getPedidoId());
    }

    void pagamentoAutorizadoComIntegracaoPendente(Long id, Exception e)
    {
        Optional<Pagamento> pagamento = repository.findById(id);

        if (!pagamento.isPresent())
        {
            throw new EntityNotFoundException();
        }

        pagamento.get().setStatus(Status.CONFIRMADO_SEM_INTEGRACAO);
        repository.save(pagamento.get());
    }

    public void alteraStatus(Long id)
    {
        Optional<Pagamento> pagamento = repository.findById(id);

        if (!pagamento.isPresent())
        {
            throw new EntityNotFoundException();
        }

        pagamento.get().setStatus(Status.CONFIRMADO_SEM_INTEGRACAO);
        repository.save(pagamento.get());

    }
}

