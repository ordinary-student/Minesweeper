package com.ui.frame;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import com.thread.PlaySoundThread;
import com.ui.button.MapButton;
import com.util.WindowUtil;

/**
 * 扫雷窗口类
 * 
 * @author ordinary-student
 *
 */
public class MinesweeperFrame extends KFrame
{
	private static final long serialVersionUID = -4566160191358577274L;

	// 行列数
	public static final int ROW = 15;
	public static final int COLUMN = 15;

	// 地雷数量
	private static int MINE_NUMBER = 30;

	// 周围的格子坐标
	public static final int AROUND_X[] = { -1, 0, 1, 0, 1, -1, -1, 1 };
	public static final int AROUND_Y[] = { 0, -1, 0, 1, -1, -1, 1, 1 };
	public static final int AROUND_NUMBER = 8;

	// 地图
	private MapButton mapButton[][] = new MapButton[ROW][COLUMN];

	// 组件
	private JButton newGameButton;
	private JButton customGameButton;
	private JButton mineNumberButton;
	public JButton timeButton;
	private JCheckBox soundCheckBox;

	// 当前地雷数量
	private int currentMineNumber = MINE_NUMBER;
	// 声音标志
	private boolean soundFlag = true;

	// 计时器
	private Timer timer;
	// 游戏时间
	public int gameTime = 0;

	/*
	 * 构造方法
	 */
	public MinesweeperFrame()
	{
		// 初始化界面
		initUI();
		// 新游戏
		newGame();
	}

	/*
	 * 初始化界面
	 */
	private void initUI()
	{
		// 设置标题
		setTitle("扫雷");
		// 设置大小
		setSize(650, 650);
		setMinimumSize(new Dimension(650, 650));
		// 设置居中
		WindowUtil.center(this);
		// 设置布局
		getContentPane().setLayout(new BorderLayout(10, 10));
		// 设置关闭方式
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		// 不可视
		setVisible(false);

		// 添加头部面板
		getContentPane().add(createHeadPanel(), BorderLayout.NORTH);
		// 添加地图容器
		getContentPane().add(createMapContainer(), BorderLayout.CENTER);

	}

	/**
	 * 创建头部面板
	 * 
	 * @return
	 */
	private JPanel createHeadPanel()
	{
		// 头部面板
		JPanel headPanel = new JPanel();

		// 新游戏按钮
		newGameButton = new JButton("新游戏");
		newGameButton.setFont(new Font("宋体", Font.CENTER_BASELINE, 18));
		newGameButton.setFocusPainted(false);
		newGameButton.addActionListener(this);
		headPanel.add(newGameButton);

		// 自定义游戏按钮
		customGameButton = new JButton("自定义");
		customGameButton.setFont(new Font("宋体", Font.CENTER_BASELINE, 18));
		customGameButton.setFocusPainted(false);
		customGameButton.addActionListener(this);
		headPanel.add(customGameButton);

		// 雷数显示按钮
		mineNumberButton = new JButton("雷数:");
		mineNumberButton.setFont(new Font("宋体", Font.CENTER_BASELINE, 18));
		mineNumberButton.setEnabled(false);
		headPanel.add(mineNumberButton);

		// 时间显示按钮
		timeButton = new JButton("时间:0");
		timeButton.setFont(new Font("宋体", Font.CENTER_BASELINE, 18));
		timeButton.setEnabled(false);
		headPanel.add(timeButton);

		// 静音勾选框
		soundCheckBox = new JCheckBox("静音");
		soundCheckBox.setFont(new Font("宋体", Font.CENTER_BASELINE, 18));
		soundCheckBox.setBorderPainted(false);
		soundCheckBox.setFocusPainted(false);
		soundCheckBox.setContentAreaFilled(false);
		soundCheckBox.addActionListener(this);
		headPanel.add(soundCheckBox);

		// 返回
		return headPanel;
	}

