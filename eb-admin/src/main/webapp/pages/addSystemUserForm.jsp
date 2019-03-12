<%-- author:潘全科 
         2019-02-15 16:07--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>
<!DOCTYPE html>
<html>
<head>
    <base href="<%=basePath %>">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <script type="text/javascript" src="<%=basePath %>/static/js/easyui/jquery.min.js"></script>
    <link rel="stylesheet" type="text/css" href="<%=basePath %>static/js/easyui/themes/icon.css">
    <title>添加用户界面</title>
</head>
<body>
<center style="padding-top: 30px">
    <form id="ff2" method="post" action="javascript:void(0)">
        <table cellpadding="5">
            <tr>
                <td>账号:</td>
                <td><input id="a1" type="text" name="username"/>
                    <span id="uNameMsg" style="color: red;font-size:12px"></span>
                </td>
            </tr>
            <tr>
                <td>密码:</td>
                <td><input id="a2" type="text" name="pwd" value="123456"></input></td>
            </tr>
            <tr>
            <td>授权：</td>
                <td>
                    <ul id="abc"></ul>
                </td>
            </tr>
            <tr>
                <td colspan="2" align="center">
                    <a href="javascript:void(0)" id="add">添加</a>
                    <a href="javascript:void(0)" id="res">重置</a>
                </td>
            </tr>
        </table>
    </form>
</center>
<script>
    $(function () {

        //加载权限树
        $("#abc").tree({
            url:'<%=basePath%>getAuthority.do',
            checkbox:true
        })

        /*出发ajax事件*/
        $("#add").off().on('click',function () {
            var dms=$('#abc').tree("getChecked",['indeterminate','checked']);

            var menuId='';
            for(var i in dms){
                console.log(dms[i].id)
                menuId+=dms[i].id+",";
            }
            $.ajax({
                url:'<%=basePath%>addSystemUser.do',
                type:'post',
                dataType:'json',
                data:{
                    "username":$("#ff2 input[name=username]").val(),
                    "pwd":$("#ff2 input[name=pwd]").val(),
                    "menuId":menuId
                },
                success(rs){
                    //3、提示添加成功
                    $.messager.show({
                        title: "提示",
                        msg: "用户添加成功"

                    });
                    //2、刷新表格

                }

            })
        })
        /*重置添加内容*/
        $("#res").click(function(){
            $("#ff2 input[type=text]").val("");
            $("#a1").focus();
        });
        $("#add").linkbutton({
            iconCls:'icon-ok'
        });
        $("#res").linkbutton({
            iconCls:'icon-cancel'
        })
        $("#a1").validatebox({
            required:true,
            validType:'length[1,10]'
        });
        $("#a2").validatebox({
            required: true,
            validType: 'length[1,10]'
        });
    })
    $(function(){

        //用于校验用户名是否重复
        $("input[name=username]").blur(function () {
            $('#uNameMsg').html("");
            $.ajax({
                url:"<%=basePath%>isusernameExits.do",
                type:'post',
                dataType:'json',
                data:{
                    "username":$(this).val()
                },
                success:function (rs) {
                  //rs为boolean类型
                    if(rs){
                    $("#uNameMsg").html("用户已经存在");
                    //让username获取焦点
                    $("input[name=username]").focus();
                    //清空里面内容
                    $("input[name=username]").val("");
                    }
                }
            })
        })
    })
</script>
</body>
</html>
