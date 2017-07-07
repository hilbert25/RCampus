<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<script type="text/javascript">window.NREUM||(NREUM={});NREUM.info={"beacon":"bam.nr-data.net","errorBeacon":"bam.nr-data.net","licenseKey":"4795905ee2","applicationID":"7334808","transactionName":"JlkNEEQLVA0DE0wTEABEEEtXB1sOEw8XORAAQhcNWANL","queueTime":0,"applicationTime":105,"agent":""}</script>
<script type="text/javascript">window.NREUM||(NREUM={}),__nr_require=function(e,n,t){function r(t){if(!n[t]){var o=n[t]={exports:{}};e[t][0].call(o.exports,function(n){var o=e[t][1][n];return r(o||n)},o,o.exports)}return n[t].exports}if("function"==typeof __nr_require)return __nr_require;for(var o=0;o<t.length;o++)r(t[o]);return r}({1:[function(e,n,t){function r(){}function o(e,n,t){return function(){return i(e,[c.now()].concat(u(arguments)),n?null:this,t),n?void 0:this}}var i=e("handle"),a=e(2),u=e(3),f=e("ee").get("tracer"),c=e("loader"),s=NREUM;"undefined"==typeof window.newrelic&&(newrelic=s);var p=["setPageViewName","setCustomAttribute","setErrorHandler","finished","addToTrace","inlineHit","addRelease"],d="api-",l=d+"ixn-";a(p,function(e,n){s[n]=o(d+n,!0,"api")}),s.addPageAction=o(d+"addPageAction",!0),s.setCurrentRouteName=o(d+"routeName",!0),n.exports=newrelic,s.interaction=function(){return(new r).get()};var m=r.prototype={createTracer:function(e,n){var t={},r=this,o="function"==typeof n;return i(l+"tracer",[c.now(),e,t],r),function(){if(f.emit((o?"":"no-")+"fn-start",[c.now(),r,o],t),o)try{return n.apply(this,arguments)}finally{f.emit("fn-end",[c.now()],t)}}}};a("setName,setAttribute,save,ignore,onEnd,getContext,end,get".split(","),function(e,n){m[n]=o(l+n)}),newrelic.noticeError=function(e){"string"==typeof e&&(e=new Error(e)),i("err",[e,c.now()])}},{}],2:[function(e,n,t){function r(e,n){var t=[],r="",i=0;for(r in e)o.call(e,r)&&(t[i]=n(r,e[r]),i+=1);return t}var o=Object.prototype.hasOwnProperty;n.exports=r},{}],3:[function(e,n,t){function r(e,n,t){n||(n=0),"undefined"==typeof t&&(t=e?e.length:0);for(var r=-1,o=t-n||0,i=Array(o<0?0:o);++r<o;)i[r]=e[n+r];return i}n.exports=r},{}],4:[function(e,n,t){n.exports={exists:"undefined"!=typeof window.performance&&window.performance.timing&&"undefined"!=typeof window.performance.timing.navigationStart}},{}],ee:[function(e,n,t){function r(){}function o(e){function n(e){return e&&e instanceof r?e:e?f(e,u,i):i()}function t(t,r,o,i){if(!d.aborted||i){e&&e(t,r,o);for(var a=n(o),u=m(t),f=u.length,c=0;c<f;c++)u[c].apply(a,r);var p=s[y[t]];return p&&p.push([b,t,r,a]),a}}function l(e,n){v[e]=m(e).concat(n)}function m(e){return v[e]||[]}function w(e){return p[e]=p[e]||o(t)}function g(e,n){c(e,function(e,t){n=n||"feature",y[t]=n,n in s||(s[n]=[])})}var v={},y={},b={on:l,emit:t,get:w,listeners:m,context:n,buffer:g,abort:a,aborted:!1};return b}function i(){return new r}function a(){(s.api||s.feature)&&(d.aborted=!0,s=d.backlog={})}var u="nr@context",f=e("gos"),c=e(2),s={},p={},d=n.exports=o();d.backlog=s},{}],gos:[function(e,n,t){function r(e,n,t){if(o.call(e,n))return e[n];var r=t();if(Object.defineProperty&&Object.keys)try{return Object.defineProperty(e,n,{value:r,writable:!0,enumerable:!1}),r}catch(i){}return e[n]=r,r}var o=Object.prototype.hasOwnProperty;n.exports=r},{}],handle:[function(e,n,t){function r(e,n,t,r){o.buffer([e],r),o.emit(e,n,t)}var o=e("ee").get("handle");n.exports=r,r.ee=o},{}],id:[function(e,n,t){function r(e){var n=typeof e;return!e||"object"!==n&&"function"!==n?-1:e===window?0:a(e,i,function(){return o++})}var o=1,i="nr@id",a=e("gos");n.exports=r},{}],loader:[function(e,n,t){function r(){if(!x++){var e=h.info=NREUM.info,n=d.getElementsByTagName("script")[0];if(setTimeout(s.abort,3e4),!(e&&e.licenseKey&&e.applicationID&&n))return s.abort();c(y,function(n,t){e[n]||(e[n]=t)}),f("mark",["onload",a()+h.offset],null,"api");var t=d.createElement("script");t.src="https://"+e.agent,n.parentNode.insertBefore(t,n)}}function o(){"complete"===d.readyState&&i()}function i(){f("mark",["domContent",a()+h.offset],null,"api")}function a(){return E.exists&&performance.now?Math.round(performance.now()):(u=Math.max((new Date).getTime(),u))-h.offset}var u=(new Date).getTime(),f=e("handle"),c=e(2),s=e("ee"),p=window,d=p.document,l="addEventListener",m="attachEvent",w=p.XMLHttpRequest,g=w&&w.prototype;NREUM.o={ST:setTimeout,SI:p.setImmediate,CT:clearTimeout,XHR:w,REQ:p.Request,EV:p.Event,PR:p.Promise,MO:p.MutationObserver};var v=""+location,y={beacon:"bam.nr-data.net",errorBeacon:"bam.nr-data.net",agent:"js-agent.newrelic.com/nr-1039.min.js"},b=w&&g&&g[l]&&!/CriOS/.test(navigator.userAgent),h=n.exports={offset:u,now:a,origin:v,features:{},xhrWrappable:b};e(1),d[l]?(d[l]("DOMContentLoaded",i,!1),p[l]("load",r,!1)):(d[m]("onreadystatechange",o),p[m]("onload",r)),f("mark",["firstbyte",u],null,"api");var x=0,E=e(4)},{}]},{},["loader"]);</script>
<title>Edit Profile</title>
<meta name="description" content="Edit profile of" />
<link rel="canonical"
	href="https://www.datacamp.com/profile/account_settings" />
