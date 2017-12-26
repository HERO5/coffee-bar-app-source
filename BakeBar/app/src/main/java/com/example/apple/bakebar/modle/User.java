package com.example.apple.bakebar.modle;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by apple on 2017/11/1.
 */

public class User implements Serializable {

    private String userId;
    private String userPwd;
    private String userName;
    private Date userCreateTime;
    private String userMail;
    private String userRealName;
    private String userPhone;
    private String userQq;
    private String userAddress;
    private String userHometown;
    private String userImg;

    // Constructors

    /** default constructor */
    public User() {
    }

    /** minimal constructor */
    public User(String userPwd, String userName, Date userCreateTime,
                String userPhone) {
        this.userPwd = userPwd;
        this.userName = userName;
        this.userCreateTime = userCreateTime;
        this.userPhone = userPhone;
    }

    /** full constructor */
    public User(String userPwd, String userName, Date userCreateTime,
                String userMail, String userRealName, String userPhone,
                String userQq, String userAddress, String userHometown,String userImg) {
        this.userPwd = userPwd;
        this.userName = userName;
        this.userCreateTime = userCreateTime;
        this.userMail = userMail;
        this.userRealName = userRealName;
        this.userPhone = userPhone;
        this.userQq = userQq;
        this.userAddress = userAddress;
        this.userHometown = userHometown;
        this.userImg = userImg;
    }

    // Property accessors

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserPwd() {
        return this.userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getUserCreateTime() {
        return this.userCreateTime;
    }

    public void setUserCreateTime(Date userCreateTime) {
        this.userCreateTime = userCreateTime;
    }

    public String getUserMail() {
        return this.userMail;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }

    public String getUserRealName() {
        return this.userRealName;
    }

    public void setUserRealName(String userRealName) {
        this.userRealName = userRealName;
    }

    public String getUserPhone() {
        return this.userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserQq() {
        return this.userQq;
    }

    public void setUserQq(String userQq) {
        this.userQq = userQq;
    }

    public String getUserAddress() {
        return this.userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getUserHometown() {
        return this.userHometown;
    }

    public void setUserHometown(String userHometown) {
        this.userHometown = userHometown;
    }

    public String getUserImg() {
        return userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }

    @Override
    public String toString() {
        return "User [userId=" + userId + ", userPwd=" + userPwd
                + ", userName=" + userName + ", userCreateTime="
                + userCreateTime + ", userMail=" + userMail + ", userRealName="
                + userRealName + ", userPhone=" + userPhone + ", userQq="
                + userQq + ", userAddress=" + userAddress + ", userHometown="
                + userHometown + ", userImg=" + userImg + "]";
    }

}
