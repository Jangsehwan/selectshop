package com.shop.selectshop.service;

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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FolderService {
    private FolderRepository folderRepository;
    private ProductRepository productRepository;

    @Autowired //  folderRepository DI
    public FolderService(
            FolderRepository folderRepository,
            ProductRepository productRepository
    ) {
        this.folderRepository = folderRepository;
        this.productRepository = productRepository;
    }
    public List<Folder> addFolders(List<String> folderNames, User user) {
        List<Folder> folderList = new ArrayList<>();

// 방법1.
//        for(String folderName : folderNames) {
//            Folder folder = new Folder(folderName, user);
//            Folder saveFolder = folderRepository.save(folder);
//            folderList.add(saveFolder);
//        }

// 방법2.
        for(String folderName : folderNames) {
            Folder folder = new Folder(folderName, user);
            folderList.add(folder);
        }
//        List<Folder> savedfolderList  = folderRepository.saveAll(folderList);
        folderList  = folderRepository.saveAll(folderList);


        return folderList;
    }

    public List<Folder> getFolders(User user) {
        List<Folder> folderListbyUser = folderRepository.findAllByUser(user);
        return folderListbyUser;
    }

    public Page<Product> getProductsInFolder(
            Long folderId,
            int page,
            int size,
            String sortBy,
            boolean isAsc,
            User user
    ) {
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Long userId = user.getId();

        Page<Product> pageProduct = productRepository.findAllByUserIdAndFolderList_Id(userId, folderId, pageable);
        return pageProduct;
    }
}
