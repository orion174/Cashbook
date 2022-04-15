package vo;

public class Category {
	private int categoryNo;
	private String categoryTitle;
	
	@Override
	public String toString() {
		return "Category [categoryNo=" + categoryNo + ", categoryTitle=" + categoryTitle + "]";
	}

	//  getter + setter
	public int getCategoryNo() {
		return categoryNo;
	}
	public void setCategoryNo(int categoryNo) {
		this.categoryNo = categoryNo;
	}
	public String getCategoryTitle() {
		return categoryTitle;
	}
	public void setCategoryTitle(String categoryTitle) {
		this.categoryTitle = categoryTitle;
	}
}