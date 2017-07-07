<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8" />
<title>Learn R, Python &amp; Data Science Online | DataCamp</title>
<meta name="description" content="Learn Data Science from the comfort of your browser, at your own pace with DataCamp's video tutorials &amp; coding challenges on R, Python, Statistics &amp; more." />
    <link rel="shortcut icon" type="image/x-icon" href="https://cdn.datacamp.com/main-app/assets/favicon-335cd0394b32102a39221d79e5fd7e51078e6d32a0c8aea59676a6869f84e9d8.ico" />
    <link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="./page/assets/css/bootstrap.min.css">
    <link rel="stylesheet" media="all" href="https://cdn.datacamp.com/main-app/assets/application-764c6b8f001bf0f157eebd5c442f20839378fdd8b59b04968c6b9210b7168d07.css" />
    <script src="./page/assets/js/jquery-2.1.1.min.js"></script>
	<script src="./page/assets/js/bootstrap.min.js"></script>
	<script src="http://static.runoob.com/assets/jquery-validation-1.14.0/dist/jquery.validate.min.js"></script>
	<script type="text/javascript">
	$().ready(function(){
		$("#errorMessage1").hide();
		$("#message").hide();
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
		});
		$("#signup").validate({
			submitHandler:function(){
				$.ajax({
					type:"post",
					url:"./signup",
					dataType:"json",
					data:"email="+$("#email0").val()+"&password="+$("#password0").val(),
					success:function(json){
						if(json.flag==1){
							//$("signin").submit();	
							$("#message").html("验证链接已发送到邮箱，请前去验证").show();
						}
						else{
							console.log("failure");
							$("#message").html("用户名或密码错误").show();
						}
					},
					error:function(){
						$("#message").html("用户名或密码错误").show();
					}
				})
			}
		});
	});
	</script>
  </head>
  <body class="">
    <ul class="offscreen-nav">
  <li class="top offscreen-nav-li"></li>
  <li class="offscreen-nav-li"><a href="./">Home</a></li>
  <li class="offscreen-nav-li"><a href="./courses/">Courses</a></li>
    <li class="offscreen-nav-li"><button type="button" class="btn btn-primary btn-lg" data-toggle="modal" data-target="#myModal">
  sign in
</button></li>
</ul>
    <div class="site-wrap">
      <div id="flash_messages">
</div>

      <div id="js-promo-coupon"></div>
      
<section class="bg bg-navigation bg-pattern" id="logged-out-header">
  <div class="container">

    <nav class="navbar clearfix">
  <div class="navbar--title">
    <a class="clearfix no-link-style" data-dc-track="" data-dc-referrer="main" href="/">
      <div class="logo"></div>
      <div class="logo-title">DataCamp</div>
</a>    <a class="small-link" href="/careers">We&#39;re hiring!</a>
  </div>
  <ul class="nav hidden-xs hidden-sm">
      <li><button type="button" class="btn btn-primary btn-lg" data-toggle="modal" data-target="#myModal">
  sign in
</button></li>
      <li class="spacer"></li>
    <li><a data-dc-track="" data-dc-referrer="main" href="./courses/">Courses</a></li>
    <li><a class="hidden-md" data-dc-track="" data-dc-referrer="main" href="./">Home</a></li>
  </ul>
  
  <div class="nav--resp-menu-toggle hidden-lg hidden-md">
    <a class="no-link-style" href=""><i class="fa fa-bars fa-2x"></i></a>
  </div>
</nav>

<!-- Modal -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
      </div>
      <div class="modal-body">
        <div role="tabpanel" class="tab-pane active" id="signin-form">
        <div class="home--sign-up card">
          <h4>Sign In</h4>
          <form class="new_user" id="signin" accept-charset="UTF-8" method="post">
          <input name="utf8" type="hidden" value="&#x2713;" />
           <input type="hidden" name="how" id="how" value="inline_form_home_page" />
            <fieldset>
              <i class="fa fa-envelope-o fa-lg inline-icon"></i>
              <input required="required" placeholder="Email" class="has-inline-icon" type="email" value="" name="user[email]" id="email" />
            </fieldset>
            <fieldset>
              <i class="fa fa-lock fa-lg inline-icon"></i>
              <input required="required" placeholder="Password" class="has-inline-icon" type="password" name="user[password]" id="password" />
            </fieldset>
            <input type="submit" name="commit" value="Sign In" class="btn btn-primary home-get-started ml0 mr0 mb0" data-disable-with="Get Started" />
            <div class="form-group">
        <div class="alert alert-danger" role="alert" id="errorMessage1"></div>
        </div>
