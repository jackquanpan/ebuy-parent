<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort() +request.getContextPath()+"/";
%>
<!DOCTYPE html>
<html>
<head>
	<base href="<%=basePath %>">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>系统用户信息查询</title>
</head>
<body>
	<%--数据表格--%>
	<table id="dg2"></table>
	<%--添加系统账号--%>
	<div id="box4">
		<center style="padding-top: 30px">
			<form id="ff2" method="post" action="javascript:void(0)">
				<table cellpadding="5">
					<tr>
						<td>账号:</td>
						<td><input id="a1" type="text" name="username"></input></td>
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
	</div>

	<div id="box3">

	</div>

	<script type="text/javascript">
		function editAuthority(id){
		   //$("#box3").window("open")
            $('#box3').panel('open').panel('refresh','<%=basePath %>pages/editAuthority.jsp?userId='+id);
		  /* $.ajax({
			   type:'post',
			   dataType:'json',
			   url:'<%=basePath%>getUserAuthority.do',
			   data:{
			       "userId":id
			   },
			   success:function (rs) {
				   console.log(rs)
               }


		   })*/
		}

        $(function () {//处理文档流
            /*表格请求前台*/
            $("#dg2").datagrid({
                url: "<%=basePath%>getAlluser.do",
                rownumbers: true,
                columns: [
                    [
                        {checkbox: true},
                        {field: 'id', title: '主键', width: 150, align: 'center', sortable: true},
                        {field: 'username', title: '用户名', width: 150},
                        {field: 'isroot', title: '角色', width: 150,
                            formatter: function(value,row,index){
                                if(value==1){
                                    return '超级管理员';
                                }
                                return '普通管理员';
                            }},
                        {field: 'updatetime', title: '更新时间', width: 200},
						{field: 'operate',title:'操作',width:200,
						formatter:function (value,row,index) {
							if(row.isroot==1){
							    return '无操作';
							}
							return '<a class="c1" onclick="editAuthority('+row.id+')"></a>'
                        }
						}
                    ]
                ],
				onLoadSuccess:function(data){
                    jQuery(".c1").linkbutton({
						iconCls:'icon-edit',
						text:'设置权限',
						plain:true
					});
				},
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
                        text: '添加账号',
                        handler: function () {//事件处理器
                            $("#box4").window('open');
                        }
                    }

                ]
            });
            $("#box4").window({
                width: 600,
                height: 600,
                href:'<%=basePath%>/pages/addSystemUserForm.jsp',
                title: "添加用户",
                iconCls: 'icon-add',
                draggable: true,
                resizable: false,
                minimizable: false,
                collapsible: false,
                maximizable: false,
                modal: true,
                closed: true, /*窗口初始化时就默认关闭*/
                onClose: function () {
                    $("#a1").val('')
                    $("#a2").val('')
                    $("#dg2").datagrid('reload')
                }
            });


            /*设置权限*/
            $("#box3").window({
                width:500,
                height:200,
                //href:'<%=basePath %>/pages/editAuthority.jsp',
                title:"设置权限",
                iconCls:'icon-edit',
                draggable:true,/*能拖动*/
                resizable:false, /*不能改变尺寸*/
                minimizable:false,
                collapsible:false,
                maximizable:false,
                modal:true,
                closed:true,  /*窗口初始化时就默认关闭*/
                onClose:function(){//关闭窗口时触发事件
                  /* $("input[checked=checked]").remoAttr('checked')*/
                    $("#dg2").datagrid('reload')
                }
            });


            //发送编辑ajax请求
            $("#edit").click(function () {
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
