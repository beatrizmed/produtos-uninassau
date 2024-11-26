package com.beatrizmed.dscatalago.entities.category;

import jakarta.persistence.*;
import lombok.*; //biblioteca para simplificar o código pra não ficar um código grande

import java.io.Serializable;
import java.time.Instant;

@AllArgsConstructor //lombok
@NoArgsConstructor //lombok
@Getter //lombok
@Setter //lombok
@EqualsAndHashCode //anotação do lombok
@Entity //modelo da api
@Table(name = "tb_category") //tabela de categoria
public class Category implements Serializable {
    private static final long serialVersionUID = 1L;

    //chave primária da tabela
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //sempre vai gerar um valor novo
    private Long id;

    private String name;

    @Setter(AccessLevel.NONE) //propriedade sem o set, só tem o get
    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private Instant createdAt; //controle de quando foi criado

    @Setter(AccessLevel.NONE) //propriedade sem o set, só tem o get
    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private Instant updatedAt; //controle de quando foi atualizado

    @PrePersist
    public void prePersist(){
        createdAt = Instant.now();
    } //salva quando cria um novo dado, setando a data que foi criado

    @PreUpdate
    public void preUpdate(){
        updatedAt = Instant.now();
    } //salva a data em que o registro foi atualizada

}
