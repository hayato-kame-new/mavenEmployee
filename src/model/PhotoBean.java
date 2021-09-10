package model;
// JavaBeansのクラスは、パッケージに属する
import java.io.Serializable;

public class PhotoBean implements Serializable {
    // privateフィールドの定義
    /**
     * シリアル番号UID
     */
    private static final long serialVersionUID = -6614330110327145057L;
    private int photoId;
    private byte[] photoData;
    private String mime; // contentTypeです    "image/jpeg"   "image/png"など  "タイプ/サブタイプ"  MIMEタイプは常にタイプとサブタイプの両方を持ち、一方だけで使われることはありません。

    /**
     * JavaBeansは、引数なしのコンストラクタをもつ
     */
    public PhotoBean() {
        super();
    }
    // アクセッサ
    public int getPhotoId() {
        return photoId;
    }

    public byte[] getPhotoData() {
        return photoData;
    }

    public String getMime() {
        return mime;
    }

    public void setPhotoId(int photoId) {
        this.photoId = photoId;
    }

    public void setPhotoData(byte[] photoData) {
        this.photoData = photoData;
    }

    public void setMime(String mime) {
        this.mime = mime;
    }





}
