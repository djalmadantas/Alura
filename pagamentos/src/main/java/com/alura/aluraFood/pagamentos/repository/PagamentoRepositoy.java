package com.alura.aluraFood.pagamentos.repository;

import com.alura.aluraFood.pagamentos.model.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PagamentoRepositoy extends JpaRepository<Pagamento, Long>
{
}
