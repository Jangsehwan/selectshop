package com.shop.selectshop.controller;


import com.shop.selectshop.dto.FolderRequestDto;
import com.shop.selectshop.model.Folder;
import com.shop.selectshop.model.User;
import com.shop.selectshop.security.UserDetailsImpl;
import com.shop.selectshop.service.FolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FolderController {

    private final FolderService folderService;

    @Autowired // folderService DI
    public FolderController(FolderService folderService) {
        this.folderService = folderService;
    }


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
}
