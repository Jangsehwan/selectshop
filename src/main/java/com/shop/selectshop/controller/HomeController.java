package com.shop.selectshop.controller;

import com.shop.selectshop.model.Folder;
import com.shop.selectshop.model.UserRoleEnum;
import com.shop.selectshop.repository.FolderRepository;
import com.shop.selectshop.security.UserDetailsImpl;
import com.shop.selectshop.service.FolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    private final FolderService folderService;
    @Autowired
    public HomeController(FolderService folderService){
        this.folderService = folderService;
    }
    @GetMapping("/")
    public String home(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        model.addAttribute("username", userDetails.getUsername());

        if (userDetails.getUser().getRole() == UserRoleEnum.ADMIN){
            model.addAttribute("admin_role", true);
        }


        List<Folder> folerList = folderService.getFolders(userDetails.getUser());
        model.addAttribute("folders", folerList);

        return "index";
    }
}