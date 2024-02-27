package edu.fisa.lab.probono.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.fisa.lab.probono.model.ProbonoService;
import edu.fisa.lab.probono.model.dto.ActivistDTO;
import edu.fisa.lab.probono.model.dto.ProbonoProjectDTO;

// jsp 없이 응답 데이터를 문자열화해서 바로 요청한 영역으로 출력
// 문자열 구조 - DTO가 응답 시 JSON 포맷의 문자열, List 응답 시 JSON 배열 형식의 문자열로 응답
// 비동기로 개발 권장
@RestController
public class ProbonoRestController {

	@Autowired
	ProbonoService probonoService;

	// http://ip:port/content/path
	// http://127.0.0.1:80
	// 모든 ProbonoProject 검색 메소드
	@GetMapping("/probonoProjectAll")
	public List<ProbonoProjectDTO> probonoProjectAll() throws Exception {
		return probonoService.getAllProbonoProjects();
	}
	

	@GetMapping("/activistAll")
	public List<ActivistDTO> activistAll() throws Exception {
		return probonoService.getAllActivists();
	}

	//한명의 기부자 검색
	//activist?id=v
	@GetMapping("/activist")
	public ActivistDTO activist(@RequestParam("id") String activistId) throws Exception {
		return probonoService.getActivist(activistId); //json 포멧으로 client의 js 요청 영역으로 출력
	}

	@PostMapping("/activistInsert")
	protected String activistInsert(ActivistDTO activist) throws Exception {
		boolean result = probonoService.addActivist(activist);
		return result ? "기부자 가입 완료" : "가입실패";
	}

}
