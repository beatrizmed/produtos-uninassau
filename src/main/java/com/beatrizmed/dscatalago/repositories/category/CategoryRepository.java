package com.beatrizmed.dscatalago.repositories.category;

import com.beatrizmed.dscatalago.entities.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

//classe de persistencia de dados
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByName(String name); //definição do orm = mapeamento de objeto realional
}
