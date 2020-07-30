package com.messed.ircmbs.Model;

import java.util.ArrayList;
import java.util.List;

public class AllStringHere {

    public static List<MenuList> menuList;

    public ArrayList<String> getRestOrderSpinner(int nums)
    {
        int temp;
        ArrayList<String> arrayList=new ArrayList<>();
        arrayList.add("Spot Order");
        for(int i=0;i<nums;i++)
        {
            temp=i+1;
            arrayList.add("Table - "+temp);
        }
        return arrayList;
    }
}
