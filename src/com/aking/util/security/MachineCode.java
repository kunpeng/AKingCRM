package com.aking.util.security;

import java.io.InputStream;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MachineCode {
	public static final int OS_TYPE_WIN = 0;
	public static final String OS_WIN_NAME_TAG = "WIN";
	public static final String EXEC_WIN_SHELL = "ipconfig /all";
	public static final int OS_TYPE_MAC_OS = 1;
	public static final String OS_MAC_OS_NAME_TAG = "MAC OS";
	public static final String EXEC_MAC_OS_SHELL = null;
	public static final int OS_TYPE_HP_UNIX = 2;
	public static final String OS_HPUX_NAME_TAG = "HP-UX";
	public static final String EXEC_HPUX_SHELL = "/usr/sbin/lanscan -a";
	public static final int OS_TYPE_IBM_AIX = 4;
	public static final String OS_AIX_NAME_TAG = "AIX";
	public static final String EXEC_AIX_SHELL = "lscfg -vpl ent0";
	public static final int OS_TYPE_SUN_SOLARIS = 8;
	public static final String OS_SUN_SOLARIS_NAME_TAG = "SUNOS";
	public static final String EXEC_SUN_SOLARIS_SHELL = "ifconfig -a";
	public static final int OS_TYPE_LINUX = 255;
	public static final String EXEC_LINUX_SHELL = "/sbin/ifconfig -a";
	private static int type = -1;
	private static List<String> addressList;
	public static final Pattern PATTERN_LINUX = Pattern.compile("([\\dA-Fa-f]{2}(\\:)){5}[\\dA-Fa-f]{2}");
	public static final Pattern PATTERN_SOLARIS = Pattern
			.compile("([\\dA-Fa-f]{1,2}(\\:)){5}[\\dA-Fa-f]{1,2}");

	public static final Pattern PATTERN_WIN = Pattern
			.compile("(?<=\\s)([\\dA-Fa-f]{2}(-)){5}[\\dA-Fa-f]{2}(?=\\s)");
	public static final Pattern PATTERN_AIX = Pattern.compile("([\\dA-Fa-f]{2}){5}[\\dA-Fa-f]{1,2}");
	public static final Pattern PATTERN_HP = Pattern.compile("(?<=0x)([\\dA-Fa-f]{2}){5}[\\dA-Fa-f]{2}");

	public static final Pattern PATTERN_ALL = Pattern.compile("([\\dA-Fa-f]{2}(\\-|\\:)?){5}[\\dA-Fa-f]{2}");
	public static final String INFO_AIX = "Network Address.............";
	static final char[] Digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e',
			'f' };
	public static final String Public_MachineCode = "OONOSLROQKPX";
	public static final Comparator<String> MAC_COMPARATOR = new Comparator<String>() {
		public int compare(String o1,
				String o2) {
			return (o1.compareTo(o2));
		}
	};

	public static boolean search(String str,
			String ch) {
		if ((str == null) || (ch == null))
			return false;
		return str.toUpperCase().indexOf(ch) > -1;
	}

	public static int getOSType() {
		if (type != -1)
			return type;
		String os = System.getProperty("os.name");
		if (search(os, "WIN"))
			type = 0;
		else if (search(os, "MAC OS"))
			type = 1;
		else if (search(os, "HP-UX"))
			type = 2;
		else if (search(os, "AIX"))
			type = 4;
		else if (search(os, "SUNOS"))
			type = 8;
		else
			type = 255;
		return type;
	}

	public static String getExecShell(int type) {
		getOSType();
		switch (type) {
		case 0:
			return "ipconfig /all";
		case 1:
			return EXEC_MAC_OS_SHELL;
		case 2:
			return "/usr/sbin/lanscan -a";
		case 4:
			return "lscfg -vpl ent0";
		case 8:
			return "ifconfig -a";
		case 3:
		case 5:
		case 6:
		case 7:
		}
		return "/sbin/ifconfig -a";
	}

	public static List<String> getHWaddr() {
		if ((addressList != null) && (addressList.size() > 0))
			return addressList;
		try {
			int osType = getOSType();
			String cmd = getExecShell(osType);
			Process p = Runtime.getRuntime().exec(cmd);
			if (p == null)
				return null;
			InputStream _in = p.getInputStream();
			try {
				StringBuilder shellStr = new StringBuilder();
				for (;;) {
					int c = _in.read();
					if (c == -1)
						break;
					shellStr.append((char) c);
				}
				_in.close();
				addressList = getAddress(shellStr.toString(), osType);
			} finally {
				_in.close();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return addressList;
	}

	public static List<String> getAddress(String str,
			int osType) throws Exception {
		switch (osType) {
		case 0:
			return getWinAddress(str);
		case 1:
			return getMacOsAddress(str);
		case 2:
			return getHpUnixAddress(str);
		case 4:
			return getIbmAixAddress(str);
		case 8:
			return getSunSolarisAddress(str);
		case 255:
			return getLinuxAddress(str);
		}
		throw new RuntimeException("未知系统!");
	}

	public static List<String> getSunSolarisAddress(String shell) {
		List<String> list = new ArrayList<String>();
		collectMacAddressFromString(list, PATTERN_SOLARIS.matcher(shell));
		for (int i = 0; i < list.size(); i++) {
			list.set(i, formatSunSolarisAddress((String) list.get(i)));
		}
		Collections.sort(list, MAC_COMPARATOR);
		return list;
	}

	private static final String formatSunSolarisAddress(String mac) {
		StringBuffer s = new StringBuffer(21);
		String[] split = mac.split(":");
		for (int i = 0; i < split.length; i++) {
			if (s.length() > 0) {
				s.append(":");
			}
			if (split[i].length() == 1)
				s.append("0");
			s.append(split[i]);
		}
		return s.toString();
	}

	public static final List<String> getHpUnixAddress(String shell) {
		List<String> list = new ArrayList<String>();
		collectMacAddressFromString(list, PATTERN_HP.matcher(shell));
		for (int i = 0; i < list.size(); i++) {
			list.set(i, formatMacAddress((String) list.get(i)));
		}
		Collections.sort(list, MAC_COMPARATOR);
		return list;
	}

	public static final List<String> getLinuxAddress(String shell) {
		List<String> list = new ArrayList<String>();
		collectMacAddressFromString(list, PATTERN_LINUX.matcher(shell));
		Collections.sort(list, MAC_COMPARATOR);
		return list;
	}

	public static List<String> getMacOsAddress(String str) {
		throw new RuntimeException("Catch Mac-OS OS MacAddress FAIL!!");
	}

	public static List<String> getIbmAixAddress(String shell) {
		List<String> list = new ArrayList<String>();
		collectMacAddressFromString(list, PATTERN_AIX.matcher(shell));
		for (int i = 0; i < list.size(); i++) {
			list.set(i, formatMacAddress((String) list.get(i)));
		}
		Collections.sort(list, MAC_COMPARATOR);
		return list;
	}

	public static List<String> getWinAddress(String shell) throws Exception {
		List<String> list = new ArrayList<String>();
		collectMacAddressFromString(list, PATTERN_WIN.matcher(shell));
		Collections.sort(list, MAC_COMPARATOR);
		return list;
	}

	private static final String formatMacAddress(String str) {
		if ((str == null) || (str.length() != 12))
			return null;
		StringBuffer sb = new StringBuffer();
		char[] c = str.toCharArray();
		for (int i = 0; i < c.length; i++) {
			sb.append(c[i]);
			if ((i > 0) && (i < c.length - 1) && ((i + 1) % 2 == 0)) {
				sb.append(":");
			}
		}
		return sb.toString();
	}

	public static byte[] encryptBytes(byte[] b,
			int i) {
		int startKey = i;
		int mulKey = i >>> 8;
		int addKey = i >>> 16;

		for (int j = 0; j < b.length; j++) {
			b[j] = (byte) (b[j] ^ startKey >>> 8);
			startKey = (b[j] + startKey) * mulKey + addKey;
		}
		return b;
	}

	public static int btoi(byte b) {
		return ((b & 0x80) << 1) + b;
	}

	public static byte[] decryptBytes(byte[] b,
			int i) {
		int startKey = i;
		int mulKey = i >>> 8;
		int addKey = i >>> 16;

		for (int j = 0; j < b.length; j++) {
			byte temp = b[j];
			b[j] = (byte) (b[j] ^ startKey >>> 8);
			startKey = (temp + startKey) * mulKey + addKey;
		}
		return b;
	}

	public static String getMd5(String s) {
		String r = "";
		try {
			MessageDigest md = MessageDigest.getInstance("md5");
			if (md == null)
				return r;
			byte[] ds = md.digest(s.getBytes());

			StringBuffer result = new StringBuffer(ds.length);
			for (int I = 0; I < ds.length; I++) {
				result.append(Digits[(ds[I] >> 4 & 0xF)]);
				result.append(Digits[(ds[I] & 0xF)]);
			}
			r = result.toString();
			return r;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return r;
	}

	public static String getMachineCode() {
		List<String> addrList = getHWaddr();
		String s = (String) ((addrList != null) && (addrList.size() > 0) ? addrList.get(0) : null);
		if (s == null) {
			return "AABBCCDDEEFF";
		}
		return macAddressToMachineCode(s);
	}

	private static final String macAddressToMachineCode(String macAddress) {
		String s = macAddress.toUpperCase().replaceAll(":", "");
		StringBuffer r = new StringBuffer();
		for (int i = s.length() - 1; i >= 0; i--) {
			char c = s.charAt(i);
			if ((c >= '0') && (c <= '9'))
				c = (char) ('T' + (char) (c - '0'));
			if (c > 'A')
				c = (char) (c - '\002');
			if (c + i < 90)
				c = (char) (c + i);
			if ((c < 'A') || (c > 'Z'))
				c = (char) (65 + i);
			r.append(c);
		}

		return r.toString();
	}

	private static void collectMacAddressFromString(List<String> r,
			Matcher mt) {
		int end = 0;
		while (mt.find(end)) {
			end = mt.end();
			String addr = mt.group().toUpperCase().replace('-', ':');
			if (!r.contains(addr))
				r.add(addr);
		}
	}

	public static final boolean matchLoaclMacCode(String macCode) {
		String s = getMachineCode();
		if (s.equals(macCode)) {
			return true;
		}
		List<String> waddr = getHWaddr();
		if ((waddr != null) && (waddr.size() > 1)) {
			for (int i = 1; i < waddr.size(); i++) {
				if (macCode.equals(macAddressToMachineCode((String) waddr.get(i))))
					return true;
			}
		}
		return false;
	}

	public static void main(String[] args) {
		System.out.println(MachineCode.getMachineCode());
	}
}