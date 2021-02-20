package com.snow.framework.utils

import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.wifi.WifiManager
import android.provider.Settings
import android.telephony.TelephonyManager
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.*

/**
 * 类目名称 网络工具类，包含网络的判断、跳转到设置页面
 */
object NetWorkUtils {
    const val NETWORK_CONNECT = -1 // 连接上网络
    const val NETWORK_NONE = 0 // 没有网络连接
    const val NETWORK_WIFI = 1 // wifi连接
    const val NETWORK_2G = 2 // 2G
    const val NETWORK_3G = 3 // 3G
    const val NETWORK_4G = 4 // 4G
    const val NETWORK_MOBILE = 5 // 手机流量

    /**
     * 判断当前是否有网络连接
     *
     * @param context 上下文
     * @return 有网络返回true；无网络返回false
     */
    fun isNetWorkEnable(context: Context): Boolean {
        return try {
            val manager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = manager.activeNetworkInfo
            if (networkInfo != null && networkInfo.isConnected) {
                if (networkInfo.state == NetworkInfo.State.CONNECTED) {
                    return true
                }
            }
            false
            //            ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//            if (connectivity != null) {
//                NetworkInfo info = connectivity.getActiveNetworkInfo();
//                // 当前网络是连接的
//                if (info != null || info.isAvailable()) {
//                    // 当前所连接的网络可用
//                    return true;
//                }
//            }
        } catch (e: Exception) {
            false
        }
    }

    /**
     * 判断当前网络是否为wifi
     *
     * @param context 上下文
     * @return 如果为wifi返回true；否则返回false
     */
    fun isWiFiConnected(context: Context): Boolean {
        val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = manager.activeNetworkInfo
        return networkInfo != null && networkInfo.type == ConnectivityManager.TYPE_WIFI
    }

    /**
     * 获取运营商名字
     *
     * @param context context
     * @return int
     */
    fun getOperatorName(context: Context): String {
        /*
         * getSimOperatorName()就可以直接获取到运营商的名字
         * 也可以使用IMSI获取，getSimOperator()，然后根据返回值判断，例如"46000"为移动
         * IMSI相关链接：http://baike.baidu.com/item/imsi
         */
        val telephonyManager =
            context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        // getSimOperatorName就可以直接获取到运营商的名字
        return telephonyManager.simOperatorName
    }

    /**
     * 获取当前网络连接的类型
     *
     * @param context context
     * @return int
     */
    fun getNetworkState(context: Context): Int {
        val connManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                ?: // 为空则认为无网络
                return NETWORK_NONE // 获取网络服务
        // 获取网络类型，如果为空，返回无网络
        val activeNetInfo = connManager.activeNetworkInfo
        if (activeNetInfo == null || !activeNetInfo.isAvailable) {
            return NETWORK_NONE
        }
        // 判断是否为WIFI
        val wifiInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
        if (null != wifiInfo) {
            val state = wifiInfo.state
            if (null != state) {
                if (state == NetworkInfo.State.CONNECTED || state == NetworkInfo.State.CONNECTING) {
                    return NETWORK_WIFI
                }
            }
        }
        // 若不是WIFI，则去判断是2G、3G、4G网
        val telephonyManager =
            context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        val networkType = telephonyManager.networkType
        return when (networkType) {
            TelephonyManager.NETWORK_TYPE_GPRS, TelephonyManager.NETWORK_TYPE_CDMA, TelephonyManager.NETWORK_TYPE_EDGE, TelephonyManager.NETWORK_TYPE_1xRTT, TelephonyManager.NETWORK_TYPE_IDEN -> NETWORK_2G
            TelephonyManager.NETWORK_TYPE_EVDO_A, TelephonyManager.NETWORK_TYPE_UMTS, TelephonyManager.NETWORK_TYPE_EVDO_0, TelephonyManager.NETWORK_TYPE_HSDPA, TelephonyManager.NETWORK_TYPE_HSUPA, TelephonyManager.NETWORK_TYPE_HSPA, TelephonyManager.NETWORK_TYPE_EVDO_B, TelephonyManager.NETWORK_TYPE_EHRPD, TelephonyManager.NETWORK_TYPE_HSPAP -> NETWORK_3G
            TelephonyManager.NETWORK_TYPE_LTE -> NETWORK_4G
            else -> NETWORK_MOBILE
        }
    }

