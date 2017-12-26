package com.example.apple.bakebar.Entity;

import java.util.Date;

/**
 * Created by apple on 2017/12/14.
 */

public class Talk  implements java.io.Serializable {


    // Fields

    private Integer talkId;
    private Integer imgId;
    private String ownerId;
    private String ownerName;
    private String talkerId;
    private String talkerName;
    private String talkBody;
    private Date talkDate;


    // Constructors

    /**
     * default constructor
     */
    public Talk() {
    }


    /**
     * full constructor
     */
    public Talk(Integer imgId, String ownerId, String ownerName, String talkerId, String talkerName, String talkBody, Date talkDate) {
        this.imgId = imgId;
        this.ownerId = ownerId;
        this.ownerName = ownerName;
        this.talkerId = talkerId;
        this.talkerName = talkerName;
        this.talkBody = talkBody;
        this.talkDate = talkDate;
    }


    // Property accessors

    public Integer getTalkId() {
        return this.talkId;
    }

    public void setTalkId(Integer talkId) {
        this.talkId = talkId;
    }

    public Integer getImgId() {
        return this.imgId;
    }

    public void setImgId(Integer imgId) {
        this.imgId = imgId;
    }

    public String getOwnerId() {
        return this.ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getTalkerId() {
        return this.talkerId;
    }

    public void setTalkerId(String talkerId) {
        this.talkerId = talkerId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getTalkerName() {
        return talkerName;
    }

    public void setTalkerName(String talkerName) {
        this.talkerName = talkerName;
    }

    public String getTalkBody() {
        return this.talkBody;
    }

    public void setTalkBody(String talkBody) {
        this.talkBody = talkBody;
    }

    public Date getTalkDate() {
        return this.talkDate;
    }

    public void setTalkDate(Date talkDate) {
        this.talkDate = talkDate;
    }

    @Override
    public String toString() {
        return "Talk [talkId=" + talkId + ", imgId=" + imgId + ", ownerId="
                + ownerId + ", ownerName=" + ownerName + ", talkerId="
                + talkerId + ", talkerName=" + talkerName + ", talkBody="
                + talkBody + ", talkDate=" + talkDate + "]";
    }
}