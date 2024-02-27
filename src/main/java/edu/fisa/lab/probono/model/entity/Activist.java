package edu.fisa.lab.probono.model.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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

@Entity
public class Activist {

	@Id
	@Column(name = "activist_id")
	private String id;

	private String name;

	private String password;

	private String major;

	@OneToMany(mappedBy = "activistId")
	private List<ProbonoProject> probonoProjects;

		
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(super.toString());
		builder.append(" 4. 재능 기부 분야 : ");
		builder.append(major);
		return builder.toString();
	}

}
