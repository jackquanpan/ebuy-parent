<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort() +request.getContextPath()+"/";
%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <base href="<%=basePath %>">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<link type="text/css" rel="stylesheet" href="<%=basePath %>static/css/style.css" />
    <!--[if IE 6]>
    <script src="<%=basePath %>static/js/iepng.js" type="text/javascript"></script>
        <script type="text/javascript">
           EvPNG.fix('div, ul, img, li, input, a'); 
        </script>
    <![endif]-->    
    <script type="text/javascript" src="<%=basePath %>static/js/jquery-1.11.1.min_044d0927.js"></script>
	<script type="text/javascript" src="<%=basePath %>static/js/jquery.bxslider_e88acd1b.js"></script>
    
    <script type="text/javascript" src="<%=basePath %>static/js/jquery-1.8.2.min.js"></script>
    <script type="text/javascript" src="<%=basePath %>static/js/menu.js"></script>    
        
	<script type="text/javascript" src="<%=basePath %>static/js/select.js"></script>
    
	<script type="text/javascript" src="<%=basePath %>static/js/lrscroll.js"></script>
    
    <script type="text/javascript" src="<%=basePath %>static/js/iban.js"></script>
    <script type="text/javascript" src="<%=basePath %>static/js/fban.js"></script>
    <script type="text/javascript" src="<%=basePath %>static/js/f_ban.js"></script>
    <script type="text/javascript" src="<%=basePath %>static/js/mban.js"></script>
    <script type="text/javascript" src="<%=basePath %>static/js/bban.js"></script>
    <script type="text/javascript" src="<%=basePath %>static/js/hban.js"></script>
    <script type="text/javascript" src="<%=basePath %>static/js/tban.js"></script>
    
	<script type="text/javascript" src="<%=basePath %>static/js/lrscroll_1.js"></script>
    
    
<title>易买网注册</title>
</head>
<body>  
<!--Begin Header Begin-->
<div class="soubg">
	<div class="sou">
        <span class="fr">
        	<span class="fl">你好，请<a href="Login.jsp">登录</a>&nbsp; <a href="Regist.jsp" style="color:#ff4e00;">免费注册</a>&nbsp;</span>
            <span class="fl">|&nbsp;关注我们：</span>
            <span class="s_sh"><a href="#" class="sh1">新浪</a><a href="#" class="sh2">微信</a></span>
            <span class="fr">|&nbsp;<a href="#">手机版&nbsp;<img src="<%=basePath %>static/images/s_tel.png" align="absmiddle" /></a></span>
        </span>
    </div>
</div>
<!--End Header End--> 
<!--Begin Login Begin-->
<div class="log_bg">	
    <div class="top">
        <div class="logo"><a href="Index.jsp"><img src="<%=basePath %>static/images/logo.png" /></a></div>
    </div>
	<div class="regist">
    	<div class="log_img"><img src="<%=basePath %>static/images/l_img.png" width="611" height="425" /></div>
		<div class="reg_c">
        	<form action="<%=basePath%>register.do" method="post" id="fm1">
            <table border="0" style="width:420px; font-size:14px; margin-top:20px;" cellspacing="0" cellpadding="0">
              <tr height="50" valign="top">
              	<td width="95">&nbsp;</td>
                <td>
                	<span class="fl" style="font-size:24px;">注册</span>
                    <span class="fr">已有商城账号，<a href="<%=basePath%>pages/Login.jsp" style="color:#ff4e00;">我要登录</a></span>
                </td>
              </tr>
              <tr height="50">
                <td align="right"><font color="#ff4e00">*</font>&nbsp;用户名 &nbsp;</td>
                <td>
                    <input type="text" value="" class="l_user" name="username" />
                    <span id="uNameMsg" style="color: red;font-size:12px"></span>
                </td>
              </tr>
              <tr height="50">
                <td align="right"><font color="#ff4e00">*</font>&nbsp;密码 &nbsp;</td>
                <td>
                    <input name="pwd" type="password" value="" class="l_pwd" />

                </td>
              </tr>
              <tr height="50">
                <td align="right"><font color="#ff4e00">*</font>&nbsp;确认密码 &nbsp;</td>
                <td>
                    <input type="password" name="checkPwd" value="" class="l_pwd" />
                    <span id="pwdMsg" style="color:red;font-size:12px"></span>
                </td>
              </tr>
              <tr height="50">
                <td align="right"><font color="#ff4e00">*</font>&nbsp;邮箱 &nbsp;</td>
                <td><input type="text" name="email" value="" class="l_email" /></td>
              </tr>
              <tr height="50">
                <td align="right"><font color="#ff4e00">*</font>&nbsp;手机 &nbsp;</td>
                <td>
                    <input type="text" name="phone" value="" class="l_tel" />
                    <span id="phoneError" style="color:red;font-size: 12px">${errorMap.uniqError}</span>
                </td>
              </tr>
              <tr height="50">
                <td align="right"> <font color="#ff4e00">*</font>&nbsp;验证码 &nbsp;</td>
                <td>
                    <input type="text" value="" class="l_ipt" />
                    <input type="button" id="sendSMS" value="发送验证码" style="height: 25px;"/>
                    <span id="smsCode" style="color:red;font-size:12px">${errorMap.smsCodeStatus}</span>
                </td>
              </tr>
              <tr>
              	<td>&nbsp;</td>
                <td style="font-size:12px; padding-top:20px;">
                	<span style="font-family:'宋体';" class="fl">
                    	<label class="r_rad"><input type="checkbox" id="agree" /></label><label class="r_txt">我已阅读并接受《用户协议》</label>
                    </span>
                </td>
              </tr>
              <tr height="60">
              	<td>&nbsp;</td>
                <td><input type="submit" value="立即注册" class="log_btn" /></td>
              </tr>
            </table>
            </form>
        </div>
    </div>
