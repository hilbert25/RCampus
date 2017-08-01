<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<base href="<%=basePath%>"></base>
<title>课程管理</title>
<meta name="description" content="这是一个 index 页面">
<meta name="keywords" content="index">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="renderer" content="webkit">
<meta http-equiv="Cache-Control" content="no-siteapp" />
<meta name="apple-mobile-web-app-title" content="Amaze UI" />
<link rel="stylesheet" href="page/assets/css/amazeui.min.css" />
<link rel="stylesheet" href="page/assets/css/admin.css">
<link rel="stylesheet" href="page/assets/css/app.css">
<link rel="stylesheet" href="page/assets/css/bootstrap.min.css">
<script src="page/assets/js/jquery.min.js"></script>
<script src="page/assets/js/amazeui.min.js"></script>
<script src="page/assets/js/app.js"></script>
<script src="page/assets/js/bootstrap.min.js"></script>
<script src="page/assets/js/string-deal.js"></script>
<style>
</style>
<script type="text/javascript">
	$(document).ready(function() {
		$("#generate").click(function() {
			$.get("course/generate", function(data, status) {
				alert(data);
			});
		});
		$("#judgeSubmit").click(function() {
			console.log($("courseOrder").val());
			console.log($("judgeItem").val());
			$.post("judge/add.do", {
				examId : $("#courseOrder").val(),
				judgeItem : $("#judgeItem").val(),
				judgeType : $("#judgeType").val(),
				judgeTips : $("#judgeTips").val()
			}, function(data, status) {
				alert("状态: " + status);
			});
		});
	});
	function getUnDealCount() {
		var xmlhttp;
		if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
			xmlhttp = new XMLHttpRequest();
		} else {// code for IE6, IE5
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
		xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
				var count = xmlhttp.responseText;
				if (count > 0) {
					var div = document.getElementById("undeal");
					var span = document.createElement("span");
					span.innerHTML = count;
					span.setAttribute("class",
							"am-badge tpl-badge-danger am-round");
					div.appendChild(span);

				}
			}
		};
		xmlhttp.open("POST", "/rcampus/message/getUnDealCount", true);
		xmlhttp.setRequestHeader("Content-type",
				"application/x-www-form-urlencoded");
		xmlhttp.send();
	}
	function addChapter() {
		var xmlhttp;
		if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
			xmlhttp = new XMLHttpRequest();
		} else {// code for IE6, IE5
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
		xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
				var data = JSON.parse(xmlhttp.responseText);
				if (data['result'])
					alert(data['result']);
				else {
					window.location.reload();
				}
			}
		};
		xmlhttp.open("POST", "/rcampus/chapter/add.do", true);
		xmlhttp.setRequestHeader("Content-type",
				"application/x-www-form-urlencoded");
		var chapterOrder = stringPredetail(document
				.getElementById("chapterOrder").value);
		var chapterName = stringPredetail(document
				.getElementById("chapterName").value);
		var chapterDescribe = stringPredetail(document
				.getElementById("chapterDescribe").value);
		xmlhttp.send("chapterOrder=" + chapterOrder + "&chapterName="
				+ chapterName + "&chapterDescribe=" + chapterDescribe);
	}
	//设置修改模态框的数据
	function getChapterById(chapterId) {
		var xmlhttp;
		if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
			xmlhttp = new XMLHttpRequest();
		} else {// code for IE6, IE5
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
		xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
				var data = JSON.parse(xmlhttp.responseText);
				$('#courseModal').modal('show');
				document.getElementById("chapterOrder").value = data['chapterOrder'];
				document.getElementById("chapterName").innerHTML = data['chapterName'];
				document.getElementById("chapterDescribe").innerHTML = data['chapterDescrbe'];
				document.getElementById("submit").setAttribute("onclick",
						"modifyChapter(" + chapterId + ");");
			}
		};
		xmlhttp.open("POST", "/rcampus/chapter/getChapterbyId", true);
		xmlhttp.setRequestHeader("Content-type",
				"application/x-www-form-urlencoded");
		xmlhttp.send("chapterId=" + chapterId);
	}
	//修改章节信息
	function modifyChapter(courseId) {
		document.getElementById("submit").setAttribute("onclick",
				"addChapter();");
		var xmlhttp;
		if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
			xmlhttp = new XMLHttpRequest();
		} else {// code for IE6, IE5
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
		xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
				var data = JSON.parse(xmlhttp.responseText);
				if (data['result'])
					alert(data['result']);
				else {
					window.location.reload();
				}
			}
		};
		xmlhttp.open("POST", "/rcampus/chapter/modifyChapter", true);
		xmlhttp.setRequestHeader("Content-type",
				"application/x-www-form-urlencoded");
		var chapterOrder = stringPredetail(document
				.getElementById("chapterOrder").value);
		var chapterName = stringPredetail(document
				.getElementById("chapterName").value);
		var chapterDescribe = stringPredetail(document
				.getElementById("chapterDescribe").value);
		xmlhttp.send("chapterId=" + chapterId + "&chapterOrder=" + chapterOrder
				+ "&chapterName=" + chapterName + "&chapterDescribe="
				+ chapterDescribe+"&courseId="+courseId);
	}
	//删除章节
	function deleteChapter(chapterId) {
		var xmlhttp;
		if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
			xmlhttp = new XMLHttpRequest();
		} else {// code for IE6, IE5
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
		xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
				window.location.reload();
			}
		};
		xmlhttp.open("POST", "/rcampus/chapter/delete", true);
		xmlhttp.setRequestHeader("Content-type",
				"application/x-www-form-urlencoded");
		xmlhttp.send("chapterId=" + chapterId);
	}
	function init() {
		getUnDealCount();
	}
