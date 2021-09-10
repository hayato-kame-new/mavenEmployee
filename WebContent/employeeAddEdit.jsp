<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ page
  import="model.EmployeeBean, java.util.*, dao.DepartmentDAO, model.DepartmentBean, java.util.Date, java.text.SimpleDateFormat"%>
<%
// リクエスト情報とか、リクエストスコープからのスコープ変数の値を取得するための、文字化け対策
request.setCharacterEncoding("UTF-8");
// EmployeeServletから、フォワードで送られて来た。リクエストスコープから取得する getAttributeメソッドを使う
String action = (String) request.getAttribute("action");
// 新規も編集も EmployeeServletから、EmployeeBeanオブジェクトが フォワードで送られて来ます　リクエストスコープから EmployeeBeanオブジェクトを取得
EmployeeBean empBean = (EmployeeBean)request.getAttribute("empBean");

String label = action.equals("add") ? "新規作成" : "編集";
// 新規登録の時には、empBeanオブジェクトの各フィールドには、各データ型の規定値が デフォルトとして入っているので、String型などの参照型の規定値はnull だから
// 編集の時には、
String employeeId =empBean.getEmployeeId(); // 新規登録の時には、employeeIdは 表示しない
// フォームに表示する場合に、参照型のフィールドの場合 null だったら、空文字を、フォームの初期値にします。 value属性の値が、フォームの初期値です。
String name = empBean.getName() == null ? "" : empBean.getName();
// 新規登録の時、empBeanオブジェクトの 基本データ型(プリミティブ型)のフィールドの規定値は、int型なら 0 です それはそのまま使う
int gender = empBean.getGender(); // 新規では、int型の 0 が規定値として入っている 編集時は、男性なら 1  女性なら 2 が取得できる
int photoId = empBean.getPhotoId(); // 新規では、int型の 0 が規定値として入っている
String zipNumber = empBean.getZipNumber() == null ? "" : empBean.getZipNumber();

String pref = empBean.getPref(); // empBeanオブジェクトの参照型のフィールドなので新規では nullが入ってる
// 都道府県リスト
List<String> prefList = new ArrayList<String>(Arrays.asList("東京都", "神奈川県", "埼玉県", "千葉県", "茨城県"));
// 新規との時は、 参照型フィールドの規定値null なので 空文字にする
String address = empBean.getAddress() == null ? "" : empBean.getAddress();
// 新規との時は、 参照型フィールドの規定値null が入ってる、nullのまま
String departmentId = empBean.getDepartmentId();

// DepartmentBeanオブジェクト のリスト
DepartmentDAO depDAO = new DepartmentDAO();
List<DepartmentBean> depList = depDAO.findAll();

// 入社日は、 新規では null が入ってる  編集では必ず入ってる(Date型)
Date hireDate = empBean.getHireDate();
// 退社日は、新規の時は null   編集の時も null ありうる 入っていれば Date型で渡ってくる
Date retirementDate = empBean.getRetirementDate();
// 書式の作成
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");


// 表示用の文字列 value属性の値を設定してる
String hireStr = "";
String retireStr = "";
if (hireDate == null && retirementDate == null) { // 新規作成の時、
  // 何もしない  hireStr  retireStr  の値  ""  のまま
} else if (hireDate != null && retirementDate == null){ // 編集の時には、必ず入社日入ってる、退社日 nullの時   退社日 nullありうる
  hireStr = sdf.format(hireDate.getTime()); // Date型の入社日を 表示用文字列にする   退社日は、 null だから、"" のまま
} else if (hireDate != null && retirementDate != null) { //編集時に 退社日も入ってる時
  hireStr = sdf.format(hireDate.getTime()); // Date型の入社日を 表示用文字列にする
  retireStr = sdf.format(retirementDate.getTime()); // Date型の退社日を 表示用文字列にする
}

