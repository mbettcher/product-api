package br.com.mtonon.product.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.mtonon.product.converter.DTOConverter;
import br.com.mtonon.product.domain.Product;
import br.com.mtonon.product.repositories.CategoryRepository;
import br.com.mtonon.product.repositories.ProductRepository;
import br.com.mtonon.shopping.domain.dto.ProductDTO;
import br.com.mtonon.shopping.exception.CategoryNotFoundException;
import br.com.mtonon.shopping.exception.ProductNotFoundException;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	public List<ProductDTO> getAll(){
		List<Product> products = productRepository.findAll();
		return products.stream().map(DTOConverter::convert).collect(Collectors.toList());
	}
	
	public List<ProductDTO> getProductByCategory(Long categoryId) {
		List<Product> products = productRepository.getProductByCategory(categoryId);
		return products.stream().map(DTOConverter::convert).collect(Collectors.toList());
	}
	
	public ProductDTO findByProductIdentifier(String productIdentifier) {
		Product product = productRepository.findByProductIdentifier(productIdentifier);
		if(product != null) {
			return DTOConverter.convert(product);
		}
		throw new ProductNotFoundException();
	}
	
	public ProductDTO save(ProductDTO productDTO) {
		Product product = productRepository.save(Product.convert(productDTO));
		return DTOConverter.convert(product);
	}
	
	public void delete(Long productId) {
		Optional<Product> product = productRepository.findById(productId);
		if(product.isPresent()) {
			productRepository.delete(product.get());
		}
		throw new ProductNotFoundException();
	}
	
	public ProductDTO editProduct(Long id, ProductDTO dto) {
		//Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Produto não localizado."));
		boolean existsCategory = categoryRepository.existsById(dto.getCategoryDTO().getId());
		/*
		if(dto.getNome() != null || !dto.getNome().isEmpty()) {
			product.setNome(dto.getNome());
		}
		if(dto.getPreco() != null) {
			product.setPreco(dto.getPreco());
		}*/
		if(!existsCategory) {
			throw new CategoryNotFoundException();
		}
		Product product = productRepository.save(Product.convert(dto));
		return DTOConverter.convert(productRepository.save(product));
	}
	
	public Page<ProductDTO> getAllPage(Pageable page){
		Page<Product> pages = productRepository.findAll(page);
		return pages.map(DTOConverter::convert);
	}

}
