<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!doctype html>
<html lang="en" data-reactroot="" data-reactid="1"
	data-react-checksum="913391198">
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
<link rel="stylesheet" href="page/assets/css/bootstrap.min.css"
	data-reactid="27" />
<link rel="icon" type="image/png" href="page/assets/img/favicon.ico"
	data-reactid="24" />
<link href="page/assets/img/apple-icon.png" rel="apple-touch-icon"
	data-reactid="25" />
<link href="page/assets/img/apple-icon.png"
	rel="apple-touch-icon-precomposed" data-reactid="26" />

<link rel='stylesheet' href='page/assets/css/style.css'>
<link rel="stylesheet" href="page/assets/css/font-awesome.min.css"
	data-reactid="28" />
<link rel="stylesheet" href="page/assets/css/codemirror/icecoder.css">
<link type="text/css" rel="stylesheet"
	href="page/assets/css/codemirror/codemirror.css" />

<link type="text/css" rel="stylesheet"
	href="page/assets/css/codemirror/show-hint.css" />
<script type="text/javascript"
	src="page/assets/js/codemirror/codemirror.js"></script>
<script type="text/javascript"
	src="page/assets/js/codemirror/show-hint.js"></script>
<script type="text/javascript" src="page/assets/js/codemirror/r.js"></script>
<script type="text/javascript" src="page/assets/js/jquery.js"></script>
<script type="text/javascript" src="page/assets/js/codemirror/r-hint.js"></script>
<script src='page/assets/js/bootstrap.min.js'></script>
<script src="page/assets/js/string-deal.js"></script>
<style>
.CodeMirror {
	border: 1px solid black;
}
</style>
<script type="text/javascript">
	function pass() {
		var nextA = document.getElementById("next");
		nextA.setAttribute("style", "float: right;");
		document.getElementById("finish_area").setAttribute("style",
				"width:100%; height: 100%;");
	}
	function getQueryString(name) {
		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
		var r = window.location.search.substr(1).match(reg);
		if (r != null)
			return unescape(r[2]);
		return null;
	}
	function closeFinishArea() {
		document.getElementById("finish_area").setAttribute("style",
				"width:100%; height: 100%;display:none;");
	}
	function init() {
		getNextCourse();
	}
