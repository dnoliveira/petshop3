package br.com.douglas.petshop.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ordem")
public class Ordem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime dtOrdem;

    private String observacao;

    /*
     * Utilizar caso o Spring jpa/hibernate
     * fizer o update na estrutura do banco
     */
    @ManyToOne
    @JoinColumn(name = "id_pet", nullable = false, foreignKey = @ForeignKey(name = "fk_ordemservico_pet"))
    private Pet pet;

    /*
     * //Utilizar caso a estrutura do banco for definida
     * //pelo diagrama no workbench.
     * 
     * @Column(name = "id_pet", nullable = false)
     * private Long idPet;
     */

    @ManyToMany
    @JoinTable(name = "ordem_servico", joinColumns = { @JoinColumn(name = "ordem_id") }, inverseJoinColumns = {
            @JoinColumn(name = "servico_id") })
    private List<Servico> servicos = new ArrayList<Servico>();

    public Ordem(LocalDateTime dtOrdem, String observacao, Pet pet, List<Servico> servicos) {
        this.dtOrdem = dtOrdem;
        this.observacao = observacao;
        this.pet = pet;
        this.servicos = servicos;
    }

}
