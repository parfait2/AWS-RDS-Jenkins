/* Spring Data JPA의 entity 개발 기술
 * 1. 명명 규칙
 * 	멤버 변수명에 대문자가 혼용되어 있다면 table의 컬럼명이 _자동 적용
 * 	probonoProjectId -> probono_project_id
 * 2. 개발자 친화적인 추상화로 인해 개발 코드가 매우 간결
 */

package edu.fisa.lab.probono.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder

@Table(name = "probono_project")
@Entity
public class ProbonoProject {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int probonoProjectId;
	private String probonoProjectName;	
	private String probonoId;
	private String activistId;
	private String receiveId;
	private String projectContent;
	
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("1. 프로젝트 id : ");
		builder.append(probonoProjectId);
		builder.append("2. 프로보노 프로젝트명 : ");
		builder.append(probonoProjectName);
		builder.append("3. 프로보노 정보 : ");
		builder.append(probonoId);
		builder.append("4. 재능 기부자 정보 : ");
		builder.append(activistId);
		builder.append("5. 수해자 정보 : ");
		builder.append(receiveId);
		builder.append("6. 프로젝트 제공내용 : ");
		builder.append(projectContent);
		return builder.toString();
	}
}
