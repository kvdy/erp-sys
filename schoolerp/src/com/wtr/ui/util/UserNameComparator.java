package com.wtr.ui.util;

import java.util.Comparator;

import com.wtr.bl.vo.UserVO;

public class UserNameComparator implements Comparator<UserVO>{
	public int compare(UserVO o1, UserVO o2) {
		String str1 = "";
		String str2 = "";
	
		str1 = ((UserVO) o1).getUserName();
		str2 = ((UserVO) o2).getUserName();		
		
		return (str1.compareTo(str2));
	}
}
