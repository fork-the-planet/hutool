package cn.hutool.json;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.convert.ConvertException;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.lang.TypeReference;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.json.test.bean.Exam;
import cn.hutool.json.test.bean.JsonNode;
import cn.hutool.json.test.bean.KeyBean;
import lombok.Data;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JSONArray单元测试
 *
 * @author Looly
 */
public class JSONArrayTest {

	@Test()
	public void createJSONArrayFromJSONObjectTest() {
		// JSONObject实现了Iterable接口，可以转换为JSONArray
		final JSONObject jsonObject = new JSONObject();

		JSONArray jsonArray = new JSONArray(jsonObject, JSONConfig.create());
		assertEquals(new JSONArray(), jsonArray);

		jsonObject.set("key1", "value1");
		jsonArray = new JSONArray(jsonObject, JSONConfig.create());
		assertEquals(1, jsonArray.size());
		assertEquals("[{\"key1\":\"value1\"}]", jsonArray.toString());
	}

	@Test
	public void addNullTest() {
		final List<String> aaa = ListUtil.of("aaa", null);
		String jsonStr = JSONUtil.toJsonStr(JSONUtil.parse(aaa,
			JSONConfig.create().setIgnoreNullValue(false)));
		assertEquals("[\"aaa\",null]", jsonStr);
	}

	@Test
	public void addTest() {
		// 方法1
		JSONArray array = JSONUtil.createArray();
		// 方法2
		// JSONArray array = new JSONArray();
		array.add("value1");
		array.add("value2");
		array.add("value3");

		assertEquals(array.get(0), "value1");
	}

	@Test
	public void parseTest() {
		String jsonStr = "[\"value1\", \"value2\", \"value3\"]";
		JSONArray array = JSONUtil.parseArray(jsonStr);
		assertEquals(array.get(0), "value1");
	}

	@Test
	public void parseWithNullTest() {
		String jsonStr = "[{\"grep\":\"4.8\",\"result\":\"右\"},{\"grep\":\"4.8\",\"result\":null}]";
		JSONArray jsonArray = JSONUtil.parseArray(jsonStr);
		assertFalse(jsonArray.getJSONObject(1).containsKey("result"));

		// 不忽略null，则null的键值对被保留
		jsonArray = new JSONArray(jsonStr, false);
		assertTrue(jsonArray.getJSONObject(1).containsKey("result"));
	}

	@Test
	public void parseFileTest() {
		JSONArray array = JSONUtil.readJSONArray(FileUtil.file("exam_test.json"), CharsetUtil.CHARSET_UTF_8);

		JSONObject obj0 = array.getJSONObject(0);
		Exam exam = JSONUtil.toBean(obj0, Exam.class);
		assertEquals("0", exam.getAnswerArray()[0].getSeq());
	}

	@Test
	public void parseBeanListTest() {
		KeyBean b1 = new KeyBean();
		b1.setAkey("aValue1");
		b1.setBkey("bValue1");
		KeyBean b2 = new KeyBean();
		b2.setAkey("aValue2");
		b2.setBkey("bValue2");

		ArrayList<KeyBean> list = CollUtil.newArrayList(b1, b2);

		JSONArray jsonArray = JSONUtil.parseArray(list);
		assertEquals("aValue1", jsonArray.getJSONObject(0).getStr("akey"));
		assertEquals("bValue2", jsonArray.getJSONObject(1).getStr("bkey"));
	}

	@Test
	public void toListTest() {
		String jsonStr = FileUtil.readString("exam_test.json", CharsetUtil.CHARSET_UTF_8);
		JSONArray array = JSONUtil.parseArray(jsonStr);

		List<Exam> list = array.toList(Exam.class);
		assertFalse(list.isEmpty());
		assertSame(Exam.class, list.get(0).getClass());
	}

	@Test
	public void toListTest2() {
		String jsonArr = "[{\"id\":111,\"name\":\"test1\"},{\"id\":112,\"name\":\"test2\"}]";

		JSONArray array = JSONUtil.parseArray(jsonArr);
		List<User> userList = JSONUtil.toList(array, User.class);

		assertFalse(userList.isEmpty());
		assertSame(User.class, userList.get(0).getClass());

		assertEquals(Integer.valueOf(111), userList.get(0).getId());
		assertEquals(Integer.valueOf(112), userList.get(1).getId());

		assertEquals("test1", userList.get(0).getName());
		assertEquals("test2", userList.get(1).getName());
	}

	@Test
	public void toDictListTest() {
		String jsonArr = "[{\"id\":111,\"name\":\"test1\"},{\"id\":112,\"name\":\"test2\"}]";

		JSONArray array = JSONUtil.parseArray(jsonArr, JSONConfig.create().setIgnoreError(false));

		List<Dict> list = JSONUtil.toList(array, Dict.class);

		assertFalse(list.isEmpty());
		assertSame(Dict.class, list.get(0).getClass());

		assertEquals(Integer.valueOf(111), list.get(0).getInt("id"));
		assertEquals(Integer.valueOf(112), list.get(1).getInt("id"));

		assertEquals("test1", list.get(0).getStr("name"));
		assertEquals("test2", list.get(1).getStr("name"));
	}

	@Test
	public void toArrayTest() {
		String jsonStr = FileUtil.readString("exam_test.json", CharsetUtil.CHARSET_UTF_8);
		JSONArray array = JSONUtil.parseArray(jsonStr);

		//noinspection SuspiciousToArrayCall
		Exam[] list = array.toArray(new Exam[0]);
		assertNotEquals(0, list.length);
		assertSame(Exam.class, list[0].getClass());
	}

