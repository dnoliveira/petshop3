package br.com.douglas.petshop.model;

import lombok.*;

import jakarta.persistence.*;
import java.math.BigDecimal;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity(name = "servico")
public class Servico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 60, nullable = false)
    private String descricao;

    @Column(nullable = false)
    private BigDecimal valor;

    public Servico(String descricao, BigDecimal valor) {
        this.descricao = descricao;
        this.valor = valor;
    }

}
