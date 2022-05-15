package net.codejava;

public class OrderDetail {
	private String productName;
	private float subtotal;
	private float arrears;
	private float tax;
	private float total;
	public OrderDetail(String productName, String subtotal, String arrears, String tax, String total) {
		super();
		this.productName = productName;
		this.subtotal = Float.parseFloat(subtotal);
		this.arrears = Float.parseFloat(arrears);
		this.tax = Float.parseFloat(tax);
		this.total = Float.parseFloat(total);
	}
	public String getProductName() {
		return productName;
	}
	
	public String getSubtotal() {
		return String.format("%.2f",subtotal);
	}
	
	public String getArrears() {
		return String.format("%.2f", arrears);
	}
	
	public String getTax() {
		return String.format("%.2f",tax);
	}
	
	public String getTotal() {
		return String.format("%.2f",total);
	}
	
	
	
	
}
