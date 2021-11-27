package com.yogo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.yogo.model.Bookmark;

public interface BookmarkRepository extends JpaRepository<Bookmark, Integer> {
	@Query(value = "SELECT * FROM bookmark WHERE id_user = ?1",nativeQuery = true)
	List<Bookmark> getAllBookmarksByIdUser(Integer id);
}
