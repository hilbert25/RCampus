package org.sunhp.rcampus.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sunhp.rcampus.bean.Judge;
import org.sunhp.rcampus.dao.JudgeDao;
import org.sunhp.rcampus.service.JudgeService;
import org.sunhp.rcampus.vo.ExamResult;

import java.util.List;

@Service("judgeService")
public class JudgeServiceImpl extends BaseServiceImpl<Judge>implements JudgeService {
	@Autowired
	public JudgeServiceImpl(JudgeDao judgeDao) {
		super.baseDao = judgeDao;
	}

	@Autowired
	JudgeDao judgeDao;
	public String test() {
		List<Judge> list = judgeDao.find(new Judge());
		return list.size()+"";
	}
	public ExamResult judegeInput(List<Judge> judgeList, String input){
		ExamResult examResult = new ExamResult();
		//初始化为正确
		examResult.setJudgeStatus(true);
		examResult.setJudgeMsg("correct!");
		for(Judge jud:judgeList){
			//去掉注释
			String[] sourceList = input.split("\n");
			StringBuffer sourceClean = new StringBuffer();
			for(String str:sourceList){
				if(!str.startsWith("#")){
					sourceClean.append(str);
				}
			}
			//目前只匹配用户输入与要求的是否一致
			if("equal".equals(jud.getJudgeType())){
				if(!sourceClean.toString().contains(jud.getJudgeItem())){
					//输入不符合课程要求
					examResult.setJudgeStatus(false);
					examResult.setJudgeMsg(jud.getJudgeTips());
					break;
				}
			}
//此处可拓展
			else{
				examResult.setJudgeStatus(false);
				examResult.setJudgeMsg("未定义的匹配方式");
			}
		}
		return examResult;
	}

}
