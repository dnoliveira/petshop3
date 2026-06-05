package br.com.douglas.petshop.service;

import br.com.douglas.petshop.model.Servico;
import br.com.douglas.petshop.repository.ServicoRepository;
import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ServicoService {

  private final Logger log = LoggerFactory.getLogger(ServicoService.class);

  private final ServicoRepository servicoRepository;

  public Servico findOne(Long id) {
    log.debug("Request to get Servico : {}", id);
    Servico servico = servicoRepository.findById(id).orElse(null);
    if (servico == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Servico not found");
    }
    return servico;
  }

  public List<Servico> findAllList() {
    log.debug("Request to get All Servico");
    return servicoRepository.findAll();
  }

  public void delete(Long id) {
    log.debug("Request to delete Servico : {}", id);
    Servico servico = servicoRepository.findById(id).orElse(null);
    if (servico == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Servico not found");
    }
    servicoRepository.deleteById(id);
  }

  public Servico createServico(Servico servico) {
    log.debug("Request to create Servico : {}", servico);
    if (servico.getId() != null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Um novo servico não pode ter um ID informado");
    }
    return servicoRepository.save(servico);
  }

  public Servico updateServico(Servico servico) {
    log.debug("Request to update Servico : {}", servico);
    if (servico.getId() == null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Servico id null");
    }

    Servico result = servicoRepository.findById(servico.getId()).orElse(null);
    if (result == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Servico not found");
    }

    result.setDescricao(servico.getDescricao());
    result.setValor(servico.getValor());

    return servicoRepository.save(result);
  }

  public List<Servico> saveAll(List<Servico> servico) {
    log.debug("Request to save Servico : {}", servico);
    servico = servicoRepository.saveAll(servico);
    return servico;
  }
}
