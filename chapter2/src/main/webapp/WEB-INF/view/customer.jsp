<%@page pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="Base" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>客户管理 - 创建客户</title>
</head>

<body>

    <h1>创建客户界面</h1>
    <table>
        <tr>
            <th>客户名称</th>
            <th>联系人</th>
            <th>电话号码</th>
            <th>邮箱地址</th>
            <th>操作</th>
        </tr>
        <c:forEach var="customer" items="${customerList}">
            <tr>
                <td>${customer.name}</td>
                <td>${customer.contact}</td>
                <td>${customer.telephone}</td>
                <td>${customer.eMail}</td>
                <td>
                    <a href="xx" >编辑</a>
                    <a href="xx" >删除</a>
                </td>
            </tr>
        </c:forEach>

    </table>

</body>

</html>