<%-- author:潘全科 
         2019-01-15 14:10--%>
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
    <title>横向菜单管理页面</title>
</head>
<body>
<table id="dg"></table>
<%--添加前台横向菜单窗口--%>
<div id="box1">
    <center style="padding-top: 30px">
        <form id="ff" method="post" action="javascript:void(0)">
            <table cellpadding="5">
                <tr>
                    <td>菜单名:</td>
                    <td><input id="a1" type="text" name="title"></input></td>
                </tr>
                <tr>
                    <td>跳转链接:</td>
                    <td><input id="a2" type="text" name="url"></input></td>
                </tr>
                <tr>
                    <td>横/纵:</td>
                    <td><input type="radio" name="menuType" checked value="1"/>横
                        <input type="radio" name="menuType" value="2"/>纵
                    </td>
                </tr>
                <tr>
                    <td colspan="2" align="center">
                        <a href="#" id="c1">添加</a>
                        <a href="#" id="c2">重置</a>
                    </td>
                </tr>
            </table>
        </form>
    </center>
</div>
<%--编辑前台横向菜单窗口--%>
<div id="box2">
    <center style="padding-top: 30px">
        <form id="ff2" method="post" action="javascript:void(0)">
            <table cellpadding="5">
                <tr>
                    <td>菜单名:</td>
                    <td><input type="text" name="title"></input></td>
                </tr>
                <tr>
                    <td>跳转链接:</td>
                    <td><input type="text" name="url"></input></td>
                </tr>
                <tr>
                    <td>横/纵:</td>
                    <td><input type="radio" name="menuType" checked value="1"/>横
                        <input type="radio" name="menuType" value="2"/>纵
                    </td>
                </tr>
                <tr>
                    <td colspan="2" align="center">
                        <a href="javascript:void(0)" id="edit">编辑</a>
                        <a href="javascript:void(0)" id="res">重置</a>
                    </td>
                </tr>
            </table>
        </form>
    </center>
</div>

