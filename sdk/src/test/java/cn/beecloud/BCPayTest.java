package cn.beecloud;

import static junit.framework.Assert.assertEquals;
import mockit.integration.junit4.JMockit;
   









import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.client.Client;

import mockit.Deencapsulation;
import mockit.Delegate;
import mockit.Expectations;
import mockit.Mock;
import mockit.MockUp;
import mockit.NonStrictExpectations;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import cn.beecloud.BCEumeration.PAY_CHANNEL;
import cn.beecloud.BCEumeration.QR_PAY_MODE;
import cn.beecloud.BCEumeration.RESULT_TYPE;
import cn.beecloud.TestConstant.CHANNEL_TYPE;
import cn.beecloud.bean.*;

@RunWith(JMockit.class)
public class BCPayTest {
	
	protected Client client;
	private String billNo;
	private String subject;
	private String refundNo;
	private String batchNo;
	private String transferId1;
	private String transferId2;
	private Map<String, Object> payOptional = new HashMap<String, Object>();
	private Map<String, Object> refundOptional = new HashMap<String, Object>();
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
	}  

	@Before
	public void setUp() throws Exception {
		BeeCloud.registerApp(TestConstant.KTestAppID, TestConstant.kTestAppSecret);
	}

	@Test
	public void testAliWeb() {  
		billNo = BCUtil.generateRandomUUIDPure();
		refundNo = new SimpleDateFormat("yyyyMMdd").format(new Date()) + BCUtil.generateNumberWith3to24digitals();
		subject = "ALI_WEB unit test";
		BCOrder param = new BCOrder(PAY_CHANNEL.ALI_WEB, 1, billNo, subject);
		payOptional.put("aliWebPay", "aliWebPay");
		param.setReturnUrl(TestConstant.aliReturnUrl);
		param.setOptional(payOptional);
		param.setBillTimeout(TestConstant.billTimeOut);
		
		testPay(param, PAY_CHANNEL.ALI_WEB);
		
		testQueryBillById(param);
//		
		BCRefund refund = new BCRefund(billNo, refundNo, 1);
		refundOptional.put("aliWebRefund", "aliWebRefund");
		refund.setOptional(refundOptional);
		refund.setChannel(PAY_CHANNEL.ALI);
//		
		testRefund(refund);
		testRefundUpdate(PAY_CHANNEL.ALI);
//		testQueryRefundById();
//		testQueryBill(PAY_CHANNEL.ALI_WEB);
//		testQueryBillCount(PAY_CHANNEL.ALI_WEB);
//		testQueryRefund(PAY_CHANNEL.ALI_WEB);
//		testQueryBillCount(PAY_CHANNEL.ALI_WEB);
	}
	
	@Test
	public void testAliQrcode() {  
		billNo = BCUtil.generateRandomUUIDPure();
		refundNo = new SimpleDateFormat("yyyyMMdd").format(new Date()) + BCUtil.generateNumberWith3to24digitals();
		subject = "ALI_QRCODE unit test";
		BCOrder param = new BCOrder(PAY_CHANNEL.ALI_QRCODE, 1, billNo, subject);
		payOptional.put("aliQrCodePay", "aliQrCodePay");
		param.setReturnUrl(TestConstant.aliReturnUrl);
		param.setOptional(payOptional);
		param.setBillTimeout(TestConstant.billTimeOut);
		param.setQrPayMode(QR_PAY_MODE.MODE_BRIEF_FRONT);
		
		testPay(param, PAY_CHANNEL.ALI_QRCODE);
//		testQueryBillById(param);
//		testQueryRefundById();
//		testQueryBill(PAY_CHANNEL.ALI_QRCODE);
//		testQueryBillCount(PAY_CHANNEL.ALI_QRCODE);
//		testQueryRefund(PAY_CHANNEL.ALI_QRCODE);
//		testQueryBillCount(PAY_CHANNEL.ALI_QRCODE);
	}
	
	@Test
	public void testAliWap() {  
		billNo = BCUtil.generateRandomUUIDPure();
		refundNo = new SimpleDateFormat("yyyyMMdd").format(new Date()) + BCUtil.generateNumberWith3to24digitals();
		subject = "ALI_WAP unit test";
		BCOrder param = new BCOrder(PAY_CHANNEL.ALI_WAP, 1, billNo, subject);
		payOptional.put("aliWapPay", "aliWapPay");
		param.setOptional(payOptional);
		param.setBillTimeout(TestConstant.billTimeOut);
		
		testPay(param, PAY_CHANNEL.ALI_WAP);
//		testQueryBillById(param);
//		testQueryRefundById();
//		testQueryBill(PAY_CHANNEL.ALI_WAP);
//		testQueryBillCount(PAY_CHANNEL.ALI_WAP);
//		testQueryRefund(PAY_CHANNEL.ALI_WAP);
//		testQueryBillCount(PAY_CHANNEL.ALI_WAP);
	}
	
