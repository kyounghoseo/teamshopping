<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<c:if test="${check == 1 }">
  <c:set var="id" value="${id}" scope="session"/>
</c:if>

<p id="ck">${check}
