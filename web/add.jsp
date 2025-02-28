<%--
  Created by IntelliJ IDEA.
  User: 86183
  Date: 2022/2/26
  Time: 19:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="zh-CN">
<head>
    <!-- 指定字符集 -->
    <meta charset="utf-8">
    <!-- 使用Edge最新的浏览器的渲染方式 -->
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <!-- viewport视口：网页可以根据设置的宽度自动进行适配，在浏览器的内部虚拟一个容器，容器的宽度与设备的宽度相同。
    width: 默认宽度与设备的宽度相同
    initial-scale: 初始的缩放比，为1:1 -->
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>添加用户</title>

    <!-- 1. 导入CSS的全局样式 -->
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <style>
        .error{
            color: red;
        }
    </style>
    <!-- 2. jQuery导入，建议使用1.9以上的版本 -->
    <script src="js/jquery-2.1.0.min.js"></script>
    <!-- 3. 导入bootstrap的js文件 -->
    <script src="js/bootstrap.min.js"></script>
    <script src="js/add校验.js"></script>
</head>
<body>
<div class="container">
    <center><h3>添加用户页面</h3></center>
    <form action="${pageContext.request.contextPath}/addUserServlet" id="form" method="post">
        <div class="form-group">
            <label for="name">姓名：</label>
            <input type="text" class="form-control" id="name" name="name" placeholder="请输入姓名">
        </div>

        <div class="form-group">
            <label>性别：</label>
            <input type="radio" name="gender" value="男" checked="checked"/>男
            <input type="radio" name="gender" value="女"/>女
        </div>

        <div class="form-group">
            <label for="age">年龄：</label>
            <input type="text" class="form-control" id="age" name="age" placeholder="请输入年龄">
            <span id="s_age" class="error"></span>
        </div>

        <div class="form-group">
            <label>籍贯：</label>
            <select name="address" class="form-control" id="jiguan">
                <option value="湖北">湖北</option>
                <option value="广东">广东</option>
                <option value="湖南">湖南</option>
                <option value="广西">广西</option>
            </select>
        </div>

        <div class="form-group">
            <label >身份证号码：</label>
            <input type="text" class="form-control" id="number" name="number" placeholder="请输入身份证号"/>
            <span id="s_number" class="error"></span>
        </div>

        <div class="form-group">
            <label >手机号码：</label>
            <input type="text" class="form-control" id="phone" name="phone" placeholder="请输入手机号码"/>
            <span id="s_phone" class="error"></span>
        </div>

        <div class="form-group" style="text-align: center">
            <input class="btn btn-primary" type="submit" value="提交" />
            <input class="btn btn-default" type="reset" value="重置" />
            <input class="btn btn-default" type="button" value="返回" />
        </div>
    </form>
</div>
</body>
</html>
