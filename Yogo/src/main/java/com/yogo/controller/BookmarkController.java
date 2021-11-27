package com.yogo.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yogo.model.Bookmark;
import com.yogo.model.User;
import com.yogo.service.BookmarkService;
import com.yogo.service.UserService;

@RestController
@RequestMapping("/bookmarks")
public class BookmarkController {
	
	@Autowired
	private BookmarkService bookmarkService;
	
	@Autowired
	private UserService userService;
	
	@GetMapping("")
	public ResponseEntity<HashMap<String, Object>> getAllBookmarksByIdUser(@RequestHeader(value = "session") String sessionKey){
		HashMap<String, Object> map = new HashMap<>();
		if(userService.isSessionValid(sessionKey) != null) {
			User client = userService.isSessionValid(sessionKey);
			List<Bookmark> bookmarks = bookmarkService.getAllBookmarksByIdUser(client.getId_user());
			map.put("bookmarks", bookmarks);
			return ResponseEntity.status(HttpStatus.OK).body(map);
		}else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
		}
	}
}
