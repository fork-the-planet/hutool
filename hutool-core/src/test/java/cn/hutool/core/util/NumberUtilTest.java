package cn.hutool.core.util;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.Console;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * {@link NumberUtil} 单元测试类
 *
 * @author Looly
 *
 */
public class NumberUtilTest {

	@Test
	public void addTest() {
		final Float a = 3.15f;
		final Double b = 4.22;
		final double result = NumberUtil.add(a, b).doubleValue();
		assertEquals(7.37, result, 0);
	}

	@Test
	public void addTest2() {
		final double a = 3.15f;//精度丢失
		final double b = 4.22;
		final double result = NumberUtil.add(a, b);
		assertEquals(7.37, result, 0.01);
	}

	@Test
	public void addTest3() {
		final float a = 3.15f;
		final double b = 4.22;
		final double result = NumberUtil.add(a, b, a, b).doubleValue();
		assertEquals(14.74, result, 0);
	}

	@Test
	public void addTest4() {
		final BigDecimal result = NumberUtil.add(new BigDecimal("133"), new BigDecimal("331"));
		assertEquals(new BigDecimal("464"), result);
	}

	@Test
	public void addBlankTest(){
		final BigDecimal result = NumberUtil.add("123", " ");
		assertEquals(new BigDecimal("123"), result);
	}

	@Test
	public void addTest5() {
		final double add = NumberUtil.add(1686036549717D, 1000D);
		assertEquals(1686036550717D, add, 0);
	}

	@Test
	public void isIntegerTest() {
		assertTrue(NumberUtil.isInteger("-12"));
		assertTrue(NumberUtil.isInteger("256"));
		assertTrue(NumberUtil.isInteger("0256"));
		assertTrue(NumberUtil.isInteger("0"));
		assertFalse(NumberUtil.isInteger("23.4"));
		assertFalse(NumberUtil.isInteger(null));
		assertFalse(NumberUtil.isInteger(""));
		assertFalse(NumberUtil.isInteger(" "));
	}

	@Test
	public void isLongTest() {
		assertTrue(NumberUtil.isLong("-12"));
		assertTrue(NumberUtil.isLong("256"));
		assertTrue(NumberUtil.isLong("0256"));
		assertTrue(NumberUtil.isLong("0"));
		assertFalse(NumberUtil.isLong("23.4"));
		assertFalse(NumberUtil.isLong(null));
		assertFalse(NumberUtil.isLong(""));
		assertFalse(NumberUtil.isLong(" "));
	}

	@Test
	public void isNumberTest() {
		assertTrue(NumberUtil.isNumber("28.55"));
		assertTrue(NumberUtil.isNumber("0"));
		assertTrue(NumberUtil.isNumber("+100.10"));
		assertTrue(NumberUtil.isNumber("-22.022"));
		assertTrue(NumberUtil.isNumber("0X22"));
	}

	@Test
	public void divTest() {
		final double result = NumberUtil.div(0, 1);
		assertEquals(0.0, result, 0);
	}

	@Test
	public void divBigDecimalTest() {
		final BigDecimal result = NumberUtil.div(BigDecimal.ZERO, BigDecimal.ONE);
		assertEquals(BigDecimal.ZERO, result.stripTrailingZeros());
	}

	@Test
	public void roundTest() {

		// 四舍
		final String round1 = NumberUtil.roundStr(2.674, 2);
		final String round2 = NumberUtil.roundStr("2.674", 2);
		assertEquals("2.67", round1);
		assertEquals("2.67", round2);

		// 五入
		final String round3 = NumberUtil.roundStr(2.675, 2);
		final String round4 = NumberUtil.roundStr("2.675", 2);
		assertEquals("2.68", round3);
		assertEquals("2.68", round4);

		// 四舍六入五成双
		final String round31 = NumberUtil.roundStr(4.245, 2, RoundingMode.HALF_EVEN);
		final String round41 = NumberUtil.roundStr("4.2451", 2, RoundingMode.HALF_EVEN);
		assertEquals("4.24", round31);
		assertEquals("4.25", round41);

		// 补0
		final String round5 = NumberUtil.roundStr(2.6005, 2);
		final String round6 = NumberUtil.roundStr("2.6005", 2);
		assertEquals("2.60", round5);
		assertEquals("2.60", round6);

		// 补0
		final String round7 = NumberUtil.roundStr(2.600, 2);
		final String round8 = NumberUtil.roundStr("2.600", 2);
		assertEquals("2.60", round7);
		assertEquals("2.60", round8);
	}