    /**
     * 判断MOBILE网络是否可用
     *
     * @param context 上下文
     * @return
     * @throws Exception
     */
    fun isMobileDataEnable(context: Context): Boolean {
        val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        var isMobileDataEnable = false
        isMobileDataEnable =
            manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)!!.isConnectedOrConnecting
        return isMobileDataEnable
    }

    /**
     * 判断wifi 是否可用
     *
     * @param context 上下文
     * @return
     * @throws Exception
     */
    fun isWifiDataEnable(context: Context): Boolean {
        val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        var isWifiDataEnable = false
        isWifiDataEnable = manager.getNetworkInfo(
            ConnectivityManager.TYPE_WIFI
        )!!.isConnectedOrConnecting
        return isWifiDataEnable
    }

    fun getIPAddress(context: Context): String? {
        val info = (context
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo
        if (info != null && info.isConnected) {
            if (info.type == ConnectivityManager.TYPE_MOBILE) { //当前使用2G/3G/4G网络
                try {
                    //Enumeration<NetworkInterface> en=NetworkInterface.getNetworkInterfaces();
                    val en = NetworkInterface.getNetworkInterfaces()
                    while (en.hasMoreElements()) {
                        val intf = en.nextElement()
                        val enumIpAddr = intf.inetAddresses
                        while (enumIpAddr.hasMoreElements()) {
                            val inetAddress = enumIpAddr.nextElement()
                            if (!inetAddress.isLoopbackAddress && inetAddress is Inet4Address) {
                                return inetAddress.getHostAddress()
                            }
                        }
                    }
                } catch (e: SocketException) {
                    e.printStackTrace()
                }
            } else if (info.type == ConnectivityManager.TYPE_WIFI) { //当前使用无线网络
                val wifiManager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
                val wifiInfo = wifiManager.connectionInfo
                return intIP2StringIP(wifiInfo.ipAddress)
            }
        } else {
            //当前无网络连接,请在设置中打开网络
        }
        return null
    }

    /**
     * 将得到的int类型的IP转换为String类型
     *
     * @param ip
     * @return
     */
    fun intIP2StringIP(ip: Int): String {
        return (ip and 0xFF).toString() + "." +
                (ip shr 8 and 0xFF) + "." +
                (ip shr 16 and 0xFF) + "." +
                (ip shr 24 and 0xFF)
    }

    /**
     * 获取外网的IP(必须放到子线程里处理)
     */
    val netIp: String
        get() {
            var IP = ""
            val inStream: InputStream
            try {
                val infoUrl = URL("http://pv.sohu.com/cityjson?ie=utf-8")
                val connection = infoUrl.openConnection()
                val httpConnection = connection as HttpURLConnection
                val responseCode = httpConnection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    inStream = httpConnection.inputStream
                    val reader = BufferedReader(InputStreamReader(inStream, "utf-8"))
                    val strber = StringBuilder()
                    var line: String?
                    while (reader.readLine().also { line = it } != null) {
                        strber.append(line).append("\n")
                    }
                    inStream.close()
                    val start = strber.indexOf("{")
                    val end = strber.indexOf("}")
                    val jsonObject = JSONObject(strber.substring(start, end + 1))
                    IP = jsonObject.optString("cip", "")
                }
            } catch (e: Throwable) {
                return ""
            }
            return IP
        }

    /**
     * 跳转到网络设置页面
     *
     * @param activity 上下文
     */
    fun GoSetting(activity: Activity) {
        val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
        activity.startActivity(intent)
    }

    /**
     * 打开网络设置界面
     */
    fun openSetting(activity: Activity) {
        val intent = Intent("/")
        val cn = ComponentName("com.android.settings", "com.android.settings.WirelessSettings")
        intent.component = cn
        intent.action = "android.intent.action.VIEW"
        activity.startActivityForResult(intent, 0)
    }
}