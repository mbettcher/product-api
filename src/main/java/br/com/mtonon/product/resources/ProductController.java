package br.com.mtonon.product.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.mtonon.product.services.ProductService;
import br.com.mtonon.shopping.domain.dto.ProductDTO;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/product")
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@GetMapping
	public List<ProductDTO> getProducts(){
		return productService.getAll();
	}

	@GetMapping(value = "/category/{categoryId}")
	public List<ProductDTO> getProductByCategory(@PathVariable Long categoryId){
		return productService.getProductByCategory(categoryId);
	}
	
	@GetMapping(value = "/{productIdentifier}")
	public ProductDTO findById(@PathVariable String productIdentifier) {
		return productService.findByProductIdentifier(productIdentifier);
	}
	
	@PostMapping
	public ProductDTO newProduct(@Valid @RequestBody ProductDTO productDTO) {
		return productService.save(productDTO);
	}
	
	@DeleteMapping
	public void delete(@PathVariable Long id) {
		productService.delete(id);
	}
	
	@PostMapping(value = "/{id}")
	public ProductDTO editProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
		return productService.editProduct(id, productDTO);
	}
	
	@GetMapping(value = "/pageable")
	public Page<ProductDTO> getProductPage(Pageable pageable){
		return productService.getAllPage(pageable);
	}
}
