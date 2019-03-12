<%-- author:潘全科
         2019-02-03 11:07--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>
<!DOCTYPE html>
<html>
<head>
    <base href="<%=basePath %>">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="<%=basePath %>static/js/easyui/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath %>static/js/easyui/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath %>static/js/easyui/demo/demo.css">
    <script type="text/javascript" src="<%=basePath %>static/js/easyui/jquery.min.js"></script>
    <script type="text/javascript" src="<%=basePath %>static/js/easyui/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="<%=basePath %>static/js/easyui/locale/easyui-lang-zh_CN.js"></script>
    <title>轮播管理</title>
</head>
<body>
<table id="bannerdg"></table>
<div id="addWin">
    <center>
        <form id="fmbanner" action="javascript:void(0)" method="post">
            <table <%--border="1px" style="width: 500px;" cellpadding="0px" cellspacing="0"--%>>
                <tr>
                    <td>轮播图片</td>
                    <td>
                        <input type="button" id="onPicUpload" value="图片上传" />
                        <input type="hidden" name="img_url"/>
                    </td>
                </tr>
                <tr>
                    <td>点击图片跳转地址</td>
                    <td>
                        <input type="text" name="href"/>
                    </td>
                </tr>
                <tr>
                    <td>备注</td>
                    <td>
                        <textarea name="remark"></textarea>
                    </td>
                </tr>
                <tr>
                    <td>排序号</td>
                    <td>
                        <select name="sort">
                            <option value="1">第1张</option>
                            <option value="2">第2张</option>
                            <option value="3">第3张</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td><input type="submit" value="添加" id="addBanner"></td>
                    <td>
                        <input type="reset" value="取消" id="caneel"/>
                    </td>
                </tr>
            </table>
        </form>
    </center>
</div>
<script>
    $(function () {

        $("#addWin input[type=submit]").click(function () {
            $.ajax({
                url:'<%=basePath%>addbanner.do',
                type:'post',
                dataType:'json',
                data:{
                    "img_url":$("#addWin input[name=img_url]").val(),
                    "href":$("#addWin input[name=href]").val(),
                    "remark":$("#addWin textarea[name=remark]").val(),
                    "sort":$("#addWin select[name=sort]").val()
                },
                success:function (rs) {
                    if(rs){
                        $.messager.show({
                            title: "提示",
                            msg: "上传成功"

                        });
                    }
                },
                error:function () {
                    $.messager.show({
                        title: "提示",
                        msg: "上传失败"
                    })
                }

            })
        })
        $("#bannerdg").datagrid({
            url:'<%--<%=basePath%>--%>getBanners.do',
            rownumbers: true,
            columns:[[
                {checkbox:true},
                {field:'id',title:'主键',width:'200'},
                {field:'img_url',title:'图片地址',width:'200'},
                {field:'href',title:'图片跳转地址',width:'250'},
                {field:'remark',title:'备注描述',width:'200'},
                {field:'sort',title:'排序',width:'200'},
                {field:'updateTime',title:'更新时间',width:'200'}
            ]],
            pagination:true,
            fit:true,
            pageList:[5,10,15,20],
            sortName: 'id',
            remoteSort: false,
            striped: true,//隔行变色
            rowStyler: function (index, row) {
                if (index % 2 == 0) {
                    return "background-color:#6293BB"
                } else {
                    return "background-color:#cc22ff"
                }
            },
            toolbar: [{
                iconCls: 'icon-add',

                handler: function(){
                    $("#addBanner").linkbutton({
                        iconCls: 'icon-ok'
                    });
                    $("#caneel").linkbutton({
                        iconCls: 'icon-cancel'
                    });

                    $("#addWin").window('open');
                }
            },'-',{
                iconCls: 'icon-edit',
                handler: function(){alert('帮助按钮')}
            }]
        });
        $("#onPicUpload").click(function() {
            var _self = $(this);
            KindEditor.editor({
                //指定上传文件参数名称
                filePostName: "uploadFile",
                //指定上传文件请求的url。
                uploadJson: '<%=basePath %>fastDFSUpload.do',
                //上传类型，分别为image、flash、media、file
                dir: "image"
            }).loadPlugin('image', function() {
                this.plugin.imageDialog({
                    showRemote: false,
                    clickFn: function(url, title, width, height, border, align) {
                        $("input[name=img_url]").val(url);
                        $("#onPicUpload").after("<a href='" + url + "' target='_blank'><img src='" + url + "' width='80' height='50'/></a>");
                        this.hideDialog();
                    }
                });
            });
        });


        $("#addWin").window({
            title:"添加轮播",
            iconCls:'icon-add',
            width: 800,
            height: 400,
            draggable:true,
            resizable:false,
            minimizable:false,
            collapsible:false,
            maximizable:false,
            modal:true,
            closed:true,
            onClose: function () {
                $("#fmbanner input[type=text]").val("")
                $("#bannerdg").datagrid('reload')
            }
        })
    })
</script>
</body>
</html>
