package com.beatrizmed.dscatalago.services.product;

import com.beatrizmed.dscatalago.dtos.caterory.CategoryDTO;
import com.beatrizmed.dscatalago.dtos.producty.ProductDTO;
import com.beatrizmed.dscatalago.entities.category.Category;
import com.beatrizmed.dscatalago.entities.product.Product;
import com.beatrizmed.dscatalago.repositories.category.CategoryRepository;
import com.beatrizmed.dscatalago.repositories.product.ProductRepository;
import com.beatrizmed.dscatalago.services.exception.DatabaseException;
import com.beatrizmed.dscatalago.services.exception.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
//serviço = ligado a regra de negócio
@Service
public class ProductService {

    //injeção de dependencia
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    //crud = pegando todos os registros da classe produtos / page = quebra na quantidade por paginas
    @Transactional(readOnly = true)
    public Page<ProductDTO> findAllPaged(PageRequest pageRequest){
        Page<Product> list = productRepository.findAll(pageRequest);
        return list.map(ProductDTO::new); //mapear os registros
    }

    //pra pesquisar o produto pelo nome ao invés do ID
    @Transactional(readOnly = true) //só permite consultas
    public ProductDTO findProductByNome(String name){
        Optional<Product> obj = productRepository.findByName(name);

        //tratamento de exceção, mas só mostra caso nao encontre o objeto (função lambda)
        Product entity = obj.orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado"));
        return new ProductDTO(entity, entity.getCategories());
    }

    //INSERT = inserir os dados
    @Transactional
    public ProductDTO insert(ProductDTO dto) {
        Product entity = new Product();
        copyDtoToEntity(dto, entity);
        productRepository.save(entity);
        return new ProductDTO(entity);
    }

    //UPDATE = retorna as informaçòes da entidade
    @Transactional
    public ProductDTO update(Long id, ProductDTO dto) {
        try {
            Product entity = productRepository.getReferenceById(id);
            copyDtoToEntity(dto, entity);
            entity = productRepository.save(entity);
            return new ProductDTO(entity);
        }catch (EntityNotFoundException e){
            throw new ResourceNotFoundException("Id " + id + ", não encontrado"); //tratamento de exceção / mostra quando não encontra o registro
        }

    }

    //DELETE
    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id) {
        if(!productRepository.existsById(id)){
            throw new ResourceNotFoundException("Recurso não encontrado"); //tratamento de exceção / mostra quando não encontra o registro ou id
        }

        try {
            productRepository.deleteById(id);
        }catch (DataIntegrityViolationException e){
            throw new DatabaseException("Falha de integridade referencial"); //tratamento de exceção
        }
    }

    //pega as informações para lançar para a tabela
    private void copyDtoToEntity(ProductDTO dto, Product entity) {
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setPrice(dto.getPrice());
        entity.setImgUrl(dto.getImgUrl());
        entity.setDate(dto.getDate());

        entity.getCategories().clear();
        for (CategoryDTO categoryDTO : dto.getCategories()) {
            Category category = categoryRepository.getReferenceById(categoryDTO.getId());
            entity.getCategories().add(category);
        }

    }
}