	@Test
	public void roundStrTest() {
		final String roundStr = NumberUtil.roundStr(2.647, 2);
		assertEquals(roundStr, "2.65");

		final String roundStr1 = NumberUtil.roundStr(0, 10);
		assertEquals(roundStr1, "0.0000000000");
	}

	@Test
	public void roundHalfEvenTest() {
		String roundStr = NumberUtil.roundHalfEven(4.245, 2).toString();
		assertEquals(roundStr, "4.24");
		roundStr = NumberUtil.roundHalfEven(4.2450, 2).toString();
		assertEquals(roundStr, "4.24");
		roundStr = NumberUtil.roundHalfEven(4.2451, 2).toString();
		assertEquals(roundStr, "4.25");
		roundStr = NumberUtil.roundHalfEven(4.2250, 2).toString();
		assertEquals(roundStr, "4.22");

		roundStr = NumberUtil.roundHalfEven(1.2050, 2).toString();
		assertEquals(roundStr, "1.20");
		roundStr = NumberUtil.roundHalfEven(1.2150, 2).toString();
		assertEquals(roundStr, "1.22");
		roundStr = NumberUtil.roundHalfEven(1.2250, 2).toString();
		assertEquals(roundStr, "1.22");
		roundStr = NumberUtil.roundHalfEven(1.2350, 2).toString();
		assertEquals(roundStr, "1.24");
		roundStr = NumberUtil.roundHalfEven(1.2450, 2).toString();
		assertEquals(roundStr, "1.24");
		roundStr = NumberUtil.roundHalfEven(1.2550, 2).toString();
		assertEquals(roundStr, "1.26");
		roundStr = NumberUtil.roundHalfEven(1.2650, 2).toString();
		assertEquals(roundStr, "1.26");
		roundStr = NumberUtil.roundHalfEven(1.2750, 2).toString();
		assertEquals(roundStr, "1.28");
		roundStr = NumberUtil.roundHalfEven(1.2850, 2).toString();
		assertEquals(roundStr, "1.28");
		roundStr = NumberUtil.roundHalfEven(1.2950, 2).toString();
		assertEquals(roundStr, "1.30");
	}

	@Test
	public void decimalFormatTest() {
		final long c = 299792458;// 光速

		final String format = NumberUtil.decimalFormat(",###", c);
		assertEquals("299,792,458", format);
	}

	@Test
	public void decimalFormatNaNTest() {
		assertThrows(IllegalArgumentException.class, ()->{
			final Double a = 0D;
			final Double b = 0D;

			final Double c = a / b;
			Console.log(NumberUtil.decimalFormat("#%", c));
		});
	}

	@Test
	public void decimalFormatNaNTest2() {
		assertThrows(IllegalArgumentException.class, ()->{
			final Double a = 0D;
			final Double b = 0D;

			Console.log(NumberUtil.decimalFormat("#%", a / b));
		});
	}

	@Test
	public void decimalFormatDoubleTest() {
		final Double c = 467.8101;

		final String format = NumberUtil.decimalFormat("0.00", c);
		assertEquals("467.81", format);
	}

	@Test
	public void isValidNumberTest() {
		boolean validNumber = NumberUtil.isValidNumber(1);
        assertTrue(validNumber);
	}

	@Test
	public void decimalFormatMoneyTest() {
		final double c = 299792400.543534534;

		final String format = NumberUtil.decimalFormatMoney(c);
		assertEquals("299,792,400.54", format);

		final double value = 0.5;
		final String money = NumberUtil.decimalFormatMoney(value);
		assertEquals("0.50", money);
	}

	@Test
	public void equalsTest() {
		assertTrue(NumberUtil.equals(new BigDecimal("0.00"), BigDecimal.ZERO));
	}

	@Test
	public void toBigDecimalTest() {
		final double a = 3.14;

		BigDecimal bigDecimal = NumberUtil.toBigDecimal(a);
		assertEquals("3.14", bigDecimal.toString());

		bigDecimal = NumberUtil.toBigDecimal("1,234.55");
		assertEquals("1234.55", bigDecimal.toString());

		bigDecimal = NumberUtil.toBigDecimal("1,234.56D");
		assertEquals("1234.56", bigDecimal.toString());

		assertEquals(new BigDecimal("9.0E+7"), NumberUtil.toBigDecimal("9.0E+7"));
	}

	@Test
	public void maxTest() {
		final int max = NumberUtil.max(5,4,3,6,1);
		assertEquals(6, max);
	}

	@Test
	public void minTest() {
		final int min = NumberUtil.min(5,4,3,6,1);
		assertEquals(1, min);
	}

