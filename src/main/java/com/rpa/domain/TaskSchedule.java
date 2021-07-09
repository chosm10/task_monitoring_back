package com.rpa.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.regex.Pattern;

import javax.persistence.Embeddable;

import lombok.Data;
@Data
@Embeddable
public class TaskSchedule implements Serializable {
	// *이면 항상 실행         * 숫자  ,  - 로만 구성되야함     ex) 2,3,4  10-13 -> 2,3,4,10,11,12,13 실행
	// 실행될 월   1~12
	private String month;

	// 실행될 요일    월-일 -> 1-7
	private String week;

	// 실행될 날짜    1~31
	private String day;

	private TaskSchedule() {}

	private TaskSchedule(String month, String week, String day) {
		this.month = month;
		this.week = week;
		this.day = day;
	}

	public static TaskSchedule getSchedule(String month, String week, String day) {
		return new TaskSchedule(month, week, day);
	}

	@Override
	public boolean equals(Object o) {
		if(o.equals(this)) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return this.hashCode();
	}

	@Override
	public String toString() {
		return "TaskSchedule [month=" + month + ", week=" + week + ", day=" + day + "]";
	}

	// 오늘이 해당 task가 실행되어야 하는 날인지 반환
	public boolean checkTaskSchedule() {

		LocalDateTime today = LocalDateTime.now();
		int todayMonth = today.getMonthValue();
		int todayWeek = today.getDayOfWeek().getValue();
		int todayDay = today.getDayOfMonth();

		month = month.replaceAll(" ", "");
		week = week.replaceAll(" ", "");
		day = day.replaceAll(" ", "");
		
		Pattern pattern = Pattern.compile("[a-zA-Z!@#$%^&()=_+.?\":{}|<>]");
		if(pattern.matcher(month).find() || pattern.matcher(week).find() || pattern.matcher(day).find()) {
			throw new IllegalStateException("shedule에 '-.'외에 다른 문자가 삽입되어 처리하지 못합니다.");
		}

		String[] months = month.split(",");
		String[] weeks = week.split(",");
		String[] days = day.split(",");

		boolean flag = false;
		flag = isRange(months, todayMonth);
		if(flag) {
			if(isRange(weeks, todayWeek) || isRange(days, todayDay)) {
				return true;
			}
		}

		return false;
	}

	private boolean isRange(String[] date, int todayValue) {
		for(String d: date) {
			if(d.equals("*")) {
				return true;
			}
			else if(d.contains("-")) {
				String[] range = d.split("-");
				if(todayValue >= Integer.parseInt(range[0]) && 
						todayValue <= Integer.parseInt(range[1])) {
					return true;
				}
			} else if(!d.equals("")) {
				if(Integer.parseInt(d) == todayValue) {
					return true;
				}
			}
		}
		
		return false;
	}
}