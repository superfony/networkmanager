package com.fony.networkmanager.webservice.request.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 查询条件实体类
 * @author fony
 * 
 */
public class RequestPram implements Parcelable {
	public String methodName;
	public String param;
	public String param2;
	public String param3;
	public String param4;
	public String param5;
	public String param6;
	public String param7;
	public String param8;
	public String param9;
	public int bizId;
	public int pluginId;
	public String userid;
	public int pageNo;
	public int pageSize;
	public String password; // 用户密码
	public String user_type;
	public static final Creator<RequestPram> CREATOR = new Creator<RequestPram>() {
		public RequestPram createFromParcel(Parcel source) {
			RequestPram field = new RequestPram();
			field.methodName = source.readString();
			field.param = source.readString();
			field.param2 = source.readString();
			field.param3 = source.readString();
			field.param4 = source.readString();
			field.param5 = source.readString();
			field.param6 = source.readString();
			field.param7 = source.readString();
			field.param8 = source.readString();
			field.param9 = source.readString();
			field.bizId=source.readInt();
			field.pluginId=source.readInt();
			field.userid=source.readString();
			field.pageNo=source.readInt();
			field.pageSize=source.readInt();
			field.password=source.readString();
			field.user_type=source.readString();
			return field;
		}

		public RequestPram[] newArray(int size) {
			return new RequestPram[size];
		}
	};

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int flags) {
		parcel.writeString(methodName);
		parcel.writeString(param);
		parcel.writeString(param2);
		parcel.writeString(param3);
		parcel.writeString(param4);
		parcel.writeString(param5);
		parcel.writeString(param6);
		parcel.writeString(param7);
		parcel.writeString(param8);
		parcel.writeString(param9);

		parcel.writeInt(bizId);
		parcel.writeInt(pluginId);
		parcel.writeString(userid);
		parcel.writeInt(pageNo);
		parcel.writeInt(pageSize);
		parcel.writeString(password);
		parcel.writeString(user_type);

	}

	@Override
	public String toString() {
		return "[methodName=" + methodName +
				"  ,param=" + param+
				"  ,param2=" + param2+
				"  ,param3=" + param3+
				"  ,param4=" + param4+
				"  ,param5=" + param5+
				"  ,param6=" + param6+
				"  ,param7=" + param7+
				"  ,param8=" + param8+
				"  ,param9=" + param9+
				"]";
	}
}
