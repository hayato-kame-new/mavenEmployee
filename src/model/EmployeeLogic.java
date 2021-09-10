package model;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class EmployeeLogic {


    /**
     * データを全て読み込んでbyte配列に格納して返す staticなメソッド(静的メソッド クラスメソッド)
     * @param is
     * @return byte[]
     * @throws IOException
     */
    public static byte[] readAll(InputStream is) throws IOException {
        ByteArrayOutputStream bout = new ByteArrayOutputStream(); // newで確保
        byte[] buffer = new byte[1024];  // 1024バイト  1キロバイト

        while (true) { // 無限ループ注意
            int len = is.read(buffer); // 1024バイトだけ取り込む
            if (len < 0) { // ループの終わりの条件
                break;
            }
            bout.write(buffer, 0, len);  // 指定しただけoutputstreamにバッファに書き込みする　最後の端数も取り込めるようにする
        }
        return bout.toByteArray(); // 戻り値 byte[] です
        // 取り込んだものをバイト配列にして戻している
    }


}
