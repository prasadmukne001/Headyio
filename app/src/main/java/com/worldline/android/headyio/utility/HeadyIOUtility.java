package com.worldline.android.headyio.utility;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Created by prasad.mukne on 02/05/2018.
 */

public class HeadyIOUtility
{


	public static boolean isNetworkAvailable(Context context)
	{
		ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
		//return activeNetworkInfo != null && activeNetworkInfo.isConnected();
		return true;
	}

	public static LinkedHashMap<Integer, Integer> sortByValue(HashMap<Integer, Integer> hm)
	{
		List<Entry<Integer, Integer>> list = new LinkedList<Entry<Integer, Integer>>(hm.entrySet());

		Collections.sort(list, new Comparator<Entry<Integer, Integer>>()
		{
			public int compare(Map.Entry<Integer, Integer> o1, Map.Entry<Integer, Integer> o2)
			{
				return (o2.getValue()).compareTo(o1.getValue());
			}
		});

		LinkedHashMap<Integer, Integer> temp = new LinkedHashMap<Integer, Integer>();
		for (Map.Entry<Integer, Integer> aa : list)
		{
			temp.put(aa.getValue(), aa.getKey());
		}
		return temp;
	}


}