</div>
<!--End Login End--> 
<!--Begin Footer Begin-->
<div class="btmbg">
    <div class="btm">
        备案/许可证编号：蜀ICP备12009302号-1-www.dingguagua.com   Copyright © 2015-2018 尤洪商城网 All Rights Reserved. 复制必究 , Technical Support: Dgg Group <br />
        <img src="<%=basePath %>static/images/b_1.gif" width="98" height="33" /><img src="<%=basePath %>static/images/b_2.gif" width="98" height="33" /><img src="<%=basePath %>static/images/b_3.gif" width="98" height="33" /><img src="<%=basePath %>static/images/b_4.gif" width="98" height="33" /><img src="<%=basePath %>static/images/b_5.gif" width="98" height="33" /><img src="<%=basePath %>static/images/b_6.gif" width="98" height="33" />
    </div>    	
</div>
<!--End Footer End -->    

</body>
<
<script type="text/javascript">
    var countdown=60;
    function settime(obj) {
        if (countdown == 0) {
            obj.removeAttribute("disabled");
            obj.value="免费获取验证码";
            countdown = 60;
            return;
        } else {
            obj.setAttribute("disabled", true);
            obj.value="重新发送(" + countdown + ")";
            countdown--;
        }
        setTimeout(function() {
                settime(obj) }
            ,1000)
    }
    $(function () {
     //用于用户名校验
    $("input[name=username]").blur(function () {
        $("#uNameMsg").html("");
        $ajax({
            url:"<%=basePath%>isUsernameExits.do",
            type:'post',
            dataType:'json',
            data:{"username":$(this).val()},
            success:function (rs) {
               //rs为boolean类型
               $("#uNameMsg").html("用户已经存在");
               //让username获取焦点
                $("input[name=username]").focus();
                //清空里面内容
                $("input[name=username]").val("");
            }
        })
    });
    //给手机文本输入添加失去焦点事件
        $("#input[name=phone]").blur(function () {
            $("#phoneError").html("");
        });
    //给短信验证码添加失去焦点的时间
    $("#sendSMS").blur(function () {
        $("#smsCode").html("");
    })
    //给注册添加点击事件
    $("#fm1").submit(function () {
        var a=$("#agree").prop("checked");
        if(!a){
            alert("请勾选已经阅读用户协议");
            return false;

        }
    });
    //确认密码校验
        $("input[name=checkPwd]").blur(function () {
            $("#pwdMsg").html("");
            var checkPwdValue=$(this).val();
            var pwdValue=$("input[name=pwd]").val();
            if(checkPwdValue!=pwdValue){
                $("#pwdMsg").html("两次输入的密码不一致，请重新输入");
                $(this).focus();
                $(this).val("");
            }
        })
    })
    $(function () {
        $("#sendSMS").click(function () {
            settime(this)
            //校验用户有没有输入
            var ph=$("input[name=phone]").val();
            console.log("ph="+ph);
            if(ph!=null || phone!=''){
                 $.ajax({
                     url:"<%=basePath %>sendSMS.do",
                     type:"post",
                     dataType:"json",
                     data:{"phone":ph},
                     success:function (rs) {
                       if(rs){
                           alert("短信已发送,1分钟有效")
                       }
                     }
                 })
            }else{
                var flag=confirm("请输入手机号码")
                if(flag){
                    $("input[name=phone]").focus();
                }
            }
        })
    })
</script>

<!--[if IE 6]>
<script src="//letskillie6.googlecode.com/svn/trunk/2/zh_CN.js"></script>
<![endif]-->
</html>
