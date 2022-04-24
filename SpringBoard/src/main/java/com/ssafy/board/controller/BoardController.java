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

/*
 * 게시판 글에 대한 Controller 
 */

@Controller
@RequestMapping("/board")
public class BoardController {

	// 게시판 기능 구현 담당 인터페이스 호출
	@Autowired
	private BoardService boardService;
	// 컨테이너와의 통신을 위한 인터페이스 호출
	@Autowired
	private ServletContext servletContext;

	/*
	 * 게시글 목록 페이지로 이동하는 메서드 검색 조건을 설정하지 않을 수 있으므로 defaultValue 값에 빈 문자열을 넣는다. mode :
	 * 검색 조건 keyword : 검색 키워드
	 */
	@GetMapping("list")
	public String list(Model model, @RequestParam(defaultValue = "") String mode,
			@RequestParam(defaultValue = "") String keyword) {
		// 모드와 검색 키워드 데이터를 담을 HashMap 생성
		HashMap<String, String> params = new HashMap<String, String>();

		/*
		 * put 메서드의 2개의 매개변수
		 * 첫 번째 매개변수 : HashMap에 입력하는 데이터를 어떤 이름으로 저장할지
		 * 두 번째 매개변수 : HashMap에 입력하는 데이터
		 */
		params.put("mode", mode); // 사용자가 선택한 검색 조건
		params.put("key", keyword); // 사용자가 입력한 검색어

		/*
		 * 검색 조건과 검색어를 포함한 HashMap을 매개변수로 하는
		 * BoardService의 게시글 목록 조회 메서드(getBoardList)를 호출
		 */
		List<Board> list = boardService.getBoardList(params);

		/*
		 * Model이라는 데이터 전송 주머니에
		 * list라는 이름으로(첫 번째 매개변수)
		 * list라는 이름을 가진 List<Board> 객체(두 번째 매개변수)를 담는다.
		 */
		model.addAttribute("list", list);

		/*
		 * 반환하는 문자열은 이동할 뷰 화면의 이름이다.
		 * 단, jsp 파일이 servlet-context에서 자동으로 붙여주는 경로보다
		 * 하위 폴더에 있거나 다른 경로에 위치하면 그에 맞춰 문자열을 작성해야 한다.
		 * 게시글 목록 화면은 board라는 하위 폴더에 있으므로 board/list라고 작성하여 반환한다.
		 */
		return "board/list";
	}

	// 글 작성 화면으로 이동하는 메서드
	@GetMapping("writeForm")
	public String writeForm() {
		// 글 작성 화면 이름을 반환한다.
		return "board/writeform";
	}

