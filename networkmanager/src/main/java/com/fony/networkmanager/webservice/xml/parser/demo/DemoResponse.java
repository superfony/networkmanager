package com.fony.networkmanager.webservice.xml.parser.demo;

import android.os.Parcel;
import android.os.Parcelable;


/**
 * 序列化的实现
 * @author fony
 *
 */
public class DemoResponse implements Parcelable {
	public String result;
	public String message;
	public UserBean userBean;

	public static final Creator<DemoResponse> CREATOR = new Creator<DemoResponse>() {
		public DemoResponse createFromParcel(Parcel source) {
			DemoResponse field = new DemoResponse();
			field.result = source.readString();
			field.message = source.readString();
			field.userBean = source.readParcelable(UserBean.class
					.getClassLoader());
			return field;
		}

		public DemoResponse[] newArray(int size) {
			return new DemoResponse[size];
		}
	};

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int flags) {
		parcel.writeString(result);
		parcel.writeString(message);
		parcel.writeParcelable(userBean, Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
	}
	@Override
	public String toString() {
		return "BasicResponse [result=" + result + ", message=" + message
				+ ", entity=" + userBean + "]";
	}
}
