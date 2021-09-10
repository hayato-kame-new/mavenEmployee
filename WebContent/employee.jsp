<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="dao.EmployeeDAO, java.util.*, model.EmployeeBean" %>

<%
// リクエスト情報の文字化け防止 request は、JSPで使える暗黙オブジェクト
request.setCharacterEncoding("UTF-8");
// 削除した後のメッセージを取得 リクエストスコープから取得する
String deleteMsg = (String)request.getAttribute("deleteMsg");
// 検索した後のメッセージを取得
String searchMsg = (String)request.getAttribute("searchMsg");
// csvファイル出力後のメッセージ取得
String csvMsg = (String)request.getAttribute("csvMsg");
// 従業員一覧表示用に、従業員リストの取得
EmployeeDAO empDAO = new EmployeeDAO();
List<EmployeeBean> empList = new ArrayList<EmployeeBean>(); // new でまずメモリ上の確保をする
String action = (String)request.getAttribute("action");
// 一番最初のアクセスは action には null が入ってる  キャンセルボタンを押した時には、
// クエリー文字列で employee?action=cancel で送られてくる
if (action == null || action.equals("cancel")) { // 一覧を表示する
  empList = empDAO.findAll(); // 戻り値は、コレクション ArrayList<EmployeeBean>型オブジェクト
} else if (action.equals("search") || action.equals("csv")) {
  empList =(List<EmployeeBean>)request.getAttribute("empList");
}

// CSVServletで使うので, empListを セッションスコープにセットする aリンクからのCSVServletへアクセスなので、
// セッションスコープを使う session は、JSPで使える暗黙オブジェクト
session.setAttribute("empList", empList);

%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>社員データベース管理ページ</title>
<style>
th {
  background-color: #0099ff;
}
</style>
</head>
<body>
<p class="page">
<a href="index.jsp">トップページへ</a><br />
<a href="department.jsp">部署一覧ページへ</a>
</p>
<h2>社員一覧:</h2>
<p>
<%
if (deleteMsg != null) {
  out.print(deleteMsg);
}
if (searchMsg != null) {
  out.print(searchMsg);
}if (csvMsg != null) {
    out.print(csvMsg);
  }
%>
</p>
<% if(empList.size() != 0) { %>
<table border="1">
<tr>
<th>社員ID</th><th>名前</th><th colspan="2"></th>
</tr>
<% for(EmployeeBean empBean : empList) { %>
<tr>
<td><%= empBean.getEmployeeId() %></td>
<td><%= empBean.getName() %></td>
<td>
<!-- フォーム送信でも、 method="GET" にすれば、EmployeeServletのdoGetを実行できる
method="GET" にすると、inputタグの内容は、クエリー文字列になって送られます URLの末尾に?employeeId=○○○&action=edit  という風になって送られます -->
  <form action="EmployeeServlet" method="GET" >
    <input type="hidden" name="employeeId" value="<%=empBean.getEmployeeId() %>" >
    <input type="hidden" name="action" value="edit" >
    <input type="submit" value="編集" />
  </form>
  <!-- aリンクでもいい、HTTPメソッドは、GETメソッドなので クエリー文字列で、送る -->
<!-- <a href="EmployeeServlet?employeeId=<   %   =   empBean.getEmployeeId()%>&action=edit"><button type="button">編集</button></a>  -->

</td>
<td>
  <form action="DeleteServlet" method="POST" >
    <input type="hidden" name="employeeId" value="<%=empBean.getEmployeeId() %>" >
    <input type="hidden" name="photoId" value="<%=empBean.getPhotoId() %>" >
    <input type="submit"  value="削除" />
  </form>

</td>
</tr>
<% } %>
</table>
<% } %>

<p>
<!-- aリンクだと、HTTPメソッドは、GETメソッドなので クエリー文字列で、送る 文字列しか送れない formタグでもmethod="GET"にして送ると、hiddenタグ内容が クエリー文字列として送られます
-->
<a href="EmployeeServlet?action=add"><button type="button">新規追加</button></a>
</p>
<p>
<a href="search.jsp"><button type="button">検索...</button></a>
</p>
<% if(empList.size() != 0) {%>
<p>
<!-- csvファイル出力する リストは、検索した後は、検索結果のリストになる もし、empListのサイズが 0の時は、CSVファイルは出力できないように非表示にする -->
<!-- aリンクは、クエリー文字列しか送れないので、リクエストスコープに リストをセットしてる -->
<a href="CSVServlet?action=csv"><button type="button">CSVファイルに出力</button></a>
</p>
<% } %>
</body>
</html>