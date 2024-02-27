package edu.fisa.lab.probono.model;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import edu.fisa.lab.probono.exception.MessageException;
import edu.fisa.lab.probono.exception.NotExistException;
import edu.fisa.lab.probono.model.dto.ActivistDTO;
import edu.fisa.lab.probono.model.dto.ProbonoProjectDTO;
import edu.fisa.lab.probono.model.entity.Activist;
import edu.fisa.lab.probono.model.entity.ProbonoProject;
import jakarta.transaction.Transactional;

@Service
public class ProbonoService {
	
	@Autowired	
	private ActivistDAO activistDAO;	

	@Autowired
	private ProbonoProjectDAO probonoProjectDAO;	
	
	private ModelMapper mapper = new ModelMapper();
	

	//기부자 id 값으로 한명의 기부자 정보 검색 service
	//entity -> dto로 변환
	public ActivistDTO getActivist(String activistId) throws Exception{
		Optional<Activist> activistEntity = activistDAO.findById(activistId);
		
		if(activistEntity.get() == null){
			throw new NotExistException("검색하는 재능 기부자가 미 존재합니다.");
		}
		
		ActivistDTO activist = mapper.map(activistEntity.get(), ActivistDTO.class);
		
		return activist;
	}
	
	
	public List<ActivistDTO> getAllActivists() throws Exception{
		List<Activist> activistAll = activistDAO.findAll();

		List<ActivistDTO> activistDTOAll = Arrays.asList(mapper.map(activistAll, ActivistDTO[].class));
		return activistDTOAll;
		
	}

	/* 모든 프로젝트 검색
	 * entity와 dto 분리 필수 - ModelMapper 로 entity와 dto 호환
	 */
	public List<ProbonoProjectDTO> getAllProbonoProjects() throws SQLException,NotExistException{
		//List<T> findAll();
		/* DAO의 repository의 Entity 선언으로 인해 반환되는 List 내부의 객체 타입은 반드시 Entity 여야만 함
		 */
		List<ProbonoProject> probonoProjectAll = probonoProjectDAO.findAll();
		
		if(probonoProjectAll == null){
			throw new NotExistException("검색하는 재능기부 프로젝트가 미 존재합니다.");
		}
		
		List<ProbonoProjectDTO> probonoProjectDTOAll = Arrays.asList(mapper.map(probonoProjectAll, ProbonoProjectDTO[].class));
		
		return probonoProjectDTOAll;
	}
	
	
	//Activist - CRUD
	public void notExistActivist(String activistId) throws Exception{
		
		//존재 여부 확인 가능한 dao의 메소드
		boolean result = activistDAO.existsById(activistId);
		
		if(result != true){
			throw new NotExistException("검색하는 재능 기부자가 미 존재합니다.");
		}
		
	}
	
	/* client가 입력한 데이터로 dto로 자동 생성
	 * -> db에 insert시에 entity로 변환필요
	 * 	(service에서 작업)
	 */
//	@Transactional
	public boolean addActivist(ActivistDTO activist) throws MessageException{
		Activist activistEntity = mapper.map(activist, Activist.class); //dto를 entity로 변환
		
		try{
			Optional<Activist> act = activistDAO.findById(activist.getId());
			if(!act.isPresent()) {
				activistEntity = activistDAO.save(activistEntity); //db에 insert 또는 update 하는 메소드
				return true;
			}
		}catch(Exception s){
			throw new MessageException("이미 존재하는 ID입니다 다시 시도 하세요");
		}
		return false;
	}
	
	/* 존재하는 데이터의 컬럼값만 변경(update)
	 * - Spring Data jpa
	 * - sql관점에서 영구 저장 및 복원 = 트렌잭션 처리로 표현
	 * 	 : tx 가 transaction의 약어
	 * 	@Transactional 
	 * 		- @Modifying @Query 로 사용자 정의로 개발된 delete or update 메소드들 호출하는 service 메소드에 필수 
	 * 		- commit과 rollback 처리하는 AOP
	 */
	//update, delete는 tx 설정 필수
	@Transactional 
	//재능 기부자 수정 메소드[ActivistDAO의 updateActivist()]
	public boolean updateActivist(String activistId, String major) throws Exception{		
		
		notExistActivist(activistId);
		
		//업데이트 적용한 row 수 반환
		int result = activistDAO.updateActivistByIdMajor(activistId, major);

		if(result == 0){
			throw new NotExistException("재능 기부자 정보 갱신 실패");
		}
		return true;
	}
	
	//재능 기부자 삭제 메소드[ ActivistDAO.deleteActivist()]
	public boolean deleteActivist(String activistId) throws Exception{
		notExistActivist(activistId);
		activistDAO.deleteById(activistId) ;
//		if(!result){
//			throw new NotExistException("재능 기부자 정보 삭제 실패");
//		}
		return true;
	}
}