	// 작성한 글을 등록하는 메서드
	@PostMapping("write")
	public String write(Board board, MultipartFile upload_file) {
		/*
		 * 사용자가 작성한 글에 첨부파일이 있는 경우
		 * 첨부한 파일을 사용하려면 servlet-context.xml에 관련 bean을 등록해야 한다.
		 * 관련 bean : MultipartResolver
		 */
		if (upload_file.getSize() != 0) {
			String uploadPath = servletContext.getRealPath("/file"); // 업로드 된 파일이 저장될 위치
			/*
			 * 한글을 파일명으로 가진 파일을 업로드할 수 있으므로
			 * web.xml에 filter를 추가해줘야 한다.
			 */
			String fileName = upload_file.getOriginalFilename(); // 업로드한 파일의 이름
			
			/*
			 * 사용자가 업로드한 파일명을 그대로 저장하면
			 * 다른 사용자가 동일한 파일명으로 저장할 때 해당 파일로 덮어쓰기가 된다.
			 * 따라서 동일한 파일명으로 업로드해도 문제가 발생하지 않도록 UUID를 사용한다.
			 */
			String saveName = UUID.randomUUID() + ""; // 실제로 저장될 파일의 이름
			File target = new File(uploadPath, saveName); // 파일 경로와 저장할 이름을 지정한 File 객체를 생성
			
			// 저장할 위치에 실제 폴더가 존재하는지 여부에 따라
			if (!new File(uploadPath).exists())
				new File(uploadPath).mkdirs(); // 존재하지 않으면 폴더를 생성한다.
			
			try {
				/*
				 * 업로드한 파일을 targe에 저장된 경로와 이름으로
				 * 해당 위치에 해당 이름으로 저장한다.
				 */
				FileCopyUtils.copy(upload_file.getBytes(), target);
				
				/*
				 * 위의 과정까지 아직 board라는 객체에 파일명과 파일 경로가 저장되어 있지 않은 상태이다.
				 * 이제 setter를 사용해서 파일명과 경로를 지정해줄건데,
				 * 추후 사용자가 다시 해당 파일을 다운로드 받을 때 업로드 때 정한 이름으로 다운로드가 지정되어야 하므로
				 * fileName은 사용자가 업로드한 파일명을 저장하고,
				 * fileUri는 target에 저장된 상대경로를 getCanonicalPath 메서드를 사용하여 정규 표현식을 통한 절대 경로로 변환된 값을 저장한다.
				 */
				board.setFileName(fileName);
				board.setFileUri(target.getCanonicalPath());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		/*
		 *  파일 첨부 여부와 상관없이 작성한 글을 DB에 반영한다.
		 *  DB에 반영하는 service의 writeBoard 메서드를 호출한다.
		 */
		boardService.writeBoard(board);
		
		/*
		 * 글 작성이 완료되면 게시글 목록 화면으로 이동한다.
		 * 단, 뷰 이름을 바로 반환하면 기본값이 forward 방식이기 때문에
		 * 새로고침 시 다시 글 작성 명령이 실행될 수 있다.
		 * 따라서, 목록 화면으로 이동 시 redirect 방식으로 이동하여
		 * 이전에 수행한 명령 정보가 남아있지 않도록 한다.
		 */
		return "redirect:/board/list";
	}

	// 글 상세 조회 페이지로 이동하는 메서드
	@GetMapping("detail")
	public String detail(Model model, int id) {
		// 클릭한 글에 고유 번호를 사용하여 해당 글 정보를 반환하는 메서드 실행
		Board board = boardService.readBoard(id);
		
		// Model이라는 주머니에 board라는 이름으로(첫 번째 매개변수) board라는 이름의 Board 객체(두 번째 매개변수)를 담는다.
		model.addAttribute("board", board);
		
		// Model이라는 주머니와 함께 detail.jsp로 이동한다.
		return "board/detail";
	}

	// 파일 다운로드 메서드
	@GetMapping("download")
	public String download(Model model, int id) {
		// 클릭한 글에 고유 번호를 사용하여 해당 글 정보를 반환하는 메서드 실행
		Board board = boardService.getBoardById(id);
		
		// 클릭한 글에 포함된 첨부파일의 uri와 파일명을 첫 번째 매개변수 자리에 입력한 이름으로 Model 주머니에 담는다.
		model.addAttribute("fileuri", board.getFileUri());
		model.addAttribute("filename", board.getFileName());
		
		/*
		 * 이 메서드의 경우 반환하는 값이 view의 이름이 아니다.
		 * 왜? servlet-context에서 'fileDownLoadView'라는 값을 반환할 때는
		 * Controller 패키지에 있는 FileDownLoadView라는 클래스를 호출하도록 설정했기 때문이다.
		 */
		return "fileDownLoadView";
	}

	// 글 수정 페이지로 이동하는 메서드
	@GetMapping("updateForm")
	public String updateForm(Model model, int id) {
		/*
		 * 주소창으로 통해 id 값을 넘겨받으면(쿼리 스트링)
		 * 자동으로 id라는 매개변수에 값이 입력되고
		 * 이 메서드에서는 해당 변수를 사용하여 글 정보를 조회한다.
		 */
		Board board = boardService.getBoardById(id);
		
		// 조회된 글 정보를 board라는 이름으로 Model에 담는다.
		model.addAttribute("board", board);
		
		// 정보가 담긴 주머니와 함께 updateform.jsp로 이동한다.
		return "board/updateform";
	}

	// 글 삭제 메서드(화면으로 이동하는 것 아님)
	@GetMapping("delete")
	public String delete(int id) {
		
		/*
		 * 삭제하려는 글의 id를 주소창을 통해 넘겨받으면(쿼리 스트링)
		 * 자동을 id라는 매개변수에 값이 입력되고
		 * 이 메서드에서는 해당 변수를 사용하여 service의 삭제 메서드를 실행한다.
		 */
		boardService.deleteBoard(id);
		
		/*
		 *  글 삭제가 완료되면 글 목록 화면으로 이동하는데
		 *  이 경우도 역시 동일한 명령의 반복 수행을 방지하기 위해 redirect 방식으로 이동한다.
		 */
		return "redirect:/board/list";
	}

	// 수정한 내용을 반영하는 메서드
	@PostMapping("update")
	public String update(Board board) {
		// 사용자가 입력한 Board 객체로 해당 글을 수정하는 메서드를 호출한다.
		boardService.modifyBoard(board);
		
		/* 
		 * 수정이 완료되면 수정된 결과 화면으로 이동한다.
		 * 이 과정도 반복 수행 방지를 위해 redirect 방식으로 이동하는데,
		 * 방금 수정한 글에 대한 세부 화면으로 이동해야 하기 때문에
		 * 해당 글의 고유 번호(id)가 필요하다.
		 * 그런데 redirect의 경우 주머니를 주고받을 수 없기 때문에
		 * 주소창에 데이터를 담는 쿼리스트링을 사용해서 id를 넘겨준다.
		 * redirect.addattribute()를 사용해서 넘겨줄 수도 있다.
		 */
		return "redirect:/board/detail?id=" + board.getId();
	}
}
