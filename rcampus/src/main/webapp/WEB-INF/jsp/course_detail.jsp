<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!doctype html>
<html lang="en" data-reactroot="" data-reactid="1"
	data-react-checksum="913391198">
<head data-reactid="2">
<base href="<%=basePath%>"></base>
<title data-reactid="3">${course.courseName }</title>
<meta charset="utf-8" data-reactid="4" />
<!-- react-text: 5 -->
<!-- /react-text -->
<meta name="robots" content="noindex" data-reactid="6" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"
	data-reactid="7" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1"
	data-reactid="8" />
<meta name="fragment" content="!" data-reactid="9" />
<meta name="keywords"
	content="R, Python, Data analysis, interactive, learning"
	data-reactid="10" />
<meta name="description"
	content="Here is an example of Variable assignment (3): Every tasty fruit basket needs oranges, so you decide to add six oranges."
	data-reactid="11" />
<link type="text/css" rel="stylesheet"
	href="page/assets/css/style-41f68dcc06bbfb7a38938712996401e4.css" />
<link rel="stylesheet"
	href="page/assets/css/bootstrap.min.css"
	data-reactid="27" />
<link rel="icon" type="image/png"
	href="page/assets/img/favicon.ico" data-reactid="24" />
<link href="page/assets/img/apple-icon.png"
	rel="apple-touch-icon" data-reactid="25" />
<link href="page/assets/img/apple-icon.png"
	rel="apple-touch-icon-precomposed" data-reactid="26" />

<link rel='stylesheet' href='page/assets/css/style.css'>
<link rel="stylesheet"
	href="page/assets/css/font-awesome.min.css"
	data-reactid="28" />
<link rel="stylesheet"
	href="page/assets/css/codemirror/icecoder.css">
<link type="text/css" rel="stylesheet"
	href="page/assets/css/codemirror/codemirror.css" />

<link type="text/css" rel="stylesheet"
	href="page/assets/css/codemirror/show-hint.css" />
<script type="text/javascript"
	src="page/assets/js/codemirror/codemirror.js"></script>
<script type="text/javascript"
	src="page/assets/js/codemirror/show-hint.js"></script>
<script type="text/javascript"
	src="page/assets/js/codemirror/r.js"></script>
<script type="text/javascript"
	src="page/assets/js/jquery.js"></script>
<script type="text/javascript"
	src="page/assets/js/codemirror/r-hint.js"></script>
<script src='page/assets/js/bootstrap.min.js'></script>
<style>
.CodeMirror {
	border: 1px solid black;
}
</style>

