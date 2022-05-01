package com.ssafy.board.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.board.model.dto.Board;
import com.ssafy.board.model.service.BoardService;

@RestController
@RequestMapping("/api")
public class ApiBoardController {

	@Autowired
	private BoardService boardService;
	@Autowired
	private ServletContext servletContext;

	@GetMapping("board")
	public ResponseEntity<List<Board>> list(@RequestParam(defaultValue = "") String mode,
			@RequestParam(defaultValue = "") String keyword) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("mode", mode);
		params.put("key", keyword);
		List<Board> list = boardService.getBoardList(params);

		ResponseEntity<List<Board>> resEntity = new ResponseEntity<List<Board>>(list, HttpStatus.OK);

		return resEntity;
	}

	@GetMapping("board/{id}/{act}")
	public ResponseEntity<Board> detail(@PathVariable("id") int id, @PathVariable int act) {
		Board board = null;

		if (act == 1)
			board = boardService.readBoard(id);
		else if (act == 2)
			board = boardService.getBoardById(id);

		ResponseEntity<Board> resEntity = new ResponseEntity<Board>(board, HttpStatus.OK);

		return resEntity;
	}

	@GetMapping("download/{id}")
	public ResponseEntity<String> renderMergedOutputModel(@PathVariable int id, HttpServletRequest request,
			HttpServletResponse response) {

		ResponseEntity<String> resEntity = null;

		try {
			Board board = boardService.getBoardById(id);
			String fileuri = board.getFileUri();
			String filename = board.getFileName();
			File file = new File(fileuri);

			response.setContentType("application/download; charset=UTF-8");
			response.setContentLength((int) file.length());

			String header = request.getHeader("User-Agent");
			boolean isIE = header.indexOf("MSIE") > -1 || header.indexOf("Trident") > -1;
			String fileName = null;
			// IE는 다르게 처리
			if (isIE) {
				fileName = URLEncoder.encode(filename, "UTF-8").replaceAll("\\+", "%20");
			} else {
				fileName = new String(filename.getBytes("UTF-8"), "ISO-8859-1");
			}
			response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\";");
			response.setHeader("Content-Transfer-Encoding", "binary");
			OutputStream out = response.getOutputStream();
			FileInputStream fis = null;
			try {
				fis = new FileInputStream(file);
				FileCopyUtils.copy(fis, out);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (fis != null) {
					try {
						fis.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			out.flush();
			resEntity = new ResponseEntity<String>("DOWNLOAD_SUCCESS", HttpStatus.OK);
		} catch (Exception e) {
			resEntity = new ResponseEntity<String>("DOWNLOAD_FAIL", HttpStatus.BAD_REQUEST);
		}

		return resEntity;

	}

	@DeleteMapping("board/{id}")
	public ResponseEntity<String> delete(@PathVariable int id) {
		ResponseEntity<String> resEntity = null;

		try {
			boardService.deleteBoard(id);
			resEntity = new ResponseEntity<String>("DELETE_SUCCESS", HttpStatus.OK);
		} catch (Exception e) {
			resEntity = new ResponseEntity<String>("DELETE_FAIL", HttpStatus.BAD_REQUEST);
		}

		return resEntity;

	}

	@PostMapping("board")
	public ResponseEntity<String> write(Board board, MultipartFile upload_file) {
		ResponseEntity<String> resEntity = null;

		try {
			if (upload_file.getSize() != 0) {
				String uploadPath = servletContext.getRealPath("/file");
				String fileName = upload_file.getOriginalFilename();
				String saveName = UUID.randomUUID() + "";
				File target = new File(uploadPath, saveName);
				if (!new File(uploadPath).exists())
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
			resEntity = new ResponseEntity<String>("WRITE_SUCCESS", HttpStatus.CREATED);
		} catch (Exception e) {
			resEntity = new ResponseEntity<String>("WRITE_FAIL", HttpStatus.BAD_REQUEST);
		}

		return resEntity;

	}

	@PutMapping("board")
	public ResponseEntity<String> update(Board board) {
		ResponseEntity<String> resEntity = null;

		try {
			boardService.modifyBoard(board);
			resEntity = new ResponseEntity<String>("UPDATE_SUCCESS", HttpStatus.OK);
		} catch (Exception e) {
			resEntity = new ResponseEntity<String>("UPDATE_FAIL", HttpStatus.BAD_REQUEST);
		}

		return resEntity;
	}

}
