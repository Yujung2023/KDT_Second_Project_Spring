package com.kedu.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kedu.project.service.BoardCommentService;

@RestController
@RequestMapping("/comment")
public class BoardCommetController {

	@Autowired
	BoardCommentService boardService;
	
	// insert
	
	
	// list
	
	// delete
}
