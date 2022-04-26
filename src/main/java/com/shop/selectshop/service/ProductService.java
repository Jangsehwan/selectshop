package com.shop.selectshop.service;

import com.shop.selectshop.model.Product;
import com.shop.selectshop.dto.ProductMypriceRequestDto;
import com.shop.selectshop.repository.ProductRepository;
import com.shop.selectshop.dto.ProductRequestDto;
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

//    @Autowired
//    public ProductService (ApplicationContext context) {
//        // 1. 빈 이름으로 가져오기(수동) - @Configuration에서 등록한 Bean에서 가져와
//        ProductRepository productRepository = (ProductRepository) context.getBean("productRepository");
//        // 2. 빈 클래스 형식으로 가져오기
////        ProductRepository productRepository = context.getBean(productRepository.class);
//        this.productRepository = productRepository;
//    }
    public Product createProduct(ProductRequestDto requestDto) {

        // 요청받은 DTO 로 DB에 저장할 객체 만들기
        Product product = new Product(requestDto);

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

    public List<Product> getProducts() {
        List<Product> products = productRepository.findAll();

        return products;
    }
}
