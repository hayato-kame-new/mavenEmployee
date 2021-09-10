<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.List, dao.DepartmentDAO, model.DepartmentBean"%>

<%
    // DepartmentBeanオブジェクト のリスト
DepartmentDAO depDAO = new DepartmentDAO();
List<DepartmentBean> depList = depDAO.findAll();
// リストの先頭に null をくっつける
/* DepartmentBean notSpecified = new DepartmentBean();
depList.add(0, null); */
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>社員データベース検索ページ</title>
</head>
<body>
<h3>条件を指定して社員情報を検索します。</h3>

<form action="SearchServlet" method="post">

<p>
所属部署:<select name="department">
  <option value="">未選択</option>
<% for(DepartmentBean depBean : depList) { %>
  <option value="<%= depBean.getDepartment() %>" ><%= depBean.getDepartment() %></option>
<% } %>

</select>
</p>

<p>
社員ID:<input type="text" name="employeeId" />
</p>

<p>
名前に含む文字:<input type="text" name="word" />
</p>
<input type="hidden" name="action" value="search" />
<input type="submit" value="検索" />
</form>

</body>
</html>