package it.olegna.test.basic.dto;

import java.io.Serializable;

public class SimpleDto implements Serializable {

	private static final long serialVersionUID = 1616554176392794288L;
	private String simpleDtoName;
	
	public SimpleDto() {
		super();
	}
	public SimpleDto(String simpleDtoName) {
		super();
		this.simpleDtoName = simpleDtoName;
	}
	public String getSimpleDtoName() {
		return simpleDtoName;
	}
	public void setSimpleDtoName(String simpleDtoName) {
		this.simpleDtoName = simpleDtoName;
	}
	
}
