package edu.fisa.lab.probono.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import edu.fisa.lab.probono.model.entity.Activist;

@Repository
public interface ActivistDAO extends JpaRepository<Activist, String>{          
	
	/* :변수 - 동적 변수 선언, 실행시 가변적인 데이터를 반영(변수에 값 대입, 바인딩)
	 * update Activist a set a.major=:major where a.id=:id
	 * 	-> update activist set major=? where id=?
	 * 
	 * 사용자 정의 sql문장 개발 기술
	 * @Modifying @Query
	 * - 주의사항 : entity를 기반으로 한 sql 문장 
	 */
	@Modifying
	@Query("update Activist a set a.major=:major where a.id=:id")
	int updateActivistByIdMajor(@Param("id") String id, @Param("major") String major);
	
}