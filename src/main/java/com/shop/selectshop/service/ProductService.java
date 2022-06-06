package com.shop.selectshop.service;

import com.shop.selectshop.dto.ProductMypriceRequestDto;
import com.shop.selectshop.dto.ProductRequestDto;
import com.shop.selectshop.model.Folder;
import com.shop.selectshop.model.Product;
import com.shop.selectshop.model.User;
import com.shop.selectshop.repository.FolderRepository;
import com.shop.selectshop.repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final FolderRepository folderRepository;

    public static final int MIN_MY_PRICE = 100;

    @Autowired
    public ProductService(
            ProductRepository productRepository,
            FolderRepository folderRepository
    ) {
        this.productRepository = productRepository;
        this.folderRepository = folderRepository;
    }

    public Product createProduct(ProductRequestDto requestDto, Long userId ) {
        // 요청받은 DTO 로 DB에 저장할 객체 만들기
        Product product = new Product(requestDto, userId);

        productRepository.save(product);

        return product;
    }

    public Product updateProduct(Long id, ProductMypriceRequestDto requestDto) {
        int myprice = requestDto.getMyprice();
        if (myprice < MIN_MY_PRICE) {
            throw new IllegalArgumentException("유효하지 않은 관심 가격입니다. 최소 " + MIN_MY_PRICE + " 원 이상으로 설정해 주세요.");
        }

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NullPointerException("해당 아이디가 존재하지 않습니다."));

        product.setMyprice(myprice);
        productRepository.save(product);

        return product;
    }

    // 회원 ID 로 등록된 상품 조회
    public Page<Product> getProducts(Long userId, int page, int size, String sortBy, boolean isAsc) {
        Sort.Direction direction =  isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);

//        Pageable pageable = new PageRequest(page, size, sort)
        Pageable pageable = PageRequest.of(page, size, sort);

        return productRepository.findAllByUserId(userId, pageable);
    }

    // (관리자용) 상품 전체 조회
    public Page<Product> getAllProducts(int page, int size, String sortBy, boolean isAsc) {
        Sort.Direction direction =  isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);

        Pageable pageable = PageRequest.of(page, size, sort);
        return productRepository.findAll(pageable);
    }

    // Product에 folder 추가
    @Transactional
    public Product addFolder(Long productId, Long folderId, User user) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NullPointerException("해당 상품 아이디가 존재하지 않습니다."));

        Folder folder = folderRepository.findById(folderId)
                .orElseThrow(() -> new NullPointerException("해당 폴더 아이디가 존재하지 않습니다."));

        // 요청한사용자와 product userId가 같은지 비교
        Long loginuserId = user.getId();
        if(!product.getUserId().equals(loginuserId) || !folder.getUser().getId().equals(loginuserId)) {
            throw new IllegalArgumentException("회원님의 관심상품이 아니거나, 회원님의 폴더가 아닙니다!");
        }

        product.addFolder(folder);
//        productRepository.save(product);
        return product;
    }
}