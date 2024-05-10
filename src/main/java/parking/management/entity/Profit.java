package parking.management.entity;

import java.math.BigDecimal;

public class Profit {
	private BigDecimal profite;
	private BigDecimal profiteForDelay;
	private BigDecimal totalProfite;
	private BigDecimal profitePending;
	private BigDecimal profiteForDelayPending;
	private BigDecimal totalProfitePending;
	public BigDecimal getProfitePending() {
		return profitePending;
	}
	public void setProfitePending(BigDecimal profitePending) {
		this.profitePending = profitePending;
	}
	public BigDecimal getProfiteForDelayPending() {
		return profiteForDelayPending;
	}
	public void setProfiteForDelayPending(BigDecimal profiteForDelayPending) {
		this.profiteForDelayPending = profiteForDelayPending;
	}
	public BigDecimal getTotalProfitePending() {
		return totalProfitePending;
	}
	public void setTotalProfitePending(BigDecimal totalProfitePending) {
		this.totalProfitePending = totalProfitePending;
	}
	public BigDecimal getProfite() {
		return profite;
	}
	public void setProfite(BigDecimal profite) {
		this.profite = profite;
	}
	public BigDecimal getProfiteForDelay() {
		return profiteForDelay;
	}
	public void setProfiteForDelay(BigDecimal profiteForDelay) {
		this.profiteForDelay = profiteForDelay;
	}
	public BigDecimal getTotalProfite() {
		return totalProfite;
	}
	public void setTotalProfite(BigDecimal totalProfite) {
		this.totalProfite = totalProfite;
	}
	
}
