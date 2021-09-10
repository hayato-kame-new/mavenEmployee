package servlet;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.EmployeeBean;

/**
 * Servlet implementation class CSVServlet
 */
@WebServlet("/CSVServlet")
public class CSVServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public CSVServlet() {
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
        // aリンクの クエリー文字列から取得できます。 aリンクは HTTPメソッドは GETです
        String action = request.getParameter("action"); // "csv"が入ってる

        //        System.out.printf("%tF", new java.util.Date());  // 2021-09-08

        // インスタンスは、employee.jspから aリンクで送られくるので、セッションスコープから取得する リクエストスコープからは取得できない
        // employee.jsp では、セッションスコープに保存する
        HttpSession session = request.getSession(); // セッションは、requestオブジェクトから、インスタンスメソッドで呼び出す。
        List<EmployeeBean> empList = (List<EmployeeBean>) session.getAttribute("empList");

        // Fileクラスのオブジェクトを作成  拡張子も書く
        String file_name = "/csv_result.csv";
        File file = new File(System.getProperty("user.home") + "/Desktop" + "/csv_result.csv"); // ユーザのデスクトップに、ファイルを作ろうとしてる
        try {
            file.createNewFile(); // createNewFile()メソッドで、ファイルを作成する。もしすでにファイルが存在してたら 何もしない falseが返るだけ
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 最後に bw.close() osw.close() fos.close()の順番で クローズ処理する
        if (file.exists()) {
            FileOutputStream fos = null;
            OutputStreamWriter osw = null;
            BufferedWriter bw = null;
            try {
                fos = new FileOutputStream(file);
                // 文字コードを指定して ファイルに書き込みます
                osw = new OutputStreamWriter(fos, "UTF-8");
                bw = new BufferedWriter(osw);

                bw.write(String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s\n", "社員ID", "名前", "年齢", "性別", "写真ID", "住所", "部署ID",
                        "入社日", "退社日"));
                for (EmployeeBean bean : empList) {
                    bw.write(String.format("%s,%s,%d,%d,%d,%s,%s,%tF,%tF\n", bean.getEmployeeId(), bean.getName(), bean.getAge(),bean.getGender(), bean.getPhotoId(), bean.getFullAddress(), bean.getDepartmentId(), bean.getHireDate(), bean.getRetirementDate()));
                }

                bw.flush();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (bw != null) {
                    try {
                        bw.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (osw != null) {
                    try {
                        osw.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        // リクエストスコープへセットする
        request.setAttribute("action", action);
        request.setAttribute("empList", empList);
        request.setAttribute("csvMsg", "csvファイル出力しました");
        //  フォワード
        RequestDispatcher dispatcher = request.getRequestDispatcher("employee.jsp");
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
