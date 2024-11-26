package com.beatrizmed.dscatalago.dtos.caterory;

import com.beatrizmed.dscatalago.entities.category.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

//dto = transferencia de dados objetos
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
//manipulação de dados
public class CategoryDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;

    public CategoryDTO(Category entity) {
        this.id = entity.getId();
        this.name = entity.getName();
    }
}
