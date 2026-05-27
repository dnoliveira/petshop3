package br.com.douglas.petshop.service;

import br.com.douglas.petshop.model.Pet;
import br.com.douglas.petshop.repository.PetRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PetService {
  private final Logger log = LoggerFactory.getLogger(PetService.class);

  private final PetRepository petRepository;

  public PetService(PetRepository petRepository){
    this.petRepository = petRepository;
  }

  public Optional<Pet> findOne(Long id) {
    log.debug("Request to get Pet : {}", id);
    return petRepository.findById(id);
  }

  public List<Pet> findAllList(){
    log.debug("Request to get All Pet");
    return petRepository.findAll();
  }

  public List<Pet> findAllListBySearchText(String searchText){
    log.info("Request to findAllListBySearchText(" +searchText+ "%)");
    return petRepository.findAllActiveUsers(searchText + "%");
  }

  public void delete(Long id) {
    log.debug("Request to delete Pet : {}", id);
    petRepository.deleteById(id);
  }

  public Pet save(Pet pet) {
    log.debug("Request to save Pet : {}", pet);
    pet = petRepository.save(pet);
    return pet;
  }

  public List<Pet> saveAll(List<Pet> pet) {
    log.debug("Request to save Pet : {}", pet);
    pet = petRepository.saveAll(pet);
    return pet;
  }

}
