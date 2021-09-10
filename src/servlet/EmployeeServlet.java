package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.EmployeeDAO;
import model.EmployeeBean;

/**
 * Servlet implementation class EmployeeServlet
 */
@WebServlet("/EmployeeServlet")
public class EmployeeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     * 表示を行うサーブレット
     */
    public EmployeeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // リクエスト情報の文字化け防止
        request.setCharacterEncoding("UTF-8");
        // 新規登録は、employeeAddEdit.jsp の aリンクのクエリーパラメータから、送られてくる
        // Checkサーブレットで、入力チェックの際に、エラーメッセージのリストに要素が入ってると、またこの EmployeeServlet()にフォワードしてくる
        // そして、また入力をしてもらう actionは、
        String action = request.getParameter("action"); // "cancel"の時には、 クエリーパラメータで送られてくる
        // actionの値が "add"の時は、新しいインスタンス(各フィールドは、各データ型の規定値)を生成してそれを リクエストスコープに保存して employeeAddEdit.jspへフォワードする
        // actionの値が "edit" "delete" の時は、クエリーパラメータで送られ来た(formからGETアクセスだから)employeeIdを取得する
        EmployeeDAO empDAO = new EmployeeDAO();
        EmployeeBean empBean = null;
        // 編集時 クエリーパラメータで送られ来たキーemployeeIdの 値を取得して、それを元に、データベースから該当するEmployeeBeanオブジェクトを取得してくる
        String employeeId = request.getParameter("employeeId"); // 引数が、クエリパラメータのキーです

        switch (action) {
        case "add":
            // 新規登録する時は、新しいインスタンスを生成 各フィールドは、それぞれのデータ型の規定値となってる
            empBean = new EmployeeBean();
            break;
        case "edit":
            empBean = empDAO.findEmpBean(employeeId);
            break;
        }

        // リクエストスコープに保存する。リクエストスコープは、フォワードできる(リダイレクトはできない)
        // リクエストスコープに保存できるのは、参照型 クラス型のインスタンスだけ。自分で作ったクラスは、JavaBeansのクラスにすること
        request.setAttribute("empBean", empBean);
        request.setAttribute("action", action);
        //  フォワードする
        RequestDispatcher dispatcher = request.getRequestDispatcher("employeeAddEdit.jsp");
        dispatcher.forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }

}
