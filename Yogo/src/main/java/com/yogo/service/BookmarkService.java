package com.yogo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yogo.model.Bookmark;
import com.yogo.repository.BookmarkRepository;

@Service
public class BookmarkService {
	
	@Autowired
	private BookmarkRepository repo;
	
	public List<Bookmark> getAllBookmarksByIdUser(Integer id){
		return repo.getAllBookmarksByIdUser(id);
	}
}
