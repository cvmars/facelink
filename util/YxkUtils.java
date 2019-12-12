package com.youxiake.util;

/**
 * Created by Cvmars on 2017/4/13.
 * <p>
 * 游侠客文档约定工具类
 */

public class YxkUtils {

    /**
     * 同时判断成人、儿童价 ，当成人价格没有，需要显示儿童价
     *
     * @param adultPrice
     * @param childPrice
     * @return
     */
    public static String getMoneyText(String adultPrice, String childPrice) {

        String result;
        if ("-1".equals(adultPrice)) {
            if (childPrice == null) {
                result = null;
            } else {
                result = childPrice;
            }
        } else if ("-2".equals(adultPrice)) {
            result = "核算中";
        } else {
            result = adultPrice;
        }

        if ("0".equals(result)) {

            result = "免费";
        }
        return result;
    }
    /**
     * 同时判断成人、儿童价 ，当成人价格没有，需要显示儿童价
     *
     * @param adultPrice
     * @return
     */
    public static String getMoneyText0(String adultPrice) {

        String result;
        if ("-1".equals(adultPrice)) {

            result = "";
        } else if ("-2".equals(adultPrice)) {
            result = "核算中";
        } else {
            result = adultPrice;
        }

//        if ("0".equals(result)) {
//
//            result = "免费";
//        }
        return result;
    }

    /**
     * 返回签证的价格
     */
    public static String getVisaPrice(String price) {

        String result;
        try {
            Integer.parseInt(price);
            result = price;
        } catch (Exception e) {

            result = String.format("%.2f", UtilHelper.ParseDouble(price));
        }
        MyLog.debug("result :" + result);
        return result;
    }

    /**
     * 只需要判断当前价格 ，当返回null时 ，后续回调需要自己处理，（比如隐藏当前控件）
     *
     * @param price
     * @return
     */
    public static String getMoneyText(String price) {

        return getMoneyText(price, null);
    }
}
