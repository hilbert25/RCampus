<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
	function getChapterList() {
		var xmlhttp;
		if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
			xmlhttp = new XMLHttpRequest();
		} else {// code for IE6, IE5
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
		xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
				var data = JSON.parse(xmlhttp.responseText);
				for (var i = 0; i < data.length; i++) {
					addChapterItem(data[i]['chapterName'],
							data[i]['chapterOrder'], data[i]['chapterId'], i);
				}
			}
		};
		xmlhttp.open("POST", "/rcampus/chapter/list.do", true);
		xmlhttp.setRequestHeader("Content-type",
				"application/x-www-form-urlencoded");
		xmlhttp.send();
	}
	function addChapterItem(chapterName, chapterOrder, chapterId, i) {
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
		//var chapter_name = document.createElement("div");
		//chapter_name.setAttribute("class", "cosA");
		//chapter_name.innerHTML = chapterName;
		var chapter_name = document.createElement("a");
		chapter_name.setAttribute("class", "cosA");
		chapter_name.setAttribute("href",
				"../chapter/chapter_detail?chapterId=" + chapterId);
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
	function modify_chapter(chapter_id) {
		alert("modify" + chapter_id);
	}
	function delete_chapter(chapter_id) {
		alert("delete" + chapter_id);
	}
	function init() {
		getUnDealCount();
		getChapterList();
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
		<div class="tpl-content-page-title">课程列表</div>
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
												<h4 class="modal-title" id="myModalLabel">模态框（Modal）标题</h4>
											</div>

											<div class="modal-body">添加章节</div>
											<div class="modal-footer">
												<form class="am-form tpl-form-line-form"
													action="../chapter/add" method="post" id="chapterForm"
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
															<input type="text" class="tpl-form-input"
																id="chapterName" name="chapterName" placeholder="请输入标题"
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
														<label for="user-intro" class="am-u-sm-3 am-form-label">章节简介</label>
														<div class="am-u-sm-9">
															<textarea class="" rows="10" id="chapterDescribe"
																name="chapterDescribe" placeholder="请输入简介内容"
																required="required"></textarea>
														</div>
													</div>
													<button type="button" class="btn btn-default"
														data-dismiss="modal">关闭</button>
													<button type="button" class="btn btn-primary" id="submit"
														name="submit" onclick="addChapter()">提交更改</button>

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
						<div class="cosa">章节号</div>
						<!-- <div class="cosb">章节名称</div> -->
					</li>
					
					<!-- <li>
						<div class="cosB">12分钟前</div>
						<div class="cosA">fdsfafa</div>
					</li> -->
				</ul>
			</div>
		</div>
	</div>
</body>

</html>