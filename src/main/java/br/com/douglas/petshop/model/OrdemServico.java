package br.com.douglas.petshop.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity(name = "ordem_servico")
public class OrdemServico {
    
    @EmbeddedId
    private OrdemServicoId id = new OrdemServicoId();

    @ManyToOne
    @MapsId("idOrdem")
    private Ordem ordem;

    @ManyToOne
    @MapsId("idServico")
    private Servico servico;

    @Column(nullable = false)
    private BigDecimal valor;
}
