package com.ARCompany.UzAdMoney;

import java.util.ArrayList;
import java.util.List;

public class Values {
    public static String ad_url, phoneVerificationId;
    public static List<String> ad_names = new ArrayList<>(), my_ads=new ArrayList<>();
    public static int ad_index;
    public static int theme = 0;
    public static boolean theme_chanched = false;
    public static boolean informations_change = false;
    public static boolean signed_in = false, passed=false, password_changed=false;


    public static int choosed_theme(){
        switch (theme){
            case 0: return R.style.AppTheme;
            case 1: return R.style.AppTheme_Blue;
            default: return R.style.AppTheme_Black;
        }
    }

}
