<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
  <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
  <%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE html>
<html>
<head>      
<title>Learn Python for Data Science - Online Course</title>
<base href="<%=basePath%>"></base>
<meta name="description" content="DataCamp's Intro to Python course teaches you how to use Python programming for data science with interactive video tutorials. Start learning Python today!" />
<link rel="canonical" href="https://www.datacamp.com/courses/intro-to-python-for-data-science" />
<link rel="author" href="https://plus.google.com/u/0/+Datacamp/" />
    <meta name="csrf-param" content="authenticity_token" />
<meta name="csrf-token" content="MV5WSuv/MoylvYTntydsZ3u2jh9L1GibU62KpLtx6wcUnWknJAnJmHYjMtcRJympiReB+2hWzDfa7FbL3oFvyQ==" />
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta name="fragment" content="!">
    <meta name="google-site-verification" content="ao3s4PdjisD2QsfTbldo7YJx7VX2QLkPEtlDpyFTjo8" />
    <link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
    <link href="https://fonts.googleapis.com/css?family=Lato:300,300i,400,400i,700,700i,800,800i" rel="stylesheet">
    <link rel="stylesheet"  href="page/assets/css/application_v2-cde77a159209f2b77774556d41a63ae4b340950bd91ad3b192fe8ce317c63d84.css" />
    <link rel="stylesheet" href="page/assets/css/bootstrap.min.css">
    <link rel="stylesheet" href="page/assets/css/font-awesome.css">
    <style type="text/css">
    .firstMenu{
    font-size: 24px;
    color: #4A515B;
    text-decoration:none
    }
    .secondMenu{
    font-size: 16px;
    color: #4A515B;
    text-decoration:none
    list-style-type:none
    }
    </style>
	<script src="../page/assets/js/jquery.js"></script>
	<script src="../page/assets/js/bootstrap.min.js"></script>
	<script type="text/javascript">
	$().ready(function(){
		console.log("success");
		$("#errorMessage").hide();
		$("#signin").validate({
		submitHandler:function(){
			$.ajax({
				type:"post",
				url:"./signin",
				dataType:"json",
				data:"email="+$("#email").val()+"&password="+$("#password").val(),
				success:function(json){
					if(json.flag==1){
						//$("signin").submit();	 
						location.href="./home";
					}
					else{
						console.log("failure");
						$("#errorMessage1").html("用户名或密码错误").show();
					}
				},
				error:function(){
					$("#errorMessage1").html("用户名或密码错误").show();
				}
			})
		}
		})
		});
	$('.firstMenu').click(function(ev){
		console.log("click");
		 $(".btn-more").children('.fa-angle-down').toggleClass('hidden');
		 $(".btn-more").children('.fa-angle-up').toggleClass('hidden');
		})
</script>
  </head>
  <body class="">
  <div style="background:#3366CC">
  <div>
  <div style="width:70%;margin:0px auto ">
  <div class="row">
  <div class="col-sm-9 col-lg-9 col-md-9" style="color:#FFFFFF">
  <h6>Free Course</h6>
  <h1>Intro to Pyhton for Data Science</h1>
  <a class="btn btn-primary btn-lg">Start Course For Free</a>
  </div>
  <div class="col-sm-3 col-lg-3 col-md-3">
  <img src="page/assets/img/setting.png" style="margin:auto">
  </div>
  </div>
  </div>
  </div>
  </div>
  <div style="width:60%;margin:auto">
  <h2>Course Description</h2>
  <p>Python is a general-purpose programming language that is becoming more and more popular for doing data science.
   Companies worldwide are using Python to harvest insights from their data and get a competitive edge.
    Unlike any other Python tutorial, this course focuses on Python specifically for data science. 
    In our Intro to Python class, you will learn about powerful ways to store and manipulate data as well as cool data science tools to start your own analyses. 
    Enter DataCamp’s online Python curriculum.</p>
    <c:forEach items="${chapterList}" var="chapter">
    <div style="border:1px solid #ccc;border-radius:10px">
    <div style="margin:20px">
    <div style="margin:10px">
    <span class="badge" style="background:#3366CC">${chapter.getChapterOrder()}</span><span style="font-size:24px"><b>${chapter.getChapterName()}</b></span> 
    </div>
    <p>${chapter.getChapterDescrbe()}</p>
     <a class="collapsed firstMenu" role="button" data-toggle="collapse" href="#${chapter.getChapterId()}" aria-expanded="false" aria-controls="collapseExample">
  <span><b>View Chapter Detail</b></span>
</a>
<span class="btn btn-more">
 <i class="fa fa-angle-down"></i>
 <i class="fa fa-angle-up hidden"></i>
</span>
<div id="${chapter.getChapterId()}" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingOne">
      <div class="list-group">
      <ul>
      <c:forEach items="${ccMap.get(chapter)}" var="course">
      <li class="secondMenu"><a href="page/courses/${course.getCourseId()}.html">${course.getCourseName()}</a></li>
      </c:forEach>
      </ul>
      </div>
      </div>
    </div>
    </div>
    </c:forEach>
    </div>
  </body>
</html>
