package br.com.douglas.petshop.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "ordem")
public class Ordem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Instant dtOrdem;
    private String observacao;

    @ManyToOne
    @JoinColumn(name = "id_pet", nullable = false, foreignKey = @ForeignKey(name = "fk_ordemservico_pet"))
    private Pet pet;

    @ManyToMany
    @JoinTable(name = "ordem_servico", joinColumns = { @JoinColumn(name = "ordem_id") }, inverseJoinColumns = {
            @JoinColumn(name = "servico_id") })
    private List<Servico> servicos = new ArrayList<Servico>();

    public Ordem(Instant dtOrdem, String observacao, Pet pet, List<Servico> servicos) {
        this.dtOrdem = dtOrdem;
        this.observacao = observacao;
        this.pet = pet;
        this.servicos = servicos;
    }

}
