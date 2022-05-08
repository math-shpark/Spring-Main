package com.ssafy.board.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.board.model.dto.Board;
import com.ssafy.board.model.service.BoardService;

@Controller
@RequestMapping("/board")
public class BoardController {
	@Autowired
	private BoardService boardService;
	
	@Autowired
	private ServletContext servletContext;
	
	@GetMapping("list")
	public String list(Model model,
			@RequestParam(defaultValue = "") String mode,
			@RequestParam(defaultValue = "") String keyword) {
		
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("mode", mode);
		params.put("key", keyword); //아까 mapper에서 key라고...해놨어서..
		List<Board> list = boardService.getBoardList(params);
		model.addAttribute("list", list);
		return "board/list";
	}
	@GetMapping("writeForm")
	public String writeForm() {
		return "board/writeform";//파일명은 소문자네요
	}
	
	
	@PostMapping("write")
	public String write(Board board, MultipartFile upload_file) {
		if( upload_file.getSize() != 0 ) {
			String uploadPath = servletContext.getRealPath("/file");
			String fileName = upload_file.getOriginalFilename();
			String saveName = UUID.randomUUID()+"";
			File target = new File(uploadPath, saveName);
			if( !new File(uploadPath).exists() )
				new File(uploadPath).mkdirs();
			try {
				FileCopyUtils.copy(upload_file.getBytes(), target);
				board.setFileName(fileName);
				board.setFileUri(target.getCanonicalPath());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		boardService.writeBoard(board);
		return "redirect:/board/list";
	}
	
	@GetMapping("detail")
	public String detail(Model model, int id) {
		Board board = boardService.readBoard(id);
		model.addAttribute("board", board);
		return "board/detail";
	}
	@GetMapping("download")
	public String download(Model model, int id) {
		Board board = boardService.getBoardById(id);
		model.addAttribute("fileuri", board.getFileUri());
		model.addAttribute("filename", board.getFileName());
		return "fileDownLoadView";
	}
	@GetMapping("updateForm")
	public String updateForm(Model model, int id) {
		Board board = boardService.getBoardById(id);
		model.addAttribute("board", board);
		return "board/updateform";//얘도 jsp파일 이름이 소문자f
	}
	@GetMapping("delete")
	public String delete(int id) {
		boardService.deleteBoard(id);
		return "redirect:/board/list";
	}
	@PostMapping("update")
	public String update(Board board) {
		boardService.modifyBoard(board);
		return "redirect:/board/detail?id=" + board.getId();
	}
}