<link rel="author" href="https://plus.google.com/u/0/+Datacamp/" />
<meta property="og:image"
	content="https://www.datacamp.com/datacamp.png" />
<meta property="og:image:width" content="1200" />
<meta property="og:image:height" content="630" />
<meta property="og:title" content="Edit Profile" />

<link rel="stylesheet" media="all"
	href="https://cdn.datacamp.com/main-app/assets/application-764c6b8f001bf0f157eebd5c442f20839378fdd8b59b04968c6b9210b7168d07.css" />
<link rel="shortcut icon" type="image/x-icon"
	href="https://cdn.datacamp.com/main-app/assets/favicon-335cd0394b32102a39221d79e5fd7e51078e6d32a0c8aea59676a6869f84e9d8.ico" />
<meta name="csrf-param" content="authenticity_token" />
<meta name="csrf-token"
	content="TYRkUfoJr6IzilULK7jKr5PsvYN/qpa+uYLVR5I0/kscGu2FDx/jakSo0qEjETZblmaYkD3EAkyWHekWjSjEHQ==" />

<base href="/">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1">
<meta name="fragment" content="!">

<link rel="stylesheet"
	href="//maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">

<script>
  (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
  (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
  m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
  })(window,document,'script','https://www.google-analytics.com/analytics.js','ga');
  ga('create', 'UA-39297847-1', 'auto');
  ga('require', 'GTM-TVZ7BNH');
