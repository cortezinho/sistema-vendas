package br.com.aweb.sistema_vendas.repository; // (O nome do arquivo deve ser PedidoRepository.java)

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.aweb.sistema_vendas.model.Pedido;
import br.com.aweb.sistema_vendas.model.StatusPedido;

// CORREÇÃO 1: Nome da interface corrigido de Reposotory para Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    
    // CORREÇÃO 2: O nome do método deve ser 'findByStatus' para bater com a propriedade 'status' da entidade Pedido
    List<Pedido> findByStatus(StatusPedido status);
}