	/**
	 * 创建地图容器
	 * 
	 * @return
	 */
	private Container createMapContainer()
	{
		// 创建容器
		Container container = new Container();
		// 设置网格布局
		container.setLayout(new GridLayout(ROW, COLUMN, 0, 0));
		// 添加按钮
		for (int i = 0; i < ROW; i++)
		{
			for (int j = 0; j < COLUMN; j++)
			{
				MapButton button = new MapButton();
				button.addMouseListener(this);
				mapButton[i][j] = button;
				container.add(button);
			}
		}

		// 返回
		return container;
	}

	/**
	 * 创建地雷
	 */
	private void createMines()
	{
		currentMineNumber = MINE_NUMBER;
		// 显示地雷数量
		mineNumberButton.setText("雷数:" + currentMineNumber);

		// 随机种子
		Random rand = new Random();

		// 遍历地图
		for (int i = 0; i < MINE_NUMBER; i++)
		{
			// 随机行
			int x = rand.nextInt(ROW);
			// 随机列
			int y = rand.nextInt(COLUMN);

			// 判断该位置
			if (mapButton[x][y].isMine())
			{
				// 若为地雷，重新选择位置
				i--;

			} else
			{
				// 若不是地雷，设为地雷
				mapButton[x][y].setMine(true);
				// 周围的格子数字加一
				for (int k = 0; k < AROUND_NUMBER; k++)
				{
					int ax = x + AROUND_X[k];
					int ay = y + AROUND_Y[k];
					if ((ax >= 0) && (ax < ROW) && (ay >= 0) && (ay < COLUMN) && (!mapButton[ax][ay].isMine()))
					{
						int number = mapButton[ax][ay].getNumber();
						number++;
						mapButton[ax][ay].setNumber(number);
					}
				}
			}
		}
	}

