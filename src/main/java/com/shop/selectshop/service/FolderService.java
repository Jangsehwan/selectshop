package com.shop.selectshop.service;

import com.shop.selectshop.model.Folder;
import com.shop.selectshop.model.User;
import com.shop.selectshop.repository.FolderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FolderService {
    private FolderRepository folderRepository;

    @Autowired //  folderRepository DI
    public FolderService(
            FolderRepository folderRepository) {
        this.folderRepository = folderRepository;
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
}
