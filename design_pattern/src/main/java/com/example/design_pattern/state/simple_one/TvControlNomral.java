package com.example.design_pattern.state.simple_one;
/**
 * �ٿص�����
 * @author Administrator
 *
 */
public class TvControlNomral {
	//����״̬
	private  final static int POWER_ON=1;
	//�ػ�״̬
	private final static int POWER_OFF=2;
	
	public int mState=POWER_OFF;
	/**
	 * ����
	 */
	public void powerOn()
	{
		mState=POWER_ON;
		if(mState==POWER_OFF)
		{
			System.out.println("�����ˣ�����Կ�������");
		}else {
			System.out.println("�Ѿ���������Ч");
		}
	}
	
	/**
	 * �ػ�
	 */
	public void powerOff()
	{
		mState=POWER_OFF;
		if(mState==POWER_ON)
		{
			System.out.println("�������Ϲػ�");
			
		}else {
			System.out.println("�Ѿ��ػ�  ��Ч");
		}
	}
	/**
	 * �л�Ƶ��
	 */
	public void nextChannel()
	{
		if(mState==POWER_ON)
		{
			System.out.println("�л���һƵ��");
			
		}
		
	}
	
	public void preChannel()
	{
		if(mState==POWER_ON)
		{
			System.out.println("�л���һƵ��");
		}
	}
}
