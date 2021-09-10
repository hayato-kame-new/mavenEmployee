<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
// フォワード先の結果を表示するjspファイルなので、WEB-INF配下にjspフォルダを作って そこに入れる　HTTPメソッドでアクセスできないようにする
// リクエストスコープから、リクエストスコープ変数を取得
String msg = (String)request.getAttribute("msg");
String title = (String)request.getAttribute("title");
String action = (String)request.getAttribute("action");

// 次の遷移先のパス
String nextPath = "";
String next = "";
if (action.equals("depAdd") || action.equals("depEdit")) {
  nextPath = "department.jsp";
  next = "部署一覧ページ";
}
if (action.equals("add") || action.equals("edit")) {
  nextPath = "employee.jsp";
    next = "従業員一覧ページ";
}


%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>データベース更新<%=title %></title>
</head>
<body>
<%
if(msg != null) {
  out.print(msg);
}
%>
<p><a href="<%=nextPath %>"><%=next %>へ戻る</a></p>
</body>
</html>