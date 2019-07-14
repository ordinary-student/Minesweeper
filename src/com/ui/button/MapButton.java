package com.ui.button;

import java.awt.Color;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 * 地图按钮类
 * 
 * @author ordinary-student
 *
 */
public class MapButton extends JButton
{
	private static final long serialVersionUID = -6392631691088032151L;

	// 被标记
	public static final int MARKED = -1;
	// 正常状态
	public static final int NORMAL = 0;
	// 被打开
	public static final int OPENED = 1;

	// 普通颜色
	public static final Color NORMAL_COLOR = new Color(230, 230, 230);
	// 地雷颜色
	public static final Color MINE_COLOR = new Color(139, 26, 26);
	// 标记颜色
	public static final Color MARKED_COLOR = Color.black;
	// 点开后的颜色
	public static final Color OPENDED_COLOR = Color.white;

	// 状态
	private int state;
	// 数字
	private int number;
	// 是否为地雷
	private boolean mine;

	/*
	 * 构造方法
	 */
	public MapButton()
	{
		this(0, false, NORMAL);
	}

	public MapButton(int number, boolean mine, int state)
	{
		super();
		setNumber(number);
		setMine(mine);
		setState(state);
	}

	/**
	 * 复位按钮
	 */
	public void reset()
	{
		setNumber(0);
		showNumber();
		setMine(false);
		setState(NORMAL);
	}

	/**
	 * @return state
	 */
	public int getState()
	{
		return state;
	}

	/**
	 * @param state
	 *            要设置的 state
	 */
	public void setState(int state)
	{
		this.state = state;
		showState(state);
	}

	/**
	 * 显示状态
	 * 
	 * @param state
	 */
	public void showState(int state)
	{
		// 判断
		switch (state)
			{
			case NORMAL:
				{
					this.setIcon(null);
					this.setBackground(NORMAL_COLOR);
					this.setEnabled(true);
				}
				break;
			case MARKED:
				{
					this.setBackground(MARKED_COLOR);
					this.setEnabled(true);
				}
				break;
			case OPENED:
				{
					if (mine)
					{
						this.setIcon(new ImageIcon("res/bomb.png"));
						this.setBackground(MINE_COLOR);
						this.setEnabled(false);
					} else
					{
						this.setForeground(Color.black);
						this.setFont(new Font("宋体", Font.BOLD, 14));
						showNumber();
						this.setEnabled(false);
					}
				}
				break;
			default:
				break;
			}
	}

	/**
	 * @return number
	 */
	public int getNumber()
	{
		return number;
	}

	/**
	 * @param number
	 *            要设置的 number
	 */
	public void setNumber(int number)
	{
		this.number = number;
	}

	/**
	 * 显示数字
	 * 
	 * @param number
	 */
	public void showNumber()
	{
		if (number >= 0)
		{
			if (number == 0)
			{
				this.setText("");
				this.setBackground(OPENDED_COLOR);
			} else
			{
				if (number == 1)
				{
					this.setBackground(new Color(255, 245, 230));
				} else if (number == 2)
				{
					this.setBackground(new Color(255, 228, 181));
				} else if (number == 3)
				{
					this.setBackground(new Color(255, 165, 0));
				} else if (number == 4)
				{
					this.setBackground(new Color(255, 105, 30));
				} else if (number >= 5)
				{
					this.setBackground(new Color(255, 48, 48));
				}

				this.setText(number + "");
			}
		}
	}

	/**
	 * @return mine
	 */
	public boolean isMine()
	{
		return mine;
	}

	/**
	 * @param mine
	 *            要设置的 mine
	 */
	public void setMine(boolean mine)
	{
		this.mine = mine;
		if (mine)
		{
			this.number = -1;
		}
	}
}
