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
		<link href="jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css" type="text/css" rel="stylesheet" />

		<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
		<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
		<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.js"></script>
		<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.zh-CN.js"></script>
		<link rel="stylesheet" type="text/css" href="jquery/bs_pagination/jquery.bs_pagination.min.css">
		<script type="text/javascript" src="jquery/bs_pagination/jquery.bs_pagination.min.js"></script>
		<script type="text/javascript" src="jquery/bs_pagination/en.js"></script>

		<script type="text/javascript">

			$(function(){
				$("#createBtn").click(function(){
					$(".time").datetimepicker({
						minView: "month",
						language:  'zh-CN',
						format: 'yyyy-mm-dd',
						autoclose: true,
						todayBtn: true,
						pickerPosition: "bottom-left"
					});
					$.ajax({
						url : "ssm/activity/getUserList",
						type : "get",
						dataType : "json",
						success : function(data){

							var html = "";

							$.each(data, function(i,n){
								html += "<option value='"+ n.id +"'>"+ n.name +"</option>";
							})
							$("#create-owner").html(html);

							$("#createActivityModal").modal("show");
						}
					})

				});
				$("#saveBtn").click(function(){
					$.ajax({
						url : "ssm/activity/insert",
						data : {
							"owner": $.trim($("#create-owner").val()),
							"name": $.trim($("#create-name").val()),
							"startDate": $.trim($("#create-startDate").val()),
							"endDate": $.trim($("#create-endDate").val()),
							"cost": $.trim($("#create-cost").val()),
							"description": $.trim($("#create-description").val())
						},
						type : "post",
						dataType : "json",
						success : function(data){
							if (data.success){
								alert("添加成功");
								$("#createActivityModal").modal("hide");
								$("#addActivityForm")[0].reset();
								pageList(1,$("#activityPage").bs_pagination('getOption', 'rowsPerPage'));
							}else {
								alert("添加失败");
							}

						}
					})
				});

				pageList(1,2);

				$("#searchBtn").click(function(){
					$("#hidden-name").val($.trim($("#search-name").val()));
					$("#hidden-owner").val($.trim($("#search-owner").val()));
					$("#hidden-startDate").val($.trim($("#search-startTime").val()));
					$("#hidden-endDate").val($.trim($("#search-endTime").val()));

					pageList(1,2);
				});

				$("#allCheck").click(function(){
					$("input[name=branchCheck]").prop("checked", this.checked);
				})

				$("#activityBody").on("click", $("input[name=branchCheck]"),function(){
					$("#allCheck").prop("checked", $("input[name=branchCheck]").length==$("input[name=branchCheck]:checked").length);
				})

				$("#delBtn").click(function(){
					var $xz = $("input[name=branchCheck]:checked");
					if ($xz.length == 0){
						alert("请选择删除的选项");
					} else {
						if (confirm("确定删除")){
							var param = "";
							for (var i = 0; i < $xz.length; i++) {
								param += "id=" + $($xz[i]).val();
								if (i < $xz.length-1){
									param += "&";
								}
							}
							$.ajax({
								url : "ssm/activity/delete",
								data : param,
								type : "post",
								dataType : "json",
								success : function(data){
									if (data.success){
										pageList($("#activityPage").bs_pagination('getOption', 'currentPage')
												,$("#activityPage").bs_pagination('getOption', 'rowsPerPage'));
									} else{
										alert("数据删除失败")
									}
								}
							})
						}
					}
				})

				$("#editBtn").click(function(){
					var $xz = $("input[name=branchCheck]:checked");
					if ($xz.length == 0){
						alert("请选择选项")
					} else if($xz.length > 1){
						alert("只能选择一个");
					} else {
						var id = $xz.val();
						$.ajax({
							url : "ssm/activity/getUserListAndUpdate",
							data : {"id" : id},
							type : "post",
							dataType : "json",
							success : function(data){
								var html = "";
								$.each(data.userList,function(i, n){
									html += "<option value='"+ n.id +"'>" + n.name + "</option>";
								})
								$("#edit-owner").html(html);

								$("#edit-id").val(data.act.id);
								$("#edit-name").val(data.act.name);
								$("#edit-startDate").val(data.act.startDate);
								$("#edit-endDate").val(data.act.endDate);
								$("#edit-cost").val(data.act.cost);
								$("#edit-description").val(data.act.description);
								$("#editActivityModal").modal("show");
							}
						})
					}
				})

				$("#updateBtn").click(function(){
					$.ajax({
						url : "ssm/activity/update",
						data : {
							"id": $.trim($("#edit-id").val()),
							"owner": $.trim($("#edit-owner").val()),
							"name": $.trim($("#edit-name").val()),
							"startDate": $.trim($("#edit-startDate").val()),
							"endDate": $.trim($("#edit-endDate").val()),
							"cost": $.trim($("#edit-cost").val()),
							"description": $.trim($("#edit-description").val())
						},
						type : "post",
						dataType : "json",
						success : function(data){
							if (data.success){
								alert("修改成功");
								$("#editActivityModal").modal("hide");
								// $("#addActivityForm")[0].reset();
								pageList($("#activityPage").bs_pagination('getOption', 'currentPage')
										,$("#activityPage").bs_pagination('getOption', 'rowsPerPage'));
							}else {
								alert("修改失败");
							}

						}
					})
				});


			});

			pageList = function(pageNo, pageSize){
				$("#allCheck").prop("checked", false);

				//查询前将隐藏域中存放的信息拿出来
				$("#search-name").val($.trim($("#hidden-name").val()));
				$("#search-owner").val($.trim($("#hidden-owner").val()));
				$("#search-startDate").val($.trim($("#hidden-startTime").val()));
				$("#search-endDate").val($.trim($("#hidden-endTime").val()));

				$.ajax({
					url : "ssm/activity/pageList",
					data : {
						"pageNo": pageNo,
						"pageSize": pageSize,
						"name": $.trim($("#search-name").val()),
						"owner": $.trim($("#search-owner").val()),
						"startDate": $.trim($("#search-startTime").val()),
						"endDate": $.trim($("#search-endTime").val())
					},
					type : "get",
					dataType : "json",
					success : function(data){

						var html = "";

						$.each(data.dataList, function(i,n){
							html += '<tr class="active">'
							html += '<td><input type="checkbox" name="branchCheck" value="'+n.id+'"/></td>'
							html += '<td><a style="text-decoration: none; cursor: pointer;" href="ssm/activity/detail?id='+ n.id +'">'+n.name +'</a></td>'
							html += '<td>'+n.owner+'</td>'
							html += '<td>'+n.startDate+'</td>'
							html += '<td>'+n.endDate+'</td>'
							html += '</tr>'
						})
						$("#activityBody").html(html);

						var totalPages = data.total%pageSize==0 ? data.total/pageSize : parseInt(data.total/pageSize) + 1;

						//处理分页数据
						$("#activityPage").bs_pagination({
							currentPage: pageNo, // 页码
							rowsPerPage: pageSize, // 每页显示的记录条数
							maxRowsPerPage: 20, // 每页最多显示的记录条数
							totalPages: totalPages, // 总页数
							totalRows: data.total, // 总记录条数

							visiblePageLinks: 3, // 显示几个卡片

							showGoToPage: true,
							showRowsPerPage: true,
							showRowsInfo: true,
							showRowsDefaultInfo: true,

							onChangePage : function(event, data){
								pageList(data.currentPage , data.rowsPerPage);
							}
						});
					}
				})
			}

		</script>
	</head>
	<body>
		<%--
			隐藏域，暂时存放表单中的信息
		--%>
		<input type="hidden" id="hidden-name">
		<input type="hidden" id="hidden-owner">
		<input type="hidden" id="hidden-startDate">
		<input type="hidden" id="hidden-endDate">

		<input type="hidden" id="edit-id">

		<!-- 创建市场活动的模态窗口 -->
		<div class="modal fade" id="createActivityModal" role="dialog">
			<div class="modal-dialog" role="document" style="width: 85%;">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">
							<span aria-hidden="true">×</span>
						</button>
						<h4 class="modal-title" id="myModalLabel1">创建市场活动</h4>
					</div>
					<div class="modal-body">

						<form id="addActivityForm" class="form-horizontal" role="form">

							<div class="form-group">
								<label for="create-marketActivityOwner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
								<div class="col-sm-10" style="width: 300px;">
									<select class="form-control" id="create-owner">
									</select>
								</div>
								<label for="create-marketActivityName" class="col-sm-2 control-label">名称<span style="font-size: 15px; color: red;">*</span></label>
								<div class="col-sm-10" style="width: 300px;">
									<input type="text" class="form-control" id="create-name">
								</div>
							</div>

							<div class="form-group">
								<label for="create-startTime" class="col-sm-2 control-label">开始日期</label>
								<div class="col-sm-10" style="width: 300px;">
									<input type="text" class="form-control time" id="create-startDate">
								</div>
								<label for="create-endTime" class="col-sm-2 control-label">结束日期</label>
								<div class="col-sm-10" style="width: 300px;">
									<input type="text" class="form-control time" id="create-endDate">
								</div>
							</div>
							<div class="form-group">

								<label for="create-cost" class="col-sm-2 control-label">成本</label>
								<div class="col-sm-10" style="width: 300px;">
									<input type="text" class="form-control" id="create-cost">
								</div>
							</div>
							<div class="form-group">
								<label for="create-describe" class="col-sm-2 control-label">描述</label>
								<div class="col-sm-10" style="width: 81%;">
									<textarea class="form-control" rows="3" id="create-description"></textarea>
								</div>
							</div>

						</form>

					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
						<button type="button" class="btn btn-primary" id="saveBtn">保存</button>
					</div>
				</div>
			</div>
		</div>

		<!-- 修改市场活动的模态窗口 -->
		<div class="modal fade" id="editActivityModal" role="dialog">
			<div class="modal-dialog" role="document" style="width: 85%;">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">
							<span aria-hidden="true">×</span>
						</button>
						<h4 class="modal-title" id="myModalLabel2">修改市场活动</h4>
					</div>
					<div class="modal-body">

						<form class="form-horizontal" role="form">

							<div class="form-group">
								<label for="edit-marketActivityOwner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
								<div class="col-sm-10" style="width: 300px;">
									<select class="form-control" id="edit-owner">

									</select>
								</div>
								<label for="edit-marketActivityName" class="col-sm-2 control-label">名称<span style="font-size: 15px; color: red;">*</span></label>
								<div class="col-sm-10" style="width: 300px;">
									<input type="text" class="form-control" id="edit-name">
								</div>
							</div>

							<div class="form-group">
								<label for="edit-startTime" class="col-sm-2 control-label">开始日期</label>
								<div class="col-sm-10" style="width: 300px;">
									<input type="text" class="form-control time" id="edit-startDate" >
								</div>
								<label for="edit-endTime" class="col-sm-2 control-label">结束日期</label>
								<div class="col-sm-10" style="width: 300px;">
									<input type="text" class="form-control time" id="edit-endDate" >
								</div>
							</div>

							<div class="form-group">
								<label for="edit-cost" class="col-sm-2 control-label">成本</label>
								<div class="col-sm-10" style="width: 300px;">
									<input type="text" class="form-control" id="edit-cost">
								</div>
							</div>

							<div class="form-group">
								<label for="edit-describe" class="col-sm-2 control-label">描述</label>
								<div class="col-sm-10" style="width: 81%;">
									<textarea class="form-control" rows="3" id="edit-description"></textarea>
								</div>
							</div>

						</form>

					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
						<button type="button" class="btn btn-primary" id="updateBtn" data-dismiss="modal">更新</button>
					</div>
				</div>
			</div>
		</div>




		<div>
			<div style="position: relative; left: 10px; top: -10px;">
				<div class="page-header">
					<h3>市场活动列表</h3>
				</div>
			</div>
		</div>
		<div style="position: relative; top: -20px; left: 0px; width: 100%; height: 100%;">
			<div style="width: 100%; position: absolute;top: 5px; left: 10px;">

				<div class="btn-toolbar" role="toolbar" style="height: 80px;">
					<form class="form-inline" role="form" style="position: relative;top: 8%; left: 5px;">

					  <div class="form-group">
						<div class="input-group">
						  <div class="input-group-addon">名称</div>
						  <input class="form-control" type="text" id="search-name">
						</div>
					  </div>

					  <div class="form-group">
						<div class="input-group">
						  <div class="input-group-addon">所有者</div>
						  <input class="form-control" type="text" id="search-owner">
						</div>
					  </div>


					  <div class="form-group">
						<div class="input-group">
						  <div class="input-group-addon">开始日期</div>
						  <input class="form-control" type="text" id="search-startTime" />
						</div>
					  </div>
					  <div class="form-group">
						<div class="input-group">
						  <div class="input-group-addon">结束日期</div>
						  <input class="form-control" type="text" id="search-endTime">
						</div>
					  </div>

					  <button type="button" id="searchBtn" class="btn btn-default">查询</button>

					</form>
				</div>
				<div class="btn-toolbar" role="toolbar" style="background-color: #F7F7F7; height: 50px; position: relative;top: 5px;">
					<div class="btn-group" style="position: relative; top: 18%;">
					  <button type="button" class="btn btn-primary" id="createBtn"><span class="glyphicon glyphicon-plus"></span> 创建</button>
					  <button type="button" class="btn btn-default" id="editBtn" ><span class="glyphicon glyphicon-pencil"></span> 修改</button>
					  <button type="button" class="btn btn-danger" id="delBtn"><span class="glyphicon glyphicon-minus"></span> 删除</button>
					</div>

				</div>
				<div style="position: relative;top: 10px;">
					<table class="table table-hover">
						<thead>
							<tr style="color: #B3B3B3;">
								<td><input type="checkbox" id="allCheck" /></td>
								<td>名称</td>
								<td>所有者</td>
								<td>开始日期</td>
								<td>结束日期</td>
							</tr>
						</thead>
						<tbody id="activityBody">
