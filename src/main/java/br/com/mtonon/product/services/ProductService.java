package br.com.mtonon.product.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.mtonon.product.domain.Product;
import br.com.mtonon.product.domain.dto.ProductDTO;
import br.com.mtonon.product.repositories.ProductRepository;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepository productRepository;
	
	public List<ProductDTO> getAll(){
		List<Product> products = productRepository.findAll();
		return products.stream().map(ProductDTO::convert).collect(Collectors.toList());
	}
	
	public List<ProductDTO> getProductByCategory(Long categoryId) {
		List<Product> products = productRepository.getProductByCategory(categoryId);
		return products.stream().map(ProductDTO::convert).collect(Collectors.toList());
	}
	
	public ProductDTO findByProductIdentifier(String productIdentifier) {
		Product product = productRepository.findByProductIdentifier(productIdentifier);
		if(product != null) {
			return ProductDTO.convert(product);
		}
		return null;
	}
	
	public ProductDTO save(ProductDTO productDTO) {
		Product product = productRepository.save(Product.convert(productDTO));
		return ProductDTO.convert(product);
	}
	
	public void delete(Long productId) {
		Optional<Product> product = productRepository.findById(productId);
		if(product.isPresent()) {
			productRepository.delete(product.get());
		}
	}
	
	public ProductDTO editProduct(Long id, ProductDTO dto) {
		Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Produto não localizado."));
		
		if(dto.getNome() != null || !dto.getNome().isEmpty()) {
			product.setNome(dto.getNome());
		}
		if(dto.getPreco() != null) {
			product.setPreco(dto.getPreco());
		}
		return ProductDTO.convert(productRepository.save(product));
	}
	
	public Page<ProductDTO> getAllPage(Pageable page){
		Page<Product> pages = productRepository.findAll(page);
		return pages.map(ProductDTO::convert);
	}

}
