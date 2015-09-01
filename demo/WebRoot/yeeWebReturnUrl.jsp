<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; UTF-8">
<title>Yee Web Return Url</title>
</head>
<body>
<%! String formatString(String text) {
		return (text == null) ? "" : text.trim();
	}
%>
<%

	String p1_MerId				= formatString(request.getParameter("p1_MerId"));
	String r0_Cmd               = formatString(request.getParameter("r0_Cmd"));
	String r1_Code              = formatString(request.getParameter("r1_Code"));
	String r2_TrxId             = formatString(request.getParameter("r2_TrxId"));
	String r3_Amt               = formatString(request.getParameter("r3_Amt"));
	String r4_Cur               = formatString(request.getParameter("r4_Cur"));
	String r5_Pid               = formatString(request.getParameter("r5_Pid"));
	String r6_Order             = formatString(request.getParameter("r6_Order"));
	String r7_Uid               = formatString(request.getParameter("r7_Uid"));
	String r8_MP                = formatString(request.getParameter("r8_MP"));
	String r9_BType             = formatString(request.getParameter("r9_BType"));
	String rb_BankId            = formatString(request.getParameter("rb_BankId"));
	String ro_BankOrderId       = formatString(request.getParameter("ro_BankOrderId"));
	String rp_PayDate           = formatString(request.getParameter("rp_PayDate"));
	String rq_CardNo            = formatString(request.getParameter("rq_CardNo"));
	String ru_Trxtime           = formatString(request.getParameter("ru_Trxtime"));
	String rq_SourceFee         = formatString(request.getParameter("rq_SourceFee"));
	String rq_TargetFee         = formatString(request.getParameter("rq_TargetFee"));
	String hmac		            = formatString(request.getParameter("hmac"));

	if(r9_BType.equals("1")) {
		out.println("易宝支付成功，商户应自行实现成功逻辑！");
		out.println("order no:" + r6_Order);
		//handle othre return parameter as you wish
		return;
	}
%>
</body>
</html>