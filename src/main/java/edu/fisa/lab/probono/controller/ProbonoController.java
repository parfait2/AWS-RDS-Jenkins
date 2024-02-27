package edu.fisa.lab.probono.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import edu.fisa.lab.probono.model.ProbonoService;
import edu.fisa.lab.probono.model.dto.ActivistDTO;

/* client 요청시 각 메소드의 반환 타입으로 JSON 으로 응답하지 않고(@RestController)
 * jsp로 응답하려 할 경우 구현하는 방식의 controller 
 * 
 * application.yml 파일의 jsp 관련 설정 확인 후 이해하기
 */
@Controller
public class ProbonoController {

	/*
	 * ProbonoService probonoService = new ProbonoService();
	 * 만일 probonoService가 interface인 경우, 하위 클래스로 자동 객체 생성
	 * Spring Bean 객체 생성, 관리도 Spring이 하겠다는 의미
	 * 기본 기질 : singleton 방식으로 객체 생성
	 * */
	@Autowired
	ProbonoService probonoService;

	/* 동기 방식으로 요청 받고 응답하는 메소드
	 * html에서 호출 -> 메소드 실행 -> activist폴더 하위의 activistList.jsp 파일 실행
	 * 
	 * API 소개
	 * 1. ModelAndView
	 * 	- HttpServletRequest 와 forward 코드 혼용된 Spring 정통 주요 API
	 * 
	 * 	- Model : 데이터 의미
	 * 	- view : 응답 화면 의미(jsp)
	 * 
	 * 	- addObject(key, value) : setAttribute()와 동일한 기능의 메소드
	 *  - setViewName() : application.yml에 지정한 jsp의 사전 폴더 및 확장자 적용하는 메소드로 jsp 파일명만 작성
	 */
	@RequestMapping(path = "/activistAllView", method = RequestMethod.GET)
	public ModelAndView activistAll() throws Exception {
		System.out.println("---------");
		List<ActivistDTO> all = probonoService.getAllActivists();
		
		// HttpServletRequest와 흡사한 기능의 객체
		// 데이터인 model과 화면 view
		ModelAndView md = new ModelAndView();
		md.addObject("activistAll", all);
		md.setViewName("activist/activistList");
		return md;
	}
	
	/* forward로 jsp파일 실행하는 코드
	 * 예외 발생시 forward방식으로 jsp에게 응답하게 하는 방식
	 * 
	 * 주요 API
	 * Model - HttpServletRequest 객체와 동일
	 * addAttribute() : setAttribute()와 동일
	 */
	@ExceptionHandler
	public String exceptionHandler(Exception e, Model m) {
		m.addAttribute("errorMsg", "발생된 이슈 " + e.getMessage());
		e.printStackTrace();
		return "forward:showError.jsp";
	}
}
