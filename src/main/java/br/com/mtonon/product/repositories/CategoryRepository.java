package br.com.mtonon.product.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.mtonon.product.domain.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{

}
