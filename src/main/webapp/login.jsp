<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
String basePath = request.getScheme() + "://" + request.getServerName()
+ ":" + request.getServerPort() + "/" + request.getContextPath() + "/";
%>
<!DOCTYPE html>
<html>
	<head>
		<base href="<%=basePath%>"/>
		<meta charset="UTF-8">
		<link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
		<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
		<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
		<script>
			$(function(){
				if (window.top != window){
					window.top.location = window.location;
				}

				flush();

				$("#loginAct").click(function(){
					$("#msg").html("");
				});

				$("#loginPwd").keydown(function (event) {
					if (event.keyCode == 13){
						loginTo();
					}
				})
			})

			flush = function(){
				var $loginAct = $("#loginAct");
				var $loginPwd = $("#loginPwd");
				$loginAct.val("");
				$loginPwd.val("");
				$loginAct.focus();
			}

			function loginTo(){
				var loginAct = $.trim($("#loginAct").val());
				var loginPwd = $.trim($("#loginPwd").val());
				if (loginAct == "" || loginPwd == ""){
					$("#msg").html("请输入账号和密码");
					return false;
				}
				$("#submitBtn").click();
			}

		</script>
	</head>
	<body>
		<div style="position: absolute; top: 0px; left: 0px; width: 60%;">
			<img src="image/IMG_7114.JPG" style="width: 100%; height: 90%; position: relative; top: 50px;">
		</div>
		<div id="top" style="height: 50px; background-color: #3C3C3C; width: 100%;">
			<div style="position: absolute; top: 5px; left: 0px; font-size: 30px; font-weight: 400; color: white; font-family: 'times new roman'">CRM &nbsp;<span style="font-size: 12px;">&copy;2021&nbsp;EFeng</span></div>
		</div>

		<div style="position: absolute; top: 120px; right: 100px;width:450px;height:400px;border:1px solid #D5D5D5">
			<div style="position: absolute; top: 0px; right: 60px;">
				<div class="page-header">
					<h1>登录</h1>
				</div>
				<form action="ssm/user/login" class="form-horizontal" role="form" method="post">
					<div class="form-group form-group-lg">
						<div style="width: 350px;">
							<input class="form-control" type="text" id="loginAct" placeholder="用户名" name="username">
						</div>
						<div style="width: 350px; position: relative;top: 20px;">
							<input class="form-control" type="password" id="loginPwd" placeholder="密码" name="password">
						</div>
						<div class="checkbox"  style="position: relative;top: 30px; left: 10px;">

								<span id="msg" style="color: red">${msg}</span>

						</div>
						<button type="submit" id="submitBtn" class="btn btn-primary btn-lg btn-block"  style="width: 350px; position: relative;top: 45px;">登录</button>
					</div>
				</form>
			</div>
		</div>
	</body>
</html>