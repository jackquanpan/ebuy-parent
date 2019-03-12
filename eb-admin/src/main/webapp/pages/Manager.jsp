<%-- author:潘全科
         2019-01-12 22:28--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>
<!DOCTYPE html>
<html>
<head>
    <base href="<%=basePath %>">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>后台管理页面</title>
    <link rel="stylesheet" type="text/css" href="<%=basePath %>static/js/easyui/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath %>static/js/easyui/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath %>static/js/easyui/demo/demo.css">
    <script type="text/javascript" src="<%=basePath %>static/js/easyui/jquery.min.js"></script>
    <script type="text/javascript" src="<%=basePath %>static/js/easyui/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="<%=basePath %>static/js/easyui/locale/easyui-lang-zh_CN.js"></script>
    <%--引入vue--%>
    <script src="https://cdn.jsdelivr.net/npm/vue"></script>
    <script src="https://cdn.jsdelivr.net/npm/vue-resource@1.5.1"></script>
    <%--引入kindeditor--%>
    <script type="text/javascript" src="<%=basePath %>static/js/kindeditor-5.1.10/kindeditor.js"></script>
    <script type="text/javascript" src="<%=basePath %>static/js/kindeditor-5.1.10/lang/zh_CN.js"></script>

</head>
<body class="easyui-layout">
<!--北京-->
<div data-options="region:'north',title:'主题栏',split:true" style="height:100px;">
    <center style="padding-top: 15px;font-feature-settings: inherit;font-size: 30px;text-align: left;margin-left: 20px;color: aqua;float: left">易买网购物后台管理系统</center>
    <center style="padding-top: 45px;font-feature-settings: inherit;font-size: 15px;text-align: right;margin-right: 20px;color: red;float: right">欢迎${username}来到   <a href="#">注销</a></center>
</div>
<!--南京-->
<div data-options="region:'south',title:'南',split:true" style="height:100px;">
    <center style="padding-top: 20px">&COPY;自主品牌|盗版必究</center>
</div>
<%--东京--%>
<div data-options="region:'east',iconCls:'icon-reload',title:'东',split:true" style="width:150px;"></div>
<%--西京--%>
<div data-options="region:'west',title:'菜单栏',split:true,iconCls:'icon-world'" style="width:150px;">
    <ul id="tt"></ul>
</div>
<%--中部--%>
<div data-options="region:'center',title:'选项卡',noheader:true" id="cen" style="padding:5px;background:#eee;">
    <div id="tb" class="easyui-tabs" data-options="fit:true">
        <div data-options="title:'首页',iconCls:'icon-house'">
            <center style="padding-top:150px;font-size: 36px;color: green;font-weight: bolder;text-shadow: 10px 10px 5px #ccc">欢迎${username}使用易买网后台管理系统</center>
        </div>
    </div>
</div>
</body>
<script type="text/javascript">
    $("#tt").tree({
        url:"<%=basePath %>getAuthorityByUsername.do",
        onlyLeafCheck:true,
        lines:true,
        onClick:function(node){
            //判断是否为叶子节点
            var isleaf =$("#tt").tree('isLeaf',node.target);
            var flag = $("#tb").tabs('exists',node.text);

           if(!isleaf){
               $("#tt").tree('toggle',node.target)
           }else{
               if(!flag&&node.url!=null){
                   $("#tb").tabs("add",{
                       title:node.text,
                       iconCls:node.iconCls,
                       closable:true,
                       href:'<%=basePath%>/pages/'+node.url
                   });

               }else{
                   $("#tab").tabs('select',node.text)
               }
           }
        }
    });
    $("#tb").tabs({
        onClose:function(title,index){
        }
    })
</script>
</html>
