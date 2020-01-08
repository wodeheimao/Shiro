<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/base.jsp"%>
<html>
<head>
    <title>修改列表</title>
    <style>
        .table{
            text-align: left;
        }
        .t{
            text-align: center;
        }
        .container{
            width: 500px;
            padding-top:20px;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="panel panel-primary">
            <div class="panel-heading">
                <div class="panel-title">
                    <h3>修改列表</h3>
                </div>
            </div>
            <form action="${pageContext.request.contextPath}/student/update" method="post">
                <input type="hidden" name="sid" value="${student.sid}">
                <table class="table  table-striped">
                    <tr>
                        <td class="t">学生姓名</td>
                        <td><input type="text"  class="form-control"  name="sname" value="${student.sname}"></td>
                    </tr>
                    <tr>
                        <td class="t">学生性别</td>
                        <td>
                            <input type="radio" name="sex" value="男" ${student.sex=='男'?'checked':''}>男
                            <input type="radio" name="sex" value="女"  ${student.sex=='女'?'checked':''}>女
                        </td>
                    </tr>
                    <tr>
                        <td class="t">学生年龄</td>
                        <td><input  class="form-control"  type="text" name="age"  value="${student.age}"></td>
                    </tr>
                    <tr>
                        <td class="t">学生住址</td>
                        <td><input  class="form-control" type="text" name="addr"  value="${student.addr}"></td>
                    </tr>
                    <tr>
                        <td class="t">出生日期</td>
                        <td><input class="form-control" type="date" name="birth"
                                   value='<fmt:formatDate value="${student.birth}" pattern="yyyy-MM-dd"/>'></td>
                    </tr>
                    <tr>
                        <td class="t">所在班级</td>
                        <td>
                            <select class="form-control" name="cid">
                            <c:forEach items="${classes}" var="c">
                                <option value="${c.cid}" ${c.cid==student.cid?'selected':''}>${c.cname}</option>
                            </c:forEach>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2"  class="t">
                            <input type="submit" value="修改" class="btn btn-primary btn-block">
                        </td>
                    </tr>
                </table>
            </form>
            <div class="panel-footer text-right">
                泽林教育版权所有2000-2019.
            </div>
            </div>
        </div>
    </div>
</body>
</html>
