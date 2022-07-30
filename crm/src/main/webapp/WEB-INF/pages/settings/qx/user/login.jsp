<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>
<base href="<%=basePath%>">
<html>
<head>
<meta charset="UTF-8">
<link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
<script type="text/javascript">
	$(function () {
		// 用户应该可以通过按下回车直接进行登录
		$(window).keydown(function (e) {
			// 如果按下的是回车，则提交登录请求
			if (e.keyCode == 13) {
				$("#loginBtn").click();
			}
		})

		// 给登录按钮添加单击事件
		$("#loginBtn").click(function () {
			// 收集参数
			var loginAct = $.trim($("#loginAct").val());
			var loginPwd = $.trim($("#loginPwd").val());
			var isRemPwd = $("#isRemPwd").prop("checked");
			
			// 表单验证
			if (loginAct == "") {
				alert("用户名不能为空！");
				return;
			}
			if (loginPwd == "") {
				alert("密码不能为空！");
				return;
			}
			
			// 到这里说明用户名和密码都输入了，进行登录操作
			$.ajax({
				url: 'settings/qx/user/login.do',
				data: {
					loginAct: loginAct,
					loginPwd: loginPwd,
					isRemPwd: isRemPwd
				},
				dataType: 'json',
				type: 'post',
				success: function (data) {
					if (data.code = "1") {
						// 登录成功，跳转到业务主页面
						window.location.href="workbench/index.do";
					} else {
						// 提示信息
						$("#msg").text(data.message);
					}
				}
			})
		})
	})

</script>
</head>
<body>
	<div style="position: absolute; top: 0px; left: 0px; width: 60%;">
		<img src="image/IMG_7114.JPG" style="width: 100%; height: 90%; position: relative; top: 50px;">
	</div>
	<div id="top" style="height: 50px; background-color: #3C3C3C; width: 100%;">
		<div style="position: absolute; top: 5px; left: 0px; font-size: 30px; font-weight: 400; color: white; font-family: 'times new roman'">CRM &nbsp;<span style="font-size: 12px;">&copy;2019&nbsp;动力节点</span></div>
	</div>
	
	<div style="position: absolute; top: 120px; right: 100px;width:450px;height:400px;border:1px solid #D5D5D5">
		<div style="position: absolute; top: 0px; right: 60px;">
			<div class="page-header">
				<h1>登录</h1>
			</div>
			<form action="workbench/index.html" class="form-horizontal" role="form">
				<div class="form-group form-group-lg">
					<div style="width: 350px;">
						<input id="loginAct" class="form-control" type="text" placeholder="用户名">
					</div>
					<div style="width: 350px; position: relative;top: 20px;">
						<input id="loginPwd" class="form-control" type="password" placeholder="密码">
					</div>
					<div class="checkbox"  style="position: relative;top: 30px; left: 10px;">
						<label>
							<input id="isRemPwd" type="checkbox"> 十天内免登录
						</label>
						&nbsp;&nbsp;
						<span id="msg"></span>
					</div>
					<button id="loginBtn" type="button" class="btn btn-primary btn-lg btn-block"  style="width: 350px; position: relative;top: 45px;">登录</button>
				</div>
			</form>
		</div>
	</div>
</body>
</html>