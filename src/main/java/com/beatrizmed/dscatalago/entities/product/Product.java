package com.beatrizmed.dscatalago.entities.product;

import com.beatrizmed.dscatalago.entities.category.Category;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "tb_product")
@Entity
@EqualsAndHashCode
public class Product implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;
    private String price;
    private String imgUrl;

    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private Instant date;

    //relacionado a categoria
    @Setter(AccessLevel.NONE) //sem o set
    @ManyToMany
    //terceira tabela, para fazer ligação de muitos para muitos
    @JoinTable(
            name = "tb_product_category",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    Set<Category> categories = new HashSet<>(); //pra pegar os registros
}
