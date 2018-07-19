package com.sprout.frame.baseframe.utils.contacts;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;

import com.sprout.frame.baseframe.widgets.nicetoast.Toasty;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Sprout on 2018/4/18
 */

public class PhoneUtil {

    /**
     * 从本机和SIM卡读取联系人并上传至后台
     */
    public static void readContactsFromPhoneAndSIM(final Context context) {
        Observable<List<ContactEntity>> observable1 = Observable
                .create(new ObservableOnSubscribe<List<ContactEntity>>() {
                    @Override
                    public void subscribe(@NonNull ObservableEmitter<List<ContactEntity>> e) throws Exception {
                        List<ContactEntity> beanList = readContactsFromPhone(context);
                        e.onNext(beanList);
                    }
                })
//                .compose(MainActivity.this.<String>bindToLifecycle())
                .subscribeOn(Schedulers.newThread());

        Observable<List<ContactEntity>> observable2 = Observable
                .create(new ObservableOnSubscribe<List<ContactEntity>>() {
                    @Override
                    public void subscribe(@NonNull ObservableEmitter<List<ContactEntity>> e) throws Exception {
                        // 读取SIM卡手机号,有两种可能:content://icc/adn 与 content://sim/adn
                        List<ContactEntity> beanList = new ArrayList<>();
                        //5.1以上才可以读双卡
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                            beanList = readContactsFromDoubleSIM(context, "content://icc/adn");
                            if (beanList.size() == 0) beanList = readContactsFromDoubleSIM(context, "content://sim/adn");
                        } else {
                            beanList = readContactsFromSingleSIM(context, "content://icc/adn");
                            if (beanList.size() == 0) beanList = readContactsFromSingleSIM(context, "content://sim/adn");
                        }
                        e.onNext(beanList);
                    }
                })
//                .compose(MainActivity.this.<String>bindToLifecycle())
                .subscribeOn(Schedulers.newThread());

        Observable
                .zip(observable1, observable2, new BiFunction<List<ContactEntity>, List<ContactEntity>, List<ContactEntity>>() {
                    @Override
                    public List<ContactEntity> apply(@NonNull List<ContactEntity> s, @NonNull List<ContactEntity> s2) throws Exception {
                        List<ContactEntity> beanList = new ArrayList<>();
                        beanList.addAll(s);
                        beanList.addAll(s2);
                        return removeDuplicateWithOrder(beanList);
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
//                .compose(MainActivity.this.<String>bindToLifecycle())
                .subscribe(new Consumer<List<ContactEntity>>() {
                    @Override
                    public void accept(List<ContactEntity> beanList) throws Exception {
                        if (beanList.size() == 0) {
                            Toasty.info(context, "请给予应用获取联系人的权限").show();
                        } else {
                            //将获取的联系人列表beanList上传至后台
                        }
                    }
                });
    }

    /**
     * 从本机读取联系人列表
     */
    @android.support.annotation.NonNull
    private static List<ContactEntity> readContactsFromPhone(Context context) {
        List<ContactEntity> beanList = new ArrayList<>();
        Cursor cursor = null;
        try {
            //获取内容提供器
            ContentResolver resolver = context.getContentResolver();
            //查询联系人数据
            cursor = resolver.query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null, null, null, null, null);
            //遍历联系人列表
            while (cursor.moveToNext()) {
                //获取联系人姓名
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                //获取联系人手机号
                String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)).trim();
                ContactEntity phonebookBean = new ContactEntity();
                phonebookBean.setName(name);
                phonebookBean.setPhone(number);
                beanList.add(phonebookBean);
            }
        } catch (Exception ex) {
            Toasty.error(context, "请给予应用获取联系人的权限").show();
            ex.printStackTrace();
        } finally {
            if (cursor != null) cursor.close();
        }
        return beanList;
    }

    /**
     * 从SIM卡(单卡)读取联系人列表
     */
    @android.support.annotation.NonNull
    private static List<ContactEntity> readContactsFromSingleSIM(Context context, String adn) {
        List<ContactEntity> beanList = new ArrayList<>();
        Cursor cursor = null;
        try {
            Intent intent = new Intent();
            intent.setData(Uri.parse(adn));
            Uri uri = intent.getData();
            cursor = context.getContentResolver().query(uri, null, null, null, null);
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    // 取得联系人名字
                    int nameIndex = cursor.getColumnIndex("name");
                    // 取得电话号码
                    int numberIndex = cursor.getColumnIndex("number");
                    String name = cursor.getString(nameIndex);
                    String number = cursor.getString(numberIndex);
                    ContactEntity phonebookBean = new ContactEntity();
                    phonebookBean.setName(name);
                    phonebookBean.setPhone(number);
                    beanList.add(phonebookBean);
                }
                cursor.close();
            }
        } catch (Exception ex) {
            if (cursor != null) cursor.close();
        }
        return beanList;
    }

    /**
     * 从SIM卡(双卡)读取联系人列表, 5.1以上才有相关API
     */
    private static List<ContactEntity> readContactsFromDoubleSIM(Context mContext, String adn) {
        List<ContactEntity> beanList = new ArrayList<>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            SubscriptionManager sm = SubscriptionManager.from(mContext);
            List<SubscriptionInfo> simList = sm.getActiveSubscriptionInfoList();//获取所有卡的集合
            for (SubscriptionInfo info : simList) {
                int simSlotIndex = info.getSimSlotIndex();  //卡槽的下标数
                //content://icc/adn/subId/0
                Uri uri = Uri.parse(adn + "/subId/" + simSlotIndex);
                Cursor cursor = null;
                try {
                    cursor = mContext.getContentResolver().query(uri, null, null, null, null);
                    if (cursor != null) {
                        while (cursor.moveToNext()) {
                            int nameIndex = cursor.getColumnIndex("name");
                            int numberIndex = cursor.getColumnIndex("number");
                            String name = cursor.getString(nameIndex);
                            String number = cursor.getString(numberIndex);
                            ContactEntity phonebookBean = new ContactEntity();
                            phonebookBean.setName(name);
                            phonebookBean.setPhone(number);
                            beanList.add(phonebookBean);
                        }
                        cursor.close();
                    }
                } catch (Exception ex) {
                    if (cursor != null) cursor.close();
                }
            }
        }
        return beanList;
    }

    /**
     * 删除ArrayList中重复元素，保持顺序
     */
    private static List<ContactEntity> removeDuplicateWithOrder(List<ContactEntity> list) {
        Set<ContactEntity> set = new TreeSet<>(new CompartorByName());
        List<ContactEntity> newList = new ArrayList<>();
        for (Iterator<ContactEntity> iter = list.iterator(); iter.hasNext(); ) {
            ContactEntity element = iter.next();
            if (set.add(element)) newList.add(element);
        }
        list.clear();
        list.addAll(newList);
        return list;
    }

    static class CompartorByName implements Comparator<ContactEntity> {
        @Override
        public int compare(ContactEntity o1, ContactEntity o2) {
            //联系人姓名和手机号都相同则示为同一人, 去重
            if (o1 == null || o1.getName() == null || o2 == null) return 1;
            int temp = o1.getName().compareTo(o2.getName());
            if (o1.getPhone() == null) return 1;
            int temp2 = o1.getPhone().compareTo(o2.getPhone());
            return temp == 0 ? temp2 : temp;
        }
    }
}
