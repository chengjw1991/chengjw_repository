package com.cheng.beans;

/**
 * @Author ChengJW
 * 2021/1/6/006
 * 专门用于迁入
 */
public class QRData {
    private String tyshxydm;
    private String qrcode;

    public QRData() {
    }

    public QRData(String tyshxydm, String qrcode) {
        this.tyshxydm = tyshxydm;
        this.qrcode = qrcode;
    }

    public String getTyshxydm() {
        return tyshxydm;
    }

    public void setTyshxydm(String tyshxydm) {
        this.tyshxydm = tyshxydm;
    }

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }

    @Override
    public String toString() {
        return "QRData{" +
                "tyshxydm='" + tyshxydm + '\'' +
                ", qrcode='" + qrcode + '\'' +
                '}';
    }
}
