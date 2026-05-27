package br.com.douglas.petshop.controller;


import br.com.douglas.petshop.model.Servico;
import br.com.douglas.petshop.service.ServicoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(path = "api/v1/servico")
public class ServicoController {

  private final Logger log = LoggerFactory.getLogger(ServicoController.class);

  private final ServicoService servicoService;

  public ServicoController(ServicoService servicoService){
    this.servicoService = servicoService;
  }

  /**
   * {@code GET  /servico/:id} : get the "id" servico.
   *
   * @param id o id do servico que sera buscado.
   * @return o {@link ResponseEntity} com status {@code 200 (OK)} e no body do servico, ou com status {@code 404 (Not Found)}.
   */
  @GetMapping("/{id}")
  public ResponseEntity<Servico> getServico(@PathVariable Long id) {
    log.info("REST request to get Servico : {}", id);
    Optional<Servico> servico = servicoService.findOne(id);
    if(servico.isPresent()) {
      return ResponseEntity.ok().body(servico.get());
    }else{
      return ResponseEntity.notFound().build();
    }

  }

  @GetMapping(value = {"/",""}, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<Servico>> getServicos(){
    List<Servico> lista = servicoService.findAllList();
    if(lista.size() > 0) {
      return ResponseEntity.ok().body(lista);
    }else{
      return ResponseEntity.notFound().build();
    }
  }

  /**
   * {@code PUT  /servicos} : Atualiza um servico existenteUpdate.
   *
   * @param servico o servico a ser atulizado.
   * @return o {@link ResponseEntity} com status {@code 200 (OK)} e no corpo o servico atualizado,
   * ou com status {@code 400 (Bad Request)} se o servico não é válido,
   * ou com status {@code 500 (Internal Server Error)} se o servico não pode ser atualizado.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PutMapping({"/",""})
  public ResponseEntity<Servico> updateServico(@RequestBody Servico servico) throws URISyntaxException {
    log.info("REST request to update Servico : {}", servico);
    if (servico.getId() == null) {
      throw new ResponseStatusException(
          HttpStatus.BAD_REQUEST, "Invalid Servico id null");
    }
    Servico result = servicoService.save(servico);
    return ResponseEntity.ok()
        .body(result);
  }

  /**
   * {@code POST  /} : Create a new servico.
   *
   * @param servico the servico to create.
   * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new servico, or with status {@code 400 (Bad Request)} if the servico has already an ID.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PostMapping({"/",""})
  public ResponseEntity<Servico> createServico(@RequestBody Servico servico) throws URISyntaxException {
    log.info("REST request to save Servico : {}", servico);
    System.out.println("Tentei criar servico");
    if (servico.getId() != null) {
      throw new ResponseStatusException(
          HttpStatus.BAD_REQUEST, "Um novo servico não pode ter um ID informado");
    }
    Servico result = servicoService.save(servico);
    return ResponseEntity.created(new URI("/api/servicos/" + result.getId()))
        .body(result);
  }

  /*
  @PostMapping(value = "/csv", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public List<Servico> upload(@RequestPart("data") MultipartFile csv) throws IOException {
    List<Servico> savedNotes = new ArrayList<>();
    List<Servico> notes = new BufferedReader(
        new InputStreamReader(Objects.requireNonNull(csv).getInputStream(), StandardCharsets.UTF_8)).lines()
        .map(Servico::parseNote).collect(Collectors.toList());
    servicoService.saveAll(notes).forEach(savedNotes::add);
    return savedNotes;
  }
  */

  /**
   * {@code DELETE  /:id} : delete pelo "id" servico.
   *
   * @param id o id do servicos que será delete.
   * @return o {@link ResponseEntity} com status {@code 204 (NO_CONTENT)}.
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteServico(@PathVariable Long id) {
    log.info("REST request to delete Servico : {}", id);

    servicoService.delete(id);
    return ResponseEntity.noContent().build();
  }

}
