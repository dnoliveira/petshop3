package br.com.douglas.petshop.service;

import br.com.douglas.petshop.model.Pet;
import br.com.douglas.petshop.repository.PetRepository;
import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PetService {
  private final Logger log = LoggerFactory.getLogger(PetService.class);

  private final PetRepository petRepository;

  public Pet findOne(Long id) {
    log.debug("Request to get Pet : {}", id);
    Pet pet = petRepository.findById(id).orElse(null);
    if (pet == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pet not found");
    }
    return pet;
  }

  public List<Pet> findAllList() {
    log.debug("Request to get All Pet");
    return petRepository.findAll();
  }

  public List<Pet> findAllListBySearchText(String searchText) {
    log.info("Request to findAllListBySearchText(" + searchText + "%)");
    return petRepository.findAllActiveUsers(searchText + "%");
  }

  public void delete(Long id) {
    log.debug("Request to delete Pet : {}", id);
    Pet pet = petRepository.findById(id).orElse(null);
    if (pet == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pet not found");
    }
    petRepository.deleteById(id);
  }

  public Pet updatePet(Pet pet) {
    log.debug("Request to update Pet : {}", pet);
    if (pet.getId() == null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Pet id null");
    }

    Pet result = petRepository.findById(pet.getId()).orElse(null);
    if (result == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pet not found");
    }

    result.setNome(pet.getNome());
    result.setDono(pet.getDono());

    result = petRepository.save(result);

    return result;
  }

  public Pet createPet(Pet pet) {
    log.debug("Request to create Pet : {}", pet);

    if (pet.getId() != null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Pet id null");
    }

    Pet result = petRepository.save(pet);

    return result;
  }

  public List<Pet> saveAll(List<Pet> pet) {
    log.debug("Request to save Pet : {}", pet);
    pet = petRepository.saveAll(pet);
    return pet;
  }

}
