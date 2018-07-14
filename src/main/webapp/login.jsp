<%@ page session="false" language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>





<!DOCTYPE html>

<html>
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"> 
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0,minimum-scale=1.0,user-scalable=0" />
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0"> 
	<title>易起云登录界面</title>
	<link href="css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
    <link href="css/default.css" rel="stylesheet" type="text/css" />
	<!--必要样式-->
    <link href="css/styles.css" rel="stylesheet" type="text/css" />
    <link href="css/demo.css" rel="stylesheet" type="text/css" />
    <link href="css/loaders.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<div class="logohead clearfix">
		<div class="pic">
			<img src="img/logo1.png" />
		</div>
		<div class="line"></div>
		<div class="text">
			<p class="hanyu">后台管理登陆</p>
			<p class="yinyu">LOGIN MANAGEMENT</p>
		</div>
	</div>
	<form id="logFm" action="${pageContext.request.contextPath}/shiro-getLogin.action" method="post">
	<div class='login'>
		<!--账号登陆-->
		<div class="login_1">
			<div class='login_fields'>
	  	<p class="wenzi">用户名</p>
	    <div class='login_fields__user border'>
	      <div class='icon'>
	        <img alt="" src='img/user.png'>
	      </div>
	      <input name="logName" placeholder='用户名' type='text' autocomplete="off">
	        <!--<div class='validation'>
	          <img alt="" src='img/user.png'>
	        </div>-->
	    </div>
	    <p class="wenzi">密码</p>
	    <div class='login_fields__password border'>
	      <div class='icon'>
	        <img alt="" src='img/password.png'>
	      </div>
	      <input name="logPass" placeholder='密码' type='password' autocomplete="off">
	      <!--<div class='validation'>
	        <img alt="" src='img/password.png'>
	      </div>-->
	    </div>
	    <p class="wenzi">验证码</p>
	    <div class="codeList clearfix">
	    	<div class='login_fields__password login_fields__code border'>
	      		<div class='icon'>
	        		<img alt="" src='img/code.png'>
	      		</div>
	      		<input name="logYzm" placeholder='验证码' maxlength="4" type='text'>
	      		<!--<div class='validation' style="opacity: 1; right: -5px;top: -3px;">-->
	      	</div>
	      	<div class="yanzhengma">
	      		<div class="J_codeimg">
	      			<div>
	            		<a href="javascript:;" class="showcode" onclick="changImg()">
	            			<img id ="servletImg" src="${pageContext.request.contextPath}/code/shiro-getCode.action">
	            		</a>
	        		</div>  
	      		</div>
	      	</div>
	    </div>
	    </div>
	    <div class='login_fields__submit'>
	      <input type='submit' value='登录'>
	    </div>
		</div>
	  </div>
	  </form>
	<p class="Itext">易起云后台管理系统<span>(苏州聚源创世信息科技有限公司技术支持)</span></p>
	<div class='success'></div>
	<div class='authent'>
	  <div class="loader" style="height: 80px;width: 80px;">
        <div class="loader-inner ball-clip-rotate-multiple">
            <div></div>
            <div></div>
            <div></div>
        </div>
        </div>
	  <p style="width: 80px">认证中...</p>
	</div>
	<div class="OverWindows"></div>
    <link href="layui/css/layui.css" rel="stylesheet" type="text/css" />
	<script src="js/jquery-3.2.1.min.js"></script>
	<script type="text/javascript" src="js/jquery-ui.min.js"></script>
	<script type="text/javascript" src='js/stopExecutionOnTimeout.js?t=1'></script>
    <script src="layui/layui.js" type="text/javascript"></script>
    <script src="js/Treatment.js" type="text/javascript"></script>
    <script src="js/jquery.mockjax.js" type="text/javascript"></script>
	<script type="text/javascript">
		
//		===
		var canGetCookie = 0;//是否支持存储Cookie 0 不支持 1 支持
		var ajaxmockjax = 1;//是否启用虚拟Ajax的请求响 0 不启用  1 启用
	    
	    $(document).keypress(function (e) {
	        // 回车键事件  
	        if (e.which == 13) {
	            $('input[type="button"]').click();
	        }
	    });
	    $('input[name="logPass"]').focus(function () {
//	        $(this).attr('type', 'password');
	    });
	    $('input[type="text"],input[type="password"]').focus(function () {
	        $(this).prev().animate({ 'opacity': '1' }, 200);
	         $(this).parents('.border').css("border","3px solid #7ac4e7");
	    });
	    $('input[type="text"],input[type="password"]').blur(function () {
	        $(this).prev().animate({ 'opacity': '.5' }, 200);
	        $(this).parents('.border').css("border","3px solid #e5e5e5");
	    });
	    $('input[name="logName"],input[name="logPass"]').keyup(function () {
	        var Len = $(this).val().length;
	        if (!$(this).val() == '' && Len >= 5) {
	            $(this).next().animate({
	                'opacity': '1',
	                'right': '30'
	            }, 200);
	        } else {
	            $(this).next().animate({
	                'opacity': '0',
	                'right': '20'
	            }, 200);
	        }
	    });
	    
	    var open = 0;
	    layui.use('layer', function () {
	        //非空验证
	        $('input[type="submit"]').click(function () {
	            var login = $('input[name="logName"]').val();
	            var pwd = $('input[name="logPass"]').val();
	            var code = $('input[name="logYzm"]').val();
	            if (login == '') {
	                ErroAlert('请输入您的账号');
	                return false;
	            } else if (pwd == '') {
	                ErroAlert('请输入密码');
	                return false;
	            } else if (code == '') {
	                ErroAlert('输入验证码');
	                return false;
	            } else if (code.length != 4){
	            	ErroAlert('验证码输入有误');
	            	return false;
	            }else{
	                //认证中..
	                $('.login').addClass('test'); 
	                setTimeout(function () {
	                    $('.authent').show().animate({ right: -320 }, {
	                        easing: 'easeOutQuint',
	                        duration: 600,
	                        queue: false
	                    });
	                    $('.authent').animate({ opacity: 1 }, {
	                        duration: 200,
	                        queue: false
	                    }).addClass('visible');
	                }, 200);
	            }
	        })
	    })
	    
	    function changImg() {
	    	  $("#servletImg").attr("src", "${pageContext.request.contextPath}/code/shiro-getCode.action?r=" + Math.random());
	    }
    </script>
</body>
</html>
