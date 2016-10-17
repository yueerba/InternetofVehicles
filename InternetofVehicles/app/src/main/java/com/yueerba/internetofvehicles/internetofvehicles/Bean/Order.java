package com.yueerba.internetofvehicles.internetofvehicles.Bean;

import android.os.Parcel;
import android.os.Parcelable;

public class Order implements Parcelable {

	public   int  id;
	public  String time;
	public  String address;
	public  String type;
	public  double price;
	public  double amount;
	public  double totalprice;
	public  String status;
	public  String username;
	
	
	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public Order() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public Order(String time, String address, String type, double price,
			double amount, double totalprice, String status, String username) {
		super();
		this.time = time;
		this.address = address;
		this.type = type;
		this.price = price;
		this.amount = amount;
		this.totalprice = totalprice;
		this.status = status;
		this.username = username;
	}


	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public double getTotalprice() {
		return totalprice;
	}
	public void setTotalprice(double totalprice) {
		this.totalprice = totalprice;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}


	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(this.id);
		dest.writeString(this.time);
		dest.writeString(this.address);
		dest.writeString(this.type);
		dest.writeDouble(this.price);
		dest.writeDouble(this.amount);
		dest.writeDouble(this.totalprice);
		dest.writeString(this.status);
		dest.writeString(this.username);
	}

	protected Order(Parcel in) {
		this.id = in.readInt();
		this.time = in.readString();
		this.address = in.readString();
		this.type = in.readString();
		this.price = in.readDouble();
		this.amount = in.readDouble();
		this.totalprice = in.readDouble();
		this.status = in.readString();
		this.username = in.readString();
	}

	public static final Creator<Order> CREATOR = new Creator<Order>() {
		@Override
		public Order createFromParcel(Parcel source) {
			return new Order(source);
		}

		@Override
		public Order[] newArray(int size) {
			return new Order[size];
		}
	};
}
