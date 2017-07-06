<%@ page language="java"  import="java.util.*"  pageEncoding="UTF-8"%>
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
<div class="overlay" style="display:none;"></div>
<nav class="navbar navbar-inverse navbar-fixed-top" id="sidebar-wrapper" role="navigation">
      <ul class="nav sidebar-nav">
          <li class="sidebar-brand">
              <a href="#">
                 Bootstrap 3
              </a>
          </li>
         <li class="dropdown">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                <i class="fa fa-fw fa-plus"></i>Chapter1<span class="caret"></span>
            </a>
            <ul class="dropdown-menu" role="menu">
              <li class="dropdown-header">这是第一章</li>
              <li><a href="#">course1</a></li>
              <li><a href="#">course2</a></li>
              <li><a href="#">course3</a></li>
              <li><a href="#">course4</a></li>
              <li><a href="#">course5</a></li>
            </ul>
          </li> 
          <li class="dropdown">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                <i class="fa fa-fw fa-plus"></i> Dropdown <span class="caret"></span>
            </a>
            <ul class="dropdown-menu" role="menu">
              <li class="dropdown-header">Dropdown heading</li>
              <li><a href="#">Action</a></li>
              <li><a href="#">Another action</a></li>
              <li><a href="#">Something else here</a></li>
              <li><a href="#">Separated link</a></li>
              <li><a href="#">One more separated link</a></li>
            </ul>
          </li>
      </ul>
</nav>
<div id="page-content-wrapper">
<button type="button" class="hamburger animated fadeInLeft is-closed" data-toggle="offcanvas">
            <span class="hamb-top"></span>
            <span class="hamb-middle"></span>
            <span class="hamb-bottom"></span>
          </button>     
</div>
</div>
<script type="text/javascript">
$(document).ready(function () {
	  var trigger = $('.hamburger'),
	      overlay = $('.overlay'),
	     isClosed = false;
	 
	    trigger.click(function () {
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
	   
	  $('[data-toggle="offcanvas"]').click(function () {
	        $('#wrapper').toggleClass('toggled');
	  });  
	});
</script>
</body>
</html>