package com.youxiake.util;

import java.lang.Character.UnicodeBlock;
import java.util.HashMap;

public class EmojiUtils {

	//过滤表情
	public static String emojiFilter(String content){
		return content.replaceAll("[\\ud83c\\udc00-\\ud83c\\udfff]|[\\ud83d\\udc00-\\ud83d\\udfff]|[\\u2600-\\u27ff]", "");
	}
	public static String utf8ToUnicode(String inStr) {
		char[] myBuffer = inStr.toCharArray();

		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < inStr.length(); i++) {
			UnicodeBlock ub = UnicodeBlock.of(myBuffer[i]);
			if (ub == UnicodeBlock.BASIC_LATIN) {
				sb.append(myBuffer[i]);
			} else if (ub == UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
				int j = (int) myBuffer[i] - 65248;
				sb.append((char) j);
			} else {
				short s = (short) myBuffer[i];
				String hexS = Integer.toHexString(s);
				String unicode = "\\u" + hexS;
				sb.append(unicode.toLowerCase());
			}
		}
		return sb.toString();
	}


	public static String unicodeToUtf8(String theString) {
		char aChar;
		int len = theString.length();
		StringBuffer outBuffer = new StringBuffer(len);
		for (int x = 0; x < len;) {
			aChar = theString.charAt(x++);
			if (aChar == '\\') {
				aChar = theString.charAt(x++);
				if (aChar == 'u') {
					// Read the xxxx
					int value = 0;
					for (int i = 0; i < 4; i++) {
						aChar = theString.charAt(x++);
						switch (aChar) {
							case '0':
							case '1':
							case '2':
							case '3':
							case '4':
							case '5':
							case '6':
							case '7':
							case '8':
							case '9':
								value = (value << 4) + aChar - '0';
								break;
							case 'a':
							case 'b':
							case 'c':
							case 'd':
							case 'e':
							case 'f':
								value = (value << 4) + 10 + aChar - 'a';
								break;
							case 'A':
							case 'B':
							case 'C':
							case 'D':
							case 'E':
							case 'F':
								value = (value << 4) + 10 + aChar - 'A';
								break;
							default:
								throw new IllegalArgumentException(
										"Malformed   \\uxxxx   encoding.");
						}
					}
					outBuffer.append((char) value);
				} else {
					if (aChar == 't')
						aChar = '\t';
					else if (aChar == 'r')
						aChar = '\r';
					else if (aChar == 'n')
						aChar = '\n';
					else if (aChar == 'f')
						aChar = '\f';
					outBuffer.append(aChar);
				}
			} else
				outBuffer.append(aChar);
		}
		return outBuffer.toString();
	}


	public static String GBK2Unicode(String str) {
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < str.length(); i++) {
			char chr1 = (char) str.charAt(i);

			if (!isNeedConvert(chr1)) {
				result.append(chr1);
				continue;
			}
			result.append("\\u" + Integer.toHexString((int) chr1));
		}

		return result.toString();
	}


	public static boolean isNeedConvert(char para) {
		return ((para & (0x00FF)) != para);
	}



	public static String Unicode2GBK(String dataStr) {
		int index = 0;
		StringBuffer buffer = new StringBuffer();

		int li_len = dataStr.length();
		while (index < li_len) {
			if (index >= li_len - 1
					|| !"\\u".equals(dataStr.substring(index, index + 2))) {
				buffer.append(dataStr.charAt(index));

				index++;
				continue;
			}

			String charStr = "";
			charStr = dataStr.substring(index + 2, index + 6);

			char letter = (char) Integer.parseInt(charStr, 16);

			buffer.append(letter);
			index += 6;
		}

		return buffer.toString();
	}
	public static String getUnicode(String source){
		String returnUniCode=null;
		String uniCodeTemp=null;
		for(int i=0;i<source.length();i++){
			uniCodeTemp = "\\u"+Integer.toHexString((int)source.charAt(i));//使用char类的charAt()的方法
			returnUniCode=returnUniCode==null?uniCodeTemp:returnUniCode+uniCodeTemp;
		}
		System.out.print(source +" 's unicode = "+returnUniCode);
		return returnUniCode;//返回一个字符的unicode的编码值
	}
	public static String emojiIcons[][] = {{
			"smiley",
			"heart_eyes",
			"pensive",
			"flushed",
			"grin",
			"kissing_heart",
			"wink",
			"angry",
			"disappointed",
			"disappointed_relieved",
			"sob",
			"stuck_out_tongue_closed_eyes",
			"rage",
			"persevere",
			"unamused",
			"smile",
			"mask",
			"kissing_face",
			"sweat",
			"joy",
			"ic_keyboard_delete"
	}, {
			"blush",
			"cry",
			"stuck_out_tongue_winking_eye",
			"fearful",
			"cold_sweat",
			"dizzy_face",
			"smirk",
			"scream",
			"sleepy",
			"confounded",
			"relieved",
			"smiling_imp",
			"ghost",
			"santa",
			"dog",
			"pig",
			"cat",
			"a00001",
			"a00002",
			"facepunch",
			"ic_keyboard_delete"
	}, {
			"fist",
			"v",
			"muscle",
			"clap",
			"point_left",
			"point_up_2",
			"point_right",
			"point_down",
			"ok_hand",
			"heart",
			"broken_heart",
			"sunny",
			"moon",
			"star2",
			"zap",
			"cloud",
			"lips",
			"rose",
			"coffee",
			"birthday",
			"ic_keyboard_delete"
	}, {
			"clock10",
			"beer",
			"mag",
			"iphone",
			"house",
			"car",
			"gift",
			"soccer",
			"bomb",
			"gem",
			"alien",
			"my100",
			"money_with_wings",
			"video_game",
			"hankey",
			"sos",
			"zzz",
			"microphone",
			"umbrella",
			"book",
			"ic_keyboard_delete"
	}
	};
	public static String emojiIconString[] = {
			"smiley",
			"heart_eyes",
			"pensive",
			"flushed",
			"grin",
			"kissing_heart",
			"wink",
			"angry",
			"disappointed",
			"disappointed_relieved",
			"sob",
			"stuck_out_tongue_closed_eyes",
			"rage",
			"persevere",
			"unamused",
			"smile",
			"mask",
			"kissing_face",
			"sweat",
			"joy",
			"blush",
			"cry",
			"stuck_out_tongue_winking_eye",
			"fearful",
			"cold_sweat",
			"dizzy_face",
			"smirk",
			"scream",
			"sleepy",
			"confounded",
			"relieved",
			"smiling_imp",
			"ghost",
			"santa",
			"dog",
			"pig",
			"cat",
			"a00001",
			"a00002",
			"facepunch",
			"fist",
			"v",
			"muscle",
			"clap",
			"point_left",
			"point_up_2",
			"point_right",
			"point_down",
			"ok_hand",
			"heart",
			"broken_heart",
			"sunny",
			"moon",
			"star2",
			"zap",
			"cloud",
			"lips",
			"rose",
			"coffee",
			"birthday",
			"clock10",
			"beer",
			"mag",
			"iphone",
			"house",
			"car",
			"gift",
			"soccer",
			"bomb",
			"gem",
			"alien",
			"my100",
			"money_with_wings",
			"video_game",
			"hankey",
			"sos",
			"zzz",
			"microphone",
			"umbrella",
			"book"
	};
	public static String emojiIcon[] = {
			"😃",
			"😍",
			"😔",
			"😳",
			"😁",
			"😘",
			"😉",
			"😠",
			"😞",
			"😥",
			"😭",
			"😝",
			"😡",
			"😣",
			"😒",
			"😄",
			"😷",
			"😘",
			"😓",
			"😂",
			"😊",
			"😢",
			"😜",
			"😨",
			"😰",
			"😵",
			"😏",
			"😱",
			"😪",
			"😖",
			"😌",
			"😈",
			"👻",
			"🎅",
			"🐶",
			"🐷",
			"🐱",
			"👍",
			"👎",
			"👊",
			"✊",
			"✌",
			"💪",
			"👏",
			"👈",
			"👆",
			"👉",
			"👇",
			"👌",
			"❤",
			"💔",
			"☀",
			"🌔",
			"🌟",
			"⚡",
			"☁",
			"👄",
			"🌹",
			"☕",
			"🎂",
			"🕙",
			"🍺",
			"🔍",
			"📱",
			"🏠",
			"🚗",
			"🎁",
			"⚽",
			"💣",
			"💎",
			"👽",
			"💯",
			"💸",
			"🎮",
			"💩",
			"🆘",
			"💤",
			"🎤",
			"☔",
			"📖",
	};
	public static HashMap<String, String> getEmoji() {
		HashMap<String, String> map = new HashMap<>();
		for (int i = 0; i < emojiIconString.length; i++) {
			map.put(emojiIconString[i], emojiIcon[i]);
		}
		return map;
	}
	public static HashMap<String, String> getEmojiIcon() {
		HashMap<String, String> map = new HashMap<>();
		for (int i = 0; i < emojiIconString.length; i++) {
			map.put(emojiIcon[i], emojiIconString[i]);
		}
		return map;
	}
}
