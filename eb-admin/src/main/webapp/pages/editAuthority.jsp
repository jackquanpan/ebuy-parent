<%-- author:潘全科
         2019-02-17 14:29--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>
<!DOCTYPE html>
<html>
<head>
    <base href="<%=basePath %>">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>权限设置</title>
</head>
<body>

<center style="padding-top: 50px">
    <form id="ff10" method="post" action="javascript:void(0)">
    <table width="400px" id="authVue">
        <tr v-for="oneMenu in oneMenuList">
            <td><strong><input type="hidden" name="oneId" v-bind:value="oneMenu.oneId">{{oneMenu.oneText}}&nbsp;&nbsp;</strong></td>
            <td v-for="twoMenu in oneMenu.twoMenuList">
                <input type="checkbox" name="twoId" v-bind:value="twoMenu.twoId">{{twoMenu.twoText}}&nbsp;&nbsp;
            </td>
           <input type="text" id="userid" value="${param.userId}" hidden="hidden"/>
        </tr>
        <tr>
            <td>
                <a href="javascript:void(0)" id="editA"  class="easyui-linkbutton" data-options="iconCls:'icon-ok'">确认</a>
            </td>
            <td>
                <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'">取消</a>
            </td>
        </tr>
    </table>
    </form>
</center>
<%--<h3 id="userId">${param.userId}</h3>--%>
<script type="text/javascript">
    var vue1 = new Vue({
        el:"#authVue",
        data:{
            oneMenuList:[]
        },
        created(){
            this.$http.post("<%=basePath %>//findAuthorityByRelation.do").then(
                function(rs){
                    this.oneMenuList=rs.body;
                }
            );
        },
        mounted(){
            console.log("--------")
          $.ajax({
              url:'<%=basePath %>showAuthority.do',
              dataType:'json',
              type:'post',
              data:{
                  "userId":$("#userid").val()
              },
              success:function (rs) {
                  for(i in rs){
                      $("input[value="+rs[i]+"]").prop("checked","checked");
                    
                  }
              }
          })
        }
    });

    $(function () {
        $("#editA").click(function () {

            var array=new Array();
            //1.获取每次遍历的id
            $("input[name='oneId']").each(function (index,item) {
                var id1=$(this).val();
                array.push(id1);
            });
            $("input[name='twoId']:checked").each(function (index, item) {
                var id = $(this).val();//获取的就是复选框里的value属性值
                array.push(id);//把每次遍历出来的id封装到array数组中
            })
            var menuid='';
            for(var i in array){
                menuid+=array[i]+","

            }

           $.ajax({
               type:'post',
               url:'<%=basePath%>editAuthority.do',
               dataType:'json',
               data:{
                   "menuid":menuid.toString(),
                   "userId":$("#userid").val()
               },
               success:function (rs) {
                   if(rs){
                       $.messager.show({
                           title: "提示",
                           msg: "权限修改成功"

                       });
                   }

               }
           })

        })
    })
</script>
</body>
</html>
