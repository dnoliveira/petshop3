package br.com.douglas.petshop.repository;

import br.com.douglas.petshop.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {

    @Query(" SELECT p FROM pet p " +
            " WHERE p.nome like :searchText " +
            "    or p.dono like :searchText ")
    List<Pet> findAllActiveUsers(@Param("searchText") String searchText);
}
