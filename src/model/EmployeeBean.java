package model;

import java.io.Serializable;
import java.util.Date;

public class EmployeeBean implements Serializable {

    /**
     * シリアル番号UID
     */
    private static final long serialVersionUID = -1224771227435510280L;
    private String employeeId;
    private String name;
    private int age;
    private int gender; //性別  1: 男  2: 女
    private int photoId;
    private String zipNumber;
    private String pref;
    private String address; // 都道府県名の下の住所
    private String departmentId;
    private Date hireDate; // java.util  入社日
    private Date retirementDate; // java.util  退社日

    /**
     * 引数なしのコンストラクタ JavaBeansのルール
     * 引数ありのコンストラクタを作ると、デフォルトコンストラクタは作れないので、明示的に作る必要ある
     */
    public EmployeeBean() {
        super();
    }

    /**
     * 引数ありのコンストラクタ
     * @param employeeId
     * @param name
     * @param age
     * @param gender
     * @param photoId
     * @param zipNumber
     * @param pref
     * @param address
     * @param departmentId
     * @param hireDate
     * @param retirementDate
     */
    public EmployeeBean(String employeeId, String name, int age, int gender, int photoId, String zipNumber, String pref,
            String address, String departmentId, Date hireDate, Date retirementDate) {
        super();
        this.employeeId = employeeId;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.photoId = photoId;
        this.zipNumber = zipNumber;
        this.pref = pref;
        this.address = address;
        this.departmentId = departmentId;
        this.hireDate = hireDate;
        this.retirementDate = retirementDate;
    }


    /**
     * 住所を表示する fullAdressプロパティを作る
     * @return 住所
     */
    public String getFullAddress() {
        return "〒" + this.zipNumber + this.pref + this.address;
    }
    /**
     * 性別をint型から文字列にする
     * @param gender
     * @return 1:男<br>2:女
     */
    public String getStringGender(int gender) {
        String str = "";
        switch(gender) {
        case 1:
            str = "男";
            break;
        case 2:
            str = "女";
            break;
        }
        return str;
    }
    // アクセッサ
    public String getEmployeeId() {
        return employeeId;
    }
    public String getName() {
        return name;
    }
    public int getAge() {
        return age;
    }
    public int getGender() {
        return gender;
    }
    public int getPhotoId() {
        return photoId;
    }
    public String getZipNumber() {
        return zipNumber;
    }
    public String getPref() {
        return pref;
    }
    public String getAddress() {
        return address;
    }
    public String getDepartmentId() {
        return departmentId;
    }
    public Date getHireDate() {
        return hireDate;
    }
    public Date getRetirementDate() {
        return retirementDate;
    }
    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public void setGender(int gender) {
        this.gender = gender;
    }
    public void setPhotoId(int photoId) {
        this.photoId = photoId;
    }
    public void setZipNumber(String zipNumber) {
        this.zipNumber = zipNumber;
    }
    public void setPref(String pref) {
        this.pref = pref;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }
    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }
    public void setRetirementDate(Date retirementDate) {
        this.retirementDate = retirementDate;
    }





}
