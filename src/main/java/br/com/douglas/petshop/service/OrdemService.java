package br.com.douglas.petshop.service;

import br.com.douglas.petshop.model.Ordem;
import br.com.douglas.petshop.model.OrdemServico;
import br.com.douglas.petshop.model.Servico;
import br.com.douglas.petshop.repository.OrdemRepository;
import br.com.douglas.petshop.repository.OrdemServicoRepository;
import br.com.douglas.petshop.repository.ServicoRepository;
import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrdemService {
    private final Logger log = LoggerFactory.getLogger(OrdemService.class);

    private final OrdemRepository ordemRepository;

    private final OrdemServicoRepository ordemServicoRepository;

    private final ServicoRepository servicoRepository;

    public Ordem findOne(Long id) {
        log.debug("Request to get Ordem : {}", id);
        Ordem ordem = ordemRepository.findById(id).orElse(null);
        if (ordem == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ordem not found");
        }
        return ordem;
    }

    public List<Ordem> findAllList() {
        log.debug("Request to get All Ordem");
        return ordemRepository.findAll();
    }

    public void delete(Long id) {
        log.debug("Request to delete Ordem : {}", id);
        Ordem ordem = ordemRepository.findById(id).orElse(null);
        if (ordem == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ordem not found");
        }
        ordemRepository.deleteById(id);
    }

    @Transactional
    public Ordem createOrdem(Ordem ordem) {
        log.debug("Request to create Ordem : {}", ordem);
        if (ordem.getId() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Um novo ordem não pode ter um ID informado");
        }

        // Obtem os servicos da ordem
        List<Servico> servicoList = ordem.getServicos();

        // Inserir a ordem sem os servicos
        ordem.setServicos(new ArrayList<>());
        ordem = ordemRepository.save(ordem);

        // Inserir os servicos individualmente para a ordem em questao.
        for (Servico servico : servicoList) {
            Optional<Servico> servicoOpt = servicoRepository.findById(servico.getId());

            if (servicoOpt.isPresent()) {
                servico = servicoOpt.get();

                OrdemServico ordemServico = new OrdemServico();
                ordemServico.setOrdem(ordem);
                ordemServico.setServico(servico);
                ordemServico.setValor(servico.getValor());
                ordemServicoRepository.save(ordemServico);
            }
        }

        return ordem;
    }

    @Transactional
    public Ordem updateOrdem(Ordem ordem) {
        log.debug("Request to update Ordem : {}", ordem);
        if (ordem.getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Ordem id null");
        }

        Ordem result = ordemRepository.findById(ordem.getId()).orElse(null);
        if (result == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ordem not found");
        }

        // Salvar a lista de servicos em uma variavel, pois ela sera zerada para salvar
        // a ordem
        List<Servico> servicoList = ordem.getServicos();

        // Alterar a ordem sem os servicos
        ordem.setServicos(new ArrayList<>());
        ordem = ordemRepository.save(ordem);

        ordemServicoRepository.deleteByOrdemId(ordem.getId());

        // Inserir os servicos individualmente para a ordem em questao.
        for (Servico servico : servicoList) {
            Optional<Servico> servicoOpt = servicoRepository.findById(servico.getId());

            if (servicoOpt.isPresent()) {
                servico = servicoOpt.get();

                OrdemServico ordemServico = new OrdemServico();
                ordemServico.setOrdem(ordem);
                ordemServico.setServico(servico);
                ordemServico.setValor(servico.getValor());
                ordemServicoRepository.save(ordemServico);
            }
        }

        return ordem;
    }

    public List<Ordem> saveAll(List<Ordem> ordem) {
        log.debug("Request to save Ordem : {}", ordem);
        ordem = ordemRepository.saveAll(ordem);
        return ordem;
    }
}
