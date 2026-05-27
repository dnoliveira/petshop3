package br.com.douglas.petshop.controller;

import br.com.douglas.petshop.model.Pet;
import br.com.douglas.petshop.service.PetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(path = "api/v1/pet")
public class PetController {

  private final Logger log = LoggerFactory.getLogger(PetController.class);

  private final PetService petService;

  public PetController(PetService petService){
    this.petService = petService;
  }

  /**
   * {@code GET  /pet/:id} : get the "id" pet.
   *
   * @param id o id do pet que sera buscado.
   * @return o {@link ResponseEntity} com status {@code 200 (OK)} e no body do pet, ou com status {@code 404 (Not Found)}.
   */
  @GetMapping("/{id}")
  public ResponseEntity<Pet> getPet(@PathVariable Long id) {
    log.info("REST request to get Pet : {}", id);
    Optional<Pet> pet = petService.findOne(id);
    if(pet.isPresent()) {
      return ResponseEntity.ok().body(pet.get());
    }else{
      return ResponseEntity.notFound().build();
    }

  }

  @GetMapping({"/searchText/{searchText}"})
  public ResponseEntity<List<Pet>> getPetsBySearchText(@PathVariable String searchText){
    List<Pet> lista;
    if (!searchText.isEmpty()){
      lista = petService.findAllListBySearchText(searchText);
    }else{
      lista = petService.findAllList();
    }

    return ResponseEntity.ok().body(lista);
  }

  @GetMapping({"", "/", "/searchText/", "/searchText"})
  public ResponseEntity<List<Pet>> getPets(){
    List<Pet> lista = petService.findAllList();
    return ResponseEntity.ok().body(lista);
  }

  /**
   * {@code PUT  /pets} : Atualiza um pet existenteUpdate.
   *
   * @param pet o pet a ser atulizado.
   * @return o {@link ResponseEntity} com status {@code 200 (OK)} e no corpo o pet atualizado,
   * ou com status {@code 400 (Bad Request)} se o pet não é válido,
   * ou com status {@code 500 (Internal Server Error)} se o pet não pode ser atualizado.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PutMapping({"", "/"})
  public ResponseEntity<Pet> updatePet(@RequestBody Pet pet) throws URISyntaxException {
    log.info("REST request to update Pet : {}", pet);
    if (pet.getId() == null) {
      throw new ResponseStatusException(
          HttpStatus.BAD_REQUEST, "Invalid Pet id null");
    }
    Pet result = petService.save(pet);
    return ResponseEntity.ok()
        .body(result);
  }

  /**
   * {@code POST  /} : Create a new pet.
   *
   * @param pet the pet to create.
   * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pet, or with status {@code 400 (Bad Request)} if the pet has already an ID.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PostMapping({"", "/"})
  public ResponseEntity<Pet> createPet(@RequestBody Pet pet) throws URISyntaxException {
    log.info("REST request to save Pet : {}", pet);
    System.out.println("Tentei criar pet");
    if (pet.getId() != null) {
      throw new ResponseStatusException(
          HttpStatus.BAD_REQUEST, "Um novo pet não pode ter um ID informado");
    }
    Pet result = petService.save(pet);
    return ResponseEntity.created(new URI("/api/pets/" + result.getId()))
        .body(result);
  }

  /*
  @PostMapping(value = "/csv", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public List<Pet> upload(@RequestPart("data") MultipartFile csv) throws IOException {
    List<Pet> savedNotes = new ArrayList<>();
    List<Pet> notes = new BufferedReader(
        new InputStreamReader(Objects.requireNonNull(csv).getInputStream(), StandardCharsets.UTF_8)).lines()
        .map(Pet::parseNote).collect(Collectors.toList());
    petService.saveAll(notes).forEach(savedNotes::add);
    return savedNotes;
  }
  */

  /**
   * {@code DELETE  /:id} : delete pelo "id" pet.
   *
   * @param id o id do pets que será delete.
   * @return o {@link ResponseEntity} com status {@code 204 (NO_CONTENT)}.
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deletePet(@PathVariable Long id) {
    log.info("REST request to delete Pet : {}", id);

    petService.delete(id);
    return ResponseEntity.noContent().build();
  }

}
