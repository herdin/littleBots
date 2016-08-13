
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<!-- The HTML 4.01 Transitional DOCTYPE declaration-->
<!-- above set at the top of the file will set     -->
<!-- the browser's rendering engine into           -->
<!-- "Quirks Mode". Replacing this declaration     -->
<!-- with a "Standards Mode" doctype is supported, -->
<!-- but may lead to some differences in layout.   -->
<%@ page contentType="text/html; charset=UTF-8"%>
<% //request.setCharacterEncoding("UTF-8");%>
<html>
  <head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <style type="text/css">
    .content{
    	font : 8pt/11pt verdana;
    	width: 450px;
    	height: 210px;
    	border: 5px solid rgb(230,230,230);
    	padding: 10px;
    }

    </style>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>
        <script>
    $(document).ready(function(){
    	console.log("hello, LGCNSnameCard.jsp");
    });
    </script>
    <title></title>
  </head>

<body>
	<div class="content">
		<img src="./images/beyondpromise.png" alt="version : 1" > <br><br>
		<b>배 현 규 ( Hyunku Bae )</b><br><br>
		${position}
		<!--
		금융/공공사업본부 MDD개발팀<br>
		서울시 영등포구 여의도동 28-1 전경련회관 33층<br>
		-->
		<font style="color: #D80546; font-weight: bold;">Mail</font> : herdin@lgcns.com |
		<font style="color: #D80546; font-weight: bold;">Mobile</font> : 010-4707-0956
	</div>
</body>
</html>
