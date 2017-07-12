<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel='stylesheet' href='../page/assets/css/bootstrap.min.css'>
<link rel='stylesheet' href='../page/assets/css/style.css'>
<script src="../page/assets/js/jquery.js" type="text/javascript"></script>
<script src='../page/assets/js/bootstrap.min.js'></script>
</head>
<body>
	<div id="wrapper" class="">
		<div class="overlay" style="display: none;"></div>
		<nav class="navbar navbar-inverse navbar-fixed-top"
			id="sidebar-wrapper" role="navigation">
			<ul class="nav sidebar-nav">
				<li class="sidebar-brand"><a href="#"> Bootstrap 3 </a></li>
				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown"> <i class="fa fa-fw fa-plus"></i>Chapter1<span
						class="caret"></span>
				</a>
					<ul class="dropdown-menu" role="menu">
						<li class="dropdown-header">这是第一章</li>
						<li><a
							href="./test?chapterName=chapterOne&courseName=courseOne">course1</a></li>
						<li><a
							href="./test?chapterName=chapterTwo&courseName=courseTwo">course2</a></li>
						<li><a
							href="./test?chapterName=chapterThree&courseName=courseThree">course3</a></li>
						<li><a
							href="./test?chapterName=chapterFour&courseName=courseFour">course4</a></li>
						<li><a
							href="./test?chapterName=chapterFive&courseName=courseFive">course5</a></li>
					</ul></li>
			</ul>
		</nav>
		<div id="page-content-wrapper">
			<button type="button" class="hamburger animated fadeInLeft is-closed"
				data-toggle="offcanvas">
				<span class="hamb-top"></span> <span class="hamb-middle"></span> <span
					class="hamb-bottom"></span>
			</button>
			<div class="container">
				<div class="row">
					<div class="col-lg-8 col-lg-offset-2">
						<p style="font-size: 200%; color: #FFFFFF">
							<c:out value="${chapterName}" />
							<c:out value="${courseName}" />
						</p>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		$(document)
				.ready(
						function() {
							var trigger = $('.hamburger'), overlay = $('.overlay'), isClosed = false;

							trigger.click(function() {
								hamburger_cross();
							});

							function hamburger_cross() {

								if (isClosed == true) {
									overlay.hide();
									trigger.removeClass('is-open');
									trigger.addClass('is-closed');
									isClosed = false;
								} else {
									overlay.show();
									trigger.removeClass('is-closed');
									trigger.addClass('is-open');
									isClosed = true;
								}
							}

							$('[data-toggle="offcanvas"]').click(function() {
								$('#wrapper').toggleClass('toggled');
							});
						});
	</script>
</body>
</html>