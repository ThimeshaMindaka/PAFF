package net.codejava;

import java.util.ArrayList;
import java.util.List;

import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Details;
import com.paypal.api.payments.Item;
import com.paypal.api.payments.ItemList;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.PayerInfo;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;

public class PaymentServices {
	private static final String CLIENT_ID = "AaPIddgTtO1i_nX6a5VMqIMR3qNZl-8xFRdQR_Dsjp8C9PpHWv0vAFM9CYSvM9EnxlK5q6Ss48V-Bzqe";
	private static final String CLIENT_SECRET = "ELnluxNGUE8u8b9jIJYWoc2dWSn6OJwd5VhrLZdcbAvXDOyC0S4yfvjpair-yv1gmtsaYZvCU-EBIXB9";
	private static final String MODE = "sandbox";
	
	
	public String authorizePayment(OrderDetail orderDetail) 
			throws PayPalRESTException {
		
		Payer payer = getPayerInformation();
		RedirectUrls redirectUrls = getRedirectURLs();
		List<Transaction> listTransaction = getTransactionInformation(orderDetail);
		
		Payment requestPayment = new Payment();
		requestPayment.setTransactions(listTransaction)
					  .setRedirectUrls(redirectUrls)
					  .setPayer(payer)
					  .setIntent("authorize");
		
		APIContext apiContext = new APIContext(CLIENT_ID, CLIENT_SECRET,MODE);
		Payment approvedPayment = requestPayment.create(apiContext);
		
		System.out.println(approvedPayment);
		
		return getApprovalLink(approvedPayment);
	}
	
	private String getApprovalLink(Payment approvedPayment) {
		List<Links> links = approvedPayment.getLinks();
		String approvalLink = null; 
		for (Links link : links) {
			if(link.getRel().equalsIgnoreCase("approval_url")) {
				approvalLink = link.getHref();
			}
		}
		
		return approvalLink;
	}

	private List<Transaction> getTransactionInformation(OrderDetail orderDetail){
		Details details = new Details();
		details.setArrears(orderDetail.getArrears());
		details.setSubtotal(orderDetail.getSubtotal());
		details.setTax(orderDetail.getTax());
		
		Amount amount = new Amount();
		amount.setCurrency("USD");
		amount.setTotal(orderDetail.getTotal());
		amount.setDetails(details);
		
		Transaction transaction = new Transaction();
		transaction.setAmount(amount);
		transaction.setDescription(orderDetail.getProductName());
		
		ItemList itemList = new ItemList();
		List<Item> items = new ArrayList<Item>();
		
		Item item = new Item();
		item.setCurrency("USD")
			.setName(orderDetail.getProductName())
			.setPrice(orderDetail.getProductName())
			.setTax(orderDetail.getTax())
			.setQuantity("1");
		
		items.add(item);
		itemList.setItems(items);
		transaction.setItemList(itemList);
		
		List<Transaction> listTransaction = new ArrayList<Transaction>();
		listTransaction.add(transaction);
		
		return listTransaction;
	}
	
	private RedirectUrls getRedirectURLs() {
		RedirectUrls redirectUrls = new RedirectUrls();
		redirectUrls.setCancelUrl("http://localhost/PayPalTest/cancel.html");
		redirectUrls.setReturnUrl("http://localhost/PayPalTest/review_payment");
	
		return redirectUrls;
	}

	public Payment getPaymentDetails(String paymentId) throws PayPalRESTException {
		APIContext apiContext = new APIContext(CLIENT_ID, CLIENT_SECRET, MODE);
		return Payment.get(apiContext, paymentId);
	}
	
	public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException {
		PaymentExecution paymentExecution = new PaymentExecution();
		paymentExecution.setPayerId(payerId);
		
		Payment payment = new Payment().setId(paymentId);
		
		APIContext apiContext = new APIContext(CLIENT_ID, CLIENT_SECRET, MODE);
		
		return payment.execute(apiContext, paymentExecution);
	}
	
	private Payer getPayerInformation() {
		Payer payer = new Payer();
		payer.setPaymentMethod("paypal");
		
		PayerInfo payerInfo = new PayerInfo();
		payerInfo.setFirstName("William")
				 .setLastName("Peterson")
				 .setEmail("william.peterson@gmail.com");
		
		payer.setPayerInfo(payerInfo);
		
		return payer;
	}
}