</script>

<script type="text/javascript">
    (function(d) {
      var config = {
        kitId: 'pjc4zfc',
        scriptTimeout: 3000,
        async: true
      },
      h=d.documentElement,t=setTimeout(function(){h.className=h.className.replace(/\bwf-loading\b/g,"")+" wf-inactive";},config.scriptTimeout),tk=d.createElement("script"),f=false,s=d.getElementsByTagName("script")[0],a;h.className+=" wf-loading";tk.src='https://use.typekit.net/'+config.kitId+'.js';tk.async=true;tk.onload=tk.onreadystatechange=function(){a=this.readyState;if(f||a&&a!="complete"&&a!="loaded")return;f=true;clearTimeout(t);try{Typekit.load(config)}catch(e){}};s.parentNode.insertBefore(tk,s)
    })(document);
    </script>
<script>
(function(h,o,t,j,a,r){
  h.hj=h.hj||function(){(h.hj.q=h.hj.q||[]).push(arguments)};
  h._hjSettings={hjid:486839,hjsv:5};
  a=o.getElementsByTagName('head')[0];
  r=o.createElement('script');r.async=1;
  r.src=t+h._hjSettings.hjid+j+h._hjSettings.hjsv;
  a.appendChild(r);
})(window,document,'//static.hotjar.com/c/hotjar-','.js?sv=');
</script>

