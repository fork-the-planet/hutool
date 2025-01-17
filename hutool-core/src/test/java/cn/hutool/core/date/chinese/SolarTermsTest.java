package cn.hutool.core.date.chinese;

import cn.hutool.core.date.ChineseDate;
import cn.hutool.core.date.DateUtil;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class SolarTermsTest {

	@Test
	public void getTermTest1(){
		final int term = SolarTerms.getTerm(1987, 3);
		assertEquals(4, term);
	}

	@Test
	public void getTermTest() {

		assertEquals("小寒", SolarTerms.getTerm(2021, 1, 5));

		assertEquals("大寒", SolarTerms.getTerm(2021, 1, 20));

		assertEquals("立春", SolarTerms.getTerm(2021, 2, 3));

		assertEquals("雨水", SolarTerms.getTerm(2021, 2, 18));

		assertEquals("惊蛰", SolarTerms.getTerm(2021, 3, 5));

		assertEquals("春分", SolarTerms.getTerm(2021, 3, 20));

		assertEquals("清明", SolarTerms.getTerm(2021, 4, 4));

		assertEquals("谷雨", SolarTerms.getTerm(2021, 4, 20));

		assertEquals("立夏", SolarTerms.getTerm(2021, 5, 5));

		assertEquals("小满", SolarTerms.getTerm(2021, 5, 21));

		assertEquals("芒种", SolarTerms.getTerm(2021, 6, 5));

		assertEquals("夏至", SolarTerms.getTerm(2021, 6, 21));

		assertEquals("小暑", SolarTerms.getTerm(2021, 7, 7));

		assertEquals("大暑", SolarTerms.getTerm(2021, 7, 22));

		assertEquals("立秋", SolarTerms.getTerm(2021, 8, 7));

		assertEquals("处暑", SolarTerms.getTerm(2021, 8, 23));

		assertEquals("白露", SolarTerms.getTerm(2021, 9, 7));

		assertEquals("秋分", SolarTerms.getTerm(2021, 9, 23));

		assertEquals("寒露", SolarTerms.getTerm(2021, 10, 8));

		assertEquals("霜降", SolarTerms.getTerm(2021, 10, 23));

		assertEquals("立冬", SolarTerms.getTerm(2021, 11, 7));

		assertEquals("小雪", SolarTerms.getTerm(2021, 11, 22));

		assertEquals("大雪", SolarTerms.getTerm(2021, 12, 7));

		assertEquals("冬至", SolarTerms.getTerm(2021, 12, 21));
	}


	@Test
	public void getTermByDateTest() {
		assertEquals("春分", SolarTerms.getTerm(DateUtil.parseDate("2021-03-20")));
		assertEquals("处暑", SolarTerms.getTerm(DateUtil.parseDate("2022-08-23")));
	}


	@Test
	public void getTermByChineseDateTest() {
		assertEquals("清明", SolarTerms.getTerm(new ChineseDate(2021, 2, 23)));
		assertEquals("秋分", SolarTerms.getTerm(new ChineseDate(2022, 8, 28)));
	}
}