</script>

</head>

<body data-type="generalComponents" onload="init();">
	<header class="am-topbar am-topbar-inverse admin-header">
		<div class="am-topbar-brand"></div>
		<div class="am-collapse am-topbar-collapse" id="topbar-collapse">
			<ul
				class="am-nav am-nav-pills am-topbar-nav am-topbar-right admin-header-list tpl-header-list">
				<li class="am-dropdown" data-am-dropdown data-am-dropdown-toggle>
					<a class="am-dropdown-toggle tpl-header-list-link"
					href="javascript:;" id="undeal"> <span
						class="am-icon-comment-o"></span> 消息
				</a>
					<ul class="am-dropdown-content tpl-dropdown-content"
						id="message_list">
						<li class="tpl-dropdown-content-external"><a href="###">全部</a>
						</li>

					</ul>
				</li>
				<li class="am-dropdown" data-am-dropdown data-am-dropdown-toggle>
					<a class="am-dropdown-toggle tpl-header-list-link"
					href="javascript:;"> <span class="tpl-header-list-user-nick">${user.userName }</span><span
						class="tpl-header-list-user-ico"> <img src="${user.photo }"></span>
				</a>
					<ul class="am-dropdown-content">
						<li><a href="#"><span class="am-icon-bell-o"></span> 资料</a></li>
						<li><a href="#"><span class="am-icon-cog"></span> 设置</a></li>
						<li><a href="#"><span class="am-icon-power-off"></span>
								退出</a></li>
					</ul>
				</li>
			</ul>
		</div>
	</header>
	<div class="tpl-page-container tpl-page-header-fixed">

		<div class="tpl-left-nav tpl-left-nav-hover">
			<div class="tpl-left-nav-list">
				<ul class="tpl-left-nav-menu">
					<li class="tpl-left-nav-item"><a href="rcampus/adminhome"
						class="nav-link active"> <i class="am-icon-home"></i> <span>用户管理</span>

					</a></li>
					<li class="tpl-left-nav-item"><a href="chapter/chapter_manage"
						class="nav-link active"> <i class="am-icon-home"></i> <span>课程管理</span><i
							class="am-icon-star tpl-left-nav-content-ico am-fr am-margin-right"></i>
					</a></li>

					<li class="tpl-left-nav-item"><a href="login.html"
						class="nav-link tpl-left-nav-link-list"> <i
							class="am-icon-key"></i> <span>其余</span>

					</a></li>
				</ul>
			</div>
		</div>
	</div>
	<div class="tpl-content-wrapper">
		<div class="tpl-content-page-title">课程列表</div>
		<div class="tpl-portlet-components">
			<div class="portlet-title">
				<a style="text-align: center;"
					href="/rcampus/chapter/getChapterList">R Campus</a>
				<div class="tpl-portlet-input tpl-fz-ml">
					<div class="portlet-input input-small input-inline">
						<div class="input-icon right">
							<i class="am-icon-search"></i> <input type="text"
								class="form-control form-control-solid" placeholder="搜索...">
						</div>
					</div>
				</div>
			</div>
			<div class="tpl-block">
				<div class="am-g">
					<div class="am-u-sm-12 am-u-md-6">
						<div class="am-btn-toolbar">
							<div class="am-btn-group am-btn-group-xs">
								<button type="button"
									class="am-btn am-btn-default am-btn-success"
									data-toggle="modal" data-target="#courseModal">
									<span class="am-icon-plus"></span> 新增
								</button>
								<!-- 	<button type="button"
									class="am-btn am-btn-default am-btn-success"
									style="margin-left: 10px" data-toggle="modal"
									data-target="#judgeModal">
									<span class="am-icon-plus"></span> 新增judge
								</button> -->
								<button id="generate" type="button"
									class="am-btn am-btn-default am-btn-success"
									style="margin-left: 10px">生成课程页</button>
								<p id="show"></p>
								<!-- course模态框（Modal） -->
								<div class="modal fade" id="courseModal" tabindex="-1"
									role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
									<div class="modal-dialog">
										<div class="modal-content">
											<div class="modal-header">
												<button type="button" class="close" data-dismiss="modal"></button>
												<h4 class="modal-title" id="myModalLabel"></h4>
											</div>

											<div class="modal-body">添加章节</div>
											<div class="modal-footer">
												<form class="am-form tpl-form-line-form"
													action="chapter/add" method="post" id="chapterForm"
													name="chapterForm">
													<div class="am-form-group">
														<label for="user-name" class="am-u-sm-3 am-form-label">章节号
															<span class="tpl-form-line-small-title">Order</span>
														</label>
														<div class="am-u-sm-9">
															<select id="chapterOrder" name="chapterOrder">

																<c:forEach varStatus="i" begin="1" end="40">
																	<option value="${i.count}">${i.count}</option>
																</c:forEach>
															</select>
														</div>

													</div>
													<div class="am-form-group">
														<label for="user-name" class="am-u-sm-3 am-form-label">标题
															<span class="tpl-form-line-small-title">Title</span>
														</label>
														<div class="am-u-sm-9">
															<textarea class="" rows="1" id="chapterName"
																name="chapterName" placeholder="请输入标题"
																required="required"></textarea>
														</div>
													</div>

													<!-- <div class="am-form-group">
														<label for="user-email" class="am-u-sm-3 am-form-label">发布时间
															<span class="tpl-form-line-small-title">Time</span>
														</label>
														<div class="am-u-sm-9">
															<input type="text" class="am-form-field tpl-form-no-bg"
																placeholder="发布时间" data-am-datepicker="" readonly /> <small>发布时间为必填</small>
														</div>
													</div> -->
													<!-- <div class="am-form-group">
														<label for="user-phone" class="am-u-sm-3 am-form-label">作者
															<span class="tpl-form-line-small-title">Author</span>
														</label>
														<div class="am-u-sm-9">
															<select class="sel">
																<option value="a">${user.userName }</option>
															</select>
														</div>
													</div> -->


													<div class="am-form-group">
														<label for="user-intro" class="am-u-sm-3 am-form-label">章节简介</label>
														<div class="am-u-sm-9">
															<textarea class="" rows="10" id="chapterDescribe"
																name="chapterDescribe" placeholder="请输入简介内容"
																required="required"></textarea>
														</div>
													</div>

												</form>
												<button type="button" class="btn btn-default"
													data-dismiss="modal">关闭</button>
												<button type="button" class="btn btn-primary" id="submit"
													name="submit" data-dismiss="modal" onclick="addChapter();">提交更改</button>
											</div>
										</div>
										<!-- /.modal-content -->
									</div>
									<!-- /.modal -->
								</div>
								<!-- judge模态框（Modal） -->
								<div class="modal fade" id="judgeModal" tabindex="-1"
									role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
									<div class="modal-dialog">
										<div class="modal-content">
											<div class="modal-header">
												<button type="button" class="close" data-dismiss="modal"></button>
												<h4 class="modal-title" id="myModalLabel">模态框（Modal）标题</h4>
											</div>

											<div class="modal-body">添加章节</div>
											<div class="modal-footer">
												<form class="am-form tpl-form-line-form"
													action="judge/add.do" method="post" id="judgeForm"
													name="judgeForm">
													<div class="am-form-group">
														<label for="courseOrder" class="am-u-sm-3 am-form-label">courseOrder
														</label>
														<div class="am-u-sm-9">
															<select id="courseOrder" name="courseOrder">
																<c:forEach varStatus="i" begin="1" end="40">
																	<option value="${i.count}">${i.count}</option>
																</c:forEach>
															</select>
														</div>
													</div>
													<div class="am-form-group">
														<label for="judgeItem" class="am-u-sm-3 am-form-label">judgeItem
														</label>
														<div class="am-u-sm-9">
															<input type="text" class="tpl-form-input" id="judgeItem"
																name="judgeItem" placeholder="请输入..."
																required="required"> <small></small>
														</div>
													</div>
													<div class="am-form-group">
														<label for="judgeType" class="am-u-sm-3 am-form-label">judgeType
														</label>
														<div class="am-u-sm-9">
															<input type="text" class="tpl-form-input" id="judgeType"
																name="judgeType" placeholder="请输入..."
																required="required"> <small></small>
														</div>
													</div>
													<!-- <div class="am-form-group">
														<label for="user-email" class="am-u-sm-3 am-form-label">发布时间
															<span class="tpl-form-line-small-title">Time</span>
														</label>
														<div class="am-u-sm-9">
															<input type="text" class="am-form-field tpl-form-no-bg"
																placeholder="发布时间" data-am-datepicker="" readonly /> <small>发布时间为必填</small>
														</div>
													</div> -->
													<!-- <div class="am-form-group">
														<label for="user-phone" class="am-u-sm-3 am-form-label">作者
															<span class="tpl-form-line-small-title">Author</span>
														</label>
														<div class="am-u-sm-9">
															<select class="sel">
																<option value="a">${user.userName }</option>
															</select>
														</div>
													</div> -->


													<div class="am-form-group">
														<label for="judgeTips" class="am-u-sm-3 am-form-label">judgeTips</label>
														<div class="am-u-sm-9">
															<textarea class="" rows="10" id="judgeTips"
																name="judgeTips" placeholder="请输入简介提示..."
																required="required"></textarea>
														</div>
													</div>
													<button type="button" class="btn btn-default"
														data-dismiss="modal">关闭</button>
													<button type="button" class="btn btn-primary"
														id="judgeSubmit" name="judgeSubmit">提交更改</button>
												</form>

											</div>
										</div>
										<!-- /.modal-content -->
									</div>
									<!-- /.modal -->
								</div>
							</div>
						</div>
					</div>
				</div>
				<table class="table table-striped">
					<thead>
						<tr>
							<th>操作1</th>
							<th>操作2</th>
							<th>章节号</th>
							<th style="text-align: center;">章节名称</th>
						</tr>
					</thead>
					<tbody>
						<c:if test="${fn:length(chapterList) gt 0}">
							<c:forEach var="i" begin="1" end="${fn:length(chapterList)}"
								step="1">
								<tr>
									<th width="10%"><button
											class="am-btn am-btn-default am-btn-secondary"
											onclick="getChapterById(${chapterList[i-1].chapterId});">修改</button></th>
									<th width="10%">
										<button class="am-btn am-btn-default am-btn-danger"
											onclick="deleteChapter(${chapterList[i-1].chapterId});">删除</button>
									</th>
									<th style="text-align: center;" width="10%"><a>${chapterList[i-1].chapterOrder }</a></th>
									<th style="text-align: center;"><a
										href="../rcampus/course/getCourseList?chapterId=${chapterList[i-1].chapterId}">${chapterList[i-1].chapterName }</a></th>
								<tr>
							</c:forEach>
						</c:if>
					</tbody>
				</table>

			</div>
		</div>
	</div>
</body>

</html>