//	@SuppressWarnings("deprecation")
//	@Test
//	public void testAliTransfer() {  
//		batchNo = BCUtil.generateRandomUUIDPure();
//		transferId1 = BCUtil.generateRandomUUIDPure();
//		transferId2 = BCUtil.generateRandomUUIDPure();
//		List<TransferData> list = new ArrayList<TransferData>();
//		
//		BCPayResult result = BCPay.startTransfer(null, batchNo, TestConstant.ALI_TRANSFER_ACCOUNT_NAME, list);
//		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.PARAM_INVALID.name(), result.getResultMsg());
//		
//		result = BCPay.startTransfer(PAY_CHANNEL.ALI, null, TestConstant.ALI_TRANSFER_ACCOUNT_NAME, list);
//		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.PARAM_INVALID.name(), result.getResultMsg());
//		
//		result = BCPay.startTransfer(PAY_CHANNEL.ALI, batchNo, null, list);
//		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.PARAM_INVALID.name(), result.getResultMsg());
//		
//		result = BCPay.startTransfer(PAY_CHANNEL.ALI, batchNo, TestConstant.ALI_TRANSFER_ACCOUNT_NAME, null);
//		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.PARAM_INVALID.name(), result.getResultMsg());
//	
//		result = BCPay.startTransfer(PAY_CHANNEL.ALI, TestConstant.ALI_TRANSFER_BATCH_NO_WITH_SPECIAL_CHARACTER, TestConstant.ALI_TRANSFER_ACCOUNT_NAME, list);
//		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.PARAM_INVALID.name(), result.getResultMsg());
//		
//		result = BCPay.startTransfer(PAY_CHANNEL.ALI, batchNo + "A", TestConstant.ALI_TRANSFER_ACCOUNT_NAME, list);
//		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.PARAM_INVALID.name(), result.getResultMsg());
//		
//		result = BCPay.startTransfer(PAY_CHANNEL.ALI, batchNo.substring(0, 10), TestConstant.ALI_TRANSFER_ACCOUNT_NAME, list);
//		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.PARAM_INVALID.name(), result.getResultMsg());
//		
//		TransferData data1 = new TransferData(null, TestConstant.ALI_TRANSFER_RECEIVER_ACCOUNT_1, TestConstant.ALI_TRANSFER_RECEIVER_NAME_1, 1, TestConstant.TRANSFER_NOTE);
//		list.add(data1);
//		result = BCPay.startTransfer(PAY_CHANNEL.ALI, batchNo, TestConstant.ALI_TRANSFER_ACCOUNT_NAME, list);
//		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.PARAM_INVALID.name(), result.getResultMsg());
//		list.remove(data1);
//		
//		data1 = new TransferData(transferId1, null, TestConstant.ALI_TRANSFER_RECEIVER_NAME_1, 1, TestConstant.TRANSFER_NOTE);
//		list.add(data1);
//		result = BCPay.startTransfer(PAY_CHANNEL.ALI, batchNo, TestConstant.ALI_TRANSFER_ACCOUNT_NAME, list);
//		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.PARAM_INVALID.name(), result.getResultMsg());
//		list.remove(data1);
//		
//		data1 = new TransferData(transferId1, TestConstant.ALI_TRANSFER_RECEIVER_ACCOUNT_1, null, 1, TestConstant.TRANSFER_NOTE);
//		list.add(data1);
//		result = BCPay.startTransfer(PAY_CHANNEL.ALI, batchNo, TestConstant.ALI_TRANSFER_ACCOUNT_NAME, list);
//		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.PARAM_INVALID.name(), result.getResultMsg());
//		list.remove(data1);
//		
//		data1 = new TransferData(transferId1, TestConstant.ALI_TRANSFER_RECEIVER_ACCOUNT_1, TestConstant.ALI_TRANSFER_RECEIVER_NAME_1, null, TestConstant.TRANSFER_NOTE);
//		list.add(data1);
//		result = BCPay.startTransfer(PAY_CHANNEL.ALI, batchNo, TestConstant.ALI_TRANSFER_ACCOUNT_NAME, list);
//		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.PARAM_INVALID.name(), result.getResultMsg());
//		list.remove(data1);
//		
//		data1 = new TransferData(transferId1, TestConstant.ALI_TRANSFER_RECEIVER_ACCOUNT_1, TestConstant.ALI_TRANSFER_RECEIVER_NAME_1, 1, null);
//		list.add(data1);
//		result = BCPay.startTransfer(PAY_CHANNEL.ALI, batchNo, TestConstant.ALI_TRANSFER_ACCOUNT_NAME, list);
//		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.PARAM_INVALID.name(), result.getResultMsg());
//		list.remove(data1);
//		
//		data1 = new TransferData("", TestConstant.ALI_TRANSFER_RECEIVER_ACCOUNT_1, TestConstant.ALI_TRANSFER_RECEIVER_NAME_1, 1, TestConstant.TRANSFER_NOTE);
//		list.add(data1);
//		result = BCPay.startTransfer(PAY_CHANNEL.ALI, batchNo, TestConstant.ALI_TRANSFER_ACCOUNT_NAME, list);
//		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.PARAM_INVALID.name(), result.getResultMsg());
//		list.remove(data1);
//		
//		data1 = new TransferData(transferId1 + "A", TestConstant.ALI_TRANSFER_RECEIVER_ACCOUNT_1, TestConstant.ALI_TRANSFER_RECEIVER_NAME_1, 1, TestConstant.TRANSFER_NOTE);
//		list.add(data1);
//		result = BCPay.startTransfer(PAY_CHANNEL.ALI, batchNo, TestConstant.ALI_TRANSFER_ACCOUNT_NAME, list);
//		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.PARAM_INVALID.name(), result.getResultMsg());
//		list.remove(data1);
//		
//		data1 = new TransferData(TestConstant.INVALID_TRANSFER_ID, TestConstant.ALI_TRANSFER_RECEIVER_ACCOUNT_1, TestConstant.ALI_TRANSFER_RECEIVER_NAME_1, 1, TestConstant.TRANSFER_NOTE);
//		list.add(data1);
//		result = BCPay.startTransfer(PAY_CHANNEL.ALI, batchNo, TestConstant.ALI_TRANSFER_ACCOUNT_NAME, list);
//		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.PARAM_INVALID.name(), result.getResultMsg());
//		list.remove(data1);
//		
//		data1 = new TransferData(transferId1, TestConstant.ALI_TRANSFER_RECEIVER_ACCOUNT_1, TestConstant.ALI_TRANSFER_RECEIVER_NAME_1, -1, TestConstant.TRANSFER_NOTE);
//		list.add(data1);
//		result = BCPay.startTransfer(PAY_CHANNEL.ALI, batchNo, TestConstant.ALI_TRANSFER_ACCOUNT_NAME, list);
//		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.PARAM_INVALID.name(), result.getResultMsg());
//		list.remove(data1);
//		
//		data1 = new TransferData(transferId1, TestConstant.ALI_TRANSFER_RECEIVER_ACCOUNT_1, TestConstant.ALI_TRANSFER_RECEIVER_NAME_1, 0, TestConstant.TRANSFER_NOTE);
//		list.add(data1);
//		result = BCPay.startTransfer(PAY_CHANNEL.ALI, batchNo, TestConstant.ALI_TRANSFER_ACCOUNT_NAME, list);
//		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.PARAM_INVALID.name(), result.getResultMsg());
//		list.remove(data1);
//		
//		data1 = new TransferData(transferId1, TestConstant.ALI_TRANSFER_RECEIVER_ACCOUNT_1, TestConstant.ALI_TRANSFER_RECEIVER_NAME_1, 1, TestConstant.TRANSFER_NOTE);
//		TransferData data2 = new TransferData(transferId2, TestConstant.ALI_TRANSFER_RECEIVER_ACCOUNT_2, TestConstant.ALI_TRANSFER_RECEIVER_NAME_2, 1, TestConstant.TRANSFER_NOTE);
//		list.add(data1);
//		list.add(data2);
//		result = BCPay.startTransfer(PAY_CHANNEL.ALI, batchNo, TestConstant.ALI_TRANSFER_ACCOUNT_NAME, list);
//		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.OK.name(), result.getResultMsg());
//	}
	
	@Test
	public void testWXNative() {
		billNo = BCUtil.generateRandomUUIDPure();
		refundNo = new SimpleDateFormat("yyyyMMdd").format(new Date()) + BCUtil.generateNumberWith3to24digitals();
		subject = "WX_NATIVE unit test";
		BCOrder param = new BCOrder(PAY_CHANNEL.WX_NATIVE, 1, billNo, subject);
		payOptional.put("wxNativePay", "wxNativePay");
		param.setOptional(payOptional);
		param.setBillTimeout(TestConstant.billTimeOut);
		testPay(param, PAY_CHANNEL.WX_NATIVE);
//		BCrefundeter refund = new BCrefundeter(billNo, refundNo, 1);
//		refundOptional.put("wxNativeRefund", "wxNativeRefund");
//		refund.setChannel(PAY_CHANNEL.WX);
//		testRefund(refund, PAY_CHANNEL.WX);
//		testRefundUpdate(PAY_CHANNEL.WX);
//		testQueryRefundById();
//		testQueryBill(PAY_CHANNEL.WX_NATIVE);
//		testQueryBillCount(PAY_CHANNEL.WX_NATIVE);
//		testQueryRefund(PAY_CHANNEL.WX_NATIVE);
//		testQueryBillCount(PAY_CHANNEL.WX_NATIVE);
	}
	
	@Test
	public void testWXJSAPI() {
		
		billNo = BCUtil.generateRandomUUIDPure();
		refundNo = new SimpleDateFormat("yyyyMMdd").format(new Date()) + BCUtil.generateNumberWith3to24digitals();
		subject = "WX_NATIVE unit test";
		BCOrder param = new BCOrder(PAY_CHANNEL.WX_NATIVE, 1, billNo, subject);
		payOptional.put("wxNativePay", "wxNativePay");
		param.setOptional(payOptional);
		param.setBillTimeout(TestConstant.billTimeOut);
		
		testPay(param, PAY_CHANNEL.WX_NATIVE);
		
		BCRefund refund = new BCRefund(billNo, refundNo, 1);
		refundOptional.put("wxNativeRefund", "wxNativeRefund");
		refund.setChannel(PAY_CHANNEL.WX);
		
//		testRefund(refund, PAY_CHANNEL.WX);
	}
	
	@Test
	public void testUNWeb() {
		
		billNo = BCUtil.generateRandomUUIDPure();
		refundNo = new SimpleDateFormat("yyyyMMdd").format(new Date()) + BCUtil.generateNumberWith3to24digitals();
		subject = "UN_WEB unit test";
		BCOrder param = new BCOrder(PAY_CHANNEL.UN_WEB, 1, billNo, subject);
		payOptional.put("unWebPay", "unWebPay");
		param.setOptional(payOptional);
		param.setReturnUrl(TestConstant.unReturnUrl);
		param.setBillTimeout(TestConstant.billTimeOut);
		
		testPay(param, PAY_CHANNEL.UN_WEB);
		
//		BCrefundeter refund = new BCrefundeter(billNo, refundNo, 1);
//		refundOptional.put("unWebRefund", "unWebRefund");
//		refund.setChannel(PAY_CHANNEL.UN);
//		
//		testRefund(refund, PAY_CHANNEL.UN);
//		testRefundUpdate(PAY_CHANNEL.UN);
//		testQueryRefundById();
//		testQueryBill(PAY_CHANNEL.UN_WEB);
//		testQueryBillCount(PAY_CHANNEL.UN_WEB);
//		testQueryRefund(PAY_CHANNEL.UN_WEB);
//		testQueryBillCount(PAY_CHANNEL.UN_WEB);
	}
	
	@Test
	public void testYeeWeb() {
		
		billNo = BCUtil.generateRandomUUIDPure();
		refundNo = new SimpleDateFormat("yyyyMMdd").format(new Date()) + BCUtil.generateNumberWith3to24digitals();
		subject = "YEE_WEB unit test";
		BCOrder param = new BCOrder(PAY_CHANNEL.YEE_WEB, 1, billNo, subject);
		payOptional.put("yeeWebPay", "yeeWebPay");
		param.setOptional(payOptional);
		param.setBillTimeout(TestConstant.billTimeOut);
		
		testPay(param, PAY_CHANNEL.YEE_WEB);
		
		BCRefund refund = new BCRefund(billNo, refundNo, 1);
		refundOptional.put("yeeWebRefund", "yeeWebRefund");
		refund.setChannel(PAY_CHANNEL.YEE);
		
//		testRefund(refund, PAY_CHANNEL.YEE);
	}
	
	@Test
	public void testYeeWap() {
		
		billNo = BCUtil.generateRandomUUIDPure();
		refundNo = new SimpleDateFormat("yyyyMMdd").format(new Date()) + BCUtil.generateNumberWith3to24digitals();
		subject = "YEE_WAP unit test";
		BCOrder param = new BCOrder(PAY_CHANNEL.YEE_WAP, 1, billNo, subject);
		payOptional.put("yeeWapPay", "yeeWapPay");
		param.setOptional(payOptional);
		param.setBillTimeout(TestConstant.billTimeOut);
		
		testPay(param, PAY_CHANNEL.YEE_WAP);
		
		BCRefund refund = new BCRefund(billNo, refundNo, 1);
		refundOptional.put("yeeWebRefund", "yeeWebRefund");
		refund.setChannel(PAY_CHANNEL.YEE);
		
//		testRefund(refund, PAY_CHANNEL.YEE);
	}
	
	@Test
	public void testYeeNoBankCard() {
		
		billNo = BCUtil.generateRandomUUIDPure();
		refundNo = new SimpleDateFormat("yyyyMMdd").format(new Date()) + BCUtil.generateNumberWith3to24digitals();
		subject = "YEE_NOBANKCARD unit test";
		BCOrder param = new BCOrder(PAY_CHANNEL.YEE_NOBANKCARD, 1, billNo, subject);
		payOptional.put("yeeNobankCardPay", "yeeNobankCardPay");
		param.setOptional(payOptional);
		param.setBillTimeout(TestConstant.billTimeOut);
		
		testPay(param, PAY_CHANNEL.YEE_NOBANKCARD);
	}
	
	@Test
	public void testJDWeb() {
		
		billNo = BCUtil.generateRandomUUIDPure();
		refundNo = new SimpleDateFormat("yyyyMMdd").format(new Date()) + BCUtil.generateNumberWith3to24digitals();
		subject = "JD_WEB unit test";
		BCOrder param = new BCOrder(PAY_CHANNEL.JD_WEB, 1, billNo, subject);
		payOptional.put("jdWebPay", "jdWebPay");
		param.setReturnUrl(TestConstant.jdWebReturnUrl);
		param.setOptional(payOptional);
		
		testPay(param, PAY_CHANNEL.JD_WEB);
		
		BCRefund refund = new BCRefund(billNo, refundNo, 1);
		refundOptional.put("yeeWebRefund", "yeeWebRefund");
		refund.setChannel(PAY_CHANNEL.JD);
		
//		testRefund(refund, PAY_CHANNEL.JD);
	}
	
	@Test
	public void testJDWap() {
		
		billNo = BCUtil.generateRandomUUIDPure();
		refundNo = new SimpleDateFormat("yyyyMMdd").format(new Date()) + BCUtil.generateNumberWith3to24digitals();
		subject = "JD_WAP unit test";
		BCOrder param = new BCOrder(PAY_CHANNEL.JD_WAP, 1, billNo, subject);
		payOptional.put("jdWapPay", "jdWapPay");
		param.setReturnUrl(TestConstant.jdWapReturnUrl);
		param.setOptional(payOptional);
		
		testPay(param, PAY_CHANNEL.JD_WAP);
	}
	
	@Test
	public void testKQWeb() {
		
		billNo = BCUtil.generateRandomUUIDPure();
		refundNo = new SimpleDateFormat("yyyyMMdd").format(new Date()) + BCUtil.generateNumberWith3to24digitals();
		subject = "KUAIQIAN_WEB unit test";
		BCOrder param = new BCOrder(PAY_CHANNEL.KUAIQIAN_WEB, 1, billNo, subject);
		payOptional.put("kqWebPay", "kqWebPay");
		param.setOptional(payOptional);
		
		testPay(param, PAY_CHANNEL.KUAIQIAN_WEB);
		
		BCRefund refund = new BCRefund(billNo, refundNo, 1);
		refundOptional.put("kqWebRefund", "kqWebRefund");
		refund.setChannel(PAY_CHANNEL.KUAIQIAN);
		
//		testRefund(refund, PAY_CHANNEL.KUAIQIAN);
	}
	
	@Test
	public void testKQWap() {
		
		billNo = BCUtil.generateRandomUUIDPure();
		refundNo = new SimpleDateFormat("yyyyMMdd").format(new Date()) + BCUtil.generateNumberWith3to24digitals();
		subject = "KUAIQIAN_WAP unit test";
		BCOrder param = new BCOrder(PAY_CHANNEL.KUAIQIAN_WAP, 1, billNo, subject);
		payOptional.put("kqWapPay", "kqWapPay");
		param.setOptional(payOptional);
		
		testPay(param, PAY_CHANNEL.KUAIQIAN_WAP);
	}
	
	@Test
	public void testBDWeb() {
		
		billNo = BCUtil.generateRandomUUIDPure();
		refundNo = new SimpleDateFormat("yyyyMMdd").format(new Date()) + BCUtil.generateNumberWith3to24digitals();
		subject = "BD_WEB unit test";
		BCOrder param = new BCOrder(PAY_CHANNEL.BD_WEB, 1, billNo, subject);
		payOptional.put("bdWebPay", "bdWebPay");
		param.setOptional(payOptional);
		
		testPay(param, PAY_CHANNEL.BD_WEB);
		
		BCRefund refund = new BCRefund(billNo, refundNo, 1);
		refundOptional.put("bdWebRefund", "bdWebRefund");
		refund.setChannel(PAY_CHANNEL.BD);
		
//		testRefund(refund, PAY_CHANNEL.BD);
	}
	
	@Test
	public void testBDWap() {
		
		billNo = BCUtil.generateRandomUUIDPure();
		refundNo = new SimpleDateFormat("yyyyMMdd").format(new Date()) + BCUtil.generateNumberWith3to24digitals();
		subject = "BD_WAP unit test";
		BCOrder param = new BCOrder(PAY_CHANNEL.BD_WAP, 1, billNo, subject);
		payOptional.put("bdWapPay", "bdWapPay");
		param.setOptional(payOptional);
		
		testPay(param, PAY_CHANNEL.BD_WAP);
	}
	
