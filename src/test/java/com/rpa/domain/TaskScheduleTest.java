package com.rpa.domain;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

public class TaskScheduleTest {

	@Test
	public void 업무스케쥴확인검사() {
//		검사일 2021년 05월 06일 기준  
//		참이될 조건 -> month는 항상 O여야 하고, week나 day중 하나는 꼭 O여야 함
//		Test1 month O, week O, day O
		String month = "*";
		String week = "*";
		String day = "*";
		TaskSchedule s1 = TaskSchedule.getSchedule(month, week, day);
		assertEquals(s1.checkTaskSchedule(), true);
//		Test2 month O, week O, day O
		month = "1,2,5,8";
		s1.setMonth(month);
		assertEquals(s1.checkTaskSchedule(), true);
//		Test3 month O, week O, day O
		month = "5";
		s1.setMonth(month);
		assertEquals(s1.checkTaskSchedule(), true);
//		Test4 month x, week O, day O
		month = "6-12";
		s1.setMonth(month);
		assertEquals(s1.checkTaskSchedule(), false);
//		Test5 month O, week O, day O
		month = "3-12";
		s1.setMonth(month);
		assertEquals(s1.checkTaskSchedule(), true);
//		Test6 month O, week x, day x
		week = "1,3";
		s1.setWeek(week);
		day = "1-3";
		s1.setDay(day);
		assertEquals(s1.checkTaskSchedule(), false);
//		Test7 month O, week O, day O
		week = "4,7";
		s1.setWeek(week);
		day = "1-3";
		s1.setDay(day);
		assertEquals(s1.checkTaskSchedule(), true);
//		Test8 month O, week x, day O
		week = "1,3";
		s1.setWeek(week);
		day = "2-5,6";
		s1.setDay(day);
		assertEquals(s1.checkTaskSchedule(), true);
//		Test9 month O, week x, day x
		week = "3,6";
		s1.setWeek(week);
		day = "1-3";
		s1.setDay(day);
		assertEquals(s1.checkTaskSchedule(), false);
//		Test10 month O, week O, day x
		week = "3-6";
		s1.setWeek(week);
		day = "1-3";
		s1.setDay(day);
		assertEquals(s1.checkTaskSchedule(), true);
//		Test11 month O, week x, day O
		week = "3,6";
		s1.setWeek(week);
		day = " 1-3, 5-7, 12-20 ";
		s1.setDay(day);
		assertEquals(s1.checkTaskSchedule(), true);
		
	}

}
