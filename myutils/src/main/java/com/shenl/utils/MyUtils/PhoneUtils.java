package com.shenl.utils.MyUtils;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import android.telephony.PhoneNumberUtils;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.view.WindowManager;
import android.widget.Toast;

import com.shenl.utils.bean.ContactsInfo;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class PhoneUtils {
    /**
     * TODO 功能：获取给用户看到的版本号
     * <p>
     * 参数说明:
     * 作    者:   沈 亮
     * 创建时间:   2018/4/27
     */
    public static String getVersionName(Context context) {
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo packageInfo = pm.getPackageInfo(context.getPackageName(), 0);
            String versionName = packageInfo.versionName;
            return versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * TODO 功能：获取用于产品升级的版本号
     * <p>
     * 参数说明:
     * 作    者:   沈 亮
     * 创建时间:   2018/4/27
     */
    public static int getVersionCode(Context context) {
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo packageInfo = pm.getPackageInfo(context.getPackageName(), 0);
            int versionCode = packageInfo.versionCode;
            return versionCode;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * TODO 功能：获取手机系统版本号
     * <p>
     * 参数说明:
     * 作    者:   沈 亮
     * 创建时间:   2018/4/28
     */
    public static int getSDKVersionNumber() {
        int sdkVersion;
        try {
            sdkVersion = Integer.valueOf(android.os.Build.VERSION.SDK_INT);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            sdkVersion = 0;
        }
        return sdkVersion;
    }

    /**
     * TODO 功能：获取手机型号
     * <p>
     * 参数说明:
     * 作    者:   沈 亮
     * 创建时间:   2018/4/28
     */
    public static String getPhoneModel() {
        return android.os.Build.MODEL;
    }

    /**
     * TODO 功能：获取手机宽度
     * <p>
     * 参数说明:
     * 作    者:   沈 亮
     * 创建时间:   2018/4/28
     */
    @SuppressWarnings("deprecation")
    public static int getPhoneWidth(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getWidth();
    }

    /**
     * TODO 功能：获取手机高度
     * <p>
     * 参数说明:
     * 作    者:   沈 亮
     * 创建时间:   2018/4/28
     */
    @SuppressWarnings("deprecation")
    public static int getPhoneHeight(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getHeight();
    }

    /**
     * TODO 功能：获取手机imei串号
     * <p>
     * 参数说明:
     * 作    者:   沈 亮
     * 创建时间:   2018/4/28
     */
    public static String getPhoneImei(Context context) {
        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        if (tm == null)
            return null;
        return tm.getDeviceId();
    }

    /**
     * TODO 功能：获取手机sim卡号并非手机号
     * <p>
     * 参数说明:
     * 作    者:   沈 亮
     * 创建时间:   2018/4/28
     */
    public String getPhoneSim(Context context) {
        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        if (tm == null)
            return null;
        return tm.getSubscriberId();
    }

    /**
     * TODO 功能：获取手机号
     * <p>
     * 参数说明:
     * 作    者:   沈 亮
     * 创建时间:   2018/4/28
     */
    public String getPhoneNum(Context context) {
        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        if (tm == null)
            return null;
        return tm.getLine1Number();
    }

    /**
     * TODO 功能：判断是否是平板
     * <p>
     * 参数说明:
     * 作    者:   沈 亮
     * 创建时间:   2018/4/28
     */
    public boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    /**
     * TODO 功能：直接呼叫指定的号码
     * (需要 <uses-permission android:name="android.permission.CALL_PHONE"/>权限)
     * <p>
     * 参数说明:
     * 作    者:   沈 亮
     * 创建时间:   2018/4/28
     */
    public static void callPhone(Context context, String phoneNumber) {
        Uri uri = Uri.parse("tel:" + phoneNumber);
        Intent call = new Intent(Intent.ACTION_CALL, uri);
        context.startActivity(call);
    }

    /**
     * TODO 功能：跳转至拨号界面
     * <p>
     * 参数说明:
     * 作    者:   沈 亮
     * 创建时间:   2018/4/28
     */
    public static void toCallPhoneActivity(Context context, String phoneNumber) {
        Uri uri = Uri.parse("tel:" + phoneNumber);
        Intent call = new Intent(Intent.ACTION_DIAL, uri);
        context.startActivity(call);
    }

    /**
     * TODO 功能：直接调用短信API发送信息(设置监听发送和接收状态)
     * <p>
     * 参数说明:
     * 作    者:   沈 亮
     * 创建时间:   2018/4/28
     */
    public static void sendMessage(final Context mContext,
                                   final String strPhone, final String strMsgContext) {
        // 处理返回的发送状态
        String SENT_SMS_ACTION = "SENT_SMS_ACTION";
        Intent sentIntent = new Intent(SENT_SMS_ACTION);
        PendingIntent sendIntent = PendingIntent.getBroadcast(mContext, 0,
                sentIntent, 0);
        // register the Broadcast Receivers
        mContext.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context _context, Intent _intent) {
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        Toast.makeText(mContext, "短信发送成功", Toast.LENGTH_SHORT)
                                .show();
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        break;
                }
            }
        }, new IntentFilter(SENT_SMS_ACTION));

        // 处理返回的接收状态
        String DELIVERED_SMS_ACTION = "DELIVERED_SMS_ACTION";
        // create the deilverIntent parameter
        Intent deliverIntent = new Intent(DELIVERED_SMS_ACTION);
        PendingIntent backIntent = PendingIntent.getBroadcast(mContext, 0,
                deliverIntent, 0);
        mContext.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context _context, Intent _intent) {
                PageUtils.showToast(mContext, strPhone + "已经成功接收");
            }
        }, new IntentFilter(DELIVERED_SMS_ACTION));

        // 拆分短信内容（手机短信长度限制）
        SmsManager smsManager = SmsManager.getDefault();
        ArrayList<String> msgList = smsManager.divideMessage(strMsgContext);
        for (String text : msgList) {
            smsManager.sendTextMessage(strPhone, null, text, sendIntent,
                    backIntent);
        }
    }

    /**
     * TODO 功能：跳转至发送短信界面(自动设置接收方的号码)
     * <p>
     * 参数说明:
     * 作    者:   沈 亮
     * 创建时间:   2018/4/28
     */
    public static void toSendMessageActivity(Context mContext, String strPhone, String strMsgContext) {
        if (PhoneNumberUtils.isGlobalPhoneNumber(strPhone)) {
            Uri uri = Uri.parse("smsto:" + strPhone);
            Intent sendIntent = new Intent(Intent.ACTION_VIEW, uri);
            sendIntent.putExtra("sms_body", strMsgContext);
            mContext.startActivity(sendIntent);
        }
    }

    /**
     * TODO 功能：跳转至联系人选择界面
     * <p>
     * 参数说明:
     * 作    者:   沈 亮
     * 创建时间:   2018/4/28
     */
    public static void toChooseContactsList(Activity mContext, int requestCode) {
        Intent intent = new Intent(Intent.ACTION_PICK,
                ContactsContract.Contacts.CONTENT_URI);
        mContext.startActivityForResult(intent, requestCode);
    }

    /**
     * TODO 功能：获取选择的联系人的手机号码
     * <p>
     * 参数说明:
     * mContext:上下文<p>
     * resultCode:请求返回Result状态区分代码<p>
     * data :onActivityResult返回的Intent<p>
     * 作    者:   沈 亮
     * 创建时间:   2018/4/28
     */
    @SuppressWarnings("deprecation")
    public static String getChoosedPhoneNumber(Activity mContext,int resultCode, Intent data) {
        // 返回结果
        String phoneResult = "";
        if (Activity.RESULT_OK == resultCode) {
            Uri uri = data.getData();
            Cursor mCursor = mContext.managedQuery(uri, null, null, null, null);
            mCursor.moveToFirst();

            int phoneColumn = mCursor
                    .getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER);
            int phoneNum = mCursor.getInt(phoneColumn);
            if (phoneNum > 0) {
                // 获得联系人的ID号
                int idColumn = mCursor
                        .getColumnIndex(ContactsContract.Contacts._ID);
                String contactId = mCursor.getString(idColumn);
                // 获得联系人的电话号码的cursor;
                Cursor phones = mContext.getContentResolver().query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID
                                + " = " + contactId, null, null);
                if (phones.moveToFirst()) {
                    // 遍历所有的电话号码
                    for (; !phones.isAfterLast(); phones.moveToNext()) {
                        int index = phones
                                .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                        int typeindex = phones
                                .getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE);
                        int phone_type = phones.getInt(typeindex);
                        String phoneNumber = phones.getString(index);
                        if (phone_type == 2) {
                            phoneResult = phoneNumber;
                        }
                    }
                    if (!phones.isClosed()) {
                        phones.close();
                    }
                }
            }
            // 关闭游标
            mCursor.close();
        }
        return phoneResult;
    }

    /**
     * TODO : 获取手机联系人
     * 参数说明 :
     * 作者 : shenl
     * 创建日期 : 2018/11/25
     * @return :
     */
    public static List<ContactsInfo> getAllContacts(Context context){
        List<ContactsInfo> list = new ArrayList<ContactsInfo>();
        //1.获取的内容解析者
        ContentResolver resolver = context.getContentResolver();
        //2.获取查询路径
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        //3.查询
        String[] projection = new String[]{
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER,
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID
        };
        //uri : 查询地址
        //projection : 查询的字段
        //selection : 查询条件
        //selectionArgs : 查询条件的参数
        //sortOrder : 排序
        Cursor cursor = resolver.query(uri, projection, null, null, null);
        //4.遍历解析cursor
        if (cursor!= null) {
            while(cursor.moveToNext()){
                //5.获取相应的数据
                String name = cursor.getString(0);
                String number = cursor.getString(1);
                String contactid = cursor.getString(2);
                //6.保存到bean类中
                ContactsInfo contactsInfo = new ContactsInfo();
                contactsInfo.name = name;
                contactsInfo.number = number;
                contactsInfo.contactid = contactid;
                //7.添加到集合中
                list.add(contactsInfo);
            }
        }
        //8.关闭cursor
        cursor.close();
        return list;
    }

    /**
     * TODO : 获取手机联系人的头像
     * 参数说明 : contactid: 获取到获取人的ID ContactsInfo.getcontactid
     * 作者 : shenl
     * 创建日期 : 2018/11/25
     * @return :
     */
    public static Bitmap getContactsPhoto(Context context, String contactid){
        //1.获取内容解析者
        ContentResolver resolver = context.getContentResolver();
        //2.获取联系人头像的uri
        //content://contacts/101
        Uri uri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_URI, contactid);
        //3.获取头像,返回的流信息
        //参数1:内容解析者
        //参数2:头像路径
        InputStream in = ContactsContract.Contacts.openContactPhotoInputStream(resolver, uri);
        //4.将流转化成bitmap
        Bitmap bitmap = BitmapFactory.decodeStream(in);
        //5.关流操作
        if (in != null) {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bitmap;
    }
}