//	@Test
//	public void testQueryRefund() {
//		BCRefundQueryParameter param = new BCRefundQueryParameter();
//		BCQueryResult bcQueryResult = BCPay.startQueryRefund(param);
//		System.out.println("test1");
//	}
//	
//	@Test
//	public void testQueryRefundStatus() {
//		BCQueryStatusResult result = BCPay.startRefundUpdate(PAY_CHANNEL.WX, "201507149424f344");
//		System.out.println("test1");
//	}
//	
//	@Test
//	public void testTransfer() {
//		List<TransferData> list = new ArrayList<TransferData>();
//		TransferData data1 = new TransferData("transfertest11221", "13584809743", "袁某某", 1, "赏赐");
//		TransferData data2 = new TransferData("transfertest11222", "13584809742", "张某某", 1, "赏赐");
//		list.add(data1);
//		list.add(data2);
//		
//		BCPayResult result = BCPay.startTransfer(PAY_CHANNEL.ALI, "transfertest1122transfertest2233", "13861331391", list);
//		System.out.println("test transfer!");
//	}
//	
//	@Test
//	public void testQueryBillById() {
//		//BCQueryResult result = BCPay.startQueryBillById("21c295fe-0f74-4697-b403-983ec61230ab");
//		System.out.println(result.getOrder());
//		System.out.println("test query by id!" + result);
//	}
	
	private void testPay(BCOrder param, PAY_CHANNEL channel) {
		System.out.println("billNO:" + billNo);
		
		try {
			BCPay.startBCPay(null);
			Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);  
		} catch (Exception e) {
			Assert.assertTrue(e.getMessage(), e instanceof BCException);
			Assert.assertTrue(e.getMessage(), e.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
			Assert.assertTrue(e.getMessage(), e.getMessage().contains(TestConstant.PAY_PARAM_EMPTY));
		}
		
		try {
			param.setChannel(null);
			BCPay.startBCPay(param);
			Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);  
		} catch (Exception e) {
			Assert.assertTrue(e.getMessage(), e instanceof BCException);
			Assert.assertTrue(e.getMessage(), e.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
			Assert.assertTrue(e.getMessage(), e.getMessage().contains(TestConstant.CHANNEL_EMPTY));
		}
		param.setChannel(channel);
		
		try {
			param.setTotalFee(null);
			BCPay.startBCPay(param);
			Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);  
		} catch (Exception e) {
			Assert.assertTrue(e.getMessage(), e instanceof BCException);
			Assert.assertTrue(e.getMessage(), e.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
			Assert.assertTrue(e.getMessage(), e.getMessage().contains(TestConstant.TOTAL_FEE_EMPTY));
		}
		param.setTotalFee(1);
		
		try {
			param.setBillNo(null);
			BCPay.startBCPay(param);
			Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);  
		} catch (Exception e) {
			Assert.assertTrue(e.getMessage(), e instanceof BCException);
			Assert.assertTrue(e.getMessage(), e.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
			Assert.assertTrue(e.getMessage(), e.getMessage().contains(TestConstant.BILL_NO_EMPTY));
		}
		param.setBillNo(billNo);
//		
		try {
			param.setTitle(null);
			BCPay.startBCPay(param);
			Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);  
		} catch (Exception e) {
			Assert.assertTrue(e.getMessage(), e instanceof BCException);
			Assert.assertTrue(e.getMessage(), e.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
			Assert.assertTrue(e.getMessage(), e.getMessage().contains(TestConstant.TITLE_EMPTY));
		}
		param.setTitle(subject);
		
		try {
			param.setTitle(TestConstant.TITLE_WITH_CHARACTER＿GREATER_THAN_32);
			BCPay.startBCPay(param);
			Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);  
		} catch (Exception e) {
			Assert.assertTrue(e.getMessage(), e instanceof BCException);
			Assert.assertTrue(e.getMessage(), e.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
			Assert.assertTrue(e.getMessage(), e.getMessage().contains(TestConstant.TITLE_FORMAT_INVALID));
		}
		param.setTitle(subject);
		
		try {
			param.setBillNo(TestConstant.BILL_NO_WITH_SPECIAL_CHARACTER);
			BCPay.startBCPay(param);
			Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);  
		} catch (Exception e) {
			Assert.assertTrue(e.getMessage(), e instanceof BCException);
			Assert.assertTrue(e.getMessage(), e.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
			Assert.assertTrue(e.getMessage(), e.getMessage().contains(TestConstant.BILL_NO_FORMAT_INVALID));
		}
		param.setBillNo(billNo);
//		
		
		try {
			param.setBillNo(billNo.substring(0, 7));
			BCPay.startBCPay(param);
			Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);  
		} catch (Exception e) {
			Assert.assertTrue(e.getMessage(), e instanceof BCException);
			Assert.assertTrue(e.getMessage(), e.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
			Assert.assertTrue(e.getMessage(), e.getMessage().contains(TestConstant.BILL_NO_FORMAT_INVALID));
		}
		param.setBillNo(billNo);
//		
		try {
			param.setBillNo(billNo + "A");
			BCPay.startBCPay(param);
			Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);  
		} catch (Exception e) {
			Assert.assertTrue(e.getMessage(), e instanceof BCException);
			Assert.assertTrue(e.getMessage(), e.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
			Assert.assertTrue(e.getMessage(), e.getMessage().contains(TestConstant.BILL_NO_FORMAT_INVALID));
		}
		param.setBillNo(billNo);
		
		if(param.getBillTimeout() != null) {
			try{
				param.setBillTimeout(0);
				BCPay.startBCPay(param);
				Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);  
			} catch (Exception e) {
				Assert.assertTrue(e.getMessage(), e instanceof BCException);
				Assert.assertTrue(e.getMessage(), e.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
				Assert.assertTrue(e.getMessage(), e.getMessage().contains(TestConstant.BILL_TIME_OUT_ZERO));
			}
			param.setBillTimeout(TestConstant.billTimeOut);
		}
		
		if (channel.equals(PAY_CHANNEL.ALI_WEB) || channel.equals(PAY_CHANNEL.ALI_QRCODE) || channel.equals(PAY_CHANNEL.UN_WEB)
				|| channel.equals(PAY_CHANNEL.JD_WAP) || channel.equals(PAY_CHANNEL.JD_WEB)) {
			String returnUrl = param.getReturnUrl();
			try {
				param.setReturnUrl(null);
				BCPay.startBCPay(param);
				Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);  
			} catch (Exception e) {
				Assert.assertTrue(e.getMessage(), e instanceof BCException);
				Assert.assertTrue(e.getMessage(), e.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
				Assert.assertTrue(e.getMessage(), e.getMessage().contains(TestConstant.RETURN_URL_EMPTY));
			}
			param.setReturnUrl(returnUrl);
		}
//		
		if (channel.equals(PAY_CHANNEL.WX_JSAPI)) {
			String openId = param.getOpenId();
			try {
				param.setOpenId(null);
				BCPay.startBCPay(param);
				Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);  
			} catch (Exception e) {
				Assert.assertTrue(e.getMessage(), e instanceof BCException);
				Assert.assertTrue(e.getMessage(), e.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
				Assert.assertTrue(e.getMessage(), e.getMessage().contains(TestConstant.OPENID_EMPTY));
			}
			param.setOpenId(openId);
		}
//		
		if (channel.equals(PAY_CHANNEL.ALI_QRCODE)) {
			QR_PAY_MODE qrPayMode = param.getQrPayMode();
			try {
				param.setQrPayMode(null);
				BCPay.startBCPay(param);
				Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);  
			} catch(Exception e) {
				Assert.assertTrue(e.getMessage(), e instanceof BCException);
				Assert.assertTrue(e.getMessage(), e.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
				Assert.assertTrue(e.getMessage(), e.getMessage().contains(TestConstant.QR_PAY_MODE_EMPTY));
			}
			param.setQrPayMode(qrPayMode);
		}
//		
		if (channel.equals(PAY_CHANNEL.YEE_NOBANKCARD)) {
			String cardNo = param.getCardNo();
			try {
			param.setCardNo(null);
			BCPay.startBCPay(param);
			Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);  
			} catch(Exception e) {
				Assert.assertTrue(e.getMessage(), e instanceof BCException);
				Assert.assertTrue(e.getMessage(), e.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
				Assert.assertTrue(e.getMessage(), e.getMessage().contains(TestConstant.YEE_NOBANCARD_FACTOR_EMPTY));
			}
			param.setCardNo(cardNo);
			
			String cardpwd = param.getCardPwd();
			try {
				param.setCardPwd(null);
				BCPay.startBCPay(param);
				Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);  
			} catch(Exception e) {
				Assert.assertTrue(e.getMessage(), e instanceof BCException);
				Assert.assertTrue(e.getMessage(), e.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
				Assert.assertTrue(e.getMessage(), e.getMessage().contains(TestConstant.YEE_NOBANCARD_FACTOR_EMPTY));
			}
			param.setCardPwd(cardpwd);
			
			String frqid = param.getFrqid();
			try {
				param.setFrqid(null);
				BCPay.startBCPay(param);
				Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);  
			} catch(Exception e) {
				Assert.assertTrue(e.getMessage(), e instanceof BCException);
				Assert.assertTrue(e.getMessage(), e.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
				Assert.assertTrue(e.getMessage(), e.getMessage().contains(TestConstant.YEE_NOBANCARD_FACTOR_EMPTY));
			}
			param.setFrqid(frqid);
		}
		
		try {
			param.setTitle(TestConstant.MOCK_TITLE_16_CHINESE_CHARACTER);
			BCPay.startBCPay(param);
		} catch(BCException ex) {
			Assert.assertTrue("", !ex.getMessage().contains(TestConstant.TITLE_FORMAT_INVALID));
		}
		
		if (channel.equals(PAY_CHANNEL.WX_NATIVE)) {
			mockWxNativePay(param, channel);
		}
		if (channel.equals(PAY_CHANNEL.ALI_WEB) || channel.equals(PAY_CHANNEL.ALI_QRCODE) || channel.equals(PAY_CHANNEL.ALI_WAP)) {
			mockUrlAndHtmlPay(param, channel);
		}
		if (channel.equals(PAY_CHANNEL.UN_WEB) || channel.equals(PAY_CHANNEL.JD_WAP)
    			|| channel.equals(PAY_CHANNEL.JD_WEB) || channel.equals(PAY_CHANNEL.KUAIQIAN_WAP) 
    			|| channel.equals(PAY_CHANNEL.KUAIQIAN_WEB)) {
			mockHtmlPay(param, channel);
		}
		if (channel.equals(PAY_CHANNEL.WX_JSAPI)) {
			mockWxJsapi(param, channel);
		}
	}

	
	@SuppressWarnings("deprecation")
	private void testRefund(BCRefund refund) {
		System.out.println("refundNo:" + refundNo);
		try {
			BCPay.startBCRefund(null);
			Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);  
		} catch (Exception e) {
			Assert.assertTrue(e.getMessage(), e instanceof BCException);
			Assert.assertTrue(e.getMessage(), e.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
			Assert.assertTrue(e.getMessage(), e.getMessage().contains(TestConstant.REFUND_PARAM_EMPTY));
		}
		
		try {
			refund.setBillNo(null);
			BCPay.startBCRefund(refund);
			Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);  
		} catch(Exception e) {
			Assert.assertTrue(e.getMessage(), e instanceof BCException);
			Assert.assertTrue(e.getMessage(), e.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
			Assert.assertTrue(e.getMessage(), e.getMessage().contains(TestConstant.BILL_NO_EMPTY));
		}
		refund.setBillNo(billNo);
		
		try {
			refund.setRefundFee(null);
			BCPay.startBCRefund(refund);
			Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);  
		} catch(Exception e) {
			Assert.assertTrue(e.getMessage(), e instanceof BCException);
			Assert.assertTrue(e.getMessage(), e.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
			Assert.assertTrue(e.getMessage(), e.getMessage().contains(TestConstant.REFUND_FEE_EMPTY));
		}
		refund.setRefundFee(1);
		
		try {
			refund.setRefundFee(0);
			BCPay.startBCRefund(refund);
			Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
		} catch(Exception e) {
			Assert.assertTrue(e.getMessage(), e instanceof BCException);
			Assert.assertTrue(e.getMessage(), e.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
			Assert.assertTrue(e.getMessage(), e.getMessage().contains(TestConstant.REFUND_FEE_INVALID));
		}
		refund.setRefundFee(1);
		
		try {
			refund.setRefundNo(null);
			BCPay.startBCRefund(refund);
			Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
		} catch(Exception e) {
			Assert.assertTrue(e.getMessage(), e instanceof BCException);
			Assert.assertTrue(e.getMessage(), e.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
			Assert.assertTrue(e.getMessage(), e.getMessage().contains(TestConstant.REFUND_NO_EMPTY));
		}
		refund.setRefundNo(refundNo);
		
		try {
			refund.setBillNo(billNo.substring(0, 7));
			BCPay.startBCRefund(refund);
			Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
		} catch(Exception e) {
			Assert.assertTrue(e.getMessage(), e instanceof BCException);
			Assert.assertTrue(e.getMessage(), e.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
			Assert.assertTrue(e.getMessage(), e.getMessage().contains(TestConstant.BILL_NO_FORMAT_INVALID));
		}
		refund.setBillNo(billNo);
		
		try {
			refund.setBillNo(billNo + "A");
			BCPay.startBCRefund(refund);
			Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
		} catch(Exception e) {
			Assert.assertTrue(e.getMessage(), e instanceof BCException);
			Assert.assertTrue(e.getMessage(), e.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
			Assert.assertTrue(e.getMessage(), e.getMessage().contains(TestConstant.BILL_NO_FORMAT_INVALID));
		}
		refund.setBillNo(billNo);
		
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_WEEK, -1);
		String date = new SimpleDateFormat("yyyyMMdd").format(cal.getTime());
		try {
			refund.setRefundNo(date + BCUtil.generateNumberWith3to24digitals());
			BCPay.startBCRefund(refund);
			Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
		} catch(Exception e) {
			Assert.assertTrue(e.getMessage(), e instanceof BCException);
			Assert.assertTrue(e.getMessage(), e.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
			Assert.assertTrue(e.getMessage(), e.getMessage().contains(TestConstant.REFUND_NO_FORMAT_INVALID));
		}
		refund.setRefundNo(refundNo);
		
		try {
			date = new SimpleDateFormat("yyyyMMdd").format(new Date());
			refund.setRefundNo(date + "000");
			BCPay.startBCRefund(refund);
			Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
		} catch(Exception e) {
			Assert.assertTrue(e.getMessage(), e instanceof BCException);
			Assert.assertTrue(e.getMessage(), e.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
			Assert.assertTrue(e.getMessage(), e.getMessage().contains(TestConstant.REFUND_NO_FORMAT_INVALID));
		}
		refund.setRefundNo(refundNo);
		
		try {
			refund.setRefundNo(date + TestConstant.REFUND_NO_SERIAL_NUMBER_LESSER_THAN_3);
			BCPay.startBCRefund(refund);
			Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
		} catch(Exception e) {
			Assert.assertTrue(e.getMessage(), e instanceof BCException);
			Assert.assertTrue(e.getMessage(), e.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
			Assert.assertTrue(e.getMessage(), e.getMessage().contains(TestConstant.REFUND_NO_FORMAT_INVALID));
		}
		refund.setRefundNo(refundNo);
		
		try {
			refund.setRefundNo(date + TestConstant.REFUND_NO_SERIAL_NUMBER_GREATER_THAN_24);
			BCPay.startBCRefund(refund);
			Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
		} catch(Exception e) {
			Assert.assertTrue(e.getMessage(), e instanceof BCException);
			Assert.assertTrue(e.getMessage(), e.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
			Assert.assertTrue(e.getMessage(), e.getMessage().contains(TestConstant.REFUND_NO_FORMAT_INVALID));
		}
		refund.setRefundNo(refundNo);
		
		try {
			refund.setRefundNo(date + TestConstant.REFUND_NO_SERIAL_NUMBER_WITH_SPECIAL_CHARACTER);
			BCPay.startBCRefund(refund);
			Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
		} catch(Exception e) {
			Assert.assertTrue(e.getMessage(), e instanceof BCException);
			Assert.assertTrue(e.getMessage(), e.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
			Assert.assertTrue(e.getMessage(), e.getMessage().contains(TestConstant.REFUND_NO_FORMAT_INVALID));
		}
		refund.setRefundNo(refundNo);
		
		
		mockRefund(refund);
	}
	
//	@SuppressWarnings("deprecation")
//	private void testQueryBill(PAY_CHANNEL channel) {
//		BCQueryParameter param = new BCQueryParameter();
//		BCQueryResult result = BCPay.startQueryBill(param);
//		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.OK.name(), result.getResultMsg());
//		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getBcOrders().size()<=10);
//		
//		result = BCPay.startQueryBill(null);
//		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.PARAM_INVALID.name(), result.getResultMsg());
//		
//		param.setChannel(channel);
//		result = BCPay.startQueryBill(param);
//		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.OK.name(), result.getResultMsg());
//		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getBcOrders().size()<=10);
//		param.setChannel(null);
//		
//		param.setBillNo(billNo);
//		result = BCPay.startQueryBill(param);
//		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.OK.name(), result.getResultMsg());
//		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getBcOrders().size()<=10);
//		
//		param.setBillNo(TestConstant.NOT_EXIST_BILL_NO);
//		result = BCPay.startQueryBill(param);
//		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.OK.name(), result.getResultMsg());
//		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getBcOrders().size()==0);
//		
//		param.setBillNo(TestConstant.BILL_NO_WITH_SPECIAL_CHARACTER);
//		result = BCPay.startQueryBill(param);
//		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.PARAM_INVALID.name(), result.getResultMsg());
//		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getBcOrders() == null);
//		
//		param.setBillNo(billNo.substring(0, 7));
//		result = BCPay.startQueryBill(param);
//		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.PARAM_INVALID.name(), result.getResultMsg());
//		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getBcOrders() == null);
//		
//		param.setBillNo(billNo + "A");
//		result = BCPay.startQueryBill(param);
//		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.PARAM_INVALID.name(), result.getResultMsg());
//		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getBcOrders() == null);
//		param.setBillNo(null);
//		
//		Calendar cal = Calendar.getInstance();
//		cal.add(Calendar.MONTH, +1);
//		param.setStartTime(cal.getTime());
//		result = BCPay.startQueryBill(param);
//		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.OK.name(), result.getResultMsg());
//		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getBcOrders().size()<=10);
//		
//		cal.add(Calendar.MONTH, -1);
//		param.setEndTime(cal.getTime());
//		result = BCPay.startQueryBill(param);
//		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getResultMsg().contains(RESULT_TYPE.PARAM_INVALID.name()));
//		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getBcOrders() == null);
//		
//		param.setStartTime(null);
//		param.setEndTime(null);
//		
//		param.setSkip(2);
//		result = BCPay.startQueryBill(param);
//		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.OK.name(), result.getResultMsg());
//		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getBcOrders().size()<=10);
//		
//		param.setSkip(-1);
//		result = BCPay.startQueryBill(param);
//		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getResultMsg().contains(RESULT_TYPE.PARAM_INVALID.name()));
//		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getBcOrders() == null);
//		
//		param.setSkip(0);
//		result = BCPay.startQueryBill(param);
//		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getResultMsg().contains(RESULT_TYPE.OK.name()));
//		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getBcOrders().size() <=10 );
//		param.setSkip(null);
//		
//		param.setLimit(8);
//		result = BCPay.startQueryBill(param);
//		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getResultMsg().contains(RESULT_TYPE.PARAM_INVALID.name()));
//		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getBcOrders() == null );
//	
//		param.setLimit(52);
//		result = BCPay.startQueryBill(param);
//		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getResultMsg().contains(RESULT_TYPE.PARAM_INVALID.name()));
//		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getBcOrders() == null );
//		param.setLimit(null);
//	}
	
//	@SuppressWarnings("deprecation")
//	private void testQueryBillCount(PAY_CHANNEL channel) {
//		BCQueryParameter param = new BCQueryParameter();
//		
//		BCQueryResult result = BCPay.startQueryBillCount(null);
//		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.PARAM_INVALID.name(), result.getResultMsg());
//		
//		result = BCPay.startQueryBillCount(param);
//		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.OK.name(), result.getResultMsg());
//		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getTotalCount() >= 0);
//		
//		param.setBillNo(TestConstant.NOT_EXIST_BILL_NO);
//		result = BCPay.startQueryBillCount(param);
//		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.OK.name(), result.getResultMsg());
//		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getTotalCount() == 0);
//		
//		param.setChannel(channel);
//		result = BCPay.startQueryBillCount(param);
//		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.OK.name(), result.getResultMsg());
//		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getTotalCount() >= 0);
//		param.setChannel(null);
//		
//		param.setBillNo(billNo);
//		result = BCPay.startQueryBillCount(param);
//		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.OK.name(), result.getResultMsg());
//		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getTotalCount() >= 0);
//		
//		param.setBillNo(TestConstant.BILL_NO_WITH_SPECIAL_CHARACTER);
//		result = BCPay.startQueryBillCount(param);
//		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.PARAM_INVALID.name(), result.getResultMsg());
//		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getTotalCount() == null);
//		
//		param.setBillNo(billNo.substring(0, 7));
//		result = BCPay.startQueryBillCount(param);
//		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.PARAM_INVALID.name(), result.getResultMsg());
//		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getTotalCount() == null);
//		
//		param.setBillNo(billNo + "A");
//		result = BCPay.startQueryBillCount(param);
//		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.PARAM_INVALID.name(), result.getResultMsg());
//		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getTotalCount() == null);
//		param.setBillNo(null);
//		
//		Calendar cal = Calendar.getInstance();
//		cal.add(Calendar.MONTH, +1);
//		param.setStartTime(cal.getTime());
//		result = BCPay.startQueryBillCount(param);
//		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.OK.name(), result.getResultMsg());
//		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getTotalCount() == 0);
//		
//		cal.add(Calendar.MONTH, -1);
//		param.setEndTime(cal.getTime());
//		result = BCPay.startQueryBillCount(param);
//		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getResultMsg().contains(RESULT_TYPE.PARAM_INVALID.name()));
//		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getTotalCount() == null);
//		
//		param.setStartTime(null);
//		param.setEndTime(null);
//	}
	
//	@SuppressWarnings("deprecation")
//	private void testQueryRefund(PAY_CHANNEL channel) {
//		BCRefundQueryParameter param = new BCRefundQueryParameter();
//		BCQueryResult result = BCPay.startQueryRefund(param);
//		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.OK.name(), result.getResultMsg());
//		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getBcRefundList().size()<=10);
//		
//		result = BCPay.startQueryRefund(null);
//		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.PARAM_INVALID.name(), result.getResultMsg());
//		
//		param.setChannel(channel);
//		result = BCPay.startQueryRefund(param);
//		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.OK.name(), result.getResultMsg());
//		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getBcRefundList().size()<=10);
//		param.setChannel(null);
//		
//		param.setBillNo(billNo);
//		result = BCPay.startQueryRefund(param);
//		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.OK.name(), result.getResultMsg());
//		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getBcRefundList().size()<=10);
//		
//		param.setBillNo(TestConstant.NOT_EXIST_BILL_NO);
//		result = BCPay.startQueryRefund(param);
//		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.OK.name(), result.getResultMsg());
//		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getBcRefundList().size()==0);
//		
//		param.setBillNo(TestConstant.BILL_NO_WITH_SPECIAL_CHARACTER);
//		result = BCPay.startQueryRefund(param);
//		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.PARAM_INVALID.name(), result.getResultMsg());
//		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getBcRefundList() == null);
//		
//		param.setBillNo(billNo.substring(0, 7));
//		result = BCPay.startQueryRefund(param);
//		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.PARAM_INVALID.name(), result.getResultMsg());
//		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getBcRefundList() == null);
//		
//		param.setBillNo(billNo + "A");
//		result = BCPay.startQueryRefund(param);
//		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.PARAM_INVALID.name(), result.getResultMsg());
//		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getBcRefundList() == null);
//		param.setBillNo(null);
//		
//		Calendar cal = Calendar.getInstance();
//		cal.add(Calendar.MONTH, +1);
//		param.setStartTime(cal.getTime());
//		result = BCPay.startQueryRefund(param);
//		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.OK.name(), result.getResultMsg());
//		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getBcRefundList().size()<=10);
//		
//		cal.add(Calendar.MONTH, -1);
//		param.setEndTime(cal.getTime());
//		result = BCPay.startQueryRefund(param);
//		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getResultMsg().contains(RESULT_TYPE.PARAM_INVALID.name()));
//		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getBcRefundList() == null);
//		
//		param.setStartTime(null);
//		param.setEndTime(null);
//		
//		param.setSkip(2);
//		result = BCPay.startQueryRefund(param);
//		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.OK.name(), result.getResultMsg());
//		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getBcRefundList().size()<=10);
//		
//		param.setSkip(-1);
//		result = BCPay.startQueryRefund(param);
//		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getResultMsg().contains(RESULT_TYPE.PARAM_INVALID.name()));
//		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getBcRefundList() == null);
//		
//		param.setSkip(0);
//		result = BCPay.startQueryRefund(param);
//		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getResultMsg().contains(RESULT_TYPE.OK.name()));
//		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getBcRefundList().size() <=10 );
//		param.setSkip(null);
//		
//		param.setLimit(8);
//		result = BCPay.startQueryRefund(param);
//		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getResultMsg().contains(RESULT_TYPE.PARAM_INVALID.name()));
//		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getBcRefundList() == null );
//	
//		param.setLimit(52);
//		result = BCPay.startQueryRefund(param);
//		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getResultMsg().contains(RESULT_TYPE.PARAM_INVALID.name()));
//		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getBcRefundList() == null );
//		param.setLimit(null);
//		
//		param.setRefundNo(refundNo);
//		result = BCPay.startQueryRefund(param);
//		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getResultMsg().contains(RESULT_TYPE.OK.name()));
//		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getBcRefundList().size() <=10 );
//		
//		param.setRefundNo(refundNo.substring(0, 8) + "000");
//		result = BCPay.startQueryRefund(param);
//		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getResultMsg().contains(RESULT_TYPE.PARAM_INVALID.name()));
//		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getBcRefundList() == null );
//	
//		param.setRefundNo(refundNo.substring(0, 8) + TestConstant.REFUND_NO_SERIAL_NUMBER_LESSER_THAN_3);
//		result = BCPay.startQueryRefund(param);
//		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getResultMsg().contains(RESULT_TYPE.PARAM_INVALID.name()));
//		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getBcRefundList() == null );
//		
//		param.setRefundNo(refundNo.substring(0, 8) + TestConstant.REFUND_NO_SERIAL_NUMBER_GREATER_THAN_24);
//		result = BCPay.startQueryRefund(param);
//		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getResultMsg().contains(RESULT_TYPE.PARAM_INVALID.name()));
//		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getBcRefundList() == null );
//		
//		param.setRefundNo(refundNo.substring(0, 8) + TestConstant.REFUND_NO_SERIAL_NUMBER_WITH_SPECIAL_CHARACTER);
//		result = BCPay.startQueryRefund(param);
//		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getResultMsg().contains(RESULT_TYPE.PARAM_INVALID.name()));
//		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getBcRefundList() == null );
//	}
	
//	@SuppressWarnings("deprecation")
//	private void testQueryRefundCount(PAY_CHANNEL channel) {
//		BCRefundQueryParameter param = new BCRefundQueryParameter();
//		BCQueryResult result = BCPay.startQueryRefundCount(param);
//		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.OK.name(), result.getResultMsg());
//		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getTotalCount()>=0);
//		
//		result = BCPay.startQueryRefundCount(null);
//		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.PARAM_INVALID.name(), result.getResultMsg());
//		
//		param.setChannel(channel);
//		result = BCPay.startQueryRefundCount(param);
//		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.OK.name(), result.getResultMsg());
//		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getTotalCount()>=0);
//		param.setChannel(null);
//		
//		param.setBillNo(billNo);
//		result = BCPay.startQueryRefundCount(param);
//		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.OK.name(), result.getResultMsg());
//		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getTotalCount()>=0);
//		
//		param.setBillNo(TestConstant.NOT_EXIST_BILL_NO);
//		result = BCPay.startQueryRefundCount(param);
//		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.OK.name(), result.getResultMsg());
//		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getTotalCount()>=0);
//		
//		param.setBillNo(TestConstant.BILL_NO_WITH_SPECIAL_CHARACTER);
//		result = BCPay.startQueryRefundCount(param);
//		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.PARAM_INVALID.name(), result.getResultMsg());
//		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getTotalCount() == null);
//		
//		param.setBillNo(billNo.substring(0, 7));
//		result = BCPay.startQueryRefundCount(param);
//		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.PARAM_INVALID.name(), result.getResultMsg());
//		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getTotalCount() == null);
//		
//		param.setBillNo(billNo + "A");
//		result = BCPay.startQueryRefundCount(param);
//		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.PARAM_INVALID.name(), result.getResultMsg());
//		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getTotalCount() == null);
//		param.setBillNo(null);
//		
//		Calendar cal = Calendar.getInstance();
//		cal.add(Calendar.MONTH, +1);
//		param.setStartTime(cal.getTime());
//		result = BCPay.startQueryRefundCount(param);
//		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.OK.name(), result.getResultMsg());
//		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getTotalCount()>=0);
//		
//		cal.add(Calendar.MONTH, -1);
//		param.setEndTime(cal.getTime());
//		result = BCPay.startQueryRefundCount(param);
//		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getResultMsg().contains(RESULT_TYPE.PARAM_INVALID.name()));
//		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getTotalCount() == null);
//		
//		param.setStartTime(null);
//		param.setEndTime(null);
//		
//		param.setRefundNo(refundNo);
//		result = BCPay.startQueryRefundCount(param);
//		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getResultMsg().contains(RESULT_TYPE.OK.name()));
//		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getTotalCount()>=0 );
//		
//		param.setRefundNo(refundNo.substring(0, 8) + "000");
//		result = BCPay.startQueryRefundCount(param);
//		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getResultMsg().contains(RESULT_TYPE.PARAM_INVALID.name()));
//		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getTotalCount() == null );
//	
//		param.setRefundNo(refundNo.substring(0, 8) + TestConstant.REFUND_NO_SERIAL_NUMBER_LESSER_THAN_3);
//		result = BCPay.startQueryRefundCount(param);
//		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getResultMsg().contains(RESULT_TYPE.PARAM_INVALID.name()));
//		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getTotalCount() == null );
//		
//		param.setRefundNo(refundNo.substring(0, 8) + TestConstant.REFUND_NO_SERIAL_NUMBER_GREATER_THAN_24);
//		result = BCPay.startQueryRefundCount(param);
//		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getResultMsg().contains(RESULT_TYPE.PARAM_INVALID.name()));
//		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getTotalCount() == null );
//		
//		param.setRefundNo(refundNo.substring(0, 8) + TestConstant.REFUND_NO_SERIAL_NUMBER_WITH_SPECIAL_CHARACTER);
//		result = BCPay.startQueryRefundCount(param);
//		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getResultMsg().contains(RESULT_TYPE.PARAM_INVALID.name()));
//		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getTotalCount() == null );
//	}
	
	private void mockRefund(BCRefund refund) {
		final Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put("id", TestConstant.MOCK_OBJECT_ID);
		returnMap.put("result_code", 0);
		returnMap.put("result_msg", "OK");
		returnMap.put("err_detail", "");
		if (refund.getChannel().equals(PAY_CHANNEL.ALI))
			returnMap.put("url", TestConstant.MOCK_ALI_REFUND_URL);
		
		new Expectations(){
			   {
			    Deencapsulation.invoke(BCPay.class, "doPost", withSubstring(BCUtilPrivate.getkApiRefund().substring(14)), withAny(Map.class));
			    returns(returnMap);
			    result = new BCException(RESULT_TYPE.CHANNEL_ERROR.ordinal(), RESULT_TYPE.CHANNEL_ERROR.name(), RESULT_TYPE.CHANNEL_ERROR.name());
			    result = new BCException(RESULT_TYPE.NO_SUCH_BILL.ordinal(), RESULT_TYPE.NO_SUCH_BILL.name(), RESULT_TYPE.NO_SUCH_BILL.name());
			   }
			};
		
		try {
			BCPay.startBCRefund(refund);
			Assert.assertEquals("",TestConstant.MOCK_OBJECT_ID, refund.getObjectId());
			if(refund.getChannel().equals(PAY_CHANNEL.ALI)) {
				Assert.assertEquals("",TestConstant.MOCK_ALI_REFUND_URL, refund.getAliRefundUrl());
			}
		} catch (BCException ex) {
			Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_THROWN);
		}
		
		try {
			BCPay.startBCRefund(refund);
			Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN); 
		} catch (Exception ex) {
			Assert.assertTrue(ex.getMessage(), ex instanceof BCException);
			Assert.assertTrue(ex.getMessage(), ex.getMessage().contains(RESULT_TYPE.CHANNEL_ERROR.name()));
		}
		
		try {
			BCPay.startBCRefund(refund);
			Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN); 
		} catch (Exception ex) {
			Assert.assertTrue(ex.getMessage(), ex instanceof BCException);
			Assert.assertTrue(ex.getMessage(), ex.getMessage().contains(RESULT_TYPE.NO_SUCH_BILL.name()));
		}
	}

	public void testQueryBillById(BCOrder param) {
		
		try {
			BCPay.startQueryBillById(null);
			Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN); 
		} catch(Exception ex) {
			System.out.println(ex.getMessage());
			Assert.assertTrue(ex.getMessage(), ex instanceof BCException);
			Assert.assertTrue(ex.getMessage(), ex.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
			Assert.assertTrue(ex.getMessage(), ex.getMessage().contains(TestConstant.OBJECT_ID_EMPTY));
		}
		
		try {
			BCPay.startQueryBillById(TestConstant.INVALID_OBJECT_ID);
			Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN); 
		} catch(Exception ex) {
			System.out.println(ex.getMessage());
			Assert.assertTrue(ex.getMessage(), ex instanceof BCException);
			Assert.assertTrue(ex.getMessage(), ex.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
			Assert.assertTrue(ex.getMessage(), ex.getMessage().contains(TestConstant.OBJECT_ID_INVALID));
		}
		
		final Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put("result_code", 0);
		returnMap.put("result_msg", "OK");
		returnMap.put("err_detail", "");
		Map<String, Object> payMap = new HashMap<String, Object>();
		payMap.put("id", TestConstant.MOCK_OBJECT_ID);
		payMap.put("spay_result", TestConstant.MOCK_PAY_RESULT);
		payMap.put("create_time", TestConstant.MOCK_CREATE_TIME);
		payMap.put("total_fee", TestConstant.MOCK_TOTAL_FEE);
		payMap.put("channel", TestConstant.MOCK_CHANNEL);
		payMap.put("trade_no", "");
		payMap.put("bill_no", TestConstant.MOCK_BILL_NO);
		payMap.put("optional", "");
		payMap.put("revert_result", false);
		payMap.put("title", TestConstant.MOCK_TITLE);
		payMap.put("sub_channel", TestConstant.MOCK_SUB_CHANNEL);
		payMap.put("message_detail", "");
		payMap.put("refund_result", false);
		returnMap.put("pay", payMap);
		
		new Expectations(){
		   {
		    Deencapsulation.invoke(BCPay.class, "doGet", withSubstring(BCUtilPrivate.getkApiQueryBillById().substring(14)), withAny(Map.class));
		    returns(returnMap);
		    result = new BCException(RESULT_TYPE.APP_INVALID.ordinal(), RESULT_TYPE.APP_INVALID.name(), RESULT_TYPE.APP_INVALID.name());
		   }
		};
		BCOrder order;
		try {
			order = BCPay.startQueryBillById(param.getObjectId());
			Assert.assertEquals("",TestConstant.MOCK_BILL_NO, order.getBillNo());
			Assert.assertEquals("","", order.getChannelTradeNo());
			Assert.assertEquals("",TestConstant.MOCK_OBJECT_ID, order.getObjectId());
			Assert.assertEquals("",TestConstant.MOCK_PAY_RESULT, order.isResulted());
			Assert.assertEquals("",TestUtil.transferDateFromLongToString(TestConstant.MOCK_CREATE_TIME), order.getDateTime());
			Assert.assertEquals("",TestConstant.MOCK_TOTAL_FEE, order.getTotalFee());
			Assert.assertEquals("",TestConstant.MOCK_CHANNEL, order.getChannel().toString().split("_")[0]);
			Assert.assertEquals("",TestConstant.MOCK_SUB_CHANNEL, order.getChannel().toString());
			Assert.assertEquals("","", order.getOptionalString());
			Assert.assertEquals("", false, order.isRevertResult());
			Assert.assertEquals("", false, order.isRefundResult());
			Assert.assertEquals("", "", order.getMessageDetail());
			Assert.assertEquals("", TestConstant.MOCK_TITLE, order.getTitle());
		} catch (BCException e) {
			Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_THROWN);
		}
		
		try {
			order = BCPay.startQueryBillById(param.getObjectId());
			Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN); 
		} catch (Exception ex) {
			Assert.assertTrue(ex.getMessage(), ex instanceof BCException);
			Assert.assertTrue(ex.getMessage(), ex.getMessage().contains(RESULT_TYPE.APP_INVALID.name()));
		}
	}
	
//	@SuppressWarnings("deprecation")
//	@Test
//	public void testQueryRefundById() {
//		BCQueryResult result = BCPay.startQueryRefundById(TestConstant.VALID_REFUND_OBJECT_ID);
//		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.OK.name(), result.getResultMsg());
//		
//		result = BCPay.startQueryRefundById(TestConstant.INVALID_OBJECT_ID);
//		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getResultMsg().contains(RESULT_TYPE.PARAM_INVALID.name()));
//	}
//	
	private void testRefundUpdate(PAY_CHANNEL channel) {
		String message;
		try {
			message = BCPay.startRefundUpdate(null, refundNo);
			Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
		} catch(Exception e) {
			Assert.assertTrue(e.getMessage(), e instanceof BCException);  
			Assert.assertTrue(e.getMessage(), e.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
			Assert.assertTrue(e.getMessage(), e.getMessage().contains(TestConstant.CHANNEL_EMPTY));
		}
		
		try {
			message = BCPay.startRefundUpdate(channel, null);
			Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
		} catch(Exception e) {
			Assert.assertTrue(e.getMessage(), e instanceof BCException);  
			Assert.assertTrue(e.getMessage(), e.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
			Assert.assertTrue(e.getMessage(), e.getMessage().contains(TestConstant.REFUND_NO_EMPTY));
		}
		
	
		result = BCPay.startRefundUpdate(channel, refundNo.substring(0, 8) + "000");
		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getResultMsg().contains(RESULT_TYPE.PARAM_INVALID.name()));
		System.out.println(result.getErrDetail());
		result = BCPay.startRefundUpdate(channel, refundNo.substring(0, 8) + TestConstant.REFUND_NO_SERIAL_NUMBER_LESSER_THAN_3);
		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getResultMsg().contains(RESULT_TYPE.PARAM_INVALID.name()));
		System.out.println(result.getErrDetail());
		result = BCPay.startRefundUpdate(channel, refundNo.substring(0, 8) + TestConstant.REFUND_NO_SERIAL_NUMBER_GREATER_THAN_24);
		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getResultMsg().contains(RESULT_TYPE.PARAM_INVALID.name()));
		System.out.println(result.getErrDetail());
		
		result = BCPay.startRefundUpdate(channel, refundNo);
		if (channel.equals(PAY_CHANNEL.WX) || channel.equals(PAY_CHANNEL.KUAIQIAN) || channel.equals(PAY_CHANNEL.YEE) || channel.equals(PAY_CHANNEL.BD)) {
			System.out.println(result.getErrDetail());
			Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getResultMsg().contains(RESULT_TYPE.NO_SUCH_REFUND.name()));
			
			result = BCPay.startRefundUpdate(channel, refundNo.substring(0, 8) + TestConstant.REFUND_NO_SERIAL_NUMBER_WITH_SPECIAL_CHARACTER);
			System.out.println(result.getErrDetail());
			Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getResultMsg().contains(RESULT_TYPE.NO_SUCH_REFUND.name()));
		} else {
			Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getErrDetail().contains("渠道选择错误"));
			
			result = BCPay.startRefundUpdate(channel, refundNo.substring(0, 8) + TestConstant.REFUND_NO_SERIAL_NUMBER_WITH_SPECIAL_CHARACTER);
			Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getErrDetail().contains("渠道选择错误"));
		}
	}
	
	private void mockWxJsapi(BCOrder param, PAY_CHANNEL channel) {
		final Map<String, Object> returnMap = new HashMap<String, Object>();
		
		returnMap.put("id", TestConstant.MOCK_OBJECT_ID);
		returnMap.put("result_code", 0);
		returnMap.put("result_msg", "OK");
		returnMap.put("err_detail", "");
		returnMap.put("app_id", TestConstant.MOCK_WXJSAPI_APPID);
		returnMap.put("timestamp", TestConstant.MOCK_WXJSAP_TIMESTAMP);
		returnMap.put("package", TestConstant.MOCK_WXJSAPI_PACKAGE);
		returnMap.put("nonce_str", TestConstant.MOCK_WXJSAPI_NONCESTR);
		returnMap.put("pay_sign", TestConstant.MOCK_WXJSAPI_PAYSIGN);
		returnMap.put("sign_type", TestConstant.MOCK_WXJSAP_SIGNTYPE);
		
		new Expectations(BCPay.class){
			   {
			    Deencapsulation.invoke(BCPay.class, "doPost", withSubstring(BCUtilPrivate.getkApiPay().substring(14)), withAny(Map.class));
			    returns(returnMap);
			    result = new BCException(RESULT_TYPE.CHANNEL_INVALID.ordinal(), RESULT_TYPE.CHANNEL_INVALID.name(), RESULT_TYPE.CHANNEL_INVALID.name());
			   }
			};
			BCOrder order;
			try {
				order = BCPay.startBCPay(param);
				Assert.assertEquals("",TestConstant.MOCK_WXJSAPI_APPID, order.getWxJSAPIMap().get("appdId"));
				Assert.assertEquals("",TestConstant.MOCK_WXJSAPI_NONCESTR, order.getWxJSAPIMap().get("nonceStr"));
				Assert.assertEquals("",TestConstant.MOCK_WXJSAP_TIMESTAMP, order.getWxJSAPIMap().get("timeStamp"));
				Assert.assertEquals("",TestConstant.MOCK_WXJSAPI_PAYSIGN, order.getWxJSAPIMap().get("paySign"));
				Assert.assertEquals("",TestConstant.MOCK_WXJSAP_SIGNTYPE, order.getWxJSAPIMap().get("signType"));
				Assert.assertEquals("",TestConstant.MOCK_WXJSAPI_PACKAGE, order.getWxJSAPIMap().get("package"));
				Assert.assertEquals("",TestConstant.MOCK_OBJECT_ID, order.getObjectId());
			} catch (BCException e) {
				Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_THROWN);
			}
			
			try {
				order = BCPay.startBCPay(param);
				Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);  
			} catch (Exception ex) {
				Assert.assertTrue(ex.getMessage(), ex instanceof BCException);
				Assert.assertTrue(ex.getMessage(), ex.getMessage().contains(RESULT_TYPE.CHANNEL_INVALID.name()));
			}
	}

	private void mockWxNativePay(BCOrder param, PAY_CHANNEL channel) {
		final Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put("id", TestConstant.MOCK_OBJECT_ID);
		returnMap.put("code_url", TestConstant.MOCK_CODE_URL);
		returnMap.put("result_code", 0);
		returnMap.put("result_msg", "OK");
		returnMap.put("err_detail", "");
		
		new Expectations(BCPay.class){
		   {
		    Deencapsulation.invoke(BCPay.class, "doPost", withSubstring(BCUtilPrivate.getkApiPay().substring(14)), withAny(Map.class));
		    returns(returnMap);
		    result = new BCException(RESULT_TYPE.PAY_FACTOR_NOT_SET.ordinal(), RESULT_TYPE.PAY_FACTOR_NOT_SET.name(), RESULT_TYPE.PAY_FACTOR_NOT_SET.name());
		   }
		};
		BCOrder order;
		try {
			order = BCPay.startBCPay(param);
			Assert.assertEquals("",TestConstant.MOCK_CODE_URL, order.getCodeUrl());
			Assert.assertEquals("",TestConstant.MOCK_OBJECT_ID, order.getObjectId());
		} catch (BCException e) {
			e.printStackTrace();
		}
		
		try {
			order = BCPay.startBCPay(param);
			Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);  
		} catch (Exception ex) {
			Assert.assertTrue(ex.getMessage(), ex instanceof BCException);
			Assert.assertTrue(ex.getMessage(), ex.getMessage().contains(RESULT_TYPE.PAY_FACTOR_NOT_SET.name()));
		}
	}
	
	private void mockUrlAndHtmlPay(BCOrder param, PAY_CHANNEL channel) {
		final Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put("id", TestConstant.MOCK_OBJECT_ID);
		returnMap.put("url", TestConstant.MOCK_PAY_URL);
		returnMap.put("html", TestConstant.MOCK_PAY_HTML);
		
		new Expectations(BCPay.class){
		   {
		    Deencapsulation.invoke(BCPay.class, "doPost", withSubstring(BCUtilPrivate.getkApiPay().substring(14)), withAny(Map.class));
		    returns(returnMap);
		    result = new BCException(RESULT_TYPE.RUNTIME_ERORR.ordinal(), RESULT_TYPE.RUNTIME_ERORR.name(), RESULT_TYPE.RUNTIME_ERORR.name());
		   }
		};
		
		BCOrder order;
		try {
			order = BCPay.startBCPay(param);
			Assert.assertEquals("",TestConstant.MOCK_PAY_URL, order.getUrl());
			Assert.assertEquals("",TestConstant.MOCK_PAY_HTML, order.getHtml());
		} catch (BCException e) {
			e.printStackTrace();
		}
		
		try {
			order = BCPay.startBCPay(param);
			Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);  
		} catch (Exception ex) {
			Assert.assertTrue(ex.getMessage(), ex instanceof BCException);
			Assert.assertTrue(ex.getMessage(), ex.getMessage().contains(RESULT_TYPE.RUNTIME_ERORR.name()));
		}
	}
	
	private void mockHtmlPay(BCOrder param, PAY_CHANNEL channel) {
		final Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put("id", TestConstant.MOCK_OBJECT_ID);
		returnMap.put("html", TestConstant.MOCK_PAY_HTML);
		
		new Expectations(BCPay.class){
		   {
		    Deencapsulation.invoke(BCPay.class, "doPost", withSubstring(BCUtilPrivate.getkApiPay().substring(14)), withAny(Map.class));
		    returns(returnMap);
		    result = new BCException(RESULT_TYPE.APP_INVALID.ordinal(), RESULT_TYPE.APP_INVALID.name(), RESULT_TYPE.APP_INVALID.name());
		   }
		};
		BCOrder order;
		try {
			order = BCPay.startBCPay(param);
			Assert.assertEquals("",TestConstant.MOCK_PAY_HTML, order.getHtml());
			Assert.assertEquals("",TestConstant.MOCK_OBJECT_ID, order.getObjectId());
		} catch (BCException e) {
			e.printStackTrace();
		}
		
		try {
			order = BCPay.startBCPay(param);
			Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);  
		} catch (Exception ex) {
			Assert.assertTrue(ex.getMessage(), ex instanceof BCException);
			Assert.assertTrue(ex.getMessage(), ex.getMessage().contains(RESULT_TYPE.APP_INVALID.name()));
		}
	}
	
	
	class wxNativeMatcher extends TypeSafeMatcher<Map<String, Object>> {

		@Override
		public void describeTo(Description arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		protected boolean matchesSafely(Map<String, Object> map) {
			if (map.get("channel").toString().equals(PAY_CHANNEL.WX_NATIVE.toString())) {
				return true;
			}
			return false;
		} 
	}
	
}
