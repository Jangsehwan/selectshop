package com.shop.selectshop.controller;


import com.shop.selectshop.dto.FolderRequestDto;
import com.shop.selectshop.model.Folder;
import com.shop.selectshop.model.Product;
import com.shop.selectshop.model.User;
import com.shop.selectshop.security.UserDetailsImpl;
import com.shop.selectshop.service.FolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FolderController {

    private final FolderService folderService;

    @Autowired // folderService DI
    public FolderController(FolderService folderService) {
        this.folderService = folderService;
    }


    // 회원에 폴더를 추가
    @PostMapping("api/folders")
    public List<Folder> addFolders(
            @RequestBody FolderRequestDto folderRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails // 로그인한 사용자정보를 받아와
    ) {
        List<String> folderNames = folderRequestDto.getFolderNames();
        User user = userDetails.getUser();

        List<Folder> folders=folderService.addFolders(folderNames, user);
        return folders;
    }

    @GetMapping("api/folders")
    public List<Folder> getFolders(
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        User user = userDetails.getUser();
        List<Folder> folderList = folderService.getFolders(user);
        return folderList;
    }

    @GetMapping("api/folders/{folderId}/products")
    public Page<Product> getProductInFolder(
            @PathVariable Long folderId,
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam String sortBy,
            @RequestParam boolean isAsc,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ){
        page = page - 1;

        Page<Product> pageProduct =  folderService.getProductsInFolder(
                folderId,
                page,
                size,
                sortBy,
                isAsc,
                userDetails.getUser()
        );
        return pageProduct;
    }
}