</head>
<body class="">
	<ul class="offscreen-nav">
		<li class="top offscreen-nav-li"></li>
		<li class="offscreen-nav-li"><a href="/">Dashboard</a></li>
		<li class="offscreen-nav-li"><a href="/courses">Courses</a></li>
		<li class="offscreen-nav-li"><a href="/tracks">Tracks</a></li>
		<li class="offscreen-nav-li"><a href="/pricing">Pricing</a></li>
		<li class="offscreen-nav-li"><a class="slack-link"
			id="slack-link-mobile" target="_blank" title="Join Slack"
			href="/slack/join"> Join Slack <img height="17"
				src="https://cdn.datacamp.com/main-app/assets/home_logged_in/slack-logo-mark-28502b3886496c3ca29eac97fd33615170181e0f0790563c622529210a1df941.svg"
				alt="Slack logo mark" />
		</a></li>
		<li class="offscreen-nav-li"><a href="/groups/mine">Groups</a></li>
		<li class="offscreen-nav-li"><a href="/community/">Community</a></li>
		<li class="offscreen-nav-li"><a
			href="/rcampus/user/toSetUserInfo?userId=${user.userId }"> My
				Account&nbsp;<small>${user.email}</small>
		</a></li>
		<li class="offscreen-nav-li"><a rel="nofollow"
			data-method="delete" href="/users/sign_out">Logout</a></li>
	</ul>

	<div class="site-wrap">
		<c:choose>
			<c:when test="${status==true}">
				<div id="flash_messages">
					<div class="flash flash-success">
						<strong>Profile updated successfully.</strong>
						<div class="close"></div>
					</div>
				</div>
			</c:when>
		</c:choose>

		<div class="site-wrap">
			<div id="flash_messages"></div>

			<div id="js-promo-coupon"></div>

			<section class="bg bg-navigation">
				<div class="container">
					<nav class="navbar clearfix">
						<div class="navbar--title">
							<a class="clearfix no-link-style" data-dc-track=""
								data-dc-referrer="main" href="/">
								<div class="logo"></div>
								<div class="logo-title">DataCamp</div>
							</a> 
						</div>



						<a class="navbar__slack hidden-xs hidden-sm" id="slack-link"
							target="_blank" title="Join Slack" href="/slack/join"> <img
							height="20"
							src="https://cdn.datacamp.com/main-app/assets/home_logged_in/slack-logo-mark-28502b3886496c3ca29eac97fd33615170181e0f0790563c622529210a1df941.svg"
							alt="Slack logo mark" />
						</a>
						<ul class="nav hidden-xs hidden-sm">
							<li class="dropdown"><a class="dropdown--trigger with-arrow"
								data-toggle="dropdown" href=""> ${user.email} </a>
								<div class="dropdown--body white">
									<div class="navbar--user">
										<img alt="" width="50" height="50"
											src="https://cdn.datacamp.com/main-app/assets/placeholder-c6e992ae2135cef49afd126fcd3bda30d1fa33284a9acafa45d6c8272f360005.png" />
										<h5>${user.email}</h5>
										<p>${user.email}</p>
									</div>
									<a class="btn btn-primary m0 fw" data-dc-track=""
										data-dc-referrer="main" href="/pricing">Upgrade Account</a>
									<div class="navbar--user-links">
										<a href="rcampus/user/getUserById?userId=${user.userId}">My
											Profile</a> <span class="spacer"></span> <a rel="nofollow"
											data-method="delete" href="/users/sign_out">Logout</a>
									</div>
								</div></li>

							<li class="dropdown notification-dropdown"><a
								class="dropdown-trigger" data-toggle="dropdown" href=""> <i
									class="fa fa-bell-o js-notifications-open"></i>
							</a>
								<div id='notificationsDropdownList'
									class="dropdown--body white notifications-dropdown__body">
								</div></li>

							<li><a data-dc-track="" data-dc-referrer="main"
								href="/community/">Community</a></li>
							<li><a data-dc-track="" data-dc-referrer="main"
								href="/groups/business">Business</a></li>
							<li><a data-dc-track="" data-dc-referrer="main"
								href="/pricing">Pricing</a></li>
							<li><a data-dc-track="" data-dc-referrer="main"
								href="/tracks">Tracks</a></li>
							<li><a data-dc-track="" data-dc-referrer="main"
								href="/courses">Courses</a></li>
							<li><a class="hidden-md" data-dc-track=""
								data-dc-referrer="main" href="/">Dashboard</a></li>

						</ul>
						<div class="nav--resp-menu-toggle hidden-lg hidden-md">
							<a class="no-link-style" href=""><i class="fa fa-bars fa-2x"></i></a>
						</div>
					</nav>

				</div>
			</section>

			<div class="container">
				<div class="row account-settings">
					<div class="col-sm-9 vertical-nav-border">
						<div class="tab-content">
							<div role="tabpanel" class="tab-pane active" id="general">
								<h3>General Information</h3>
								<hr>
								<form class="edit_user" id="edit_user"
									enctype="multipart/form-data"
									action="/rcampus/user/setUserInfo?userId=${user.userId}"
									accept-charset="UTF-8" method="post">
									<div class="row">
										<div class="col-sm-6">
											<fieldset>
												<label for="user_name"></label> UserName<input
													placeholder="User Name" required="required" type="text"
													name="user_name" id="user_name" value="${user.userName}" />
											</fieldset>
											<fieldset>
												<label for="user_email">Email</label> <input
													placeholder="Email" required="required" type="email"
													value="${user.email}" name="user_email" id="user_email" />
											</fieldset>
										</div>
										<div class="col-sm-6">
											<fieldset>
												<label for="user_head"></label>
												<div class="avatar-selector">
													<img class="image-editable image-rounded" width="150"
														height="150" src=${user.photo } /> <input
														class="clickable" type="file" name="user_head"
														id="user_head" />
												</div>
											</fieldset>
										</div>
									</div>
									<div class="row">
										<div class="col-sm-8 change-password">
											<a href="/users/edit">Change Password</a>
										</div>
										<div class="col-sm-4">
											<input type="submit" name="commit" value="Save Changes"
												class="btn btn-primary m0" data-disable-with="Save Changes" />
										</div>
									</div>

									<fieldset>
										<input name="user[receive_mail]" type="hidden" value="0" /><input
											type="checkbox" value="1" checked="checked"
											name="user[receive_mail]" id="user_receive_mail" /> <label
											for="user_receive_mail">I want to receive email from
											DataCamp</label>
									</fieldset>

								</form>
							</div>
							<div role="tabpanel" class="tab-pane" id="social">
								<h3>Social</h3>
								<hr>

								<h5>Unlinked Accounts</h5>
								<div class="row">
									<div class="col-sm-4">
										<a class="btn btn-secondary ml0 fw btn-linkedin"
											href="/users/auth/linkedin"> <i
											class="fa fa-linkedin-square"></i> Link LinkedIn
										</a>
									</div>
								</div>
								<div class="row">
									<div class="col-sm-4">
										<a class="btn btn-secondary ml0 fw btn-facebook"
											href="/users/auth/facebook"> <i class="fa fa-facebook"></i>
											Link Facebook
										</a>
									</div>
								</div>
								<div class="row">
									<div class="col-sm-4">
										<a class="btn btn-secondary ml0 fw btn-google-plus"
											href="/users/auth/google_oauth2"> <i
											class="fa fa-google-plus"></i> Link Google +
										</a>
									</div>
								</div>
								<div class="row">
									<div class="col-sm-4">
										<a class="btn btn-secondary ml0 fw btn-github"
											href="/users/auth/github"> <i class="fa fa-github"></i>
											Link GitHub
										</a>
									</div>
								</div>
							</div>
							<div role="tabpanel" class="tab-pane" id="payments">

								<h3>Payments</h3>
								<hr>
								<h5>Payment Method(s)</h5>
								<div class="row">
									<div class="col-xs-12 col-sm-6 col-md-5 col-lg-4">
										<a class="btn btn-primary ml0 mr0 fw"
											href="/profile/add_payment_method">Add Payment Method</a>
									</div>
								</div>

								<h5>Order History</h5>
							</div>

							<div role="tabpanel" class="tab-pane" id="advanced">
								<h3>Advanced</h3>
								<hr>
								<h5>Deactivate account?</h5>
								<div class="row">
									<div class="col-xs-12 col-sm-6 col-md-5 col-lg-4">
										<form class="button_to" method="post" action="/users">
											<input type="hidden" name="_method" value="delete" /><input
												data-confirm="Are you sure you want to deactivate your Account?"
												class="btn btn-danger m0" type="submit"
												value="Deactivate account" /><input type="hidden"
												name="authenticity_token"
												value="6AfLJ3tm/2ZSCYMfzFVGL+kMcUSagZIbqyRpoBkd+K+5mULzjnCzriUrBLXE/Lrb7IZUV9jvBumEu1XxBgHC+Q==" />
										</form>
									</div>
								</div>
							</div>

						</div>
					</div>
				</div>
			</div>
		</div>

		<div class="bottom-navigation">
			<div class="container clearfix">
				<div class="navbar--title">
					<a class="clearfix no-link-style" href="/">
						<div class="logo"></div>
						<div class="logo-title">DataCamp</div>
					</a> 
				</div>
				<div class="footer-links-container hidden-xs">
					<ul class="footer-links-list">
						<li>DataCamp</li>
						<li><a href="/about">About</a></li>
						<li><a href="/pricing">Pricing</a></li>
						<li><a href="/brand">Press</a></li>
						<li><a href="/careers">Jobs</a></li>
					</ul>
					<ul class="footer-links-list">
						<li>Resources</li>
						<li><a href="/courses">View All Courses</a></li>
						<li><a href="/community/">Community</a></li>
						<li><a href="/groups/business">DataCamp for Groups</a></li>
						<li><a href="/teach/">Become a Teacher</a></li>
						<li><a target="_blank" href="http://www.r-fiddle.org/">R
								Fiddle</a></li>
						<li><a target="_blank" href="https://www.rdocumentation.org">RDocumentation</a></li>
					</ul>
					<ul class="footer-links-list">
						<li>Support</li>
						<li><a href="/contact-us">Contact Us</a></li>
						<li><a href="/terms-of-use">Terms of Use</a></li>
						<li><a href="/privacy-policy">Privacy Policy</a></li>
					</ul>
				</div>
				<div class="social-icons clearfix">
					<a target="_blank" href="https://twitter.com/datacamp"><i
						class="fa fa-twitter fa-2x"></i></a> <a target="_blank"
						href="https://www.facebook.com/pages/DataCamp/726282547396228"><i
						class="fa fa-facebook fa-2x"></i></a> <a target="_blank"
						href="https://www.linkedin.com/company/datamind-org"><i
						class="fa fa-linkedin fa-2x"></i></a> <a target="_blank"
						href="https://www.youtube.com/channel/UC79Gv3mYp6zKiSwYemEik9A"><i
						class="fa fa-youtube fa-2x"></i></a>
				</div>
			</div>
		</div>
	</div>

	<script src="https://js.braintreegateway.com/v2/braintree.js"></script>
	<script src="https://js.braintreegateway.com/v1/braintree-data.js"></script>
	<script
		src="https://cdn.datacamp.com/main-app/assets/application-9ae4e5dc43cf000c36896200ab20dff5fbf6ab8b59016251f65c5df85f9f0a6f.js"></script>
	<script src="https://cdn.datacamp.com/datacamp-analytics-1.1.7.min.js"></script>
	<script type="text/javascript">
      window.DC = window.DC || {}
      window.DC.analytics = new DCAnalytics({"api_domain": "https://www.datacamp.com", "collection": "main", "autotrack_utm": true, "autotrack_data_attr": true });
    </script>

	<script type="text/javascript" src="https://js.stripe.com/v2/"></script>
	<script type="text/javascript">Stripe.setPublishableKey('pk_live_4bvFOkHgRQgjsTiRzCWM0yLk');</script>
	<script type="text/javascript">
  dataLayer = [
    {
      'user_id': '851469',
      'email':   'xjtuhht@163.com'
    }
  ];
