package com.beatrizmed.dscatalago.resources.category;

import com.beatrizmed.dscatalago.dtos.caterory.CategoryDTO;
import com.beatrizmed.dscatalago.services.category.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/categories")
public class CategoryResource {

    @Autowired
    private CategoryService categoryService;

    @Operation(description = "Busca paginada de todas as categorias")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna as categorias"),
            @ApiResponse(responseCode = "404", description = "Não a registro da categoria")
    })
    @GetMapping
    public ResponseEntity<Page<CategoryDTO>> findAll(Pageable pageable) {

        Page<CategoryDTO> list = categoryService.findAllPaged(pageable);

        return ResponseEntity.ok().body(list);
    }

    @Operation(description = "Busca categoria por nome")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna a categoria"),
            @ApiResponse(responseCode = "404", description = "Não teve retorno da categoria com nome informado")
    })
    @GetMapping("/{name}")
    public ResponseEntity<CategoryDTO> findCategoriesByNome(@PathVariable String name) {
        CategoryDTO dto = categoryService.findCategoriesByNome(name);

        return ResponseEntity.ok().body(dto);
    }

    @Operation(description = "Inserindo nova categoria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Inserção realizada"),
            @ApiResponse(responseCode = "404", description = "Não realizado inserção de nova categoria")
    })
    @PostMapping
    public ResponseEntity<CategoryDTO> insertCategory(@RequestBody CategoryDTO dto) {
        dto = categoryService.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @Operation(description = "Atualizar a categoria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Atualização realizada"),
            @ApiResponse(responseCode = "404", description = "Não realizado a atualização")
    })
    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable Long id, @RequestBody CategoryDTO dto) {
        dto = categoryService.update(id, dto);
        return ResponseEntity.ok().body(dto);
    }

    @Operation(description = "Deletar a categoria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Apagado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Não possivel ser deletado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
