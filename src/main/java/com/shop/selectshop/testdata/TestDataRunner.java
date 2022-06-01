package com.shop.selectshop.testdata;

import com.shop.selectshop.dto.ItemDto;
import com.shop.selectshop.model.Product;
import com.shop.selectshop.model.User;
import com.shop.selectshop.model.UserRoleEnum;
import com.shop.selectshop.repository.ProductRepository;
import com.shop.selectshop.repository.UserRepository;
import com.shop.selectshop.service.ItemSearchService;
import com.shop.selectshop.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.shop.selectshop.service.ProductService.MIN_MY_PRICE;

@Component // spring bean으로 등록
public class TestDataRunner implements ApplicationRunner {

    @Autowired
    UserService userService;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    ItemSearchService itemSearchService;

// @Autowired를 사용하지 않고 주입
    // @Autowired를 사용한 주입은 spring이 권장하진 않아!
//   public TestDataRunner(ItemSearchService itemSearchService) {
//        this.itemSearchService = itemSearchService
//    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // 테스트 User 생성
        User testUser = new User("슈가", passwordEncoder.encode("123"), "sugar@sparta.com", UserRoleEnum.USER);
        testUser = userRepository.save(testUser);

        // 테스트 User 의 관심상품 등록
        // 검색어 당 관심상품 10개 등록
        createTestData(testUser, "신발");
        createTestData(testUser, "과자");
        createTestData(testUser, "키보드");
        createTestData(testUser, "휴지");
        createTestData(testUser, "휴대폰");
        createTestData(testUser, "앨범");
        createTestData(testUser, "헤드폰");
        createTestData(testUser, "이어폰");
        createTestData(testUser, "노트북");
        createTestData(testUser, "무선 이어폰");
        createTestData(testUser, "모니터");
    }

    private void createTestData(User user, String searchWord) throws IOException {
        // 네이버 쇼핑 API 통해 상품 검색
        List<ItemDto> itemDtoList = itemSearchService.getItems(searchWord);

        List<Product> productList = new ArrayList<>();

        for (ItemDto itemDto : itemDtoList) {
            Product product = new Product();
            // 관심상품 저장 사용자
            product.setUserId(user.getId());
            // 관심상품 정보
            product.setTitle(itemDto.getTitle());
            product.setLink(itemDto.getLink());
            product.setImage(itemDto.getImage());
            product.setLprice(itemDto.getLprice());

            // 희망 최저가 랜덤값 생성
            // 최저 (100원) ~ 최대 (상품의 현재 최저가 + 10000원)
            int myPrice = getRandomNumber(MIN_MY_PRICE, itemDto.getLprice() + 10000);
            product.setMyprice(myPrice);

            productList.add(product);
        }

        // list들이 모두 db에 저장
        productRepository.saveAll(productList);
    }

    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
}