<%--							<tr class="active">--%>
<%--								<td><input type="checkbox" /></td>--%>
<%--								<td><a style="text-decoration: none; cursor: pointer;" href="ssm/activity/detail">发传单</a></td>--%>
<%--								<td>zhangsan</td>--%>
<%--								<td>2020-10-10</td>--%>
<%--								<td>2020-10-20</td>--%>
<%--							</tr>--%>
<%--							<tr class="active">--%>
<%--								<td><input type="checkbox" /></td>--%>
<%--								<td><a style="text-decoration: none; cursor: pointer;" href="ssm/activity/detail">发传单</a></td>--%>
<%--								<td>zhangsan</td>--%>
<%--								<td>2020-10-10</td>--%>
<%--								<td>2020-10-20</td>--%>
<%--							</tr>--%>
						</tbody>
					</table>
				</div>

				<div style="height: 50px; position: relative;top: 30px;">
					<div id="activityPage">

					</div>
<%--					<div>--%>
<%--						<button type="button" class="btn btn-default" style="cursor: default;">共<b>50</b>条记录</button>--%>
<%--					</div>--%>
<%--					<div class="btn-group" style="position: relative;top: -34px; left: 110px;">--%>
<%--						<button type="button" class="btn btn-default" style="cursor: default;">显示</button>--%>
<%--						<div class="btn-group">--%>
<%--							<button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">--%>
<%--								10--%>
<%--								<span class="caret"></span>--%>
<%--							</button>--%>
<%--							<ul class="dropdown-menu" role="menu">--%>
<%--								<li><a href="#">20</a></li>--%>
<%--								<li><a href="#">30</a></li>--%>
<%--							</ul>--%>
<%--						</div>--%>
<%--						<button type="button" class="btn btn-default" style="cursor: default;">条/页</button>--%>
<%--					</div>--%>
<%--					<div style="position: relative;top: -88px; left: 285px;">--%>
<%--						<nav>--%>
<%--							<ul class="pagination">--%>
<%--								<li class="disabled"><a href="#">首页</a></li>--%>
<%--								<li class="disabled"><a href="#">上一页</a></li>--%>
<%--								<li class="active"><a href="#">1</a></li>--%>
<%--								<li><a href="#">2</a></li>--%>
<%--								<li><a href="#">3</a></li>--%>
<%--								<li><a href="#">4</a></li>--%>
<%--								<li><a href="#">5</a></li>--%>
<%--								<li><a href="#">下一页</a></li>--%>
<%--								<li class="disabled"><a href="#">末页</a></li>--%>
<%--							</ul>--%>
<%--						</nav>--%>
<%--					</div>--%>
				</div>

			</div>

		</div>
	</body>
</html>