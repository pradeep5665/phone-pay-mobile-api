package org.uhc.dao.dto;

import java.util.Date;

public class LatestVersionDto {

	private long versionID;
	private String latestAppVersion;
	private String osName;
	private Date productionDate;
	public long getVersionID() {
		return versionID;
	}
	public void setVersionID(long versionID) {
		this.versionID = versionID;
	}
	public String getLatestAppVersion() {
		return latestAppVersion;
	}
	public void setLatestAppVersion(String latestAppVersion) {
		this.latestAppVersion = latestAppVersion;
	}
	public String getOsName() {
		return osName;
	}
	public void setOsName(String osName) {
		this.osName = osName;
	}
	public Date getProductionDate() {
		return productionDate;
	}
	public void setProductionDate(Date productionDate) {
		this.productionDate = productionDate;
	}
	
	
	
}
