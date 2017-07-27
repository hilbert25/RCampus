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
 <meta name="csrf-param" content="authenticity_token" />
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta name="fragment" content="!">
    <meta name="google-site-verification" content="ao3s4PdjisD2QsfTbldo7YJx7VX2QLkPEtlDpyFTjo8" />
    <link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
    <link href="https://fonts.googleapis.com/css?family=Lato:300,300i,400,400i,700,700i,800,800i" rel="stylesheet">
    <link rel="stylesheet"  href="page/assets/css/application_v2-cde77a159209f2b77774556d41a63ae4b340950bd91ad3b192fe8ce317c63d84.css" />
    <link rel="stylesheet" href="page/assets/css/bootstrap.min.css">
    <link rel="stylesheet" href="page/assets/css/font-awesome.css">
   <script src="page/assets/js/jquery-2.1.1.min.js"></script>
	<script src="page/assets/js/bootstrap.min.js"></script>
	<script src="http://static.runoob.com/assets/jquery-validation-1.14.0/dist/jquery.validate.min.js"></script>
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
	<script type="text/javascript">
	$().ready(
			function() {
				$("#errorMessage").hide();
				$("#message").hide();
				$("#signin").validate(
						{
							submitHandler : function() {
								$.ajax({
									type : "post",
									url : "signin",
									dataType : "json",
									data : "email=" + $("#email").val()
											+ "&password="
											+ $("#password").val(),
									success : function(json) {
										if (json.flag == 1) {
											//$("signin").submit();	 
											location.href = "home";
										} else if (json.flag == 2) {
											location.href = "adminhome";
										} else {
											console.log("failure");
											$("#errorMessage")
													.html("用户名或密码错误").show();
										}
									},
									error : function() {
										$("#errorMessage").html("用户名或密码错误")
												.show();
									}
								})
							}
						});
				$("#signup").validate(
						{
							submitHandler : function() {
								$.ajax({
									type : "post",
									url : "signup",
									dataType : "json",
									data : "email=" + $("#emailup").val()
											+ "&password="
											+ $("#passwordup").val(),
									success : function(json) {
										if (json.flag == 1) {
											$("#message").html(
													"验证链接已发送到邮箱，请前去验证").show();
										} else {
											console.log("failure");
											$("#message").html("用户名或密码错误")
													.show();
										}
									},
									error : function() {
										$("#message").html("用户名或密码错误").show();
									}
								})
							}
						});
			});
	$('.firstMenu').click(function(ev){
		console.log("click");
		 $(".btn-more").children('.fa-angle-down').toggleClass('hidden');
		 $(".btn-more").children('.fa-angle-up').toggleClass('hidden');
		})
</script>
  </head>
  <body>
  <div>
  <div style="background:#3366CC;margin-bottom:-25px">
  <nav class="navbar navbar-default" role="navigation">
    <div class="container-fluid" style="height:100px;background:#3366CC">
    <div class="navbar-header" style="margin-left:100px">
        <a class="navbar-brand" style="color:#FFFFFF;font-size:250%" href="#">Rcampus</a>
    </div>
    <div>
        <ul class="nav navbar-nav navbar-right" style="margin-right:100px">
            <li><a style="font-size:150%;color:#FFFFFF" href="./">Home</a></li>
            <li><a style="font-size:150%;color:#FFFFFF" href="course/">Courses</a></li>
            <c:choose>
            <c:when test="${user!=null}">
            <li>
  <div class="dropdown">
  <a class="btn btn-default dropdown-toggle" style="color:#FFFFFF;margin-top:5px;font-size:150%;border:0px;background:#3366CC" 
  type="button" id="dropdownMenu1" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
    <c:out value="${user.getEmail()}"/>
    <span class="caret"></span>
  </a>
  <ul class="dropdown-menu" style="width:250px" aria-labelledby="dropdownMenu1">
    <li><div class="row">
    <div class="col-xs-6"><img class="img-circle" style="margin-left:30px" src="./page/assets/img/placeholder.png"></div>
    <div class="col-xs-6"><span style="margin-left:-10px">ayahui3@126.com</span></div>
    </div></li>
    <li><a href="../user/toSetUserInfo/userId=${user.getUserId()}" class="btn btn-primary btn-lg" role="button">Update Profile</a></li>
    <li>
    <div class="row">
    <div class="col-xs-6"><span style="margin-left:50px"><a href="../user/getUserById/userId=${user.getUserId()}">myProfile</a></span></div>
    <div class="col-xs-6"><span style="margin-left:30px"><a href="./logout">logout</a></span></div>
    </div>
    </li>
  </ul>
</div>
</li>
</c:when>
<c:otherwise>
<li><button type="button" class="btn btn-primary btn-lg navbar-btn" style="background:FF9900" data-toggle="modal" data-target="#signInModal">
  sign in
</button></li>
<li><button type="button" class="btn btn-primary btn-lg navbar-btn" style="background:FF9900" data-toggle="modal" data-target="#signUpModal">
  create free account
</button></li>
  </c:otherwise>
</c:choose>
 </ul>
    </div>
    </div>
    <!-- Modal -->
<div class="modal fade" id="signInModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
      </div>
      <div class="modal-body">
          <div style="text-align:center"><h4>Sign In</h4></div>
          <form class="new_user" id="signin"  accept-charset="UTF-8" method="post">
     <div class="form-group">
    <div class="input-group">
    <span class="input-group-addon" id="basic-addon1"><span class="glyphicon glyphicon-envelope" aria-hidden="true"></span></span> 
    <input type="email" class="form-control" id="email" placeholder="Email" required="required">
    </div>
    </div>
    <div class="form-group">
    <div class="input-group">
          <span class="input-group-addon" id="basic-addon1"><span class="glyphicon glyphicon-lock" aria-hidden="true"></span></span>
          <input type="password" class="form-control" id="password" placeholder="Password" required="required">  
            </div>
            </div>
            <div class="form-group">
            <input type="submit" name="commit" value="Sign In" class="btn btn-primary btn-lg" data-disable-with="Get Started" />
            </div>
          <div class="form-group">    
        <div class="alert alert-danger" role="alert" id="errorMessage"></div>
        </div>
</form>
        </div>
        </div>
       </div>
       </div>
       <div class="modal fade" id="signUpModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
      </div>
      <div class="modal-body">
          <div style="text-align:center"><h4>create your account</h4></div>
          <form class="new_user" id="signup" accept-charset="UTF-8" method="post">
     <div class="form-group">
    <div class="input-group">
    <span class="input-group-addon" id="basic-addon1"><span class="glyphicon glyphicon-envelope" aria-hidden="true"></span></span> 
    <input type="email" class="form-control" id="emailup" placeholder="Email" required="required">
    </div>
    </div>
    <div class="form-group">
    <div class="input-group">
          <span class="input-group-addon" id="basic-addon1"><span class="glyphicon glyphicon-lock" aria-hidden="true"></span></span>
          <input type="password" class="form-control" id="passwordup" placeholder="Password" required="required">  
            </div>
            </div>
            <div class="form-group">
            <input type="submit" name="commit" value="Sign up" class="btn btn-primary btn-lg" data-disable-with="Get Started" />
            </div>
          <div class="form-group">    
        <div class="alert alert-danger" role="alert" id="message"></div>
        </div>
</form>
        </div>
        </div>
       </div>
       </div>
<!-- !Modal -->
</nav>
  </div>
  <%@ include file="/page/courseContent.html"%>
  </div>
  </body>
</html>
