package br.com.douglas.petshop.controller;

import br.com.douglas.petshop.model.Servico;
import br.com.douglas.petshop.service.ServicoService;
import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("api/v1/servico")
@RequiredArgsConstructor
public class ServicoController {

  private final Logger log = LoggerFactory.getLogger(ServicoController.class);

  private final ServicoService servicoService;

  /**
   * {@code GET  /servico/:id} : get the "id" servico.
   *
   * @param id o id do servico que sera buscado.
   * @return o {@link ResponseEntity} com status {@code 200 (OK)} e no body do
   *         servico, ou com status {@code 404 (Not Found)}.
   */
  @GetMapping("/{id}")
  public ResponseEntity<Servico> getServico(@PathVariable Long id) {
    log.info("REST request to get Servico : {}", id);
    Servico servico = servicoService.findOne(id);
    return ResponseEntity.ok().body(servico);
  }

  @GetMapping("")
  public ResponseEntity<List<Servico>> getServicos() {
    List<Servico> lista = servicoService.findAllList();
    return ResponseEntity.ok().body(lista);
  }

  /**
   * {@code PUT  /servicos} : Atualiza um servico existenteUpdate.
   *
   * @param servico o servico a ser atulizado.
   * @return o {@link ResponseEntity} com status {@code 200 (OK)} e no corpo o
   *         servico atualizado,
   *         ou com status {@code 400 (Bad Request)} se o servico não é válido,
   *         ou com status {@code 500 (Internal Server Error)} se o servico não
   *         pode ser atualizado.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PutMapping("")
  public ResponseEntity<Servico> updateServico(@RequestBody Servico servico) throws URISyntaxException {
    log.info("REST request to update Servico : {}", servico);
    Servico result = servicoService.updateServico(servico);
    return ResponseEntity.ok()
        .body(result);
  }

  /**
   * {@code POST  /} : Create a new servico.
   *
   * @param servico the servico to create.
   * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
   *         body the new servico, or with status {@code 400 (Bad Request)} if the
   *         servico has already an ID.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PostMapping("")
  public ResponseEntity<Servico> createServico(@RequestBody Servico servico) throws URISyntaxException {
    log.info("REST request to save Servico : {}", servico);
    System.out.println("Tentei criar servico");
    Servico result = servicoService.createServico(servico);
    return ResponseEntity.created(new URI("/api/servicos/" + result.getId()))
        .body(result);
  }

  /*
   * @PostMapping(value = "/csv", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
   * public List<Servico> upload(@RequestPart("data") MultipartFile csv) throws
   * IOException {
   * List<Servico> savedNotes = new ArrayList<>();
   * List<Servico> notes = new BufferedReader(
   * new InputStreamReader(Objects.requireNonNull(csv).getInputStream(),
   * StandardCharsets.UTF_8)).lines()
   * .map(Servico::parseNote).collect(Collectors.toList());
   * servicoService.saveAll(notes).forEach(savedNotes::add);
   * return savedNotes;
   * }
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
