package br.com.douglas.petshop.repository;

import br.com.douglas.petshop.model.OrdemServico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdemServicoRepository extends JpaRepository<OrdemServico, Long> {
    void deleteByOrdemId(Long ordemId);
}
