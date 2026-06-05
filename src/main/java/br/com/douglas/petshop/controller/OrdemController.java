package br.com.douglas.petshop.controller;

import br.com.douglas.petshop.model.Ordem;
import br.com.douglas.petshop.service.OrdemService;
import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/ordem")
@RequiredArgsConstructor
public class OrdemController {

  private final Logger log = LoggerFactory.getLogger(OrdemController.class);

  private final OrdemService ordemService;

  /**
   * {@code GET  /ordem/:id} : get the "id" ordem.
   *
   * @param id o id do ordem que sera buscado.
   * @return o {@link ResponseEntity} com status {@code 200 (OK)} e no body do
   *         ordem, ou com status {@code 404 (Not Found)}.
   */
  @GetMapping("/{id}")
  public ResponseEntity<Ordem> getOrdem(@PathVariable Long id) {
    log.info("REST request to get Ordem : {}", id);
    Ordem ordem = ordemService.findOne(id);
    return ResponseEntity.ok().body(ordem);
  }

  @GetMapping("")
  public ResponseEntity<List<Ordem>> getOrdems() {
    List<Ordem> lista = ordemService.findAllList();
    return ResponseEntity.ok().body(lista);
  }

  /**
   * {@code PUT  /ordems} : Atualiza um ordem existenteUpdate.
   *
   * @param ordem o ordem a ser atulizado.
   * @return o {@link ResponseEntity} com status {@code 200 (OK)} e no corpo o
   *         ordem atualizado,
   *         ou com status {@code 400 (Bad Request)} se o ordem não é válido,
   *         ou com status {@code 500 (Internal Server Error)} se o ordem não pode
   *         ser atualizado.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PutMapping("")
  public ResponseEntity<Ordem> updateOrdem(@RequestBody Ordem ordem) throws URISyntaxException {
    log.info("REST request to update Ordem : {}", ordem);
    Ordem result = ordemService.updateOrdem(ordem);
    return ResponseEntity.ok()
        .body(result);
  }

  /**
   * {@code POST  /} : Create a new ordem.
   *
   * @param ordem the ordem to create.
   * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
   *         body the new ordem, or with status {@code 400 (Bad Request)} if the
   *         ordem has already an ID.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PostMapping("")
  public ResponseEntity<Ordem> createOrdem(@RequestBody Ordem ordem) throws URISyntaxException {
    log.info("REST request to save Ordem : {}", ordem);
    System.out.println("Tentei criar ordem");
    Ordem result = ordemService.createOrdem(ordem);
    return ResponseEntity.ok().body(result);
  }

  /*
   * @PostMapping(value = "/csv", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
   * public List<Ordem> upload(@RequestPart("data") MultipartFile csv) throws
   * IOException {
   * List<Ordem> savedNotes = new ArrayList<>();
   * List<Ordem> notes = new BufferedReader(
   * new InputStreamReader(Objects.requireNonNull(csv).getInputStream(),
   * StandardCharsets.UTF_8)).lines()
   * .map(Ordem::parseNote).collect(Collectors.toList());
   * ordemService.saveAll(notes).forEach(savedNotes::add);
   * return savedNotes;
   * }
   */

  /**
   * {@code DELETE  /:id} : delete pelo "id" ordem.
   *
   * @param id o id do ordems que será delete.
   * @return o {@link ResponseEntity} com status {@code 204 (NO_CONTENT)}.
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteOrdem(@PathVariable Long id) {
    log.info("REST request to delete Ordem : {}", id);

    ordemService.delete(id);
    return ResponseEntity.noContent().build();
  }

}
