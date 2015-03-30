package com.reding.qrcard.core;

import java.sql.Date;
import java.util.regex.Pattern;

public class RegularExpression{
	
	
	public CallingCard regularEp(String cc)
	{
		CallingCard cToreturn = new CallingCard();
		String regex = "\n";
		Pattern p = Pattern.compile(regex);
		String[] s = p.split(cc);
		for(String x :s)
		{
			String regex2 = ":";
			Pattern xp = Pattern.compile(regex2);
			String[] xx = xp.split(x);
			if(xx[0].equals("N")){
				if(xx.length>1)
				cToreturn.setName(xx[1]);
			}
			if(xx[0].equals("EMAIL")){
				if(xx.length>1)
				cToreturn.setEmail(xx[1]);
			}
			if(xx[0].equals("TEL")){
				if(xx.length>1)
				cToreturn.setTel(xx[1]);
			}
			if(xx[0].equals("TEL;CELL")){
				if(xx.length>1)
				cToreturn.setMobile(xx[1]);
			}
			if(xx[0].equals("TEL;FAX")){
				if(xx.length>1)
				cToreturn.setFax(xx[1]);
			}
			if(xx[0].equals("ADR")){
				if(xx.length>1)
				cToreturn.setAddress(xx[1]);
			}
			if(xx[0].equals("WORK")){
				if(xx.length>1)
				cToreturn.setCompany(xx[1]);
			}
			if(xx[0].equals("TITLE")){
				if(xx.length>1)
				cToreturn.setPosition(xx[1]);
			}
			if(xx[0].equals("URL")){
				if(xx.length>1)
				cToreturn.setWeb(xx[1]);
			}
			if(xx[0].equals("NOTE")){
				if(xx.length>1)
				cToreturn.setRemark(xx[1]);
			}
			if(xx[0].equals("X-QQ")){
				if(xx.length>1)
				cToreturn.setIm(xx[1]);
			}
			if(xx[0].equals("BDAY")){
				if(xx.length>1)
				cToreturn.setBirthday(Date.valueOf(xx[1]));
			}
		}
		return cToreturn;
	}
}