	/**
	 * 单元测试用于测试在列表元素中有null时的情况下是否出错
	 */
	@Test
	public void toListWithNullTest() {
		String json = "[null,{'akey':'avalue','bkey':'bvalue'}]";
		JSONArray ja = JSONUtil.parseArray(json, JSONConfig.create().setIgnoreNullValue(false));

		List<KeyBean> list = ja.toList(KeyBean.class);
		assertNull(list.get(0));
		assertEquals("avalue", list.get(1).getAkey());
		assertEquals("bvalue", list.get(1).getBkey());
	}

	@Test
	public void toListWithErrorTest() {
		assertThrows(ConvertException.class, () -> {
			String json = "[['aaa',{'akey':'avalue','bkey':'bvalue'}]]";
			JSONArray ja = JSONUtil.parseArray(json);
			ja.toBean(new TypeReference<List<List<KeyBean>>>() {
			});
		});
	}

	@Test
	public void toBeanListTest() {
		List<Map<String, String>> mapList = new ArrayList<>();
		mapList.add(buildMap("0", "0", "0"));
		mapList.add(buildMap("1", "1", "1"));
		mapList.add(buildMap("+0", "+0", "+0"));
		mapList.add(buildMap("-0", "-0", "-0"));
		JSONArray jsonArray = JSONUtil.parseArray(mapList);
		List<JsonNode> nodeList = jsonArray.toList(JsonNode.class);

		assertEquals(Long.valueOf(0L), nodeList.get(0).getId());
		assertEquals(Long.valueOf(1L), nodeList.get(1).getId());
		assertEquals(Long.valueOf(0L), nodeList.get(2).getId());
		assertEquals(Long.valueOf(0L), nodeList.get(3).getId());

		assertEquals(Integer.valueOf(0), nodeList.get(0).getParentId());
		assertEquals(Integer.valueOf(1), nodeList.get(1).getParentId());
		assertEquals(Integer.valueOf(0), nodeList.get(2).getParentId());
		assertEquals(Integer.valueOf(0), nodeList.get(3).getParentId());

		assertEquals("0", nodeList.get(0).getName());
		assertEquals("1", nodeList.get(1).getName());
		assertEquals("+0", nodeList.get(2).getName());
		assertEquals("-0", nodeList.get(3).getName());
	}

	@Test
	public void getByPathTest() {
		String jsonStr = "[{\"id\": \"1\",\"name\": \"a\"},{\"id\": \"2\",\"name\": \"b\"}]";
		final JSONArray jsonArray = JSONUtil.parseArray(jsonStr);
		assertEquals("b", jsonArray.getByPath("[1].name"));
		assertEquals("b", JSONUtil.getByPath(jsonArray, "[1].name"));
	}

	@Test
	public void putToIndexTest() {
		JSONArray jsonArray = new JSONArray(JSONConfig.create().setIgnoreNullValue(false));
		jsonArray.put(3, "test");
		// 第三个位置插入值，0~2都是null
		assertEquals(4, jsonArray.size());

		jsonArray = new JSONArray(JSONConfig.create().setIgnoreNullValue(true));
		jsonArray.put(3, "test");
		// 第三个位置插入值，忽略null，则追加
		assertEquals(1, jsonArray.size());
	}

	// https://github.com/dromara/hutool/issues/1858
	@Test
	public void putTest2() {
		final JSONArray jsonArray = new JSONArray();
		jsonArray.put(0, 1);
		assertEquals(1, jsonArray.size());
		assertEquals(1, jsonArray.get(0));
	}

	private static Map<String, String> buildMap(String id, String parentId, String name) {
		Map<String, String> map = new HashMap<>();
		map.put("id", id);
		map.put("parentId", parentId);
		map.put("name", name);
		return map;
	}

	@Data
	static class User {
		private Integer id;
		private String name;
	}

	@Test
	public void filterIncludeTest() {
		JSONArray json1 = JSONUtil.createArray()
			.set("value1")
			.set("value2")
			.set("value3")
			.set(true);

		final String s = json1.toJSONString(0, (pair) -> pair.getValue().equals("value2"));
		assertEquals("[\"value2\"]", s);
	}

	@Test
	public void filterExcludeTest() {
		JSONArray json1 = JSONUtil.createArray()
			.set("value1")
			.set("value2")
			.set("value3")
			.set(true);

		final String s = json1.toJSONString(0, (pair) -> false == pair.getValue().equals("value2"));
		assertEquals("[\"value1\",\"value3\",true]", s);
	}

	@Test
	public void putNullTest() {
		final JSONArray array = JSONUtil.createArray(JSONConfig.create().setIgnoreNullValue(false));
		array.set(null);

		assertEquals("[null]", array.toString());
	}

	@Test
	public void parseFilterTest() {
		String jsonArr = "[{\"id\":111,\"name\":\"test1\"},{\"id\":112,\"name\":\"test2\"}]";
		//noinspection MismatchedQueryAndUpdateOfCollection
		final JSONArray array = new JSONArray(jsonArr, null, (mutable) -> mutable.get().toString().contains("111"));
		assertEquals(1, array.size());
		assertTrue(array.getJSONObject(0).containsKey("id"));
	}

	@Test
	public void parseFilterEditTest() {
		String jsonArr = "[{\"id\":111,\"name\":\"test1\"},{\"id\":112,\"name\":\"test2\"}]";
		//noinspection MismatchedQueryAndUpdateOfCollection
		final JSONArray array = new JSONArray(jsonArr, null, (mutable) -> {
			final JSONObject o = new JSONObject(mutable.get());
			if ("111".equals(o.getStr("id"))) {
				o.set("name", "test1_edit");
			}
			mutable.set(o);
			return true;
		});
		assertEquals(2, array.size());
		assertTrue(array.getJSONObject(0).containsKey("id"));
		assertEquals("test1_edit", array.getJSONObject(0).get("name"));
	}
}