	/**
	 * 创建计时任务
	 */
	private void createRecordTimeTask()
	{
		// 计时
		timer = new Timer(1000, new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				gameTime++;
				timeButton.setText("时间:" + gameTime + "s");
			}
		});

		// 重复执行
		timer.setRepeats(true);
		// 启动计时器
		timer.start();
	}

	/**
	 * 打开指定位置的按钮
	 * 
	 * @param x
	 * @param y
	 */
	private void openButton(int x, int y)
	{
		// 设为打开状态
		mapButton[x][y].setState(MapButton.OPENED);

		// 判断数字是否为0
		if (mapButton[x][y].getNumber() == 0)
		{
			// 打开连着的数字为0的格子
			for (int i = 0; i < AROUND_NUMBER / 2; i++)
			{
				int gx = x + AROUND_X[i];
				int gy = y + AROUND_Y[i];

				if ((gx >= 0) && (gy >= 0) && (gx < ROW) && (gy < COLUMN) && (mapButton[gx][gy].isEnabled() == true)
						&& (!mapButton[gx][gy].isMine()))
				{
					// 是0就打开
					if (mapButton[gx][gy].getNumber() == 0)
					{
						openButton(gx, gy);
					}
				}
			}
		} else
		{
			return;
		}

	}

	/**
	 * 打开地图按钮
	 */
	private void openButton(MapButton button)
	{
		// 获取被点击按钮的位置
		int bx = 0, by = 0;
		start: for (int i = 0; i < ROW; i++)
		{
			for (int j = 0; j < COLUMN; j++)
			{
				if (button.equals(mapButton[i][j]))
				{
					bx = i;
					by = j;
					break start;
				}
			}
		}

		// 不为地雷则打开
		openButton(bx, by);
		// 检查游戏是否胜利
		checkWin();
	}

	/**
	 * 新游戏
	 */
	private void newGame()
	{
		// 停止计时
		if (timer != null)
		{
			timer.stop();
		}

		// 清零时间
		gameTime = 0;
		timeButton.setText("时间:" + gameTime + "s");

		// 遍历复位按钮
		for (int i = 0; i < ROW; i++)
		{
			for (int j = 0; j < COLUMN; j++)
			{
				mapButton[i][j].reset();
			}
		}

		// 添加地雷
		createMines();
		// 计时
		createRecordTimeTask();
	}

	/**
	 * 检查游戏是否胜利
	 */
	private void checkWin()
	{
		int count = 0;
		// 遍历
		for (int i = 0; i < ROW; i++)
		{
			for (int j = 0; j < COLUMN; j++)
			{
				// 可用
				if (mapButton[i][j].isEnabled() == true)
				{
					// 不是地雷
					if (!mapButton[i][j].isMine())
					{
						count++;
					}
				}
			}
		}

		// 胜利
		if (count == 0)
		{
			winGame();
		} else
		{
		}

	}

	/**
	 * 游戏失败
	 */
	private void loseGame()
	{
		// 停止计时
		timer.stop();

		// 播放失败音效
		if (soundFlag)
		{
			new PlaySoundThread("lose.wav").start();
		}

		// 显示所有
		ShowAll();
		JOptionPane.showMessageDialog(this, "You lose!!!");
	}

	/**
	 * 游戏胜利
	 */
	private void winGame()
	{
		// 停止计时
		timer.stop();

		// 播放胜利音效
		if (soundFlag)
		{
			new PlaySoundThread("win.wav").start();
		}

		// 显示所有
		ShowAll();
		JOptionPane.showMessageDialog(this, "You win!!!\r\n用时" + gameTime + "秒");
	}

	/**
	 * 显示全部
	 */
	private void ShowAll()
	{
		for (int i = 0; i < ROW; i++)
		{
			for (int j = 0; j < COLUMN; j++)
			{
				// 设为打开状态
				mapButton[i][j].setState(MapButton.OPENED);
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent ae)
	{
		// 判断来源
		if (ae.getSource() == soundCheckBox)
		{
			soundFlag = !soundCheckBox.isSelected();

		} else if (ae.getSource() == newGameButton)
		{
			// 新游戏
			newGame();

		} else if (ae.getSource() == customGameButton)
		{
			try
			{
				// 自定义
				String bombStr = JOptionPane.showInputDialog(this, "设置地雷数量（1~100）");
				// 地雷数量
				int num = Integer.parseInt(bombStr.trim());
				if ((num >= 1) && (num <= 100))
				{
					MINE_NUMBER = num;
				} else
				{
					JOptionPane.showMessageDialog(this, "地雷数量设置不合理！");
					return;
				}

				// 新游戏
				newGame();
			} catch (Exception e)
			{
				JOptionPane.showMessageDialog(this, "地雷数量设置不合理！");
				return;
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent me)
	{
		// 获取源按钮
		MapButton button = (MapButton) me.getSource();

		// 判断是否可用
		if (button.isEnabled() == false)
		{
			return;
		} else
		{
			// 获取鼠标按键
			int mouseButton = me.getButton();

			// 判断鼠标左右键
			if (mouseButton == MouseEvent.BUTTON3)
			{
				// 鼠标右键
				if (button.getState() == MapButton.MARKED)
				{
					// 若被标记，设置为正常
					button.setState(MapButton.NORMAL);
					currentMineNumber++;

				} else if (button.getState() == MapButton.NORMAL)
				{
					// 若为正常，则标记该格子
					button.setState(MapButton.MARKED);
					currentMineNumber--;

				} else
				{
				}

				// 显示剩余雷数
				mineNumberButton.setText("雷数:" + currentMineNumber);

			} else
			{
				// 鼠标左键
				if (button.getState() == MapButton.NORMAL)
				{
					// 点击正常的格子才有响应
					button.setState(MapButton.OPENED);
					// 如果是地雷
					if (button.isMine())
					{
						loseGame();
					} else
					{
						// 不为地雷则打开
						openButton(button);
					}
				} else
				{
					// 点击被标记的格子或者已经打开的格子，则不作任何响应
				}
			}
		}
	}
}
