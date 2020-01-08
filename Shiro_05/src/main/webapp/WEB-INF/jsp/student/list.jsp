<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ include file="/base.jsp"%>
<html>
<head>
    <title>学生列表</title>
    <style>
        .table{
            text-align: center;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="panel panel-success">
            <div class="panel-heading">
                <div class="panel-title">
                    <h3>学生列表</h3>
                </div>
            </div>
                <table class="table table-bordered table-striped">
                    <tr>
                        <td>是否删除</td>
                        <td>学生姓名</td>
                        <td>学生性别</td>
                        <td>年龄</td>
                        <td>住址</td>
                        <td>出生日期</td>
                        <td>所在班级</td>
                        <td>操作</td>
                    </tr>
                    <c:forEach items="${students}" var="stud">
                        <tr>
                            <td>
                                <input type="checkbox">
                            </td>
                            <td>${stud.sname}</td>
                            <td>${stud.sex}</td>
                            <td>${stud.age}</td>
                            <td>${stud.addr}</td>
                            <td>
                                <%--利用jstl的格式化标签库显示日期字段--%>
                                <fmt:formatDate value="${stud.birth}" pattern="yyyy-MM-dd"/>
                             </td>
                            <td>${stud.cname}</td>
                            <td>
                                <a type="button" href="${pageContext.request.contextPath}/student/toupdate/${stud.sid}" class="btn btn-success btn-sm">修改</a>
                                <a type="button" onclick="return confirm('你真的要删除吗?')"
                                   href="${pageContext.request.contextPath}/student/delete/${stud.sid}"  class="btn btn-danger btn-sm">删除</a>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            <div class="panel-footer text-right">
                <shiro:hasPermission name="student:creat">
                    <a  href="${pageContext.request.contextPath}/student/toadd" class="btn btn-info btn-sm">添加学生</a>
                </shiro:hasPermission>
            </div>
            </div>
        </div>
    </div>
</body>
</html>
