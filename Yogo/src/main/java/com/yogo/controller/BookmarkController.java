package com.yogo.controller;

import java.util.HashMap;
import java.util.List;

import com.yogo.message.MessageText;
import com.yogo.message.ResponseMessage;
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
import com.yogo.business.auth.UserService;
import org.springframework.web.client.HttpClientErrorException;

@RestController
@RequestMapping("/bookmarks")
public class BookmarkController {

    @Autowired
    private BookmarkService bookmarkService;

    @Autowired
    private UserService userService;

    @GetMapping("")
    public ResponseEntity<?> getAllBookmarksByIdUser(@RequestHeader(value = "session") String sessionKey) {
        HashMap<String, Object> map = new HashMap<>();
        if (userService.isSessionValid(sessionKey) != null) {
            User client = userService.isSessionValid(sessionKey);
            List<Bookmark> bookmarks = bookmarkService.getAllBookmarksByIdUser(client.getId());
            map.put("bookmarks", bookmarks);
            return ResponseMessage.success(map);
        }
        throw new HttpClientErrorException(HttpStatus.FORBIDDEN, MessageText.FORBIDDEN);
    }
}
