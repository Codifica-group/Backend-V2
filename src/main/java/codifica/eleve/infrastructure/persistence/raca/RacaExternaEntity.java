package codifica.eleve.infrastructure.persistence.raca;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "racas_externas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RacaExternaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", nullable = false, unique = true)
    private String nome;

    @Column(name = "nome_original")
    private String nomeOriginal;

    @Column(name = "raca_id_externo")
    private Integer racaIdExterno;

    @Column(name = "temperamento", columnDefinition = "TEXT")
    private String temperamento;

    @Column(name = "vida_media")
    private String vidaMedia;

    @Column(name = "altura")
    private String altura;

    @Column(name = "peso")
    private String peso;

    @Column(name = "origem")
    private String origem;

    @Column(name = "proposito")
    private String proposito;

    @Column(name = "grupo")
    private String grupo;

    @Column(name = "imagem_url")
    private String imagemUrl;

    @Column(name = "descricao", columnDefinition = "TEXT")
    private String descricao;

    @Column(name = "data_sincronizacao")
    private java.time.LocalDateTime dataSincronizacao;

    @Column(name = "ativo")
    private Boolean ativo = true;
}
