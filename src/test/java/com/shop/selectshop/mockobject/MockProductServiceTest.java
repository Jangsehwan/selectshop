package com.shop.selectshop.mockobject;

import com.shop.selectshop.dto.ProductMypriceRequestDto;
import com.shop.selectshop.dto.ProductRequestDto;
import com.shop.selectshop.model.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.shop.selectshop.service.ProductService.MIN_MY_PRICE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MockProductServiceTest {
    @Test
    @DisplayName("관심 상품 희망가 - 최저가 이상으로 변경")
    void updateProduct_Normal() {
        // given
        int myprice = MIN_MY_PRICE + 1000;

        ProductMypriceRequestDto requestMyPriceDto = new ProductMypriceRequestDto(
                myprice
        );

        Long userId = 777L;
        ProductRequestDto requestProductDto = new ProductRequestDto(
                "오리온 꼬북칩 초코츄러스맛 160g",
                "https://shopping-phinf.pstatic.net/main_2416122/24161228524.20200915151118.jpg",
                "https://search.shopping.naver.com/gate.nhn?id=24161228524",
                2350
        );

        MockProductService mockProductService = new MockProductService();
        // 회원의 관심상품을 생성
        Product product = mockProductService.createProduct(requestProductDto, userId);

        // when - 이랬을때
        Product result = mockProductService.updateProduct(product.getId(), requestMyPriceDto);

        // then - 그결과는
        assertEquals(myprice, result.getMyprice());
    }

    @Test
    @DisplayName("관심 상품 희망가 - 최저가 미만으로 변경")
    void updateProduct_Failed() {
        // given
        int myprice = MIN_MY_PRICE - 50;

        ProductMypriceRequestDto requestMyPriceDto = new ProductMypriceRequestDto(
                myprice
        );

        Long userId = 777L;
        ProductRequestDto requestProductDto = new ProductRequestDto(
                "오리온 꼬북칩 초코츄러스맛 160g",
                "https://shopping-phinf.pstatic.net/main_2416122/24161228524.20200915151118.jpg",
                "https://search.shopping.naver.com/gate.nhn?id=24161228524",
                2350
        );

        MockProductService mockProductService = new MockProductService();
        // 회원의 관심상품을 생성
        Product product = mockProductService.createProduct(requestProductDto, userId);

        // when
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            mockProductService.updateProduct(product.getId(), requestMyPriceDto);
        });

        // then
        assertEquals(
                "유효하지 않은 관심 가격입니다. 최소 " + MIN_MY_PRICE + " 원 이상으로 설정해 주세요.",
                exception.getMessage()
        );
    }
}