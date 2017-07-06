<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!doctype html>
<html lang="en" data-reactroot="" data-reactid="1"
	data-react-checksum="913391198">
<head data-reactid="2">
<title data-reactid="3">Variable assignment (3) | R</title>
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
<link rel="icon" type="image/png"
	href="../../rcampus/page/assets/img/favicon.ico" data-reactid="24" />
<link href="../../rcampus/page/assets/img/apple-icon.png"
	rel="apple-touch-icon" data-reactid="25" />
<link href="../../rcampus/page/assets/img/apple-icon.png"
	rel="apple-touch-icon-precomposed" data-reactid="26" />
<link rel="stylesheet"
	href="../../rcampus/page/assets/css/bootstrap.min.css"
	data-reactid="27" />
<link rel="stylesheet"
	href="../../rcampus/page/assets/css/font-awesome.min.css"
	data-reactid="28" />
<link rel="stylesheet"
	href="../../rcampus/page/assets/css/style-41f68dcc06bbfb7a38938712996401e4.css"
	data-reactid="29" />
<script src="../../rcampus/page/assets/js/pjc4zfc.js" data-reactid="30"></script>
<link rel="stylesheet"
	href="../../rcampus/page/assets/css/codemirror/icecoder.css">
<link type="text/css" rel="stylesheet"
	href="../../rcampus/page/assets/css/codemirror/codemirror.css" />
<link type="text/css" rel="stylesheet"
	href="../../rcampus/page/assets/css/codemirror/show-hint.css" />
<script type="text/javascript"
	src="../../rcampus/page/assets/js/codemirror/codemirror.js"></script>
<script type="text/javascript"
	src="../../rcampus/page/assets/js/codemirror/show-hint.js"></script>
<script type="text/javascript"
	src="../../rcampus/page/assets/js/codemirror/r.js"></script>
<script type="text/javascript"
	src="../../rcampus/page/assets/js/jquery.js"></script>
<script type="text/javascript"
	src="../../rcampus/page/assets/js/codemirror/r-hint.js"></script>
<style>
.CodeMirror {
	border: 1px solid black;
}
</style>

</head>
<body data-reactid="32">
	<div id="root" data-reactid="33">
		<div data-reactroot="" data-reactid="1"
			data-react-checksum="576685586">
			<section id="navigation" class="bg-navigation" data-reactid="2">
				<div class="clearfix" data-reactid="3">
					<a href="https://www.datacamp.com/" class="navbar--title"
						data-reactid="4"><div class="logo" data-reactid="5"></div> <span
						class="logo-title" data-reactid="6">DataCamp</span></a>
					<div data-reactid="7">
						<div class="__react_component_tooltip place-top type-dark "
							data-id="tooltip" data-reactid="8"></div>
						<div class="__react_component_tooltip place-top type-dark "
							data-id="tooltip" data-reactid="9"></div>
						<div class="__react_component_tooltip place-top type-dark "
							data-id="tooltip" data-reactid="10"></div>
						<div class="navigation--buttons-center"
							data-onboarding="button-center" data-reactid="11">
							<a class="navigation--left" data-tip="true"
								data-for="nav-tp-prev"
								href="/courses/free-introduction-to-r/chapter-1-intro-to-basics-1?ex=4"
								data-reactid="12"><i class="fa fa-chevron-left"
								data-reactid="13"></i></a><a
								class="navigation--outline navigation--middle" data-tip="true"
								data-for="nav-tp-outline" href="" data-reactid="14"><i
								class="fa fa-bars" data-reactid="15"></i><span data-reactid="16">Course
									Outline</span></a>
							<div style="display: inline-block;" data-tip="true"
								data-for="nav-tp-next" data-reactid="17">
								<a class="navigation--right"
									href="/courses/free-introduction-to-r/chapter-1-intro-to-basics-1?ex=6"
									data-reactid="18"><i class="fa fa-chevron-right"
									data-reactid="19"></i></a>
							</div>
						</div>
					</div>
					<div class="navigation--buttons-right" data-reactid="20">
						<div class="app-status" id="app-status" data-reactid="21">
							<div class="hcSlide-wrapper" data-reactid="22">
								<!-- react-empty: 23 -->
							</div>
						</div>
						<a style="position: relative;" href="" data-reactid="24"><div
								data-tip="true" data-for="tp-notifications" data-reactid="25">
								<div class="__react_component_tooltip place-top type-dark "
									data-id="tooltip" data-reactid="26"></div>
								<i class="fa fa-bell-o hover-icon" data-reactid="27"></i>
							</div></a><a style="position: relative;" href="" data-reactid="28"><div
								data-tip="true" data-for="tp-issue" data-reactid="29">
								<div class="__react_component_tooltip place-top type-dark "
									data-id="tooltip" data-reactid="30"></div>
								<span class="fa-stack fa-lg" data-reactid="31"><i
									class="fa fa-circle-thin fa-stack-2x" data-reactid="32"></i><i
									class="fa fa-exclamation fa-stack-1x" data-reactid="33"></i></span>
							</div></a>
					</div>
				</div>
			</section>
			<div class="exercise-area " data-reactid="34">
				<div data-reactid="35">
					<aside class="exercise--sidebar" style="width: 40%;"
						data-reactid="36">
						<div class="exercise--sidebar-content" data-reactid="37">
							<div class="exercise--assignment exercise--typography"
								data-onboarding="assignment" data-reactid="38">
								<h1 class="exercise--title" data-reactid="39">${course.courseName }</h1>
								<div class="skills-tooltip" data-reactid="40">
									<!-- react-text: 41 -->
									100
									<!-- /react-text -->
									<!-- react-text: 42 -->
									xp
									<!-- /react-text -->
								</div>
								<div data-reactid="43">
									<div data-reactid="44">
										<p>${course.courseNote }</p>

										<pre>
											<code>我也不知道这是啥