	@Test
	public void parseIntTest() {
		int number = NumberUtil.parseInt("0xFE");
		assertEquals(254, number);

		// 0开头
		number = NumberUtil.parseInt("010");
		assertEquals(10, number);

		number = NumberUtil.parseInt("10");
		assertEquals(10, number);

		number = NumberUtil.parseInt("   ");
		assertEquals(0, number);

		number = NumberUtil.parseInt("10F");
		assertEquals(10, number);

		number = NumberUtil.parseInt("22.4D");
		assertEquals(22, number);

		number = NumberUtil.parseInt("22.6D");
		assertEquals(22, number);

		number = NumberUtil.parseInt("0");
		assertEquals(0, number);

		number = NumberUtil.parseInt(".123");
		assertEquals(0, number);
	}

	@Test
	public void parseIntTest2() {
		// from 5.4.8 issue#I23ORQ@Gitee
		// 千位分隔符去掉
		final int v1 = NumberUtil.parseInt("1,482.00");
		assertEquals(1482, v1);
	}

	@Test
	public void parseIntTest3() {
		assertThrows(NumberFormatException.class, ()->{
			final int v1 = NumberUtil.parseInt("d");
			assertEquals(0, v1);
		});
	}

	@Test
	public void parseIntTest4() {

		// -------------------------- Parse failed -----------------------

		Assertions.assertNull(NumberUtil.parseInt("abc", null));

		assertEquals(456, NumberUtil.parseInt("abc", 456));

		// -------------------------- Parse success -----------------------

		assertEquals(123, NumberUtil.parseInt("123.abc", 789));

		assertEquals(123, NumberUtil.parseInt("123.3", null));

	}

	@Test
	public void parseNumberTest4(){
		assertThrows(NumberFormatException.class, () -> {
			// issue#I5M55F
			// 科学计数法忽略支持，科学计数法一般用于表示非常小和非常大的数字，这类数字转换为int后精度丢失，没有意义。
			final String numberStr = "429900013E20220812163344551";
			NumberUtil.parseInt(numberStr);
		});
	}

	@Test
	public void parseNumberTest() {
		// from 5.4.8 issue#I23ORQ@Gitee
		// 千位分隔符去掉
		final int v1 = NumberUtil.parseNumber("1,482.00").intValue();
		assertEquals(1482, v1);

		final Number v2 = NumberUtil.parseNumber("1,482.00D");
		assertEquals(1482L, v2.longValue());
	}

	@Test
	public void parseNumberTest2(){
		// issue#I5M55F
		final String numberStr = "429900013E20220812163344551";
		final Number number = NumberUtil.parseNumber(numberStr);
		assertNotNull(number);
		assertInstanceOf(BigDecimal.class, number);
	}

	@Test
	public void parseNumberTest3(){

		// -------------------------- Parse failed -----------------------

		assertNull(NumberUtil.parseNumber("abc", (Number) null));

		assertNull(NumberUtil.parseNumber(StrUtil.EMPTY, (Number) null));

		assertNull(NumberUtil.parseNumber(StrUtil.repeat(StrUtil.SPACE, 10), (Number) null));

		assertEquals(456, NumberUtil.parseNumber("abc", 456).intValue());

		// -------------------------- Parse success -----------------------

		assertEquals(123, NumberUtil.parseNumber("123.abc", 789).intValue());

		assertEquals(123.3D, NumberUtil.parseNumber("123.3", (Number) null).doubleValue());

		assertEquals(0.123D, NumberUtil.parseNumber("0.123.3", (Number) null).doubleValue());

	}

	@Test
	public void parseHexNumberTest() {
		// 千位分隔符去掉
		final int v1 = NumberUtil.parseNumber("0xff").intValue();
		assertEquals(255, v1);
	}

	@Test
	public void parseLongTest() {
		long number = NumberUtil.parseLong("0xFF");
		assertEquals(255, number);

		// 0开头
		number = NumberUtil.parseLong("010");
		assertEquals(10, number);

		number = NumberUtil.parseLong("10");
		assertEquals(10, number);

		number = NumberUtil.parseLong("   ");
		assertEquals(0, number);

		number = NumberUtil.parseLong("10F");
		assertEquals(10, number);

		number = NumberUtil.parseLong("22.4D");
		assertEquals(22, number);

		number = NumberUtil.parseLong("22.6D");
		assertEquals(22, number);

		number = NumberUtil.parseLong("0");
		assertEquals(0, number);

		number = NumberUtil.parseLong(".123");
		assertEquals(0, number);
	}

