package com.shop.selectshop.repository;

import com.shop.selectshop.model.Folder;
import com.shop.selectshop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FolderRepository extends JpaRepository<Folder, Long> {
    List<Folder> findAllByUser(User user);
}
