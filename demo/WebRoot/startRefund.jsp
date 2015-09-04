<%@page import="cn.beecloud.BCEumeration.PAY_CHANNEL"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="cn.beecloud.*"%>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; UTF-8">
<title>Start Refund</title>
<script type="text/javascript">
</script>
</head>
<body>
<%
	String billNo = request.getParameter("bill_no");
	Object channelObject = request.getParameter("channel");
	Map optional = new HashMap();
	optional.put("test", "test");
	Integer refundFee = Integer.parseInt(request.getParameter("total_fee"));
	PAY_CHANNEL channel = null;
	if (channelObject != null && !channelObject.equals("")) {
		if (channelObject.toString().contains("WX")) {
			channel = PAY_CHANNEL.WX;
		} else if (channelObject.toString().contains("ALI")) {
			channel = PAY_CHANNEL.ALI;
		} else if (channelObject.toString().contains("UN")) {
			channel = PAY_CHANNEL.UN;
		} else if (channelObject.toString().contains("YEE")) {
			channel = PAY_CHANNEL.YEE;
		} else if (channelObject.toString().contains("JD")) {
			channel = PAY_CHANNEL.JD;
		} else if (channelObject.toString().contains("KUAIQIAN")) {
			channel = PAY_CHANNEL.KUAIQIAN;
		}
	}
	System.out.println("channel:" +channel);
	String refundNo = new SimpleDateFormat("yyyyMMdd").format(new Date()) + BCUtil.generateNumberWith3to24digitals();
	BCPayResult result = BCPay.startBCRefund(channel, refundNo, billNo, refundFee, optional);
	if (result.getType().ordinal() == 0 ) {
		out.println(result.getObjectId());
		Thread.sleep(5000);
		if (result.getUrl() != null) {
			response.sendRedirect(result.getUrl());
		} else {
			out.println(result.getSucessMsg());
		}
	} else {
		out.println(result.getErrMsg());
		out.println(result.getErrDetail());
	}
%>
</body>