</form>
        </div>
        </div>
       </div>
       </div>
       </div>
       </div>
<!-- !Modal -->
    <div class="row">
      <div class="col-sm-7">
        <div class="home--header">
          <hr>
          <h4 class="subhead">THE EASIEST WAY TO</h4>
          <h1>Learn Data Science Online</h1>
          <p>Master data analysis from the comfort of your browser, at your own pace, tailored to your needs and expertise. Whether you want to learn R, Python or Data Visualization, we want to help!</p>
        </div>
      </div>
      <div class="col-sm-5">
        <div class="home--sign-up card">
          <h4>Create Your Free Account</h4>
          <form class="new_user" id="signup" action="/users" accept-charset="UTF-8" method="post"><input name="utf8" type="hidden" value="&#x2713;" />
          <input type="hidden" name="authenticity_token" value="K3U1qm2Zw3un3xg2/1fLJkd/kVIDM3PP4h1pUkqGysKQTvBM5c6pUTcKp/IcY/Iqqxs5axxUHVnSKAIqqVyJFg==" />
            <input type="hidden" name="how" id="how" value="inline_form_home_page" />
            <fieldset>
              <i class="fa fa-envelope-o fa-lg inline-icon"></i>
              <input required="required" placeholder="Email" class="has-inline-icon" type="email" value="" name="user[email]" id="email0" />
            </fieldset>
            <fieldset>
              <i class="fa fa-lock fa-lg inline-icon"></i>
              <input required="required" placeholder="Password" class="has-inline-icon" type="password" name="user[password]" id="password0" />
            </fieldset>
            <input type="submit" name="commit" value="Get Started" class="btn btn-primary home-get-started ml0 mr0 mb0" data-disable-with="Get Started" />
             <div class="form-group">
        <div class="alert alert-danger" role="alert" id="message"></div>
        </div>
</form>        </div>
      </div>
    </div>

    <div class="row home--header-icons">
      <div class="hidden-xs col-sm-3"><img src="https://cdn.datacamp.com/main-app/assets/home_logged_out/home_1-c66e2d9a6a9a29f2e6b028f67f01b30cd907d3673357b8cf9cabf78f10d65400.png" alt="Home 1" /></div>
      <div class="hidden-xs col-sm-3"><img src="https://cdn.datacamp.com/main-app/assets/home_logged_out/home_2-7a41ab899acfc1e73fd391137ee7183de57eed4e27fdfe2c43171dd7d32f3e59.png" alt="Home 2" /></div>
      <div class="hidden-xs col-sm-3"><img src="https://cdn.datacamp.com/main-app/assets/home_logged_out/home_3-8fcc69106e9c6e2dab72595934db9f407ba2965b7ae568d0a55e821693407838.png" alt="Home 3" /></div>
      <div class="hidden-xs col-sm-3"><img src="https://cdn.datacamp.com/main-app/assets/home_logged_out/home_4-772f427e12522647a5a1c325b6d123bb0deb1dff2e3a5f76ca371f8808f8d563.png" alt="Home 4" /></div>
    </div>

  </div>
</section>
<section class="home--data-science">
  <div class="container text-center">
    <h2>Improve your skills - and your career</h2>
    <div class="row">
      <div class="col-sm-6 col-sm-offset-3">
        <p>No matter what industry youâre in, learning how to analyze and understand your data is critical. Thatâs why DataCamp provides you with the tools to learn the data science skills you need to start your own data projects.</p>
      </div>
    </div>
  </div>
  <div class="data-science-picture">
    <span class="gradient"></span>
    <img src="https://cdn.datacamp.com/main-app/assets/home_logged_out/home_data_science-555c6fd4d8790b9674c43168a9bf0c043f4be0f3da3e90923d0411c11fcfea73.svg" alt="Home data science" />
  </div>
</section>
    </div>
  </body>
</html>