</script>
</head>
<body data-reactid="32" onload="init();">
	<div id="main">
		<div id="wrapper" class="">
			<div class="overlay" style="display: none;"></div>
			<nav class="navbar navbar-inverse navbar-fixed-top"
				id="sidebar-wrapper" role="navigation">
				<ul class="nav sidebar-nav">
					<li class="sidebar-brand"><a href="#">R</a></li>
					<c:if test="${fn:length(chapterList) gt 0}">
						<c:forEach items="${chapterList}" var="chapter"
							varStatus="chapterIndex">
							<li class="dropdown"><a href="#" class="dropdown-toggle"
								data-toggle="dropdown"> <i class="fa fa-fw fa-plus"
									style="color: green;"></i>Chapter ${chapter.chapterOrder}
									${chapter.chapterName }<span class="caret"></span>
							</a>
								<ul class="dropdown-menu" role="menu">

									<!-- <li class="dropdown-header">这个标签是用来干蛤的？</li> -->
									<c:if test="${fn:length(chapter.courseList) gt 0}">
										<c:forEach items="${chapter.courseList}" var="course"
											varStatus="courseIndex">

											<!--<li><a href="page/courses/${course.courseId}.html">${course.courseName }</a></li>-->
											<li><c:choose>
													<c:when test="${course.courseId le nextCourse.courseId}">
														<a
															href="page/courses/getCourseById?courseId=${course.courseId}"
															style="color: green;">${course.courseName }</a>
													</c:when>
													<c:when test="${course.courseId gt nextCourse.courseId}">
														<a href="javascript:return false;">${course.courseName }</a>
													</c:when>
												</c:choose></li>
										</c:forEach>
									</c:if>
								</ul></li>
						</c:forEach>
					</c:if>
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
				data-react-checksum="-2128556104">
				<section id="navigation" class="bg-navigation" data-reactid="2">
					<div class="clearfix" data-reactid="3">
						<div data-reactid="7">
							<div class="__react_component_tooltip place-top type-dark "
								data-id="tooltip" data-reactid="8"></div>
							<div
								class="__react_component_tooltip place-bottom type-dark bottom tooltip"
								data-id="tooltip" data-reactid="9"
								style="left: 456px; top: 40px;">
								<div class="tooltip-inner">Ctrl+O</div>
							</div>
							<div class="__react_component_tooltip place-top type-dark "
								data-id="tooltip" data-reactid="10"></div>

						</div>

						<div class="navigation--buttons-right" data-reactid="20">

							<div data-tip="true" data-for="tp-notifications"
								data-reactid="25">
								<div class="__react_component_tooltip place-top type-dark "
									data-id="tooltip" data-reactid="26"></div>
								<i class="fa fa-bell-o hover-icon" data-reactid="27"
									data-toggle="modal" data-target="#report">report issues</i> <a
									href="/rcampus">index</a>
							</div>
						</div>
					</div>
				</section>
				<div class="modal fade" class="modal fade" id="report" tabindex="-1"
					role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
					<div class="modal-backdrop fade in"></div>
					<div role="dialog" class="modal modal-window in modal-dialog">
						<div>
							<div class="modal-content" id="report-exercise">
								<div class="modal-header">
									<h4 class="modal-title">Report an Issue</h4>
								</div>
								<div class="modal-body">
									<div class="form-group">
										<textarea placeholder="Provide more information"
											name="report_text" id="report_text"></textarea>

									</div>
								</div>
								<div class="modal-footer">
									<button class="btn btn-secondary btn-small pull-left m0"
										data-dismiss="modal">Cancel</button>
									<button class="btn btn-primary btn-small pull-right m0"
										onclick="postReport();" data-dismiss="modal">Submit</button>
								</div>
							</div>
						</div>
					</div>
					<iframe id="fake" src="about:blank"></iframe>
				</div>
				<div class="exercise-area " data-reactid="34">
					<div class="lm_item lm_stack"
						style="width: 100%; height: 100%; display: none;" id="finish_area">
						<div class="lm_items" style="width: 100%; height: 100%;">
							<div class="lm_item_container" style="width: 40%; height: 526px;">
								<div class="lm_content" style="width: 100%; height: 100%;">
									<div data-reactroot="" id="gl-aside" class="gl-content">
										<aside class="exercise--sidebar">
											<div class="exercise--sidebar-content">
												<div class="exercise--assignment exercise--typography"
													data-onboarding="assignment">
													<h1 class="exercise--title">How it works</h1>
													<div class="skills-tooltip">
														<!-- react-text: 7 -->
														100
														<!-- /react-text -->
														<!-- react-text: 8 -->
														xp
														<!-- /react-text -->
													</div>
													<div>
														<div>
															<p>In the editor on the right you should type R code
																to solve the exercises. When you hit the 'Submit Answer'
																button, every line of code is interpreted and executed
																by R and you get a message whether or not your code was
																correct. The output of your R code is shown in the
																console in the lower right corner.</p>

															<p>
																R makes use of the
																<code>#</code>
																sign to add comments, so that you and others can
																understand what the R code is about. Just like Twitter!
																Comments are not run as R code, so they will not
																influence your result. For example, <em>Calculate 3
																	+ 4</em> in the editor on the right is a comment.
															</p>


														</div>
													</div>
												</div>
												<div class="">
													<div class="exercise--instructions-title">
														<h2>Instructions</h2>
													</div>
													<div data-onboarding="instructions"
														class="exercise--instructions exercise--typography">
														<div>
															<div class="exercise--instructions__content">
																<ul>
																	<li>In the editor on the right there is already
																		some sample code. Can you see which lines are actual R
																		code and which are comments?</li>
																	<li>Add a line of code that calculates the sum of
																		6 and 12, and hit the 'Submit Answer' button.</li>
																</ul>
															</div>
														</div>
														<div class="sct-feedback" style="position: relative;">
															<div
																style="position: absolute; left: 0px; top: 0px; right: 0px; bottom: 0px; overflow: scroll; z-index: -1; visibility: hidden;"
																class=" react-resize-detector">
																<div
																	style="position: absolute; left: 0px; top: 0px; right: 0px; bottom: 0px; overflow: scroll; z-index: -1; visibility: hidden;">
																	<div
																		style="position: absolute; left: 0px; top: 0px; width: 299px; height: 31px;"></div>
																</div>
																<div
																	style="position: absolute; left: 0px; top: 0px; right: 0px; bottom: 0px; overflow: scroll; z-index: -1; visibility: hidden;">
																	<div
																		style="position: absolute; left: 0px; top: 0px; width: 200%; height: 200%;"></div>
																</div>
															</div>
															<ul class="content--tab mono">
																<div data-tip="true" data-for="tp-hint"
																	style="display: inline-block;" currentitem="false">
																	<div
																		class="__react_component_tooltip place-top type-dark "
																		data-id="tooltip"></div>
																	<a class="exercise--show-hint"
																		href="https://campus.datacamp.com/courses/free-introduction-to-r/chapter-1-intro-to-basics-1?ex=1"><i
																		class="fa fa-lightbulb-o"></i><span> <!-- react-text: 29 -->Take
																			Hint<!-- /react-text --> <!-- react-text: 30 --> (-<!-- /react-text -->
																			<!-- react-text: 31 -->30<!-- /react-text --> <!-- react-text: 32 -->xp)<!-- /react-text -->
																	</span></a>
																</div>
															</ul>
														</div>
													</div>
												</div>
											</div>
											<div class="sidebar-overlay">
												<a class="modal--close" href="javascript:void(0);"
													onclick="closeFinishArea();">点击关闭</a>
												<div class="sidebar-overlay--content text-center">
													<h4>
														<!-- react-text: 38 -->
														Exercise Completed
														<!-- /react-text -->
														<div class="progress-tooltip inbetween completed">
															<span><i class="fa fa-check"></i> <!-- react-text: 42 -->100<!-- /react-text -->
																<!-- react-text: 43 -->xp<!-- /react-text --></span>
														</div>
													</h4>
													<div></div>
													<div>
														<p>Awesome! See how the console shows the result of
															the R code you submitted? Now that you're familiar with
															the interface, let's get down to R business!</p>
													</div>

													<div class="sidebar-overlay__continue">
														<span>PRESS ENTER TO </span>
														<div tabindex="-1">
															<a
																class="btn btn-small btn-primary next-exercise animation--shake"
																id="next_course"
																href="/rcampus/course/getCourseById?courseId=${nextCourse.courseId }">
																下节课</a>
														</div>
													</div>
												</div>

											</div>
										</aside>
									</div>
								</div>
							</div>
						</div>
					</div>

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
											var courseId = getQueryString("courseId");
											var code = editor.getValue();
											code = stringPredetail(code);
											code = code.replace(/\%/g, "%25");
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
													spilt_data = resultData['console']
															.split("\n");
													var codeout = document
															.getElementById("codeout");
													codeout.innerHTML = "  ";
													document
															.getElementById("codeout").innerHTML = resultData['console'];
													if (data['status']) {
														var nextA = document
																.getElementById("next");
														nextA
																.setAttribute(
																		"style",
																		"float: right;");
														document
																.getElementById(
																		"finish_area")
																.setAttribute(
																		"style",
																		"width:100%; height: 100%;");
													}
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
								<div>
									<button id="submit" name="submit" value="submit"
										onclick="postCode()" class="btn btn-primary btn-round">提交</button>
									<a id="next" class="btn btn-primary btn-round"
										style="float: right; display: none;"
										href="/getCourseById?courseId=${nextCourse.courseId}">你通过了这节课，点击开始下节课</a>
									<c:if test="${isFinish && next.courseId ne course.courseId}">
										<a class="btn btn-primary btn-round" style="float: right;"
											href="/rcampus/course/getCourseById?courseId=${nextCourse.courseId}">下一节课</a>
									</c:if>
								</div>
								<p style="color: #3aaaca;">请做完全部题目再提交，不要有空缺，否则会造成误判。</p>
								<!-- <div id="codeout"
									style="overflow: auto; width: 750px; height: 400px; float: right;">


								</div> -->

								<textarea name="codeout" id="codeout" style="height: 200px"></textarea>
							</div>
						</section>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		function postReport() {
			var xmlhttp;
			if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
				xmlhttp = new XMLHttpRequest();
			} else {// code for IE6, IE5
				xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
			}
			xmlhttp.onreadystatechange = function() {
				if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
					var data = JSON.parse(xmlhttp.responseText);
					alert("您的举报已被受理。");
				}
			};
			xmlhttp.open("POST", "/rcampus/message/addMessage", true);
			xmlhttp.setRequestHeader("Content-type",
					"application/x-www-form-urlencoded");
			xmlhttp.send("content="
					+ document.getElementById("report_text").value);
		}
	</script>
</body>
</html>