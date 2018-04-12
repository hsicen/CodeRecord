package com.toucheart.library.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.telephony.TelephonyManager;

import java.util.UUID;

/**
 * 作者：Toucheart  2017/9/2 16:59
 * 邮箱：codinghuang@163.com
 * 作用：通过 android.os.Build 和 SystemProperty两个类来获取
 * 描述：PhoneUtils
 */
public class PhoneUtils {
	private PhoneUtils() {

		throw new UnsupportedOperationException("u can't instantiate me...");
	}

	/**
	 * 判断网络是否可用
	 *
	 * @param context context
	 * @return boolean
	 */
	public static boolean isNetworkAvailable(Context context) {
		if (context != null) {
			ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo info = cm.getActiveNetworkInfo();
			if (info != null) {
				return info.isAvailable();
			}
		}
		return false;
	}


	/**
	 * 判断当前网络是否是wifi网络
	 * if(activeNetInfo.getType()==ConnectivityManager.TYPE_MOBILE) { //判断3G网
	 *
	 * @param context context
	 * @return boolean
	 */
	public static boolean isWifi(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
		if (activeNetInfo != null
				&& activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI) {
			return true;
		}
		return false;
	}

	/**
	 * @return 主板信息
	 */
	public static String getBoard() {
		return Build.BOARD;
	}

	/**
	 * @return Android系统定制商
	 */
	public static String getBrand() {
		return Build.BRAND;
	}

	/**
	 * @return 设备参数
	 */
	public static String getDevice() {
		return Build.DEVICE;
	}

	/**
	 * @return 显示屏参数
	 */
	public static String getDisplay() {
		return Build.DISPLAY;
	}

	/**
	 * @return 设备唯一编号
	 */
	public static String getOnlyCOde() {
		return Build.FINGERPRINT;
	}

	/**
	 * @return 硬件序列编号
	 */
	public static String getSerial() {
		return Build.ID;
	}

	/**
	 * @return 硬件制造商
	 */
	public static String getHardProduct() {
		return Build.MANUFACTURER;
	}

	/**
	 * @return 版本
	 */
	public static String getModel() {
		return Build.MODEL;
	}

	/**
	 * @return 硬件名
	 */
	public static String getHardware() {
		return Build.HARDWARE;
	}

	/**
	 * @return 手机产品名
	 */
	public static String getProduct() {
		return Build.PRODUCT;
	}

	/**
	 * @return 当前开发代号
	 */
	public static String getCodeName() {
		return Build.VERSION.CODENAME;
	}

	/**
	 * @return 源码控制版本号
	 */
	public static String getInCremental() {
		return Build.VERSION.INCREMENTAL;
	}

	/**
	 * @return 版本字符串
	 */
	public static String getRelease() {
		return Build.VERSION.RELEASE;
	}

	/**
	 * @return 版本号
	 */
	public static int getSdkInt() {
		return Build.VERSION.SDK_INT;
	}

	/**
	 * @return Host值
	 */
	public static String getHost() {
		return Build.HOST;
	}

	/**
	 * @return User名
	 */
	public static String getName() {
		return Build.USER;
	}

	/**
	 * @return 编译时间
	 */
	public static long getTime() {
		return Build.TIME;
	}

	/**
	 * @return OS版本
	 */
	public static String getOsVersion() {
		return System.getProperty("os.version");
	}

	/**
	 * @return OS名称
	 */
	public static String getOsName() {
		return System.getProperty("os.name");
	}

	/**
	 * @return OS架构
	 */
	public static String getOsArch() {
		return System.getProperty("os.arch");
	}

	/**
	 * @return Home属性
	 */
	public static String getUserHome() {
		return System.getProperty("user.home");
	}

	/**
	 * @return Name属性
	 */
	public static String getUserName() {
		return System.getProperty("user,name");
	}

	/**
	 * @return Java版本
	 */
	public static String getJavaVersion() {
		return System.getProperty("java.version");
	}

	/**
	 * 获取手机设备号
	 *
	 * @param context context
	 * @return 设备的唯一标识符
	 */
	public static String getUniqueDeviceID(Context context) {
		String serial = null;
		String mSzdevidshort = "35" + Build.BOARD.length() % 10 + Build.BRAND.length() % 10 + Build.CPU_ABI.length() % 10
				+ Build.DEVICE.length() % 10 + Build.DISPLAY.length() % 10 + Build.HOST.length() % 10
				+ Build.ID.length() % 10 + Build.MANUFACTURER.length() % 10
				+ Build.MODEL.length() % 10 + Build.PRODUCT.length() % 10 + Build.TAGS.length() % 10
				+ Build.TYPE.length() % 10 + Build.USER.length() % 10; //13 位
		try {
			serial = android.os.Build.class.getField("SERIAL").get(null).toString();
			return new UUID(mSzdevidshort.hashCode(), serial.hashCode()).toString();
		} catch (Exception exception) {
			TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
			@SuppressLint("MissingPermission") String deviceId = telephonyManager.getDeviceId();
			serial = "serial:" + deviceId;
		}
		return new UUID(mSzdevidshort.hashCode(), serial.hashCode()).toString();
	}
}