</script>

	<!-- Google Tag Manager -->
	<script>(function(w,d,s,l,i){w[l]=w[l]||[];w[l].push({'gtm.start':
new Date().getTime(),event:'gtm.js'});var f=d.getElementsByTagName(s)[0],
j=d.createElement(s),dl=l!='dataLayer'?'&l='+l:'';j.async=true;j.src=
'https://www.googletagmanager.com/gtm.js?id='+i+dl;f.parentNode.insertBefore(j,f);
})(window,document,'script','dataLayer','GTM-TGGWB2P');</script>
	<!-- End Google Tag Manager -->

	<script type="text/javascript">
  (function(i,s,o,g,r,a,m){i['ProfitWellObject']=r;i[r]=i[r]||function(){
    (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
  m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m);
  })(window,document,'script','https://dna8twue3dlxq.cloudfront.net/js/profitwell.js','profitwell');

  profitwell('auth_token', 'f5649c7f778dc2ce6e1e59b28fa7e8d0');
    profitwell('user_email', 'xjtuhht@163.com');
</script>



	<script type="text/javascript">
    $(document).ready(airbrake.call(function(){
      var options = JSON.parse('{}');
      DC.analytics.track_event_from("main", "Visit Account Settings", "visit", options);
    }));
  </script>

	<script src="//static.tapfiliate.com/tapfiliate.js"
		type="text/javascript" async></script>
	<script type="text/javascript">
  window['TapfiliateObject'] = i = 'tap';
  window[i] = window[i] || function () {
      (window[i].q = window[i].q || []).push(arguments);
  };

  tap('create', '1802-65ce86');
  tap('detectClick', function() {
		Cookies.set("tap_vid", tap.vid, { expires: 36500 });
	});
</script>

	<script>
        $(function(){
          $.get('/api/templates/promo_coupon', { referrer_action: "account_settings" }).done(function(html){
            $('#js-promo-coupon').html(html);
            initializePromoClock('#js-promo-coupon .js-promo-coupon-counter');
          })
        })
    </script>
</body>
</html>
