package com.shop.selectshop.service;

import com.shop.selectshop.dto.ProductMypriceRequestDto;
import com.shop.selectshop.dto.ProductRequestDto;
import com.shop.selectshop.model.Product;
import com.shop.selectshop.repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product createProduct(ProductRequestDto requestDto, Long userId ) {
// 요청받은 DTO 로 DB에 저장할 객체 만들기
        Product product = new Product(requestDto, userId);

        productRepository.save(product);

        return product;
    }

    public Product updateProduct(Long id, ProductMypriceRequestDto requestDto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NullPointerException("해당 아이디가 존재하지 않습니다."));

        int myprice = requestDto.getMyprice();
        product.setMyprice(myprice);
        productRepository.save(product);

        return product;
    }

    // 회원 ID 로 등록된 상품 조회
    public List<Product> getProducts(Long userId) {
        return productRepository.findAllByUserId(userId);
    }
}