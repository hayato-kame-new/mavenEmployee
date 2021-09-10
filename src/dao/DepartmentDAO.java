package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.DepartmentBean;

public class DepartmentDAO {

    final String DRIVER_NAME = "org.postgresql.Driver";
    final String JDBC_URL = "jdbc:postgresql://localhost:5432/emp01";
    final String DB_USER = "postgres";
    final String DB_PASS = "postgres";

    /**
     * 部署のリストを取得する 失敗した時、null返す
     * @return depLists
     */
    public List<DepartmentBean> findAll() {
        // 中身は、ArrayListで作ること、ループで取り出したいから
        List<DepartmentBean> depList = new ArrayList<DepartmentBean>();

        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement pstmt = null;

        try {
            Class.forName(DRIVER_NAME);
            conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
            // PostgreSQL だと、order by departmentId を付けないと、順番が、更新されたのが一番最後の順になってします。
            String sql = "select departmentId, department from departments order by departmentId";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                String departmentId = rs.getString("departmentId");
                String department = rs.getString("department");
                DepartmentBean depBean = new DepartmentBean(departmentId, department);
                //            	DepartmentBean departmentBean = new DepartmentBean();
                //				departmentBean.setDepartmentId(departmentId);
                //				departmentBean.setDepartment(department);
                depList.add(depBean);
            }
        } catch (SQLException | ClassNotFoundException e) {
            // データベース接続やSQL実行失敗時の処理
            // JDBCドライバが見つからなかったときの処理
            e.printStackTrace();
            return null;
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    return null;
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    return null;
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }
        // 成功したら、リストを返す。一つも登録されてないときは、空のリストを返す
        return depList;
    }

    /**
     * 部署Idを生成する
     * @return id
     */
    public String generateId() {
        String id = null;

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            Class.forName(DRIVER_NAME);
            conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
            String sql = "select departmentId from departments order by departmentId desc limit 1";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            // limit 1 ですから、1つしか返さないから、while じゃなくて ifを使ってる
            if (rs.next()) {
                String result = rs.getString("departmentId");
                int resultInt = Integer.parseInt(result.substring(1));
                id = String.format("D%02d", resultInt + 1);
            } else {//何にも登録されてなかったら、一番目だから
                id = "D01";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    return null;
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    // クローズ処理失敗時の処理
                    e.printStackTrace();
                    return null;
                }
            }
            // データベース切断
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    // データベース切断失敗時の処理
                    e.printStackTrace();
                    return null;
                }
            }
        }
        return id;
    }

    /**
     * 部署を新規登録
     * @param depBean
     * @return true 成功<br> false 失敗
     */
    public boolean depAdd(DepartmentBean depBean) {

        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            Class.forName(DRIVER_NAME);
            conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
              String sql = "insert into departments (departmentId, department) values (?, ?)";
              pstmt = conn.prepareStatement(sql);
              pstmt.setString(1, depBean.getDepartmentId());
              pstmt.setString(2, depBean.getDepartment());
              // 成功したら、executeUpdateメソッドの戻り値は、更新された行数を表します。
              int result = pstmt.executeUpdate();
        if (result != 1) { // １データしか更新しないから（１データしか挿入しないから）
                  return false; // 失敗したら、falseを返す
              }
              // 1 が帰ったら成功なので、すすむ
        } catch (SQLException |  ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        } finally {
            // PrepareStatementインスタンスのクローズ処理
            if(pstmt != null) {
                try {
                    pstmt.close();
                } catch(SQLException e) {
                    e.printStackTrace();
                    return false;
                }
            }
            // データーベース切断
            if(conn != null) {
                try {
                    conn.close();
                } catch(SQLException e) {
                    e.printStackTrace();
                    return false;
                }
            }
        }
        return true; // ここまで来たら、成功なので trueを返す
    }
    /**
     * 部署名を更新する
     * @param editDepName
     * @param departmentId
     * @return true 成功<br />false 失敗
     */
    public boolean depUpdate(String editDepName, String departmentId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            Class.forName(DRIVER_NAME);
            conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
            String sql = "update departments set department = ? where departmentId = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, editDepName); // 引数で渡ってきた部署名をセットする
            pstmt.setString(2, departmentId); // 引数で渡って来た部署IDをセットする
         // 成功したら、executeUpdateメソッドの戻り値は、更新された行数を表します。
            int result = pstmt.executeUpdate();
            if (result != 1) { // １データしか更新しないから
                return false; // 失敗したら、falseを返す
            }
            // 1 が帰ったら成功なので、すすむ
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        } finally {
            // PrepareStatementインスタンスのクローズ処理
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    return false;
                }
            }
         // データーベース切断
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 部署データを削除する
     * @param departmentId
     * @return true 成功<br />false 失敗
     */
    public boolean depDelete(String departmentId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            Class.forName(DRIVER_NAME);
            conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
            String sql = "delete from departments where departmentId = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, departmentId); // 引数で渡って来たのをセットする
            // 成功したら、executeUpdateメソッドの戻り値は、更新された行数を表します。
            int result = pstmt.executeUpdate();
            if(result != 1) {
                return false;
            }
            // 1 だったら成功
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        } finally {
            // PrepareStatementインスタンスのクローズ処理
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    return false;
                }
            }
         // データーベース切断
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 部署名から、部署IDを取得する
     * @param department
     * @return departmentId
     */
    public String getDepartmentId(String department) {
        String departmentId = null;

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            Class.forName(DRIVER_NAME);
            conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
            String sql = "select departmentId from departments where department = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, department);
            rs = pstmt.executeQuery();
            if(rs.next()) { // departmentをuniqu にして一意にしてるから  while じゃなくて if にする 結果セットには1データだけしかないから
                departmentId = rs.getString(1); // 先頭のカラムから取得してる  rs.getString("departmentId")でもいい
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return null; // エラーが発生したら、nullを返す
        } finally {
            if(rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    return null; // エラーが発生したら、nullを返す
                }
            }
            if(pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    return null; // エラーが発生したら、nullを返す
                }
            }
            if(conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    return null; // エラーが発生したら、nullを返す
                }
            }
        }
        return departmentId;
    }


}
