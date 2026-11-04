package br.com.etechas.pw2_6_descarte_sustentavel.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "TBL_DESCARTE_ELETRONICO", schema = "dbo")
@Getter
@Setter
@NoArgsConstructor
public class DescarteEletronico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_DESCARTE")
    private Long id;

    @Column(name = "TX_MATRICULA_ALUNO")
    private String matriculaAluno;

    @Column(name = "TX_TIPO_ITEM")
    private String tipoItem;

    @Column(name = "PESO_GRAMAS")
    private Double pesoGramas;

    @Column(name = "PONTOS_GERADOS")
    private Integer pontosGerados;

    @Column(name = "VALIDADO")
    private Boolean validado;
}
