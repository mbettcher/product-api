package br.com.mtonon.product.converter;

import br.com.mtonon.product.domain.Category;
import br.com.mtonon.product.domain.Product;
import br.com.mtonon.shopping.domain.dto.CategoryDTO;
import br.com.mtonon.shopping.domain.dto.ProductDTO;

public class DTOConverter {
	
	public static CategoryDTO convert(Category category) {
		CategoryDTO categoryDTO = new CategoryDTO();
		categoryDTO.setId(category.getId());
		categoryDTO.setNome(category.getNome());
		return categoryDTO;
	}
	
	public static ProductDTO convert(Product product) {
		ProductDTO productDTO = new ProductDTO();
		productDTO.setId(product.getId());
		productDTO.setProductIdentifier(product.getProductIdentifier());
		productDTO.setNome(product.getNome());
		productDTO.setDescricao(product.getDescricao());
		productDTO.setPreco(product.getPreco());
		if(product.getCategory() != null) {
			productDTO.setCategoryDTO(convert(product.getCategory()));
		}
		return productDTO;
	}

}
