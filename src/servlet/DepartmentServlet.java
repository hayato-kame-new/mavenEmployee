package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DepartmentDAO;
import model.DepartmentBean;

/**
 * Servlet implementation class DepartmentServlet
 */
@WebServlet("/DepartmentServlet")
public class DepartmentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public DepartmentServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        response.getWriter().append("Served at: ").append(request.getContextPath());
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        String department = request.getParameter("department");
        String action = request.getParameter("action");
        // 新規の時は、hiddenフィールドから departmentIdは、null

        // デフォルトのフォワード先、結果ページへのパス
        String path = "/WEB-INF/jsp/result.jsp";
        // 結果ページへメッセージを出す
        String msg = "";
        String title = "成功";

        DepartmentDAO depDAO = new DepartmentDAO();
        //        行いたい操作によって、分岐
        boolean result = true; // データ新規や更新が成功したかどうか
        switch(action) {
        case "depAdd":
            DepartmentBean depBean = new DepartmentBean();
            // departmentIdカラム 主キーが、文字列なので、departmentIdを生成するメソッドを呼び出す
            String generatedId = depDAO.generateId(); // 生成した文字列の主キー departmentId を取得
            depBean.setDepartmentId(generatedId); // 主キーをセット
            depBean.setDepartment(department); // フォームから送られた部署名をセット
            // 新規登録します。新規登録のメソッドを呼び出します 成功したら、true 失敗したら falseを返します。
            result = depDAO.depAdd(depBean);
            if (!result) { // trueじゃなかったら、失敗だから、エラーメッセージを結果ページに出す
                msg = "データの新規登録に失敗しました";
                title = "失敗";
            }
            msg = "部署を新規登録しました。";
            break;
        case "depEdit":
            // 編集時は departmentId が hiddenフィールド で送られて来てる 実引数に渡して、データ更新をする
            String editDepName = request.getParameter("department"); // 変更したい名前
            // 送られて来た部署名に変更するメソッドを呼び出す
            result = depDAO.depUpdate(editDepName, request.getParameter("departmentId"));
            if(!result) { // trueじゃなかったら、失敗だから、エラーメッセージを結果ページに出す
                msg = "データの更新に失敗しました";
                title = "失敗";
            }
            msg = "部署名を更新しました";
            break;
        case "depDelete":
            // departmentId が hiddenフィールド で送られて来てる
            result = depDAO.depDelete(request.getParameter("departmentId"));
            if(!result) { // trueじゃなかったら、失敗 削除に失敗した時は、部署一覧に戻る
                msg = "データの削除に失敗しました";
            }
            // 成功しても、失敗しても、部署一覧に戻るので
            path = "department.jsp";
            msg = "データを削除しました。";
            break;
        case "cancel":
            // 部署一覧に行く
            path = "department.jsp";
            msg = "キャンセルしました。";
            break;
        }

         // リクエストスコープに保存する リクエストスコープは、フォワードはできる（リダイレクトはできない）
        request.setAttribute("msg", msg);
        request.setAttribute("title", title);
        request.setAttribute("action", action);
        // フォワード
        RequestDispatcher dispatcher = request.getRequestDispatcher(path);
        dispatcher.forward(request, response);

    }

}