<script type="text/javascript">
    $(function () {//处理文档流
        /*表格请求前台*/
        $("#dg").datagrid({
            url: "<%=basePath%>getHxmenu.do",
            rownumbers: true,
            pagination: true,
            pageList: [5, 10, 20, 30, 50],
            columns: [
                [
                    {checkbox: true},
                    {field: 'id', title: '主键', width: 150, align: 'center', sortable: true},
                    {field: 'title', title: '菜单名', width: 150},
                    {field: 'url', title: '跳转链接', width: 150},
                    {field: 'updateTime', title: '更新时间', width: 200}
                ]
            ],
            sortName: 'id', /*定义field=id的列可以排序*/
            remoteSort: false, /*关闭远程排序*/
            fit: true,
            striped: true,//隔行变色
            rowStyler: function (index, row) {
                if (index % 2 == 0) {
                    return "background-color:#6293BB"
                } else {
                    return "background-color:#cc22ff"
                }
            },
            toolbar: [         //添加按钮
                {
                    iconCls: 'icon-add',
                    text: '添加',
                    handler: function () {//事件处理器
                        $("#c1").linkbutton({
                            iconCls: 'icon-ok'
                        });
                        $("#c2").linkbutton({
                            iconCls: 'icon-cancel'
                        });
                        $("#ff input[name=title]").validatebox({
                            required: true,
                            validType: 'length[1,10]'
                        });
                        $("#ff input[name=url]").validatebox({
                            required: true,
                            validType: 'url'

                        });
                        $("#box1").window('open');
                    }
                }, '-',//编辑按钮
                {
                    iconCls: 'icon-edit',
                    text: '修改',
                    handler: function () {
                        var ckAttr = $("#dg").datagrid("getSelections");
                        var len = ckAttr.length
                        if (len == 0) {
                            $.messager.alert('警告', '必须选择一个');
                        } else if (len == 1) {
                            $("#edit").linkbutton({
                                iconCls: 'icon-ok'
                            });
                            $("#res").linkbutton({
                                iconCls: 'icon-cancel'
                            });
                            $("#ff2 input[name=title]").validatebox({
                                required: true,
                                validType: 'length[1,10]'
                            });
                            $("#ff2 input[name=url]").validatebox({
                                required: true,
                                validType: 'url'
                            });
                            var row = $("#dg").datagrid("getSelected");
                            $("#ff2 input[name=title]").val(row.title);
                            $("#ff2 input[name=url]").val(row.url);
                            $("#box2").window('open');
                        } else {
                            $.messager.alert('警告', '只能选择一个进行编辑');
                        }
                    }
                }, '-',
                {
                    iconCls: 'icon-remove',
                    text: '批量删除',
                    handler: function () {
                        //首先判断是否选中数据
                        var idArry = $("#dg").datagrid("getSelections");
                        var len = idArry.length;
                        if (len == 0) {
                            $.messager.alert('警告', '必须选择一项或者多项');
                        } else {
                            $.messager.confirm('确认', '是否删除，一旦删除无法恢复', function (r) {
                                if (r) {
                                    var idstr = "";
                                    for (var i = 0; i < idArry.length; i++) {
                                        idstr += idArry[i].id + ",";
                                    }
                                    console.log(idstr)
                                    //发送ajax请求
                                    $.ajax({
                                        url: "<%=basePath %>deleteWebMenu.do",
                                        type: "post",
                                        dataType: "json",
                                        data: {
                                            "idstr": idstr
                                        },
                                        success: function (r) {
                                            if (r > 0) {
                                                $.messager.show({
                                                    title: "提示",
                                                    msg: "菜单删除成功"
                                                });
                                                $("#dg").datagrid('reload')
                                            } else {
                                                $.messager.alert('提示', '删除失败');
                                            }
                                        },
                                        error: function () {
                                            $.messager.alert('提示', '删除失败');
                                        }
                                    })
                                }
                            })
                        }
                    }
                },//删除按钮
            ]
        });
        /**前台请求添加窗口*/
        $("#box1").window({
            width: 300,
            height: 200,
            title: "添加横向菜单",
            iconCls: 'icon-add',
            draggable: true,
            resizable: false,
            minimizable: false,
            collapsible: false,
            maximizable: false,
            modal: true,
            closed: true, /*窗口初始化时就默认关闭*/
            onClose: function () {
                $("#ff input[type=text]").val("")
            }
        });
        /*添加触发器*/
        $("#c1").off().on('click', (function () {

            $.ajax({
                url: "<%=basePath %>/addWebMenu.do",
                type: "post",
                dataType: "json",
                data: {
                    "title": $("input[name=title]").val(),
                    "url": $("input[name=url]").val(),
                    "menuType": $("#ff input:radio[name='menuType']:checked").val()
                },
                success: function (rs) {
                    if (rs) {//添加成功
                        //1、关闭添加前台菜单窗口
                        $("#box1").window('close');


                        //3、提示添加成功
                        $.messager.show({
                            title: "提示",
                            msg: "菜单添加成功"
                        });
                        //2、刷新表格
                        $("#dg").datagrid('reload')
                    } else {//添加失败
                        $.messager.alert('提示', '添加失败，请重试');
                    }
                },
                error: function (err) {
                    $.messager.alert('提示', '添加失败，请重试');
                }
            });
        }));
        /*编辑前台横向菜单窗口*/

        //发送编辑ajax请求
        $("#edit").click(function () {
            $("#box2").window({
                width: 300,
                height: 200,
                title: "编辑横向菜单",
                iconCls: 'icon-edit',
                draggable: true, /*能拖动*/
                resizable: false, /*不能改变尺寸*/
                minimizable: false,
                collapsible: false,
                maximizable: false,
                modal: true,
                closed: true
            });
            var row = $("#dg").datagrid("getSelected");
            var id = row.id;
            $.ajax({
                type: "post",
                dataType: "json",
                url: "<%=basePath%>updateWebMenu.do",
                data: {
                    "title": $("#ff2 input[name=title]").val(),
                    "url": $("#ff2 input[name=url]").val(),
                    "id": id,
                    "menuType": $("#ff2 input:radio[name=menuType]:checked").val()
                },
                success: function (rs) {
                    if (rs) {
                        //1、关闭添加前台菜单窗口
                        $("#box2").window('close');
                        //2、刷新表格
                        $("#dg").datagrid('reload');
                        //3、提示添加成功
                        $.messager.show({
                            title: "提示",
                            msg: "菜单编辑成功"
                        });
                    } else {
                        $.messager.alert('提示', '编辑失败，请重试');
                    }
                },
                error: function () {
                    $.messager.alert('提示', '编辑失败，请重试');
                }
            })
        })
    });
</script>
</body>

</html>
