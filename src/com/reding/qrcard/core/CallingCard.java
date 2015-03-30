package com.reding.qrcard.core;

import java.util.Date;

import android.graphics.Bitmap;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

public class CallingCard {
	private String name;
	private String tel;
	private String mobile;
	private String web;
	private String email;
	private String company;
	private String position;
	private String address;
	private String remark;
	private Date birthday;
	private String fax;
	private String im;
	

	@Override
	public String toString() {
		return "BEGIN:VCARD\nVERSION:3.0\n"+
				"N:" + name + "\nEMAIL:"+email+"\nTEL:"+tel+"TEL;\nCELL:"+mobile+"\nADR:"+address+
				"\nWORK:"+company+"\nTITLE:"+position+"\nURL:"+web+"\nNOTE:"+remark+"\nEND:VCARD\n";
	}

	/**
	 * @param name
	 * @param tel
	 * @param mobile
	 * @param web
	 * @param email
	 * @param company
	 * @param position
	 * @param address
	 * @param remark
	 */
	public CallingCard(String name, String tel, String mobile, String web,
			String email, String company, String position, String address,
			String remark) {
		super();
		this.name = name;
		this.tel = tel;
		this.mobile = mobile;
		this.web = web;
		this.email = email;
		this.company = company;
		this.position = position;
		this.address = address;
		this.remark = remark;
	}
	
	

    /**
	 * 
	 */
	public CallingCard() {
		super();
		this.address="";
		this.company="";
		this.email="";
		this.mobile="";
		this.name="";
		this.position="";
		this.remark="";
		this.tel="";
		this.web="";
		this.fax="";
		this.im = "";
		this.birthday = new Date();
	}

	//creat a qrcode bitmap from a string
    public Bitmap Creat2DCode(String s) throws WriterException{
    	BitMatrix matrix = new MultiFormatWriter().encode(s, BarcodeFormat.QR_CODE, 400, 400);
		int width = matrix.getWidth();
		int height = matrix.getHeight();
		int[] pixels = new int[width*height];
		for(int y = 0;y<height;y++){
			for(int x = 0;x<width;x++)
			{
				if(matrix.get(x, y)){
					 pixels[y * width + x] = 0xff000000;
				}
			}
		}
		Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
		return bitmap;
    }
	
	

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}


	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}


	/**
	 * @return the tel
	 */
	public String getTel() {
		return tel;
	}


	/**
	 * @param tel the tel to set
	 */
	public void setTel(String tel) {
		this.tel = tel;
	}


	/**
	 * @return the mobile
	 */
	public String getMobile() {
		return mobile;
	}


	/**
	 * @param mobile the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}


	/**
	 * @return the web
	 */
	public String getWeb() {
		return web;
	}


	/**
	 * @param web the web to set
	 */
	public void setWeb(String web) {
		this.web = web;
	}


	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}


	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}


	/**
	 * @return the company
	 */
	public String getCompany() {
		return company;
	}


	/**
	 * @param company the company to set
	 */
	public void setCompany(String company) {
		this.company = company;
	}


	/**
	 * @return the position
	 */
	public String getPosition() {
		return position;
	}


	/**
	 * @param position the position to set
	 */
	public void setPosition(String position) {
		this.position = position;
	}


	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}


	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}


	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}


	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * @return the birthday
	 */
	public Date getBirthday() {
		return birthday;
	}

	/**
	 * @param birthday the birthday to set
	 */
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	/**
	 * @return the fax
	 */
	public String getFax() {
		return fax;
	}

	/**
	 * @param fax the fax to set
	 */
	public void setFax(String fax) {
		this.fax = fax;
	}

	/**
	 * @return the im
	 */
	public String getIm() {
		return im;
	}

	/**
	 * @param im the im to set
	 */
	public void setIm(String im) {
		this.im = im;
	}

}