	@Test
	public void parseLongTest2() {

		// -------------------------- Parse failed -----------------------

		final Long v1 = NumberUtil.parseLong(null, null);
		assertNull(v1);

		final Long v2 = NumberUtil.parseLong(StrUtil.EMPTY, null);
		assertNull(v2);

		final Long v3 = NumberUtil.parseLong("L3221", 1233L);
		assertEquals(1233L, v3);

		// -------------------------- Parse success -----------------------

		final Long v4 = NumberUtil.parseLong("1233L", null);
		assertEquals(1233L, v4);

	}

	@Test
	public void parseFloatTest() {

		// -------------------------- Parse failed -----------------------

		assertNull(NumberUtil.parseFloat("abc", null));

		assertNull(NumberUtil.parseFloat("a123.33", null));

		assertNull(NumberUtil.parseFloat("..123", null));

		assertEquals(1233F, NumberUtil.parseFloat(StrUtil.EMPTY, 1233F));

		// -------------------------- Parse success -----------------------

		assertEquals(123.33F, NumberUtil.parseFloat("123.33a", null));

		assertEquals(0.123F, NumberUtil.parseFloat(".123", null));

	}

	@Test
	public void parseDoubleTest() {

		// -------------------------- Parse failed -----------------------

		assertNull(NumberUtil.parseDouble("abc", null));
		assertNull(NumberUtil.parseDouble("a123.33", null));
		assertNull(NumberUtil.parseDouble("..123", null));
		assertEquals(1233D, NumberUtil.parseDouble(StrUtil.EMPTY, 1233D));

		// -------------------------- Parse success -----------------------

		assertEquals(123.33D, NumberUtil.parseDouble("123.33a", null));
		assertEquals(0.123D, NumberUtil.parseDouble(".123", null));
	}

	@Test
	public void factorialTest(){
		long factorial = NumberUtil.factorial(0);
		assertEquals(1, factorial);

		assertEquals(1L, NumberUtil.factorial(1));
		assertEquals(1307674368000L, NumberUtil.factorial(15));
		assertEquals(2432902008176640000L, NumberUtil.factorial(20));

		factorial = NumberUtil.factorial(5, 0);
		assertEquals(120, factorial);
		factorial = NumberUtil.factorial(5, 1);
		assertEquals(120, factorial);

		assertEquals(5, NumberUtil.factorial(5, 4));
		assertEquals(2432902008176640000L, NumberUtil.factorial(20, 0));
	}

	@Test
	public void factorialTest2(){
		long factorial = NumberUtil.factorial(new BigInteger("0")).longValue();
		assertEquals(1, factorial);

		assertEquals(1L, NumberUtil.factorial(new BigInteger("1")).longValue());
		assertEquals(1307674368000L, NumberUtil.factorial(new BigInteger("15")).longValue());
		assertEquals(2432902008176640000L, NumberUtil.factorial(20));

		factorial = NumberUtil.factorial(new BigInteger("5"), new BigInteger("0")).longValue();
		assertEquals(120, factorial);
		factorial = NumberUtil.factorial(new BigInteger("5"), BigInteger.ONE).longValue();
		assertEquals(120, factorial);

		assertEquals(5, NumberUtil.factorial(new BigInteger("5"), new BigInteger("4")).longValue());
		assertEquals(2432902008176640000L, NumberUtil.factorial(new BigInteger("20"), BigInteger.ZERO).longValue());
	}

	@Test
	public void mulTest(){
		final BigDecimal mul = NumberUtil.mul(new BigDecimal("10"), null);
		assertEquals(BigDecimal.ZERO, mul);
	}


	@Test
	public void isPowerOfTwoTest() {
		assertFalse(NumberUtil.isPowerOfTwo(-1));
		assertTrue(NumberUtil.isPowerOfTwo(16));
		assertTrue(NumberUtil.isPowerOfTwo(65536));
		assertTrue(NumberUtil.isPowerOfTwo(1));
		assertFalse(NumberUtil.isPowerOfTwo(17));
	}

	@Test
	public void generateRandomNumberTest(){
		final int[] ints = NumberUtil.generateRandomNumber(10, 20, 5);
		assertEquals(5, ints.length);
		final Set<?> set = Convert.convert(Set.class, ints);
		assertEquals(5, set.size());
	}

	@Test
	public void toStrTest(){
		assertEquals("1", NumberUtil.toStr(new BigDecimal("1.0000000000")));
		assertEquals("0", NumberUtil.toStr(NumberUtil.sub(new BigDecimal("9600.00000"), new BigDecimal("9600.00000"))));
		assertEquals("0", NumberUtil.toStr(NumberUtil.sub(new BigDecimal("9600.0000000000"), new BigDecimal("9600.000000"))));
		assertEquals("0", NumberUtil.toStr(new BigDecimal("9600.00000").subtract(new BigDecimal("9600.000000000"))));
	}

