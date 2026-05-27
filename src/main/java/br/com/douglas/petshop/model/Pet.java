package br.com.douglas.petshop.model;

import lombok.*;

import jakarta.persistence.*;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity(name = "pet")
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 60, nullable = false)
    private String nome;

    @Column(length = 60, nullable = false)
    private String dono;

    public Pet(String nome, String dono) {
        this.nome = nome;
        this.dono = dono;
    }
}
