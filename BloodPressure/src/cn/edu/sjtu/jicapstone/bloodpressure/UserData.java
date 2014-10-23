package cn.edu.sjtu.jicapstone.bloodpressure;

import java.util.Date;

/**
 * This class is a POJO that contains all the data
 * @author Shaoxiang Su
 *
 */
public class UserData {
	private java.util.Date date;
	private int dbpValue;
	private int sbpValue;
	private int heartRate;
	public UserData(Date date, int dbpValue, int sbpValue, int heartRate) {
		super();
		this.date = date;
		this.dbpValue = dbpValue;
		this.sbpValue = sbpValue;
		this.heartRate = heartRate;
	}
	public java.util.Date getDate() {
		return date;
	}
	public void setDate(java.util.Date date) {
		this.date = date;
	}
	public int getDbpValue() {
		return dbpValue;
	}
	public void setDbpValue(int dbpValue) {
		this.dbpValue = dbpValue;
	}
	public int getSbpValue() {
		return sbpValue;
	}
	public void setSbpValue(int sbpValue) {
		this.sbpValue = sbpValue;
	}
	
	public int getHeartRate() {
		return heartRate;
	}
	public void setHeartRate(int heartRate) {
		this.heartRate = heartRate;
	}
	
}