	@Test
	public void generateRandomNumberTest2(){
		// 检查边界
		final int[] ints = NumberUtil.generateRandomNumber(1, 8, 7);
		assertEquals(7, ints.length);
		final Set<?> set = Convert.convert(Set.class, ints);
		assertEquals(7, set.size());
	}

	@Test
	public void toPlainNumberTest(){
		final String num = "5344.34234e3";
		final String s = new BigDecimal(num).toPlainString();
		assertEquals("5344342.34", s);
	}

	@Test
	public void generateBySetTest(){
		final Integer[] integers = NumberUtil.generateBySet(10, 100, 5);
		assertEquals(5, integers.length);
	}

	@Test
	public void isOddOrEvenTest(){
		final int[] a = { 0, 32, -32, 123, -123 };
		assertFalse(NumberUtil.isOdd(a[0]));
		assertTrue(NumberUtil.isEven(a[0]));

		assertFalse(NumberUtil.isOdd(a[1]));
		assertTrue(NumberUtil.isEven(a[1]));

		assertFalse(NumberUtil.isOdd(a[2]));
		assertTrue(NumberUtil.isEven(a[2]));

		assertTrue(NumberUtil.isOdd(a[3]));
		assertFalse(NumberUtil.isEven(a[3]));

		assertTrue(NumberUtil.isOdd(a[4]));
		assertFalse(NumberUtil.isEven(a[4]));
	}

	@Test
	public void divIntegerTest(){
		assertEquals(1001013, NumberUtil.div(100101300, (Number) 100).intValue());
	}

	@Test
	public void isDoubleTest(){
		assertFalse(NumberUtil.isDouble(null));
		assertFalse(NumberUtil.isDouble(""));
		assertFalse(NumberUtil.isDouble("  "));
	}

	@Test
	public void range(){
		assertFalse(NumberUtil.isIn(new BigDecimal("1"),new BigDecimal("2"),new BigDecimal("12")));
		assertTrue(NumberUtil.isIn(new BigDecimal("1"),new BigDecimal("1"),new BigDecimal("2")));
		assertTrue(NumberUtil.isIn(new BigDecimal("1"),new BigDecimal("0"),new BigDecimal("2")));
		assertFalse(NumberUtil.isIn(new BigDecimal("0.23"),new BigDecimal("0.12"),new BigDecimal("0.22")));
		assertTrue(NumberUtil.isIn(new BigDecimal("-0.12"),new BigDecimal("-0.3"),new BigDecimal("0")));
	}

	@Test
	public void issueI79VS7Test() {
		final String value = "+0.003";
		if(NumberUtil.isNumber(value)) {
			assertEquals(0.003, NumberUtil.parseNumber(value).doubleValue(), 0);
		}
	}

	@Test
	public void issueI7R2B6Test() {
		assertEquals(61.67D,
			NumberUtil.div(NumberUtil.mul(15858155520D, 100), 25715638272D, 2), 0.01);

		assertEquals(61.67, NumberUtil.div(NumberUtil.mul(15858155520D, 100), 25715638272D, 2), 0.01);
	}

	@Test
	public void issueI7R2B6Test2() {
		final BigDecimal mul = NumberUtil.mul((Number) 15858155520D, 100.0);
		assertEquals("1585815552000", mul.toString());
	}

	@Test
	public void testPowZero() {
		BigDecimal number = new BigDecimal("2.5");
		int exponent = 0;
		BigDecimal expected = new BigDecimal("1");
		assertEquals(expected, NumberUtil.pow(number, exponent));
	}

	@Test
	public void testPowNegative() {
		BigDecimal number = new BigDecimal("2.5");
		int exponent = -2;
		BigDecimal expected = new BigDecimal("0.16");
		assertEquals(expected, NumberUtil.pow(number, exponent));
	}

	@Test
	public void testPowSmallNumber() {
		BigDecimal number = new BigDecimal("0.1");
		int exponent = -3;
		BigDecimal expected = new BigDecimal("1000.00");
		assertEquals(expected, NumberUtil.pow(number, exponent));
	}

	@Test
	public void issue3636Test() {
		final Number number = NumberUtil.parseNumber("12,234,456");
		assertEquals(new BigDecimal(12234456), number);
	}

	@Test
	public void addIntAndDoubleTest() {
		int v1 = 91007279;
		double v2 = 0.3545;
		final double result = NumberUtil.add(v1, v2);
		assertEquals(91007279.3545, result, 0);
	}
}
