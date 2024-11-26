package com.beatrizmed.dscatalago.resources.product;

import com.beatrizmed.dscatalago.dtos.producty.ProductDTO;
import com.beatrizmed.dscatalago.services.product.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/products")
public class ProductResource {

    @Autowired
    private ProductService productService;

    @Operation(description = "Busca paginada de todas os produtos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna os produtos"),
            @ApiResponse(responseCode = "404", description = "Não a registro do produto")
    })
    @GetMapping
    public ResponseEntity<Page<ProductDTO>> findAll(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "12") Integer linesPerPage,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction,
            @RequestParam(value = "orderBy", defaultValue = "name") String orderBy
    ) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction),orderBy);

        Page<ProductDTO> list = productService.findAllPaged(pageRequest);

        return ResponseEntity.ok().body(list);
    }

    @Operation(description = "Busca produto por nome")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna o produto"),
            @ApiResponse(responseCode = "404", description = "Não teve retorno do produto com nome informado")
    })
    @GetMapping("/{name}")
    public ResponseEntity<ProductDTO> findProductByNome(@PathVariable String name) {
        ProductDTO dto = productService.findProductByNome(name);

        return ResponseEntity.ok().body(dto);
    }

    @Operation(description = "Inserindo novoo produto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Inserção realizada"),
            @ApiResponse(responseCode = "404", description = "Não realizado inserção de novo produto")
    })
    @PostMapping
    public ResponseEntity<ProductDTO> insertProduct(@RequestBody ProductDTO dto) {
        dto = productService.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @Operation(description = "Atualizar o produto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Atualização realizada"),
            @ApiResponse(responseCode = "404", description = "Não realizado a atualização do produto")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @RequestBody ProductDTO dto) {
        dto = productService.update(id, dto);
        return ResponseEntity.ok().body(dto);
    }

    @Operation(description = "Deletar o produto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Apagado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Não possivel deletar o produto")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
