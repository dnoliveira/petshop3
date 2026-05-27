package br.com.douglas.petshop.model;

import lombok.*;

import jakarta.persistence.Embeddable;
import java.io.Serializable;

@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Embeddable
public class OrdemServicoId implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long idOrdem;
    private Long idServico;

}
