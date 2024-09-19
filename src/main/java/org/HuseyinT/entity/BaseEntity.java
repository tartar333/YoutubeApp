package org.YoutubeApp.entity;

public class BaseEntity {
	private Byte state;
	private Long createdAt;
	private Long updatedAt;
	
	public BaseEntity() {
	}
	
	public BaseEntity(Byte state, Long createdAt, Long updatedAt) {
		this.state = state;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}
	
	public Byte getState() {
		return state;
	}
	
	public void setState(Byte state) {
		this.state = state;
	}
	
	public Long getCreatedAt() {
		return createdAt;
	}
	
	public void setCreatedAt(Long createdAt) {
		this.createdAt = createdAt;
	}
	
	public Long getUpdatedAt() {
		return updatedAt;
	}
	
	public void setUpdatedAt(Long updatedAt) {
		this.updatedAt = updatedAt;
	}
	
	@Override
	public String toString() {
		return "BaseEntity{" + "state=" + getState() + ", createdAt=" + getCreatedAt() + ", updatedAt=" + getUpdatedAt() + '}';
	}
}