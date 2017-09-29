package com.caiyi.data;

import java.io.Serializable;

public class TrendData implements Serializable {
    private static final long serialVersionUID = -6330569541989274173L;
    private String balls;
    private String blue;
    private String bss;
    private String codes;
    private String five;
    private String form;
    private String four;
    private String mulPos;
    private String num;
    private String oes;
    private String one;
    private String pid;
    private String playType;
    private String red;
    private String space;
    private String sum;
    private String three;
    private String times;
    private String two;
    private String type;

    public String getNum() {
        return this.num;
    }

    public void setNum(String str) {
        this.num = str;
    }

    public String getCodes() {
        return this.codes;
    }

    public void setCodes(String str) {
        this.codes = str;
    }

    public String getSum() {
        return this.sum;
    }

    public void setSum(String str) {
        this.sum = str;
    }

    public String getSpace() {
        return this.space;
    }

    public void setSpace(String str) {
        this.space = str;
    }

    public String getTimes() {
        return this.times;
    }

    public void setTimes(String str) {
        this.times = str;
    }

    public String getForm() {
        return this.form;
    }

    public void setForm(String str) {
        this.form = str;
    }

    public String getPid() {
        return this.pid;
    }

    public void setPid(String str) {
        this.pid = str;
    }

    public String getRed() {
        return this.red;
    }

    public void setRed(String str) {
        this.red = str;
    }

    public String getBlue() {
        return this.blue;
    }

    public void setBlue(String str) {
        this.blue = str;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String str) {
        this.type = str;
    }

    public String getOes() {
        return this.oes;
    }

    public void setOes(String str) {
        this.oes = str;
    }

    public String getBss() {
        return this.bss;
    }

    public void setBss(String str) {
        this.bss = str;
    }

    public String getBalls() {
        return this.balls;
    }

    public void setBalls(String str) {
        this.balls = str;
    }

    public String getOne() {
        return this.one;
    }

    public void setOne(String str) {
        this.one = str;
    }

    public String getTwo() {
        return this.two;
    }

    public void setTwo(String str) {
        this.two = str;
    }

    public String getThree() {
        return this.three;
    }

    public void setThree(String str) {
        this.three = str;
    }

    public String getMulPos() {
        return this.mulPos;
    }

    public void setMulPos(String str) {
        this.mulPos = str;
    }

    public String getFour() {
        return this.four;
    }

    public void setFour(String str) {
        this.four = str;
    }

    public String getFive() {
        return this.five;
    }

    public void setFive(String str) {
        this.five = str;
    }

    public String toString() {
        return "TrendData [pid=" + this.pid + ", red=" + this.red + ", blue=" + this.blue + ", type=" + this.type + ", balls=" + this.balls + ", oes=" + this.oes + ", bss=" + this.bss + ", one=" + this.one + ", two=" + this.two + ", three=" + this.three + ", mulPos=" + this.mulPos + ", four=" + this.four + ", five=" + this.five + ", playType=" + this.playType + "]";
    }

    public String getPlayType() {
        return this.playType;
    }

    public void setPlayType(String str) {
        this.playType = str;
    }
}
