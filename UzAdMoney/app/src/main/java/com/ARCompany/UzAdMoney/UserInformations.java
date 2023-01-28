package com.ARCompany.UzAdMoney;

public class UserInformations {
    public String Surname;
    public String Name;
    public String Sex;
    public String Region;
    public String Date;
    public String Money;
    public String Profession;
    public String Phone;
    public Object Ads;
    public Object Referals;


    public UserInformations(String surname, String name, String sex, String region, String profession, String date, String phone) {
        Surname = surname;
        Name = name;
        Sex = sex;
        Region = region;
        Profession = profession;
        Date = date;
        Phone = phone;
        Money="0";
        Ads=null;
        Referals=null;
    }

}
