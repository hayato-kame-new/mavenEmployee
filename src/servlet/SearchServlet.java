package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DepartmentDAO;
import dao.EmployeeDAO;
import model.EmployeeBean;

/**
 * Servlet implementation class SearchServlet
 */
@WebServlet("/SearchServlet")
public class SearchServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // TODO Auto-generated method stub
        response.getWriter().append("Served at: ").append(request.getContextPath());
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action"); // 値 "search" が入ってる

        // リクエストパラメータから取得 未選択の時にはそれぞれ "" 空文字 が入ってきます。
        String department = request.getParameter("department");
        String employeeId = request.getParameter("employeeId");
        String word = request.getParameter("word");

        // 部署の名前から、部署IDを取得する
        DepartmentDAO depDAO = new DepartmentDAO();
        // department は未選択の時には、"" 空文字 が入っているので、departmentId も ""空文字にする
        String departmentId = "";
        if (!department.equals("")) { // 空文字じゃなかったら、つまり、部署名が選択されていたら
            departmentId = depDAO.getDepartmentId(department); // 検索してくる
        }

        // 検索メソッドを使う 検索結果は、リストで取得する
        // 一つもデータが検索されない場合空のリストが返る  ArrrayaListは、内部は、配列なので、[]が返る
        // 検索が失敗したら nullが返る
        EmployeeDAO empDAO = new EmployeeDAO();
        List<EmployeeBean> empList = new ArrayList<EmployeeBean>(); // newで確保
        empList = empDAO.search(departmentId, employeeId, word);
        String searchMsg = "検索結果です。";
        if (empList == null) {
            searchMsg = "エラー発生検索失敗しました。";
        } else if (empList.size() == 0) {
            searchMsg = "検索条件に合致する、社員がありません。";
        }

        // リクエストスコープに保存する
        request.setAttribute("searchMsg", searchMsg);
        request.setAttribute("action", action);
        request.setAttribute("empList", empList);

        // フォワード先
        RequestDispatcher dispatcher = request.getRequestDispatcher("employee.jsp");
        dispatcher.forward(request, response);

    }

}
