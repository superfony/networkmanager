package com.fony.networkmanager.webservice.xml.parser.demo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 *
 * @author fony
 * 2018年06月06日09:29:18
 */
public class UserBean implements Parcelable {
	public String userid;//用户id
	public String ename;//用户英文名
	public String chname;//中文名
	public String rosecode;//角色编码
	public String rosename;//角色名称
	public String password;//登录密码
	public String rember;//是否记住

	public static final Creator<UserBean> CREATOR = new Creator<UserBean>() {
		public UserBean createFromParcel(Parcel source) {
			UserBean field = new UserBean();
			field.userid = source.readString();
			field.ename = source.readString();
			field.chname = source.readString();
			field.rosecode = source.readString();
			field.rosename = source.readString();
			return field;
		}
		public UserBean[] newArray(int size) {
			return new UserBean[size];
		}
	};

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int flags) {
		parcel.writeString(userid);
		parcel.writeString(ename);
		parcel.writeString(chname);
		parcel.writeString(rosecode);
		parcel.writeString(rosename);
	}
}
