<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!-- 红配蓝，狗都嫌 -->
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>Amaze UI Admin index Examples</title>
<meta name="description" content="这是一个 index 页面">
<meta name="keywords" content="index">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="renderer" content="webkit">
<meta http-equiv="Cache-Control" content="no-siteapp" />
<meta name="apple-mobile-web-app-title" content="Amaze UI" />
<link rel="stylesheet" href="../page/assets/css/amazeui.min.css" />
<link rel="stylesheet" href="../page/assets/css/admin.css">
<link rel="stylesheet" href="../page/assets/css/app.css">
<link rel="stylesheet" href="../page/assets/css/bootstrap.min.css">
<script src="../page/assets/js/jquery.min.js"></script>
<script src="../page/assets/js/amazeui.min.js"></script>
<script src="../page/assets/js/app.js"></script>
<script src="../page/assets/js/bootstrap.min.js"></script>
<script src="../page/assets/js/string-deal.js"></script>
<style>
</style>
<script type="text/javascript">
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

	function addCourseItem(chapterName, chapterOrder, chapterId, i) {
		var div = document.getElementById("chapter-list");
		var ul = document.createElement("ul");
		var content_li = document.createElement("li");
		var btn_modify = document.createElement("button");
		btn_modify.setAttribute("class",
				"am-btn am-btn-default am-btn-secondary");
		btn_modify.innerHTML = "修改";
		btn_modify.setAttribute("onclick", "modify_chapter(" + chapterId + ")");

		var btn_delete = document.createElement("button");
		btn_delete.setAttribute("class", "am-btn am-btn-default am-btn-danger");
		btn_delete.innerHTML = "删除";
		btn_delete.setAttribute("onclick", "delete_chapter(" + chapterId + ")");
		var chapter_id = document.createElement("div");
		chapter_id.setAttribute("class", "cosB");
		chapter_id.innerHTML = chapterOrder;
		var chapter_name = document.createElement("a");
		chapter_name.setAttribute("class", "cosA");
		chapter_name.setAttribute("href", "/chapter/chapter_detail?cchapterId="
				+ chapterId);
		chapter_name.innerHTML = chapterName;
		if (i % 2 == 0) {
			ul.setAttribute("style", "background:#CDCDC1;");
		}
		content_li.appendChild(btn_modify);
		content_li.appendChild(btn_delete);
		content_li.appendChild(chapter_id);
		content_li.appendChild(chapter_name);
		ul.appendChild(content_li);
		div.appendChild(ul);
	}

	//添加课程
	function addCourse() {
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
		xmlhttp.open("POST", "/rcampus/course/add", true);
		xmlhttp.setRequestHeader("Content-type",
				"application/x-www-form-urlencoded");
		var courseOrder = stringPredetail(document
				.getElementById("courseOrder").value);
		var courseName = stringPredetail(document.getElementById("courseName").value);
		var courseNote = stringPredetail(document.getElementById("courseNote").value);
		var belongChapter = stringPredetail(document.getElementById("chapter").value);
		xmlhttp.send("courseOrder=" + courseOrder + "&courseName=" + courseName
				+ "&courseNote=" + courseNote + "&belongChapter="
				+ belongChapter);
	}
	function modify_course(chapter_id) {
		alert("modify" + chapter_id);
	}
	function delete_course(chapter_id) {
		alert("delete" + chapter_id);
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
					<li class="tpl-left-nav-item"><a href="/adminhome"
						class="nav-link active"> <i class="am-icon-home"></i> <span>用户管理</span>

					</a></li>
					<li class="tpl-left-nav-item"><a href="chapter_manage"
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
		<div class="tpl-content-page-title">章节列表</div>
		<div class="tpl-portlet-components">
			<div class="portlet-title">
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
									data-toggle="modal" data-target="#myModal">
									<span class="am-icon-plus"></span> 新增
								</button>

								<!-- 模态框（Modal） -->
								<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
									aria-labelledby="myModalLabel" aria-hidden="true">
									<div class="modal-dialog">
										<div class="modal-content">
											<div class="modal-header">
												<button type="button" class="close" data-dismiss="modal"></button>
												<h4 class="modal-title" id="myModalLabel"></h4>
											</div>

											<div class="modal-body">添加课程</div>
											<div class="modal-footer">
												<form class="am-form tpl-form-line-form"
													action="../course/add" method="post" id="courseForm"
													name="courseForm">
													<div class="am-form-group">
														<label for="user-name" class="am-u-sm-3 am-form-label">课程号
															<span class="tpl-form-line-small-title">Order</span>
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
														<label for="user-name" class="am-u-sm-3 am-form-label">标题
															<span class="tpl-form-line-small-title">Title</span>
														</label>
														<div class="am-u-sm-9">
															<input type="text" class="tpl-form-input" id="courseName"
																name="courseName" placeholder="请输入标题"
																required="required"> <small></small>
														</div>
													</div>



													<div class="am-form-group">
														<label for="user-intro" class="am-u-sm-3 am-form-label">CourseNote</label>
														<div class="am-u-sm-9">
															<textarea class="" rows="10" id="courseNote"
																name="courseNote" placeholder="请输入课程内容"
																required="required"></textarea>
														</div>
													</div>
													<div class="am-form-group" style="display: none">
														<textarea class="" rows="10" id="chapter" name="chapter"
															required="required">${chapter.chapterId }</textarea>
													</div>
													<button type="button" class="btn btn-default"
														data-dismiss="modal">关闭</button>
													<button type="submit" class="btn btn-primary" id="submit"
														name="submit" data-dismiss="modal" onclick="addCourse();">提交更改</button>

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
				<ul class="tpl-task-list tpl-task-remind" id="chapter-list">
					<li>
						<div class="cosa">课程名称</div> <!-- <div class="cosb">课程号</div> -->
					</li>
					<c:forEach items="${courseList}" var="course" varStatus="loop">
						<c:choose>
							<c:when test="${loop.count%2 ne 0}">
								<li style="background: #CDCDC1;"><button
										class="am-btn am-btn-default am-btn-secondary" onclick="">修改</button>
									<button class="am-btn am-btn-default am-btn-danger" onclick="">删除</button>
									<a class="cosA">${course.courseName }</a> <a class="cosB">${course.courseOrder }</a></li>
							</c:when>
							<c:when test="${loop.count%2 eq 0}">
								<li><button class="am-btn am-btn-default am-btn-secondary"
										onclick="">修改</button>
									<button class="am-btn am-btn-default am-btn-danger" onclick="">删除</button>
									<a class="cosA">${course.courseName }</a> <a class="cosB">${course.courseOrder }</a></li>
							</c:when>
						</c:choose>
					</c:forEach>
				</ul>
			</div>
		</div>
	</div>
</body>

</html>