</head>
<body data-reactid="32">
	<div id="main">
		<div id="wrapper" class="">
			<div class="overlay" style="display: none;"></div>
			<nav class="navbar navbar-inverse navbar-fixed-top"
				id="sidebar-wrapper" role="navigation">
				<ul class="nav sidebar-nav">
					<li class="sidebar-brand"><a href="#">R</a></li>
					<c:forEach items="${chapterList}" var="chapter"
						varStatus="chapterIndex">
						<li class="dropdown"><a href="#" class="dropdown-toggle"
							data-toggle="dropdown"> <i class="fa fa-fw fa-plus"></i>Chapter
								${chapter.chapterId} ${chapter.chapterName }<span class="caret"></span>
						</a>
							<ul class="dropdown-menu" role="menu">

								<!-- <li class="dropdown-header">这个标签是用来干蛤的？</li> -->
								<c:forEach items="${chapter.courseList}" var="course"
									varStatus="courseIndex">
									<li><a href="page/courses/${course.courseId}.html">${course.courseName }</a></li>
								</c:forEach>
							</ul></li>
					</c:forEach>
				</ul>
			</nav>
			<div id="page-content-wrapper">
				<button type="button"
					class="hamburger animated fadeInLeft is-closed"
					data-toggle="offcanvas">
					<span class="hamb-top"></span> <span class="hamb-middle"></span> <span
						class="hamb-bottom"></span>
				</button>
				<div class="container">
					<div class="row">
						<div class="col-lg-8 col-lg-offset-2">
							<p style="font-size: 200%; color: #FFFFFF">
								<c:out value="${chapterName}" />
								<c:out value="${course.courseName}" />
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

								$('[data-toggle="offcanvas"]').click(
										function() {
											$('#wrapper')
													.toggleClass('toggled');
										});
							});
		</script>
	</div>
	<div id="main">
		<div id="root" data-reactid="33">
			<div data-reactroot="" data-reactid="1"
				data-react-checksum="576685586">
				<section id="navigation" class="bg-navigation" data-reactid="2">
				</section>
				<div class="exercise-area " data-reactid="34">
					<div data-reactid="35">
						<aside class="exercise--sidebar" style="width: 40%;"
							data-reactid="36">
							<div class="exercise--sidebar-content" data-reactid="37">
								<div class="exercise--assignment exercise--typography"
									data-onboarding="assignment" data-reactid="38">
									<h1 class="exercise--title" data-reactid="39">${course.courseName }</h1>
									<div data-reactid="43">
										<div data-reactid="44">
											<p style="color: black">${course.courseNote }</p>
											<pre>
											<code>我也不知道这是啥</code>
										</pre>
										</div>
									</div>
								</div>
								<div class="" data-reactid="45">
									<div class="exercise--instructions-title" data-reactid="46">
										<h2 data-reactid="47">Instructions</h2>
									</div>
									<div data-onboarding="instructions"
										class="exercise--instructions exercise--typography"
										data-reactid="48">
										<div data-reactid="49">
											<div class="exercise--instructions__content"
												data-reactid="50">
												<ul>
													<li>${course.examIntro }</li>
												</ul>
											</div>
										</div>
									</div>
								</div>
							</div>
						</aside>
						<!-- code area -->
						<section class="exercise--content"
							style="width: 60%; height: 100%" data-reactid="67">
							<div data-reactid="68">
								<article>
									<textarea name="code" id="code">${course.examPage}</textarea>

									<input name="courseId" id="courseId" type="hidden"
										value="${course.courseId }" />
									<script>
										var editor = CodeMirror
												.fromTextArea(
														document
																.getElementById("code"),
														{
															lineNumbers : true,
															extraKeys : {
																"Ctrl" : "autocomplete"
															},
															mode : {
																name : "r",
																globalVars : true
															},
															theme : "icecoder"
														});
										function postCode() {
											document.getElementById("codeout").innerHTML = "pending...";
											var xmlhttp;
											var courseId = document
													.getElementById("courseId").value;
											var code = editor.getValue();
											code = code.replace(/\+/g, "%2B");
											code = code.replace(/\&/g, "%26");
											if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
												xmlhttp = new XMLHttpRequest();
											} else {// code for IE6, IE5
												xmlhttp = new ActiveXObject(
														"Microsoft.XMLHTTP");
											}
											xmlhttp.onreadystatechange = function() {
												if (xmlhttp.readyState == 4
														&& xmlhttp.status == 200) {
													var data = JSON
															.parse(xmlhttp.responseText);
													var result = data['ocpuJSON'];
													var resultData = JSON
															.parse(result);
													document
															.getElementById("codeout").innerHTML = resultData['console'];
												}
											};
											xmlhttp
													.open(
															"POST",
															"/rcampus/course/submit.do",
															true);
											xmlhttp
													.setRequestHeader(
															"Content-type",
															"application/x-www-form-urlencoded");
											xmlhttp.send("courseId=" + courseId
													+ "&code=" + code);
										}
									</script>
								</article>
								<button id="submit" name="submit" value="submit"
									onclick="postCode()" class="btn btn-primary btn-round">Submit</button>
								<textarea name="codeout" id="codeout" style="height: 200px"> </textarea>
							</div>
						</section>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>