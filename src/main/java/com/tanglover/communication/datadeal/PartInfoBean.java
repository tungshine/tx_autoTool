package com.tanglover.communication.datadeal;

import java.io.Serializable;

public class PartInfoBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String partName;
	private String partIntroduce;
	public String getPartName() {
		return partName;
	}
	public void setPartName(String partName) {
		this.partName = partName;
	}
	public String getPartIntroduce() {
		return partIntroduce;
	}
	public void setPartIntroduce(String partIntroduce) {
		this.partIntroduce = partIntroduce;
	}
	public PartInfoBean(String partName, String partIntroduce) {
		super();
		this.partName = partName;
		this.partIntroduce = partIntroduce;
	}
	public PartInfoBean() {
		super();
	}
}
