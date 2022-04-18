package vo;

public class Cashbook {
	private int cashbookNo;
	private String cashDate;
	private String kind;
	private int cash;
	private String memo;
	private String updateDate;
	private String createDate;
	
	@Override
	public String toString() {
		return "Cashbook [cashbookNo=" + cashbookNo + ", cashDate=" + cashDate + ", kind=" + kind + ", cash=" + cash
				+ ", memo=" + memo + ", updateDate=" + updateDate + ", createDate=" + createDate + "]";
	}
	
	// getter + setter
	public int getCashbookNo() {
		return cashbookNo;
	}
	public void setCashbookNo(int cashbookNo) {
		this.cashbookNo = cashbookNo;
	}
	public String getCashDate() {
		return cashDate;
	}
	public void setCashDate(String cashDate) {
		this.cashDate = cashDate;
	}
	public String getKind() {
		return kind;
	}
	public void setKind(String kind) {
		this.kind = kind;
	}
	public int getCash() {
		return cash;
	}
	public void setCash(int cash) {
		this.cash = cash;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	
	
}
