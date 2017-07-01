package org.sunhp.rcampus.service;

import org.sunhp.rcampus.bean.Judge;
import org.sunhp.rcampus.vo.ExamResult;

import java.util.List;

public interface JudgeService extends BaseService<Judge> {
	String test();
	ExamResult judegeInput(List<Judge> judgeList,String input);
}
