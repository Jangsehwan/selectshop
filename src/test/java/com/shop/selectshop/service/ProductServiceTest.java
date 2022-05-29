package com.shop.selectshop.service;

import com.shop.selectshop.dto.ProductMypriceRequestDto;
import com.shop.selectshop.dto.ProductRequestDto;
import com.shop.selectshop.model.Product;
import com.shop.selectshop.repository.ProductRepository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static com.shop.selectshop.service.ProductService.MIN_MY_PRICE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class) // Mockito framework사용
class ProductServiceTest {
    @Mock // 가짜객체를 만들어줘
    ProductRepository productRepository;

    @Test
    @DisplayName("관심 상품 희망가 - 최저가 이상으로 변경")
    void updateProduct_Normal() {
        // given
        Long productId = 100L;
        int myprice = MIN_MY_PRICE + 1000;

        ProductMypriceRequestDto requestMyPriceDto = new ProductMypriceRequestDto(
                myprice
        );

        // sample data
        Long userId = 777L;
        ProductRequestDto requestProductDto = new ProductRequestDto(
                "오리온 꼬북칩 초코츄러스맛 160g",
                "https://shopping-phinf.pstatic.net/main_2416122/24161228524.20200915151118.jpg",
                "https://search.shopping.naver.com/gate.nhn?id=24161228524",
                2350
        );
        Product product = new Product(requestProductDto, userId);

        ProductService productService = new ProductService(productRepository);

        // 가짜 객체에 값까진 넣어주진않아서 직접 넣어줘야해 -> 아니면 nullpointerror
        // 진짜 db조회까진 하지않기때문에  그냥 넣어줘 - service test에 집중!
        when(productRepository.findById(productId)) // input - Mock객체에 지정한 함수가 호츨됐을떼
                .thenReturn(Optional.of(product)); // output - 이런 결과를 내줘! 라고 직접 주입(그결과는 optional형태)

        // when
        Product result = productService.updateProduct(productId, requestMyPriceDto);

        // then
        assertEquals(myprice, result.getMyprice());
    }

    @Test
    @DisplayName("관심 상품 희망가 - 최저가 미만으로 변경")
    void updateProduct_Failed() {
        // given
        Long productId = 100L;
        int myprice = MIN_MY_PRICE - 50;

        ProductMypriceRequestDto requestMyPriceDto = new ProductMypriceRequestDto(
                myprice
        );

        ProductService productService = new ProductService(productRepository);

        // when
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            productService.updateProduct(productId, requestMyPriceDto);
        });

        // then
        assertEquals(
                "유효하지 않은 관심 가격입니다. 최소 " + MIN_MY_PRICE + " 원 이상으로 설정해 주세요.",
                exception.getMessage()
        );
    }
}