// 入力チェックしたときに、エラーが出たら、リクエストスコープにエラーリストが保存されてる CheckServletの 177行目で request.setAttribute("errMsgList", errMsgList);
List<String> errMsgList = (List<String>)request.getAttribute("errMsgList");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>社員データ<%=label%></title>
</head>
<body>
  <h2>
    社員データを<%=label%>します
  </h2>
  <p>(<small style="color:red;">※</small>)印は、入力必須です。</p>
  <!--  != null だけでもいい -->
  <% if(errMsgList != null && errMsgList.size() != 0) {%>
  <p><%=errMsgList %></p>
<% } %>
  <!-- フォーム送信先は、チェック用サーブレットです。
 まず、photosテーブルについて操作をできるようにする
 CheckServletでは、@MultipartConfigアノテーションを クラス宣言の上に付けてください -->

  <form action="CheckServlet" enctype="multipart/form-data" method="post">
    <input type="hidden" name="action" value="<%=action%>">
    <input type="hidden" name="employeeId" value="<%=employeeId %>" >
    <!-- 新規では、photoId の値は 0  -->
    <input type="hidden" name="photoId" value="<%=photoId %>" >
  <%if (employeeId != null) { // 新規登録の時には、表示しない %>
    <p>
      社員ID:<%=employeeId%>
    </p>
    <% } %>
    <p>
      名前(<small style="color:red;">※</small>):<input type="text" name="name" value="<%=name%>" />
    </p>
    <p>
      年齢(<small style="color:red;">※</small>):<input type="text" name="age" value="<%=empBean.getAge()%>" />
    </p>
    <p>
      性別(<small style="color:red;">※</small>):
      <%
    switch (gender) {
    case 0: //新規の時は 0 がint型の規定値なので入っています。
    %>
      <input type="radio" name="gender" value="1" />男性 <input type="radio"
        name="gender" value="2" />女性
      <%
      break;
      case 1: // 編集時は、男性なら int型の 1 が入っています
      %>
      <input type="radio" name="gender" value="1" checked="checked" />男性 <input
        type="radio" name="gender" value="2" />女性
      <%
      break;
      case 2: // 編集時は、女性なら int型の 2 が入っています
      %>
      <input type="radio" name="gender" value="1" />男性 <input type="radio"
        name="gender" value="2" checked="checked" />女性
      <%
      break;
      }
      %>
    </p>

    <!-- 写真表示 -->
    <p>
      <!-- 写真の表示は、PhotoDisplayServletがコントローラーです クエリー文字列で、キーphotoId 値が empBean.getPhotoId() で送ってるので、 HTTPメソッドは、GETメソッド -->
      写真:<img src="PhotoDisplayServlet?photoId=<%=empBean.getPhotoId()%>"
        alt="写真" title="社員の写真" width="300" height="250" />
    </p>
    <!-- 写真表示ここまで -->
    <!-- 写真アップロード formタグには enctype="multipart/form-data" が必要です。 また、送信先のサーブレットには、クラス宣言のところに @MultipartConfigアノテーション が必要です -->
    (<small style="color:red;">※</small>)<input type="file" name="image" accept=".jpeg, .jpg, .png" /> <input
      type="hidden" name="photoId" value="" />
    <!-- 写真アップロードここまで -->

    <p>
    <small>半角数字で ×××-×××× の形式で入力してください。</small><br />
      郵便番号(<small style="color:red;">※</small>):<input type="text" name="zipNumber" value="<%=zipNumber%>" />
    </p>

    <p>
      都道府県(<small style="color:red;">※</small>):<select name="pref">
        <%
        if (pref == null) { // 新規の時は、参照型フィールドの規定値の null が入ってる
          for (String p : prefList) {
        %>
        <option value="<%=p%>"><%=p%></option>
        <%
        }
        %>
        <%
        } else { // 編集の時 表示した時に、選択済みにしておく
        for (String p : prefList) { //拡張ループを回しながら、リストの中の要素と、同じのを、選択済みにする
          if (p.equals(pref)) {
        %>
        <option value="<%=p%>" selected><%=p%></option>
        <%
        } else {
        %>
        <option value="<%=p%>"><%=p%></option>
        <%
        }
        }
        }
        %>
      </select>
    </p>

    <p>
      住所(<small style="color:red;">※</small>):<input type="text" name="address" value="<%=address%>" />
    </p>

    <p>
      所属(<small style="color:red;">※</small>):<select name="department">
        <%
        if (departmentId == null) { // 新規の時には null が入ってる
          for (DepartmentBean depBean : depList) {
        %>
        <option value="<%=depBean.getDepartment()%>"><%=depBean.getDepartment()%></option>
        <%
        }
        } else { // 編集の時には、リクエストスコープから取得した、employeeBeanオブジェクトのフィールドの値を調べて、同じものを選択済みにする
        for (DepartmentBean depBean : depList) {
        if (depBean.getDepartmentId().equals(empBean.getDepartmentId())) {
        %>
        <option value="<%=depBean.getDepartment()%>" selected><%=depBean.getDepartment()%></option>
        <%
        } else {
        %>
        <option value="<%=depBean.getDepartment()%>"><%=depBean.getDepartment()%></option>
        <%
        }
        }
        }
        %>
      </select>
    </p>

    <p>
    <small>半角数字で ××××-××-×× の形式で入力してください。 例( 2016-03-20 )</small><br />
    入社日(<small style="color:red;">※</small>):<input type="text" name="hireDate" value="<%=hireStr %>" >
    </p>

    <p>
    <small>半角数字で ××××-××-×× の形式で入力してください。 例( 2016-03-20 )</small><br />
      退社日:<input type="text" name="retirementDate" value="<%=retireStr %>" >
    </p>
    <input type="submit" value="送信" />
    <!-- CheckServletへ行く -->
  </form>

  <!-- キャンセルボタンを押したときに HTTPメソッド GET クエリー文字列で送られます employee.jsp?action=cancel  aリンクと同じようになる -->
  <form action="employee.jsp" method="GET">
    <input type="hidden" name="action" value="cancel" />
    <input type="submit" value="キャンセル" />
  </form>

</body>
</html>