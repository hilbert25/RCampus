<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <link rel="stylesheet" href="./page/assets/css/bootstrap.min.css"> 
	<link href="./page/assets/css/bootstrap-responsive.min.css" rel="stylesheet" type="text/css"/>
	<link href="./page/assets/css/font-awesome.css" rel="stylesheet" type="text/css"/>
	<script src="./page/assets/js/jquery-2.1.1.min.js"></script>
	<script src="./page/assets/js/bootstrap.min.js"></script>
	<script src="http://static.runoob.com/assets/jquery-validation-1.14.0/dist/jquery.validate.min.js"></script>
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
</script>
<body>
<nav class="navbar navbar-default" role="navigation">
    <div class="container-fluid" style="height:100px;background:#3366CC">
    <div class="navbar-header" style="margin-left:100px">
        <a class="navbar-brand" style="color:#FFFFFF;font-size:250%" href="#">Rcampus</a>
    </div>
    <div>
        <ul class="nav navbar-nav navbar-right" style="margin-right:100px">
            <li><a style="font-size:150%;color:#FFFFFF" href="/">Home</a></li>
            <li><a style="font-size:150%;color:#FFFFFF" href="./courses/">Courses</a></li>
            <li>
   <c:if test="${user!=null}">
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
    <li><a href="#" class="btn btn-primary btn-lg" role="button">Update Profile</a></li>
    <li>
    <div class="row">
    <div class="col-xs-6"><span style="margin-left:50px"><a href="#">myProfile</a></span></div>
    <div class="col-xs-6"><span style="margin-left:30px"><a href="./logout">logout</a></span></div>
    </div>
    </li>
  </ul>
</div>
</c:if>
</li>
<c:if test="{user==null}">
<li><button type="button" class="btn btn-primary btn-lg navbar-btn" style="background:FF9900" data-toggle="modal" data-target="#myModal">
  sign in
</button></li>
<li><button type="button" class="btn btn-primary btn-lg navbar-btn" style="background:FF9900" data-toggle="modal" data-target="#myModal">
  create free account
</button></li>
</c:if>
 </ul>
    </div>
    </div>
    <!-- Modal -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
      </div>
      <div class="modal-body">
          <div style="text-align:center"><h4>Sign In</h4></div>
          <form class="new_user" id="signin" action="/users" accept-charset="UTF-8" method="post">
     <div class="form-group">
    <div class="input-group">
    <span class="input-group-addon" id="basic-addon1"><span class="glyphicon glyphicon-envelope" aria-hidden="true"></span></span> 
    <input type="email" class="form-control" id="email" placeholder="Email">
    </div>
    </div>
    <div class="form-group">
    <div class="input-group">
          <span class="input-group-addon" id="basic-addon1"><span class="glyphicon glyphicon-lock" aria-hidden="true"></span></span>
          <input type="password" class="form-control" id="password" placeholder="Password">  
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
<!-- !Modal -->
</nav>
<div style="width:20%;margin:auto">
<ul id="myTab" class="nav nav-tabs">
	<li class="active">
		<a href="#inprogress" data-toggle="tab">
			 IN PROGRESS
		</a>
	</li>
	<li><a href="#completed" data-toggle="tab">COMPLETED</a></li>
</ul>
</div>
<div id="myTabContent" class="tab-content">
	<div class="tab-pane fade in active" id="inprogress">
	<div style="width:80%;margin:auto;margin-top:30px">
<div class="row">
  <div class="col-lg-3 col-md-4">
    <div class="thumbnail">
      <div class="caption">
      <div style="margin-bottom:50px">
        <h3>Introduction to R</h3>
        </div>
        <p>Master the basis of data analysis by
        manipulating common data structures such as
         vectors,matrics and data frames</p>
        <p>
        <a href="./courseIntro" class="btn btn-primary" style="background:#FF6600" role="button">Learn More</a> 
        </p>
      </div>
    </div>
  </div>
  </div>
</div>
	</div>
	<div class="tab-pane fade" id="completed">
	</div>
</div>
 
</body>
</html> 