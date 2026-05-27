package br.com.douglas.petshop.repository;

import br.com.douglas.petshop.model.OrdemServico;
import br.com.douglas.petshop.model.OrdemServicoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdemServicoRepository extends JpaRepository<OrdemServico, OrdemServicoId> {

}
