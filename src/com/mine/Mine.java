package com.mine;

import java.awt.Color;

/**
 * 地雷类
 * 
 * @author ordinary-student
 *
 */
public class Mine
{
	// 地雷
	public static final int MINE = -2;
	// 被标记
	public static final int MARKED = -1;
	// 正常状态
	public static final int NORMAL = 0;
	// 被打开
	public static final int OPENED = 1;

	// 普通颜色
	public static final Color NORMAL_COLOR = new Color(245, 245, 245);
	// 地雷颜色
	public static final Color MINE_COLOR = Color.red;
	// 标记颜色
	public static final Color MARKED_COLOR = Color.black;
	// 点开后的颜色
	public static final Color OPENDED_COLOR = Color.white;
}
