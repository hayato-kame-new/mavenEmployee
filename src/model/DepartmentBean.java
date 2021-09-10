package model;

import java.io.Serializable;

public class DepartmentBean implements Serializable {
    // privateフィールドの定義
    /**
     * シリアル番号UID  Javaコンパイラーのエラー/警告設定の中の「serialVersionUIDなしのシリアライズ可能クラス」を「警告」に設定します。
     */
    private static final long serialVersionUID = 7893445846277417558L;
    private String departmentId; // 部署ID 主キー
    private String department; // 部署名


    // JavaBeansに必要な引数なしのコンストラクタ
    public DepartmentBean() {
        super();
    }

    /**
     * 引数2つ有りのコンストラクタ
     * @param departmentId
     * @param department
     */
    public DepartmentBean(String departmentId, String department) {
        super();
        this.departmentId = departmentId;
        this.department = department;
    }

    // アクセッサ
    public String getDepartmentId() {
        return departmentId;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

}
