package servlet;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.PhotoDAO;

/**
 * Servlet implementation class PhotoDisplayServlet
 */
@WebServlet("/PhotoDisplayServlet")
public class PhotoDisplayServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public PhotoDisplayServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // imgタグからのクエリー文字列で、送られてきてるのを取得する
        request.setCharacterEncoding("UTF-8");
        String strPhotoId = request.getParameter("photoId"); // クエリー文字列から取得すると文字列
        // photoIdフィールドは、int型なので、変換する
        int photoId = Integer.parseInt(strPhotoId);
        // photoIdを元にして、データベースのphotosテーブルから画像データを取得する
        PhotoDAO phoDAO = new PhotoDAO();
        byte[] photoData = phoDAO.getPhotoData(photoId);
   //    byte[] photoData = phoDAO.getPhotoData(4);
       // photoIdを元にして、データベースのphotosテーブルからMIMEタイプ(コンテンツタイプ)を取得する
        // "image/jpeg"  "image/png" など "タイプ/サブタイプ" という形です。MIMEタイプは常にタイプとサブタイプの両方を持ち、一方だけで使われることはありません。
        String mime = phoDAO.getMime(photoId);
        // 新規登録の時には、フォームでは、photoData 必ず渡ってくるようにするけど。 でも、編集の時、 nullの時もある。データベース直接nullを入れてる時など。
        if (photoData != null) { // null回避 nullじゃなかったら、表示するようにするので
            try {
                ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
                byteOut.write(photoData); // ByteArrayOutputStreamオブジェクトに、データベースから取得したbyte配列データを書き込みます
                // 画像をクライアントに返却する
                response.setContentType(mime); // mime には "image/jpeg" とかが入ってる
                response.setContentLength(byteOut.size());

                OutputStream out = response.getOutputStream();
                out.write(byteOut.toByteArray());
                out.flush();
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }

}
