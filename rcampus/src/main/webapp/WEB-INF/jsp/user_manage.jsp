<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>用户管理</title>
<meta name="description" content="这是一个 index 页面">
<meta name="keywords" content="index">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="renderer" content="webkit">
<meta http-equiv="Cache-Control" content="no-siteapp" />
<meta name="apple-mobile-web-app-title" content="Amaze UI" />
<link rel="stylesheet" href="./page/assets/css/amazeui.min.css" />
<link rel="stylesheet" href="./page/assets/css/admin.css">
<link rel="stylesheet" href="./page/assets/css/app.css">
<script src="./page/assets/js/echarts.min.js"></script>
<script type="text/javascript">
	function getUserCount() {
		var xmlhttp;
		if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
			xmlhttp = new XMLHttpRequest();
		} else {// code for IE6, IE5
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
		xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
				document.getElementById("user_count").innerHTML = xmlhttp.responseText;
			}
		};
		xmlhttp.open("POST", "/rcampus/user/countUser", true);
		xmlhttp.setRequestHeader("Content-type",
				"application/x-www-form-urlencoded");
		xmlhttp.send();
	}
	function getAdminCount() {
		var xmlhttp;
		if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
			xmlhttp = new XMLHttpRequest();
		} else {// code for IE6, IE5
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
		xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
				document.getElementById("admin_count").innerHTML = xmlhttp.responseText;
			}
		};
		xmlhttp.open("POST", "/rcampus/user/countAdmin", true);
		xmlhttp.setRequestHeader("Content-type",
				"application/x-www-form-urlencoded");
		xmlhttp.send();
	}
	function getUserList(page, type) {
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
					addUserItem(data[i]['photo'], data[i]['userName'],
							data[i]['email'], data[i]['userType'], type);
				}
			}
		};
		xmlhttp.open("POST", "/rcampus/user/userlist", true);
		xmlhttp.setRequestHeader("Content-type",
				"application/x-www-form-urlencoded");
		xmlhttp.send("page=" + page + "&type=" + type);
	}
	function addUserItem(photo, name, email, type, useroradmin) {
		var div = document.getElementById(useroradmin + "list");
		var tr = document.createElement("tr");
		var imgtd = document.createElement("td");
		var img = document.createElement("img");
		img.setAttribute("src", photo);
		img.setAttribute("class", "user-pic");
		imgtd.appendChild(img);
		var nametd = document.createElement("a");
		nametd.setAttribute("class", "user-name");
		nametd.innerHTML = name;
		var emailtd = document.createElement("td");
		emailtd.innerHTML = email;
		var typetd = document.createElement("td");
		typetd.setAttribute("class", "font-green bold");
		if (type == 1 || type == 2) {
			typetd.innerHTML = "O";
		} else if (type == 0) {
			typetd.innerHTML = "S";
		} else {
			typetd.innerHTML = "V";
		}
		tr.appendChild(imgtd);
		tr.appendChild(nametd);
		tr.appendChild(emailtd);
		tr.appendChild(typetd);
		div.appendChild(tr);//插入到div内
	};
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
	function getShortMessageList() {
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
					var user = data[i]['user'];
					addMessageItem(user['userId'], user['photo'],
							user['userName'], data[i]['messageContent'],
							data[i]['createDiff']);
				}

			}
		};
		xmlhttp.open("POST", "/rcampus/message/getShortMessageList", true);
		xmlhttp.setRequestHeader("Content-type",
				"application/x-www-form-urlencoded");
		xmlhttp.send();
	}
	function addMessageItem(userId, userPhoto, userName, messageContent,
			datetime) {
		var div = document.getElementById("message_list");
		var li = document.createElement("li");
		var a = document.createElement("a");
		a.setAttribute("class", "tpl-dropdown-content-message");
		a.setAttribute("href", "");
		var span_photo = document.createElement("span");
		span_photo.setAttribute("class", "tpl-dropdown-content-photo");
		var img = document.createElement("img");
		img.setAttribute("src", userPhoto);
		span_photo.appendChild(img);
		a.appendChild(span_photo);
		var span_subject = document.createElement("span");
		span_subject.setAttribute("class", "tpl-dropdown-content-subject");
		var span_from = document.createElement("span");
		span_from.setAttribute("class", "tpl-dropdown-content-from");
		span_from.innerHTML = userName;
		var span_time = document.createElement("span");
		span_time.setAttribute("class", "tpl-dropdown-content-time");
		span_time.innerHTML = datetime;
		span_subject.appendChild(span_from);
		span_subject.appendChild(span_time);
		a.appendChild(span_subject);
		var span_font = document.createElement("span");
		span_font.setAttribute("class", "tpl-dropdown-content-font");
		span_font.innerHTML = messageContent;
		a.appendChild(span_font);
		li.appendChild(a);
		div.appendChild(li);
	}
	function init() {
		getUserCount();
		getAdminCount();
		getUserList(0, 'admin');
		getUserList(0, 'user');
		getUnDealCount();
		getShortMessageList();
	}
</script>
<style type="text/css">
ul {
	border: 0;
	margin: 0;
	padding: 0;
}

#pagination-flickr li {
	border: 0;
	margin: 0;
	padding: 0;
	font-size: 16px;
	list-style: none;
}