</code>
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
										<div class="exercise--instructions__content" data-reactid="50">
											<ul>
												<li>${course.examIntro }</li>
											</ul>
										</div>
									</div>
									<div class="sct-feedback" data-reactid="51">
										<div
											style="position: absolute; left: 0; top: 0; right: 0; bottom: 0; overflow: scroll; z-index: -1; visibility: hidden;"
											data-reactid="52">
											<div
												style="position: absolute; left: 0; top: 0; right: 0; bottom: 0; overflow: scroll; z-index: -1; visibility: hidden;"
												data-reactid="53">
												<div
													style="position: absolute; left: 0; top: 0; width: 0; height: 0;"
													data-reactid="54"></div>
											</div>
											<div
												style="position: absolute; left: 0; top: 0; right: 0; bottom: 0; overflow: scroll; z-index: -1; visibility: hidden;"
												data-reactid="55">
												<div
													style="position: absolute; left: 0; top: 0; width: 200%; height: 200%;"
													data-reactid="56"></div>
											</div>
										</div>
										<ul class="content--tab mono" data-reactid="57">
											<div style="display: inline-block;" data-tip="true"
												data-for="tp-hint" data-reactid="58">
												<div class="__react_component_tooltip place-top type-dark "
													data-id="tooltip" data-reactid="59"></div>
												<a class="exercise--show-hint" href="" data-reactid="60"><i
													class="fa fa-lightbulb-o" data-reactid="61"></i><span
													data-reactid="62"> <!-- react-text: 63 -->Take Hint<!-- /react-text -->
														<!-- react-text: 64 --> (-<!-- /react-text --> <!-- react-text: 65 -->30<!-- /react-text -->
														<!-- react-text: 66 -->xp)<!-- /react-text -->
												</span></a>
											</div>
										</ul>
									</div>
								</div>
							</div>
						</div>
					</aside>
					<!-- code area -->
					<section class="exercise--content" style="width: 60%; height: 100%"
						data-reactid="67">
						<div data-reactid="68">
							<article>
								<textarea name="code" id="code">${course.examPage}</textarea>

								<input name="courseId" id="courseId" type="hidden"
									value="${course.courseId }" />
								<script>
									var editor = CodeMirror.fromTextArea(
											document.getElementById("code"), {
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
												var obj = JSON
														.parse(xmlhttp.responseText);
												
												alert(ocpuJSON);
												document
														.getElementById("codeout").innerHTML = "";
											}
										};
										xmlhttp.open("POST",
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
							<textarea name="codeout" id="codeout" style="height: 200px"> </textarea>
						</div>
						<button id="submit" name="submit" value="submit"
							onclick="postCode()">Submit</button>
						<p id="codeout"></p>

					</section>
				</div>

				<!-- react-empty: 72 -->
				<div data-reactid="73"></div>
			</div>
			<!-- react-empty: 74 -->
			<!-- react-empty: 75 -->
		</div>
	</div>
	<script data-reactid="34">
		window.env = "production";
		window.release = "563";
	</script>
	<script type="text/x-mathjax-config" data-reactid="36">MathJax && MathJax.Hub && MathJax.Hub.Config && MathJax.Hub.Config({
              messageStyle: "none"
            });</script>

	<script type="text/javascript"
		src="../../rcampus/page/assets/js/vendors-bundle.js" data-reactid="38"></script>
	<script charset="UTF-8" data-reactid="31">
		try {
			Typekit.load();
		} catch (e) {
		}
	</script>
</body>
</html>