<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ page import="dao.DepartmentDAO, java.util.*, model.DepartmentBean"%>


<%
// 部署一覧を取得する
DepartmentDAO depDAO = new DepartmentDAO();
List<DepartmentBean> depList = new ArrayList<DepartmentBean>();
depList = depDAO.findAll();

// 削除しようとして失敗した時のエラーメッセージ
String msg = (String) request.getAttribute("msg");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>部署データベース一覧</title>
<style>
th {
  background-color: #0099ff;
}
</style>
</head>
<body>
  <%
  if (msg != null) {
    out.print(msg);
  }
  %>
<p class="page">
<a href="index.jsp">トップページへ</a><br />
<a href="employee.jsp">社員一覧ページへ</a>
</p>
  <h2>部署一覧:</h2>
    <%
    if (depList.size() == 0) {
    %>
    <p>現在登録されている部署はありません。</p>
    <%
    } else {
    %>
  <table border="1">
    <tr>
      <th>ID</th>
      <th>部署名</th>
      <th colspan="2"></th>
    </tr>
    <%
    for (DepartmentBean depBean : depList) {
    %>
    <tr>
      <td><%=depBean.getDepartmentId()%></td>
      <td><%=depBean.getDepartment()%></td>
      <td>
        <form method="post" action="departmentAddEdit.jsp">
          <input type="hidden" name="departmentId"
            value="<%=depBean.getDepartmentId()%>" /> <input type="hidden"
            name="department" value="<%=depBean.getDepartment()%>" /> <input
            type="hidden" name="action" value="depEdit" /> <input
            type="submit" value="編集" />
        </form>
      </td>
      <td>
        <form method="post" action="DepartmentServlet">
          <input type="hidden" name="departmentId"
            value="<%=depBean.getDepartmentId()%>" /> <input type="hidden"
            name="action" value="depDelete" /> <input type="submit"
            value="削除" />
        </form>
      </td>
    </tr>
    <%
    }
    %>
  </table>
  <%
    }
    %>
  <p>
    <a href="departmentAddEdit.jsp?action=depAdd"><button
        type="button">新規作成</button></a>
  </p>

</body>
</html>