#pagination-flickr a {
	border: solid 1px #DDDDDD;
	margin-right: 2px;
}

#pagination-flickr .previous-off,#pagination-flickr .next-off {
	color: #666666;
	display: block;
	float: left;
	font-weight: bold;
	padding: 0px 0px;
}

#pagination-flickr .next a,#pagination-flickr .previous a {
	/*font-weight: bold;
	border: solid 1px #FFFFFF;*/
	color: #666666;
	display: block;
	float: left;
	font-weight: bold;
	padding: 0px 0px;
}

#pagination-flickr .active {
	color: #ff0084;
	display: block;
	float: left;
	padding: 0px 0px;
}

#pagination-flickr a:link,#pagination-flickr a:visited {
	color: #33aacc;
	display: block;
	float: left;
	padding: 3px 6px;
	text-decoration: none;
}

#pagination-flickr a:hover {
	border: solid 1px #666666;
}
</style>
</head>
<body data-type="index" onload="init();">
	<header class="am-topbar am-topbar-inverse admin-header">
	<div class="am-collapse am-topbar-collapse" id="topbar-collapse">
		<ul
			class="am-nav am-nav-pills am-topbar-nav am-topbar-right admin-header-list tpl-header-list">
			<li class="am-dropdown" data-am-dropdown data-am-dropdown-toggle>
				<a class="am-dropdown-toggle tpl-header-list-link"
				href="javascript:;" id="undeal"> <span class="am-icon-comment-o"></span>
					消息
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
					<li><a href="#"><span class="am-icon-power-off"></span> 退出</a></li>
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
							<i
							class="am-icon-star tpl-left-nav-content-ico am-fr am-margin-right"></i>
					</a></li>
					<li class="tpl-left-nav-item"><a href="chapter/chapter_manage"
						class="nav-link active"> <i class="am-icon-home"></i> <span>课程管理</span>
					</a></li>

					<li class="tpl-left-nav-item"><a href="login.html"
						class="nav-link tpl-left-nav-link-list"> <i
							class="am-icon-key"></i> <span>其余</span>

					</a></li>
				</ul>
			</div>
		</div>





		<div class="tpl-content-wrapper">
			<div class="tpl-content-page-title">用户后台管理</div>

			<div class="row">
				<div class="am-u-lg-3 am-u-md-6 am-u-sm-12">
					<div class="dashboard-stat blue">
						<div class="visual">
							<i class="am-icon-comments-o"></i>
						</div>
						<div class="details">
							<div class="number" id="user_count">0</div>
							<div class="desc">用户数量</div>
						</div>
					</div>
				</div>
				<div class="am-u-lg-3 am-u-md-6 am-u-sm-12">
					<div class="dashboard-stat red">
						<div class="visual">
							<i class="am-icon-bar-chart-o"></i>
						</div>
						<div class="details">
							<div class="number" id="admin_count">0</div>
							<div class="desc">管理员数量</div>
						</div>
					</div>
				</div>
				<div class="am-u-lg-3 am-u-md-6 am-u-sm-12">
					<div class="dashboard-stat green">
						<div class="visual">
							<i class="am-icon-apple"></i>
						</div>
						<div class="details">
							<div class="number">653</div>
							<div class="desc">课程数量</div>
						</div>
					</div>
				</div>
				<div class="am-u-lg-3 am-u-md-6 am-u-sm-12">
					<div class="dashboard-stat purple">
						<div class="visual">
							<i class="am-icon-android"></i>
						</div>
						<div class="details">
							<div class="number">786</div>
							<div class="desc">题目数量</div>
						</div>
					</div>
				</div>



			</div>

			<div class="am-u-md-6 am-u-sm-12 row-mb">
				<div class="tpl-portlet">
					<div class="tpl-portlet-title">
						<div class="tpl-caption font-red ">
							<i class="am-icon-bar-chart"></i> <span>管理员列表</span>
						</div>
					</div>
					<div class="tpl-scrollable">

						<table class="am-table tpl-table">
							<thead>
								<tr class="tpl-table-uppercase">
									<th>头像</th>
									<th>昵称</th>
									<th>邮箱</th>
									<th>类型</th>
								</tr>
							</thead>
							<tbody id="adminlist">

							</tbody>

						</table>
						<a>more</a>
					</div>
				</div>
			</div>
			<div class="am-u-md-6 am-u-sm-12 row-mb">
				<div class="tpl-portlet">
					<div class="tpl-portlet-title">
						<div class="tpl-caption font-red ">
							<i class="am-icon-bar-chart"></i> <span>用户列表</span>
						</div>
					</div>
					<div class="tpl-scrollable">

						<table class="am-table tpl-table">
							<thead>
								<tr class="tpl-table-uppercase">
									<th>头像</th>
									<th>昵称</th>
									<th>邮箱</th>
									<th>类型</th>
								</tr>
							</thead>
							<tbody id="userlist">

							</tbody>

						</table>
						<a>more</a>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script src="./page/assets/js/jquery.min.js"></script>
	<script src="./page/assets/js/amazeui.min.js"></script>
	<script src="./page/assets/js/app.js"></script>

</body>

</html>